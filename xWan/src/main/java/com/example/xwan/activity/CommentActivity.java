package com.example.xwan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.xwan.Info.CommentInfo;
import com.example.xwan.Info.ImageInfo;
import com.example.xwan.R;
import com.example.xwan.Util.PicImgCompressUtil;
import com.example.xwan.Util.ViewUtil;
import com.example.xwan.View.Image.photoview.PhotoView;
import com.example.xwan.base.TitleBaseActivity;
import com.example.xwan.common.MApplication;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class CommentActivity extends TitleBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int REQUEST_IMAGE = 2;//图片选着器的code
    private String TAG = "发表评论";
    private Context mContext;
    private EditText input_comment;
    private GridView comment_img;
    private Button comment_commit;
    private List<ImageInfo> list_img = new ArrayList<ImageInfo>();
    private LayoutInflater layoutInflater;
    private ArrayList<String> mSelectPath;
    private GriViewAdpter mGriViewAdpter;
    private Boolean isshowCamera = true;//是否显示相机
    private int maxPicNum = 6;//设置最大选取图片数目
    private int selectedMode = MultiImageSelectorActivity.MODE_MULTI;//多选，单选是：MODE_SINGLE;

    @Override
    protected void bindEvent() {
        getTitle_leftImageButton().setOnClickListener(this);
        comment_commit.setOnClickListener(this);
        comment_img.setOnItemClickListener(this);
        super.bindEvent();
    }

    @Override
    protected View initContentView(Bundle savedInstanceState, LayoutInflater layoutInflater) {
        mContext = this;
        this.layoutInflater = layoutInflater;
        MApplication.getIntance().addActivitys(this);
        View view = layoutInflater.inflate(R.layout.activity_comment, null);
        input_comment = (EditText) getViewId(view, R.id.input_commet);
        comment_img = (GridView) getViewId(view, R.id.add_comment_img);
        comment_commit = (Button) getViewId(view, R.id.add_comment_commit);
        setTitle();
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list_img.clear();
        ImageInfo info = new ImageInfo();
        info.setId(-1);
        info.setImg_rouseId(R.drawable.sh_icon_addpictrue);
        list_img.add(info);
        mGriViewAdpter = new GriViewAdpter();
        comment_img.setAdapter(mGriViewAdpter);
    }

    /**
     * 设置标题栏
     */
    private void setTitle() {
        isRightTitleButtonVisibility(false);
        setTitle(R.drawable.icon_back, TAG, -1);
    }

    /**
     * 跳转图片选择器
     */
    private void nextChooseImg() {
        Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, isshowCamera);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxPicNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_comment_commit://添加评论
                CommentInfo info = new CommentInfo();
                info.setCommentContent_text(input_comment.getText().toString());
                if (mSelectPath != null && mSelectPath.size() > 0) {
                    List<ImageInfo> commentImgUrls = new ArrayList<>();
                    for (String str : mSelectPath) {
                        ImageInfo img = new ImageInfo();
                        img.setImg_locaPath(str);
                        commentImgUrls.add(img);
                    }
                    info.setCommentImgUrls(commentImgUrls);
                }
                Intent intent = new Intent();
                intent.putExtra(COMMENT_KEY, info);
                setResult(COMMENT_CODE, intent);
                this.finish();
                break;
            default:
                this.finish();
                break;
        }
    }


    class GriViewAdpter extends BaseAdapter {

        @Override
        public int getCount() {
            return list_img.size();
        }

        @Override
        public Object getItem(int i) {
            return list_img.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            PhotoView phoview = (PhotoView) layoutInflater.inflate(R.layout.img_photo_layout, null);
            ViewUtil.Size size = ViewUtil.getWindowsSize(mContext);
            int w = size.width / 4 + 30;
            AbsListView.LayoutParams absla = new AbsListView.LayoutParams(w, w);
            phoview.setLayoutParams(absla);
            ImageInfo info = list_img.get(i);
            phoview.setTag(info.getImg_locaPath());
            if (info.getId() == -1) {
                phoview.setImageResource(info.getImg_rouseId());
            } else {
                phoview.setImageBitmap(PicImgCompressUtil.get100Bitmap(info.getImg_locaPath()));
            }
            return phoview;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (list_img.get(i).getId() == -1) {
            nextChooseImg();
        } else {
            PhotoView Pview = (PhotoView) view;
            showBigLocaImage(Pview, (String) Pview.getTag());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (mSelectPath != null && mSelectPath.size() > 0) {
                    int i = 0;
                    list_img.clear();
                    for (String p : mSelectPath) {
                        ImageInfo info = new ImageInfo();
                        info.setId(i);
                        info.setImg_locaPath(p);
                        list_img.add(info);
                        i += 1;
                    }
                    if (mSelectPath.size() < maxPicNum) {
                        ImageInfo info = new ImageInfo();
                        info.setId(-1);
                        info.setImg_rouseId(R.drawable.sh_icon_addpictrue);
                        list_img.add(info);
                    }
                    mGriViewAdpter.notifyDataSetChanged();
                }
            }
        }
    }
}
