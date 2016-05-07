/*
 * Copyright (C) 2013 Manuel Peinado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.xwan.View.GlassAction;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.xwan.Util.LogUtil;
import com.example.xwan.Util.ViewUtil;
import com.example.xwan.View.GlassAction.ListViewScrollObserver.OnListViewScrollListener;
import com.example.xwan.View.GlassAction.NotifyingScrollView.OnScrollChangedListener;

public class GlassActionBarHelper implements OnGlobalLayoutListener,
		OnScrollChangedListener, BlurTask.Listener, OnListViewScrollListener {
	private View content;
	private ListAdapter adapter;
	private ImageView blurredOverlay;
	private int actionBarHeight;
	private int width;
	private int height;
	private Bitmap scaled;
	private int blurRadius = GlassActionBar.DEFAULT_BLUR_RADIUS;
	private BlurTask blurTask;
	private int lastScrollPosition = -1;
	private NotifyingScrollView scrollView;
	private ListView listView;
	private int downSampling = GlassActionBar.DEFAULT_DOWNSAMPLING;
	private static final String TAG = "GlassActionBarHelper";
	private boolean verbose = GlassActionBar.verbose;
	private Drawable windowBackground;
	private Context mContext;

	public GlassActionBarHelper contentLayout(Context mContext, View layout,
			ImageView imageview) {
		this.content = layout;
		this.blurredOverlay = imageview;
		createView(mContext);
		return this;
	}

	public GlassActionBarHelper contentLayout(Context mContext, View layout,
			ImageView imageview, ListAdapter adapter) {
		this.content = layout;
		this.blurredOverlay = imageview;
		this.adapter = adapter;
		createView(mContext);
		return this;
	}

	public View createView(Context context) {
		this.mContext = context;
		int[] attrs = { android.R.attr.windowBackground };

		// Need to get resource id of style pointed to from actionBarStyle
		TypedValue outValue = new TypedValue();
		context.getTheme().resolveAttribute(android.R.attr.windowBackground,
				outValue, true);

		TypedArray style = context.getTheme().obtainStyledAttributes(
				outValue.resourceId, attrs);
		windowBackground = style.getDrawable(0);
		style.recycle();
		content.getViewTreeObserver().addOnGlobalLayoutListener(this);
		if (content instanceof NotifyingScrollView) {
			if (verbose)
				LogUtil.d(TAG, "ScrollView content!");
			scrollView = (NotifyingScrollView) content;
			scrollView.setOnScrollChangedListener(this);
		} else if (content instanceof ListView) {
			if (verbose)
				LogUtil.d(TAG, "ListView content!");
			listView = (ListView) content;
			listView.setAdapter(adapter);
			ListViewScrollObserver observer = new ListViewScrollObserver(
					listView);
			observer.setOnScrollUpAndDownListener(this);
		}

		actionBarHeight = ViewUtil.getViewSize(blurredOverlay).height;
		LogUtil.d(TAG, "actionBarHeight:" + actionBarHeight);
		return content;
	}

	public void invalidate() {
		if (verbose)
			LogUtil.d(TAG, "invalidate()");
		scaled = null;
		computeBlurOverlay();
		updateBlurOverlay(lastScrollPosition, true);
	}

	public void setBlurRadius(int newValue) {
		if (!GlassActionBar.isValidBlurRadius(newValue)) {
			throw new IllegalArgumentException("Invalid blur radius");
		}
		if (blurRadius == newValue) {
			return;
		}
		blurRadius = newValue;
		invalidate();
	}

	public int getBlurRadius() {
		return blurRadius;
	}

	public void setDownsampling(int newValue) {
		if (!GlassActionBar.isValidDownsampling(newValue)) {
			throw new IllegalArgumentException("Invalid downsampling");
		}
		if (downSampling == newValue) {
			return;
		}
		downSampling = newValue;
		invalidate();
	}

	public int getDownsampling() {
		return downSampling;
	}

	protected int getActionBarHeight(Context context) {
		TypedValue outValue = new TypedValue();
		context.getTheme().resolveAttribute(android.R.attr.actionBarSize,
				outValue, true);
		return context.getResources()
				.getDimensionPixelSize(outValue.resourceId);
	}

	@Override
	public void onGlobalLayout() {
		if (verbose)
			LogUtil.d(TAG, "onGlobalLayout()");
		if (width != 0) {
			if (verbose)
				LogUtil.d(TAG,
						"onGlobalLayout() - returning because not first time it's called");
			return;
		}
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(
				blurredOverlay.getWidth(), MeasureSpec.AT_MOST);
		int heightMeasureSpec;
		if (listView != null) {
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(
					blurredOverlay.getHeight(), MeasureSpec.EXACTLY);
		} else {
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(
					LayoutParams.WRAP_CONTENT, MeasureSpec.UNSPECIFIED);
		}
		content.measure(widthMeasureSpec, heightMeasureSpec);
		width = blurredOverlay.getWidth();
		height = content.getMeasuredHeight();
		lastScrollPosition = scrollView != null ? scrollView.getScrollY() : 0;
		invalidate();
	}

	private void computeBlurOverlay() {
		if (verbose)
			LogUtil.d(TAG, "computeBlurOverlay()");
		if (scaled != null) {
			if (verbose)
				LogUtil.d(TAG,
						"computeBlurOverlay() - returning because scaled is not null");
			return;
		}
		if (verbose)
			LogUtil.d(TAG, "computeBlurOverlay() - drawing layout to canvas");
		int scrollPosition = 0;
		if (scrollView != null) {
			scrollPosition = scrollView.getScrollY();
			if (verbose)
				LogUtil.d(TAG, "computeBlurOverlay() - scroll position is "
						+ scrollPosition);
		}
		long start = System.nanoTime();

		scaled = Utils.drawViewToBitmap(scaled, content, width, height,
				downSampling, windowBackground);

		long delta = System.nanoTime() - start;
		if (verbose)
			LogUtil.d(TAG,
					"computeBlurOverlay() - drawing layout to canvas took "
							+ delta / 1e6f + " ms");

		if (verbose)
			LogUtil.d(TAG, "computeBlurOverlay() - starting blur task");
		startBlurTask();

		if (scrollView != null) {
			if (verbose)
				LogUtil.d(TAG, "computeBlurOverlay() - restoring scroll from "
						+ scrollView.getScrollY() + " to " + scrollPosition);
			scrollView.scrollTo(0, scrollPosition);
		}
	}

	private void startBlurTask() {
		if (verbose)
			LogUtil.d(TAG, "startBlurTask()");
		if (blurTask != null) {
			if (verbose)
				LogUtil.d(TAG,
						"startBlurTask() - task was already running, canceling it");
			blurTask.cancel();
		}
		blurTask = new BlurTask(mContext, this, scaled, blurRadius);
	}

	private void updateBlurOverlay(int top, boolean force) {
		if (verbose)
			LogUtil.d(TAG, "updateBlurOverlay() - top=" + top);

		if (scaled == null) {
			if (verbose)
				LogUtil.d(TAG,
						"updateBlurOverlay() - returning because scaled is null");
			return;
		}

		if (top < 0) {
			if (verbose)
				LogUtil.d(TAG, "updateBlurOverlay() - clamping top to 0");
			top = 0;
		}
		if (!force && lastScrollPosition == top) {
			if (verbose)
				LogUtil.d(TAG,
						"updateBlurOverlay() - returning because scroll position hasn't changed");
			return;
		}
		lastScrollPosition = top;
		LogUtil.d(TAG, "y:" + top / downSampling + "-----h:" + actionBarHeight
				/ downSampling + "--scaled.getHeight():" + scaled.getHeight());
		int y = top / downSampling;
		int h = actionBarHeight / downSampling;
		if (y > scaled.getHeight() - h) {
			y = scaled.getHeight() - h;
		}
		Bitmap actionBarSection = Bitmap.createBitmap(scaled, 0, y, width
				/ downSampling, h);
		// Blur here until background finished (will make smooth jerky during
		// the first second or so).
		Bitmap blurredBitmap;
		if (isBlurTaskFinished()) {
			if (verbose)
				LogUtil.d(
						TAG,
						"updateBlurOverlay() - blur task finished, no need to blur content under action bar");
			blurredBitmap = actionBarSection;
		} else {
			if (verbose)
				LogUtil.d(
						TAG,
						"updateBlurOverlay() - blur task not finished, blurring content under action bar");
			blurredBitmap = Blur.apply(mContext, actionBarSection);
		}
		Bitmap enlarged = Bitmap.createScaledBitmap(blurredBitmap, width,
				actionBarHeight, false);
		blurredBitmap.recycle();
		actionBarSection.recycle();
		blurredOverlay.setImageBitmap(enlarged);
	}

	private boolean isBlurTaskFinished() {
		return blurTask == null;
	}

	@Override
	public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
		// ScrollView scroll

		onNewScroll(t);
	}

	@Override
	public void onScrollUpDownChanged(int delta, int scrollPosition,
			boolean exact) {
		// ListView scroll
		if (verbose)
			LogUtil.d(TAG, "onScrollUpDownChanged() " + exact);
		if (exact) {
			onNewScroll(-scrollPosition);
		}
	}

	private void onNewScroll(int t) {
		if (verbose)
			LogUtil.d(TAG, "onNewScroll() - new scroll position is " + t);

		updateBlurOverlay(t, false);
	}

	@Override
	public void onBlurOperationFinished() {
		if (verbose)
			LogUtil.d(TAG,
					"onBlurOperationFinished() - blur operation finished");
		blurTask = null;
		updateBlurOverlay(lastScrollPosition, true);
		// Utils.saveToSdCard(scaled, "blurred.png");
	}

	@Override
	public void onScrollIdle() {
	}

}