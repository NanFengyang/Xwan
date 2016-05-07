package com.example.xwan.video;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xwan.R;
import com.example.xwan.Util.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VedioPalyAdapter extends BaseAdapter {
	private Context mContext;
	private List<VideoInfo> list;
	private Map<Integer, View> m = new HashMap<Integer, View>();
	private Boolean isfrist = true;
	private int selectId = 0;
	private MyVideoView myVideoView;

	public VedioPalyAdapter(Context mContext, List<VideoInfo> list,
			MyVideoView mMyVideoView) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.list = list;
		isfrist = true;
		this.myVideoView = mMyVideoView;
	}

	/**
	 * 选着默认的播放列表item，默认第一个
	 *
	 * @param id
	 */
	public void setFirstViewItem(int id) {
		this.selectId = id;
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

	public void setList(List<VideoInfo> list) {
		this.list = list;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder mHolder = null;
		arg1 = m.get(arg0);
		if (arg1 == null) {
			mHolder = new ViewHolder();
			arg1 = View.inflate(mContext, R.layout.vidoplaylist_itemlayout,
					null);
			mHolder.title = (TextView) arg1.findViewById(R.id.play_title);
			mHolder.playcount = (TextView) arg1.findViewById(R.id.play_count);
			mHolder.playlayout = (RelativeLayout) arg1
					.findViewById(R.id.itemlayoutid);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		VideoInfo vi = list.get(arg0);
		mHolder.title.setText(vi.getVideo_title());
		// mHolder.playcount.setText(vi.getVideo_time());
		LinearLayout.LayoutParams widthLayout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		widthLayout.width = ViewUtil.getWindowsSize(mContext).width / 3;
		mHolder.playlayout.setLayoutParams(widthLayout);
		m.put(arg0, arg1);
		if (isfrist) {
			if (arg0 == selectId) {
				myVideoView.setFirstChildView(arg1);
				arg1.setBackgroundColor(mContext.getResources().getColor(
						R.color.app_bg_color));
			} else {
				arg1.setBackgroundColor(mContext.getResources().getColor(
						R.color.white_color));
			}
			isfrist = false;
		}

		return arg1;
	}

	class ViewHolder {
		private TextView title;
		private TextView playcount;
		private RelativeLayout playlayout;
	}
}
