package com.example.xwan.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.xwan.Info.CommentInfo;
import com.example.xwan.Info.ImageInfo;
import com.example.xwan.R;
import com.example.xwan.Util.LogUtil;
import com.example.xwan.Util.ViewUtil;
import com.example.xwan.View.DisScroollView.DiscrollView;
import com.example.xwan.View.GlassAction.NotifyingScrollView.OnScrollChangedListener;
import com.example.xwan.View.Image.photoview.PhotoView;
import com.example.xwan.View.comment.CommentView;
import com.example.xwan.View.windos8view.ui.Windos8CardActivity;
import com.example.xwan.adpter.DetailImageAdapter;
import com.example.xwan.adpter.DetailImageAdapter.OnAdapterClick;
import com.example.xwan.base.TitleBaseActivity;
import com.example.xwan.common.MApplication;
import com.example.xwan.video.MyVideoView;
import com.example.xwan.video.VideoInfo;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends TitleBaseActivity implements
        OnClickListener, OnScrollChangedListener, OnAdapterClick, CommentView.GridImageOnlistenner {
    private String TAG = "详 情";
    private Context mContext;
    private DiscrollView scrollview;
    private MyVideoView mMyVideoView;
    private List<ImageInfo> list_img, list_header;
    private DetailImageAdapter
            mDetailImageAdapter_hearder;
    private GridView mGridView_header;
    private TextView img_more, head_img;
    private ImageButton collent;
    private CommentView mCommentView;
    private Button addComment;
    /**
     * 播放信息列表
     */
    private List<VideoInfo> videoPalyList = new ArrayList<VideoInfo>();

    @Override
    protected void bindEvent() {
        // TODO Auto-generated method stub
        getTitle_leftImageButton().setOnClickListener(this);
        scrollview.setOnScrollChangedListener(this);
        img_more.setOnClickListener(this);
        head_img.setOnClickListener(this);
        collent.setOnClickListener(this);
        addComment.setOnClickListener(this);
        mDetailImageAdapter_hearder.setOnAdapterClick(this);
    }

    public void addVideoData() {// 静态数据
        String TEST_PLAY_URL = "http://v.iask.com/v_play_ipad.php?vid=138152839";
        String TEST_PLAY_URL2 = "http://v.iask.com/v_play_ipad.php?vid=138116139";
        String TEST_PLAY_TITLE = "(GTA5)闪电侠席卷圣洛城";
        String TEST_PLAY_TITLE2 = "DOTA2-TI5国际邀请赛";
        String urlimg = "http://d.hiphotos.baidu.com/image/pic/item/a2cc7cd98d1001e9b230cf71ba0e7bec54e79744.jpg";
        String urling2 = "http://imgsrc.baidu.com/forum/w%3D580/sign=4b286c1b4fed2e73fce98624b700a16d/b5cdbede9c82d158b1edda2e850a19d8bd3e4202.jpg";
        VideoInfo vi = new VideoInfo();
        vi.setVideo_img(urlimg);
        vi.setVideo_title(TEST_PLAY_TITLE);
        vi.setVideo_url(TEST_PLAY_URL);
        videoPalyList.add(vi);
        VideoInfo vi1 = new VideoInfo();
        vi1.setVideo_img(urling2);
        vi1.setVideo_title(TEST_PLAY_TITLE2);
        vi1.setVideo_url(TEST_PLAY_URL2);
        videoPalyList.add(vi1);
        mMyVideoView.initPlatyUrlList(videoPalyList);
    }

    @Override
    protected View initContentView(Bundle savedInstanceState,
                                   LayoutInflater layoutInflater) {
        // TODO Auto-generated method stub
        showLoadingDialog(null);
        mContext = this;
        MApplication.getIntance().addActivitys(this);
        View view = layoutInflater.inflate(R.layout.activity_detail, null);
        isRightTitleButtonVisibility(false);
        setTtitleTextAlpha(0);
        setTtitleTextColor(getResources().getColor(android.R.color.white));
        setTitle(R.drawable.icon_back, TAG, -1);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        scrollview = (DiscrollView) getViewId(view, R.id.detail_scrollview);
        mMyVideoView = (MyVideoView) getViewId(view, R.id.myvideo);
        mGridView_header = (GridView) getViewId(view,
                R.id.detail_userheardgridView);
        img_more = (TextView) getViewId(view, R.id.img_more);
        head_img = (TextView) getViewId(view, R.id.head_more);
        collent = (ImageButton) getViewId(view, R.id.detail_title_hear);
        mCommentView = (CommentView) getViewId(view, R.id.mycomment);
        addComment = (Button) getViewId(view, R.id.add_comment);
        mCommentView.setGridImageOnlistenner(this);
        mGridView_header.setFocusable(false);
        addVideoData();
        addata();
    }

    private void addata() {
        list_img = new ArrayList<ImageInfo>();
        list_header = new ArrayList<ImageInfo>();
        for (int i = 0; i < 10; i++) {
            ImageInfo Ima1 = new ImageInfo();
            ImageInfo Ima2 = new ImageInfo();
            Ima1.setId(i);
            Ima2.setId(10 + i);
            Ima1.setImg_rouseId(R.drawable.test_main_grid_item6);
            Ima2.setImg_rouseId(R.drawable.hearder);
            list_img.add(Ima1);
            list_header.add(Ima2);
        }
        int img_head = ViewUtil.getWindowsSize(mContext).width / 8;
        mDetailImageAdapter_hearder = new DetailImageAdapter(mContext,
                list_header, img_head, img_head, 8, true);
        mGridView_header.setAdapter(mDetailImageAdapter_hearder);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.img_more:
                intent.setClass(mContext, Windos8CardActivity.class);
                startActivity(intent);
                break;
            case R.id.head_more:
                intent.setClass(mContext, UserContactActivity.class);
                startActivity(intent);
                break;
            case R.id.add_comment:
                intent.setClass(mContext, CommentActivity.class);
                startActivityForResult(intent, COMMENT_CODE);
                break;
            case R.id.detail_title_hear:
                showWarningDoubleDialog("选择点赞？", "你真的要大力点赞吗？", "不，我在想想", "是，大力点赞",
                        "确认点赞？", "再一次确认，真的点赞？", "取消点赞？", "我想清楚了，不点赞");
                break;
            default:
                this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mMyVideoView.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMyVideoView.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        dismissLoadingDialog(1000);
        super.onResume();
        mMyVideoView.onResume();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mMyVideoView.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mMyVideoView != null
                && keyCode == KeyEvent.KEYCODE_BACK
                && mMyVideoView.getVideoConfiguration() == Configuration.ORIENTATION_LANDSCAPE) {// 全屏的时候监听
            return mMyVideoView.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        // TODO Auto-generated method stub
        LogUtil.d(this.getClass().getSimpleName(), "l:" + l + "--t:" + t
                + "--oldl:" + oldl + "--oldt:" + oldt);
        setAlphaBg(t, false);
    }

    @Override
    public void onAdapterClick(View arg0) {
        // TODO Auto-generated method stub
        int id = (Integer) arg0.getTag();
        showBigImage(arg0, id);
    }

    @Override
    public void onGridImageClick(PhotoView imgview) {
        showBigImage(imgview, (Integer) imgview.getTag());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case COMMENT_CODE:
                CommentInfo info= (CommentInfo) data.getSerializableExtra(COMMENT_KEY);
                LogUtil.d("CommentInfo",info.toString());
                mCommentView.addComment(info);
                break;
        }
    }
}
