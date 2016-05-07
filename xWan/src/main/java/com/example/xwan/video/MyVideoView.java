package com.example.xwan.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xwan.R;
import com.example.xwan.View.HorizontalListView;
import com.sina.sinavideo.coreplayer.util.LogS;
import com.sina.sinavideo.sdk.VDVideoExtListeners.OnVDVideoFrameADListener;
import com.sina.sinavideo.sdk.VDVideoExtListeners.OnVDVideoInsertADListener;
import com.sina.sinavideo.sdk.VDVideoExtListeners.OnVDVideoPlaylistListener;
import com.sina.sinavideo.sdk.VDVideoView;
import com.sina.sinavideo.sdk.VDVideoViewController;
import com.sina.sinavideo.sdk.data.VDVideoInfo;
import com.sina.sinavideo.sdk.data.VDVideoListInfo;
import com.sina.sinavideo.sdk.utils.VDVideoFullModeController;
import com.sina.sinavideo.sdk.widgets.playlist.VDVideoPlayListContainer;

import java.util.ArrayList;
import java.util.List;

public class MyVideoView extends LinearLayout implements
		OnVDVideoFrameADListener, OnVDVideoInsertADListener,
		OnVDVideoPlaylistListener, OnClickListener, OnItemClickListener {
	private final String TAG = "MyVideoView";
	private Context mContext;
	/**
	 * 布局主文件，父容器
	 */
	private View convertView;
	/**
	 * video播放器放置的父容器，用来放置播放器
	 */
	private ViewGroup videoLayout;
	/**
	 * 播放器的logo，开始页面显示的自定义
	 */
	private ImageView videologo;
	/**
	 * 开始播放按钮
	 */
	private ImageView videoplay;
	/**
	 * 播放时间
	 */
	private TextView videoplayTime;
	/**
	 * 播放器
	 */
	private VDVideoView videoView;
	/**
	 * 当前播放的视屏的名字
	 */
	private TextView palyName_TV;
	/**
	 * 播放器控制器
	 */
	private VDVideoPlayListContainer mVDVideoPlayListContainer;

	/**
	 * 播放信息列表
	 */
	private List<VideoInfo> videoPalyList = new ArrayList<VideoInfo>();
	/**
	 * 发送弹幕开关 自定义
	 */
	private TextView sendBarrage;
	/**
	 * 弹出弹幕输入界面
	 */
	private PopupWindow barrage_Window;
	/**
	 * 弹幕输入内容
	 */
	private EditText barrageEditText;

	/**
	 * 放置弹幕内容的父组件
	 */
	private RelativeLayout containerVG;

	/**
	 * 缩小播放时的弹幕
	 *
	 */
	private RelativeLayout barragelayout;
	/**
	 * 缩小是的播放列表
	 */
	private HorizontalListView palylistView;

	/**
	 * 播放列表之前选中的ID
	 */
	private View play_Oldselect_view = null;

	/**
	 * 播放ID
	 */
	private int play_id = 0;

	/**
	 * 屏幕状态
	 *
	 */
	private int VideoConfiguration = Configuration.ORIENTATION_PORTRAIT;
	/**
	 * 播放列表适配器
	 */
	private VedioPalyAdapter mVedioPalyAdapter;

	public int getVideoConfiguration() {
		return VideoConfiguration;
	}

	public MyVideoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public MyVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	@SuppressLint("NewApi")
	public MyVideoView(Context context, AttributeSet attrs, int style) {
		super(context, attrs, style);
		// TODO Auto-generated constructor stub
		init(context);
	}

	/**
	 * 播放界面默认选中的ItemView
	 */
	public void setFirstChildView(View frist) {
		play_Oldselect_view = frist;
	}

	/**
	 * 控件初始化
	 *
	 * @param context
	 */
	private void init(Context context) {
		videoPalyList.clear();
		this.mContext = context;
		convertView = LayoutInflater.from(this.mContext).inflate(
				R.layout.video_layout, null);
		videoLayout = (ViewGroup) convertView.findViewById(R.id.video_layout);
		videologo = (ImageView) convertView.findViewById(R.id.iv_video_thumb);
		videoplay = (ImageView) convertView
				.findViewById(R.id.iv_video_playicon);
		videoplayTime = (TextView) convertView
				.findViewById(R.id.iv_video_playtime);
		barragelayout = (RelativeLayout) convertView
				.findViewById(R.id.barrage_lay);
		palylistView = (HorizontalListView) convertView
				.findViewById(R.id.paly_listview);
		palyName_TV = (TextView) convertView.findViewById(R.id.playvideo_name);
		barragelayout.setVisibility(View.GONE);
		Button sendBarrage = (Button) barragelayout
				.findViewById(R.id.barrage_send);
		sendBarrage.setOnClickListener(this);
		barrageEditText = (EditText) barragelayout
				.findViewById(R.id.barrage_input);
		View container = getVideoView();
		if (container == null || this.videoLayout == null) {
			return;
		}
		if (container.getParent() != null) {
			((ViewGroup) container.getParent()).removeAllViews();
		}
		videoLayout.addView(container);
		registerListener();
		this.setVerticalGravity(LinearLayout.VERTICAL);
		this.addView(convertView);
	}

	/**
	 * 获取播放器
	 *
	 * @return
	 */
	private View getVideoView() {
		View videoContainer = LayoutInflater.from(this.mContext).inflate(
				R.layout.videocontainer_layout, null);
		videoView = (VDVideoView) videoContainer.findViewById(R.id.vv1);
		containerVG = (RelativeLayout) videoContainer
				.findViewById(R.id.vv_layout);
		videoView.setVDVideoViewContainer((ViewGroup) videoView.getParent());
		mVDVideoPlayListContainer = (VDVideoPlayListContainer) videoContainer
				.findViewById(R.id.playlist1);
		sendBarrage = (TextView) videoContainer.findViewById(R.id.barrage1);
		sendBarrage.setVisibility(View.GONE);
		return videoContainer;
	}

	/**
	 * 视屏是否播放
	 */
	private void isShowVieo(Boolean isshow) {
		if (isshow) {
			videologo.setVisibility(View.GONE);
			videoplay.setVisibility(View.GONE);
			videoplayTime.setVisibility(View.GONE);
		} else {
			videologo.setVisibility(View.VISIBLE);
			videoplay.setVisibility(View.VISIBLE);
			videoplayTime.setVisibility(View.GONE);
		}
	}

	/**
	 * 初始化播放列表
	 *
	 * @param List
	 */
	public void initPlatyUrlList(List<VideoInfo> List) {
		if (List != null && List.size() > 0) {
			videoPalyList.clear();
			videoPalyList = List;
			Log.i(TAG, List.size() + "----" + videoPalyList.size());
			palyName_TV.setText(videoPalyList.get(0).getVideo_title());
			VDVideoListInfo infoList = new VDVideoListInfo();
			for (VideoInfo vinfo : videoPalyList) {
				VDVideoInfo info = new VDVideoInfo();
				info.mTitle = vinfo.getVideo_title();
				info.mPlayUrl = vinfo.getVideo_url();
				infoList.addVideoInfo(info);
			}
			videoView.open(mContext, infoList);
			mVedioPalyAdapter = new VedioPalyAdapter(mContext, videoPalyList,
					this);
			palylistView.setAdapter(mVedioPalyAdapter);
		}
	}

	/**
	 * 开始播放
	 */
	private void startPlayVidoPlayList() {
		isShowVieo(true);
		if (videoPalyList != null && videoPalyList.size() > 0) {
			videoView.play(play_id);
		} else {
			Log.d(TAG, "palylist is  null!!");
		}

	}

	/**
	 * 父Acitivity必须调用
	 */
	public void onResume() {
		if (VDVideoViewController.getInstance(mContext) != null
				&& VDVideoViewController.getInstance(mContext)
						.getVideoInfoNum() > 0) {
			VDVideoViewController.getInstance(mContext).onResume();
		}
	}

	/**
	 * 父Acitivity必须调用
	 */
	public void onPause() {
		if (VDVideoViewController.getInstance(mContext) != null) {
			VDVideoViewController.getInstance(mContext).onPause();
		}
	}

	/**
	 * 父Acitivity必须调用
	 */
	public void onDestroy() {
		videoView.release(false);
	}

	/**
	 * 父Acitivity必须调用
	 */
	public void onStop() {
		if (VDVideoViewController.getInstance(mContext) != null) {
			VDVideoViewController.getInstance(mContext).onStop();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		VideoConfiguration = newConfig.orientation;
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			if (videoView != null) {
				videoView.setIsFullScreen(true);
				LogS.e(VDVideoFullModeController.TAG,
						"onConfigurationChanged---横屏");
			}
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			if (videoView != null) {
				videoView.setIsFullScreen(false);
				LogS.e(VDVideoFullModeController.TAG,
						"onConfigurationChanged---竖屏");
			}
		}
	}

	/**
	 * 添加播放列表
	 *
	 * @param List
	 */
	public void addPlatyUrlList(List<VideoInfo> List) {
		if (List != null && List.size() > 0) {
			videoPalyList.addAll(List);
		}

	}

	/**
	 * 注册监听事件
	 */
	private void registerListener() {
		if (VDVideoViewController.getInstance(mContext) != null) {
			VDVideoViewController.getInstance(mContext).getExtListener()
					.setFrameADListener(this);
			VDVideoViewController.getInstance(mContext).getExtListener()
					.setInsertADListener(this);
			VDVideoViewController.getInstance(mContext).getExtListener()
					.setPlaylistListener(this);
		}
		videoplay.setOnClickListener(this);
		sendBarrage.setOnClickListener(this);
		palylistView.setOnItemClickListener(this);
	}

	/**
	 * 是否返回判断
	 *
	 * @return
	 */
	public boolean holdGoBack() {
		boolean isLandscape = !VDVideoFullModeController.getInstance()
				.getIsPortrait();
		if (isLandscape) {
			return true;
		}
		return false;
	}

	@Override
	public void onPlaylistClick(VDVideoInfo info, int p) {
		// TODO Auto-generated method stub
		videoView.play(p);
	}

	@Override
	public void onInsertADClick(VDVideoInfo info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInsertADStepOutClick(VDVideoInfo info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFrameADPrepared(VDVideoInfo info) {
		// TODO Auto-generated method stub

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (holdGoBack()) {
				if (VDVideoViewController.getInstance(mContext) != null
						&& !VDVideoViewController.getInstance(mContext)
								.onKeyEvent(event)) {
					return false;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.iv_video_playicon:
			startPlayVidoPlayList();
			break;
		case R.id.barrage1:
			Log.d(TAG, "弹出发送弹幕，····");
			addPopWindowBarrage();
			break;
		case R.id.barrage_send:
			Log.d(TAG, "发送弹幕，····");
			if (barrageEditText.getText().toString().length() == 0) {
				Log.d(TAG, "内容为空");
				return;
			}
			if (barrage_Window != null && barrage_Window.isShowing()) {
				barrage_Window.dismiss();
			}
			break;
		default:
			break;
		}

	}

	/**
	 * 添加全屏时期的弹幕效果
	 */
	private void addPopWindowBarrage() {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.barragepopwindos_layout, null);
		barrage_Window = new PopupWindow(view,
				WindowManager.LayoutParams.MATCH_PARENT, 100);

		// 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
		barrage_Window.setFocusable(true);

		// 实例化一个ColorDrawable颜色为半透明
		// ColorDrawable dw = new ColorDrawable(0xb0000000);
		ColorDrawable dw = new ColorDrawable(0xffffffff);
		barrage_Window.setBackgroundDrawable(dw);
		// 在底部显示
		barrage_Window.showAtLocation(convertView, Gravity.BOTTOM, 0, 0);
		Button sendBarrage = (Button) view.findViewById(R.id.barrage_send);
		sendBarrage.setOnClickListener(this);
		barrageEditText = (EditText) view.findViewById(R.id.barrage_input);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		onPause();
		isShowVieo(false);
		play_id = arg2;
		palyName_TV.setText(videoPalyList.get(arg2).getVideo_title());
		if (play_Oldselect_view != null) {
			play_Oldselect_view.setBackgroundColor(mContext.getResources()
					.getColor(R.color.white_color));
		}
		arg1.setBackgroundColor(mContext.getResources().getColor(
				R.color.app_bg_color));

		play_Oldselect_view = arg1;
	}
}
