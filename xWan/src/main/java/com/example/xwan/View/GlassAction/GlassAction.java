package com.example.xwan.View.GlassAction;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.xwan.Util.LogUtil;
import com.example.xwan.Util.ViewUtil;
import com.example.xwan.View.GlassAction.BlurTask.Listener;
import com.example.xwan.View.GlassAction.ListViewScrollObserver.OnListViewScrollListener;
import com.example.xwan.View.GlassAction.NotifyingScrollView.OnScrollChangedListener;

public class GlassAction implements OnScrollChangedListener,
		OnListViewScrollListener, Listener, OnGlobalLayoutListener {
	private String TAG = "GlassAction";
	private Context mContext;
	private ImageView Bg_imageview;
	private View contentView;
	private NotifyingScrollView scrollView;
	private ListView listView;
	private int GlassHeight, GlassWidth;
	private Bitmap contentViewBipM;
	private BlurTask blurTask;
	private int blurRadius = 10;
	private int lastScrollPosition = -1;
	private ImageView img;

	public GlassAction(Context mContext, View view, ImageView imageview) {
		this.mContext = mContext;
		this.Bg_imageview = imageview;
		this.contentView = view;
		this.img = img;
		init();
	}

	private void init() {
		contentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
		if (contentView instanceof NotifyingScrollView) {
			LogUtil.d(TAG, "ScrollView content!");
			scrollView = (NotifyingScrollView) contentView;
			scrollView.setOnScrollChangedListener(this);
		} else if (contentView instanceof ListView) {
			LogUtil.d(TAG, "ListView content!");
			listView = (ListView) contentView;
			ListViewScrollObserver observer = new ListViewScrollObserver(
					listView);
			observer.setOnScrollUpAndDownListener(this);
		}
		GlassHeight = ViewUtil.getViewSize(Bg_imageview).height;
		GlassWidth = ViewUtil.getWindowsSize(mContext).width;
		lastScrollPosition = scrollView != null ? scrollView.getScrollY() : 0;
		LogUtil.d(TAG, "GlassHeight:" + GlassHeight);

	}

	public void invalidate() {
		LogUtil.d(TAG, "invalidate()");
		if (contentView instanceof NotifyingScrollView) {
			contentViewBipM = ViewUtil.getBitmapByScrollView(
					(ScrollView) contentView, GlassWidth);
		} else if (contentView instanceof ListView) {
			contentViewBipM = ViewUtil
					.getBitmapByListView(listView, GlassWidth);
		} else {
			contentViewBipM = ViewUtil.getViewBitmap(contentView);
		}
		startBlurTask();
	}

	private void startBlurTask() {
		LogUtil.d(TAG, "startBlurTask()");
		if (blurTask != null) {
			LogUtil.d(TAG,
					"startBlurTask() - task was already running, canceling it");
			blurTask.cancel();
		}
		if (contentViewBipM == null) {
			LogUtil.d(TAG, " contentViewBipM is null");
		}
		blurTask = new BlurTask(mContext, this, contentViewBipM, blurRadius);
	}

	private void updateBlurOverlay(int top) {
		LogUtil.d(TAG, "updateBlurOverlay() - top=" + top);

		if (contentViewBipM == null) {
			LogUtil.d(TAG,
					"updateBlurOverlay() - returning because contentViewBipM is null");
			return;
		}
		lastScrollPosition = top;
		if (top < 0) {
			LogUtil.d(TAG, "updateBlurOverlay() - clamping top to 0");
			top = 0;
		}
		int downSampling = 1;
		LogUtil.d(TAG,
				"y:" + top / downSampling + "-----h:" + GlassHeight
						/ downSampling + "--scaled.getHeight():"
						+ contentViewBipM.getHeight());
		int y = top / downSampling;
		int h = GlassHeight / downSampling;
		Bitmap actionBarSection = Bitmap.createBitmap(contentViewBipM, 0, y,
				GlassWidth / downSampling, h);
		// Blur here until background finished (will make smooth jerky during
		// the first second or so).
		Bg_imageview.setImageBitmap(actionBarSection);
		img.setImageBitmap(actionBarSection);
		// Bg_imageview.setImageResource(R.drawable.test_main_list_item1);
	}

	/**
	 * scrollView 滑动监听
	 */
	@Override
	public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub

	}

	/**
	 * listview 滑动监听
	 */
	@Override
	public void onScrollUpDownChanged(int delta, int scrollPosition,
			boolean exact) {
		// TODO Auto-generated method stub
		// updateBlurOverlay(-scrollPosition);
	}

	@Override
	public void onScrollIdle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBlurOperationFinished() {
		// TODO Auto-generated method stub
		if (contentViewBipM == null) {
			LogUtil.d(TAG, "source - null");
		}
		LogUtil.d(TAG, "onBlurOperationFinished() - blur operation finished");
		blurTask = null;
		updateBlurOverlay(lastScrollPosition);
	}

	private Boolean isFrist = true;

	@Override
	public void onGlobalLayout() {
		// TODO Auto-generated method stub
		if (isFrist) {
			invalidate();
			isFrist = false;
		}

	}

}
