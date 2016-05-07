package com.example.xwan.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.xwan.Info.MBannerInfo;
import com.example.xwan.Info.MmainFoodInfo;
import com.example.xwan.R;
import com.example.xwan.Util.AppConfigUtil;
import com.example.xwan.Util.LogUtil;
import com.example.xwan.View.GlassAction.ListViewScrollObserver;
import com.example.xwan.View.GlassAction.ListViewScrollObserver.OnListViewScrollListener;
import com.example.xwan.View.ViewPager.AutoScrollViewPager;
import com.example.xwan.View.refresh.XListView;
import com.example.xwan.View.refresh.XListView.IXListViewListener;
import com.example.xwan.adpter.MainListViewAdpter;
import com.example.xwan.base.TitleBaseActivity;
import com.example.xwan.common.MApplication;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends TitleBaseActivity implements
		OnItemClickListener, IXListViewListener, OnListViewScrollListener {
	private String TAG = "周末去哪儿";
	private XListView mListView;
	private Context mContext;
	private MainListViewAdpter mMainListViewAdpter;
	private List<MmainFoodInfo> list;
	private AutoScrollViewPager mAutoScrollViewPager;
	private List<MBannerInfo> Banner_list;
	private List<View> Banner_listView;

	@Override
	protected View initContentView(Bundle savedInstanceState,
			LayoutInflater layoutInflater) {
		// TODO Auto-generated method stub

		View view = layoutInflater.inflate(R.layout.activity_main, null);
		MApplication.getIntance().addActivitys(this);
		mContext = this;
		mListView = (XListView) getViewId(view, R.id.xListView);
		mListView.setPullLoadEnable(true);// 加载更多允许
		isRightTitleButtonVisibility(false);
		setTtitleTextAlpha(0);
		setTtitleTextColor(getResources().getColor(android.R.color.white));
		setTitle(-1, TAG, -1);
		addData();
		return view;
	}

	private void addData() {
		super.showLoadingDialog(null);
		View viewbanner = LayoutInflater.from(mContext).inflate(
				R.layout.mainbanner_layout, null);
		mAutoScrollViewPager = (AutoScrollViewPager) getViewId(viewbanner,
				R.id.banner);
		mListView.addHeaderView(viewbanner);
		list = new ArrayList<MmainFoodInfo>();
		for (int i = 0; i < 10; i++) {
			list.add(new MmainFoodInfo());
		}
		mMainListViewAdpter = new MainListViewAdpter(mContext, list);
		mListView.setAdapter(mMainListViewAdpter);
		Banner_list = new ArrayList<MBannerInfo>();
		Banner_listView = new ArrayList<View>();
		for (int i = 0; i < 5; i++) {
			View view = LayoutInflater.from(mContext).inflate(
					R.layout.mbanner_leftlayout, null);
			Banner_listView.add(view);
			Banner_list.add(new MBannerInfo());
		}
		mAutoScrollViewPager.setAdapter(new MainBannerAdapter());
		mAutoScrollViewPager.setPageMargin(5);// 图片间距
		mAutoScrollViewPager.startAutoScroll(2000);// 切换时长
		mAutoScrollViewPager.setAutoScrollDurationFactor(20);// 滑动时长
		super.dismissLoadingDialog(-1);
	}

	@SuppressLint("HandlerLeak")
	private Handler MyHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH:
				mListView.stopRefresh();
				break;
			case LOADMORE:
				mListView.stopLoadMore();
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(this);
		ListViewScrollObserver observer = new ListViewScrollObserver(mListView);
		observer.setOnScrollUpAndDownListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent detai = new Intent();
		detai.setClass(mContext, DetailActivity.class);
		startActivity(detai);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		MyHandler.sendEmptyMessageDelayed(REFRESH, 2000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		MyHandler.sendEmptyMessageDelayed(LOADMORE, 2000);
	}

	public class MainBannerAdapter extends PagerAdapter {
		@Override
		public void destroyItem(View container, int position, Object object) {

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(View container, int position) {
			int idNow = position % Banner_listView.size();
			if (Banner_listView.get(idNow).getParent() != null) {
				((ViewGroup) Banner_listView.get(idNow).getParent())
						.removeView(Banner_listView.get(idNow));
			}
			((ViewPager) container).addView(Banner_listView.get(idNow));
			return Banner_listView.get(idNow);

		}
	}

	@Override
	public void onScrollUpDownChanged(int delta, int scrollPosition,
			boolean exact) {
		// TODO Auto-generated method stub
		double nowH = -scrollPosition;
		LogUtil.d(this.getClass().getCanonicalName(), "alph: " + nowH
				% AppConfigUtil.Height_Size + "--delta:" + delta
				+ "--scrollPosition:" + scrollPosition + "--exact:" + exact);
		setAlphaBg(nowH, false);
	}

	@Override
	public void onScrollIdle() {
		// TODO Auto-generated method stub

	}

}
