package com.example.xwan.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xwan.Info.MmainFoodInfo;
import com.example.xwan.R;
import com.example.xwan.View.Image.shapeimageview.CircularImageView;

import java.util.List;

public class MainListViewAdpter extends BaseAdapter {
	private List<MmainFoodInfo> list;
	private LayoutInflater mLayoutInflater;

	public MainListViewAdpter(Context context, List<MmainFoodInfo> mlist) {
		this.list = mlist;
		this.mLayoutInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
		ViewHold mViewHold;
		// TODO Auto-generated method stub
		if (arg1 == null) {
			mViewHold = new ViewHold();
			arg1 = mLayoutInflater.inflate(R.layout.adapter_main_item, null);
			mViewHold.uesrName = (TextView) arg1.findViewById(R.id.uesr_name);
			mViewHold.Title = (TextView) arg1.findViewById(R.id.item_title);
			mViewHold.address = (TextView) arg1.findViewById(R.id.item_address);
			mViewHold.time = (TextView) arg1.findViewById(R.id.item_time);
			mViewHold.img = (ImageView) arg1.findViewById(R.id.item_img);
			mViewHold.uesrHeader = (CircularImageView) arg1
					.findViewById(R.id.header_img);
			arg1.setTag(mViewHold);
		} else {
			mViewHold = (ViewHold) arg1.getTag();
		}
		mViewHold.uesrHeader.setBorderAlpha(0);
		MmainFoodInfo maininfo = list.get(arg0);
		if (maininfo != null) {

		}
		return arg1;
	}

	class ViewHold {
		CircularImageView uesrHeader;
		ImageView img;
		TextView uesrName;
		TextView Title;
		TextView address;
		TextView time;
	}

}
