package com.example.xwan.View;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.ScrollView;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class ScrollViewGridView extends GridView {
	private ScrollView mScrollView;

	public ScrollViewGridView(Context context) {
		super(context);
	}

	public ScrollViewGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollViewGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	public void setPrentView(ScrollView mScrollView) {
		this.mScrollView = mScrollView;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (this.mScrollView != null) {
			this.mScrollView.requestDisallowInterceptTouchEvent(true);
		}
		return super.onTouchEvent(ev);
	}

}
