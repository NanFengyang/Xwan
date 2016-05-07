package com.example.xwan.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.example.xwan.Info.ImageInfo;
import com.example.xwan.R;
import com.example.xwan.Util.LogUtil;
import com.example.xwan.View.Image.shapeimageview.CircularImageView;
import com.example.xwan.View.Image.shapeimageview.RoundedImageView;

import java.util.List;

public class DetailImageAdapter extends BaseAdapter implements OnClickListener {
	private List<ImageInfo> list;
	private LayoutInflater mLayoutInflater;
	private int width = 50, height = 50;
	private int size = 0;
	private Boolean isRound;

	public DetailImageAdapter(Context context, List<ImageInfo> list, int w,
			int h, int size, Boolean isRound) {
		mLayoutInflater = LayoutInflater.from(context);
		this.isRound = isRound;
		width = w;
		height = h;
		this.list = list;
		if (this.list.size() <= size) {
			this.size = this.list.size();
		} else {
			this.size = size;
		}
		LogUtil.d(this.getClass().getSimpleName(), "this.size:" + this.size
				+ "--this.list.size()" + this.list.size() + "--size:" + size);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.size;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHold mViewHold;
		if (arg1 == null) {
			mViewHold = new ViewHold();
			arg1 = mLayoutInflater.inflate(R.layout.detail_itemimageview, null);
			mViewHold.img_Rounded = (RoundedImageView) arg1
					.findViewById(R.id.roundimageview);
			mViewHold.img_Circular = (CircularImageView) arg1
					.findViewById(R.id.circularimageview);
			arg1.setTag(mViewHold);
		} else {
			mViewHold = (ViewHold) arg1.getTag();
		}
		if (this.isRound) {
			mViewHold.img_Rounded.setTag(list.get(arg0).getImg_rouseId());
			mViewHold.img_Rounded.setVisibility(View.VISIBLE);
			mViewHold.img_Circular.setVisibility(View.GONE);
			mViewHold.img_Rounded.setRadius(10);
			mViewHold.img_Rounded.setBorderWidth(0);
			mViewHold.img_Rounded.setBorderAlpha(0);
			mViewHold.img_Rounded.setImageResource(list.get(arg0)
					.getImg_rouseId());
			RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(
					width, height);
			mViewHold.img_Rounded.setOnClickListener(this);
			mViewHold.img_Rounded.setLayoutParams(ll);
		} else {
			mViewHold.img_Circular.setTag(list.get(arg0).getImg_rouseId());
			mViewHold.img_Rounded.setVisibility(View.GONE);
			mViewHold.img_Circular.setVisibility(View.VISIBLE);
			mViewHold.img_Circular.setBorderAlpha(0);
			mViewHold.img_Circular.setImageResource(list.get(arg0)
					.getImg_rouseId());
			RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(
					width, height);
			mViewHold.img_Circular.setOnClickListener(this);
			mViewHold.img_Circular.setLayoutParams(ll);
		}
		return arg1;
	}

	class ViewHold {
		CircularImageView img_Circular;
		RoundedImageView img_Rounded;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (mOnAdapterClick != null) {
			mOnAdapterClick.onAdapterClick(arg0);
		}

	}

	public void setOnAdapterClick(OnAdapterClick onAdapterClick) {
		mOnAdapterClick = onAdapterClick;
	}

	private OnAdapterClick mOnAdapterClick;

	public interface OnAdapterClick {
		public void onAdapterClick(View arg0);
	}
}
