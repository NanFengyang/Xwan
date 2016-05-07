package com.example.xwan.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ScrollViewListView extends ListView {

	public ScrollViewListView(Context context) {
		super(context);
	}

	public ScrollViewListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollViewListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}


}
