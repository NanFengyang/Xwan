package com.example.xwan.View.comment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.xwan.Info.CommentInfo;
import com.example.xwan.Info.ImageInfo;
import com.example.xwan.R;
import com.example.xwan.View.Image.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YangYouTao on 2016/3/19.
 */
public class CommentView extends LinearLayout implements CommetnAdapter.GridImageOnlistenner {
    private Context mContext;
    private ListView commetnListView;
    private List<CommentInfo> commentInfoList;
    private CommetnAdapter mCommetnAdapter;
    private View view;

    public CommentView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public CommentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    private void init() {
        view = LayoutInflater.from(mContext).inflate(R.layout.commentview_layout, null);
        addData();
        commetnListView = (ListView) view.findViewById(R.id.commentlistview);
        mCommetnAdapter = new CommetnAdapter(mContext, commentInfoList);
        mCommetnAdapter.setGridImageOnlistenner(this);
        commetnListView.setAdapter(mCommetnAdapter);
        this.addView(view);
    }

    private void addData() {
        commentInfoList = new ArrayList<CommentInfo>();
        for (int i = 0; i < 5; i++) {
            CommentInfo info = new CommentInfo();
            List<ImageInfo> commentImgUrls = new ArrayList<ImageInfo>();
            for (int j = 0; j < i % 3; j++) {
                commentImgUrls.add(new ImageInfo());
            }
            info.setCommentImgUrls(commentImgUrls);
            commentInfoList.add(info);
        }
    }

    /**
     * 添加评论
     *
     * @param info
     */
    public void addComment(CommentInfo info) {
        if (info == null) {
            return;
        }
        commentInfoList.add(info);
        mCommetnAdapter.notifyDataSetChanged();

    }

    private GridImageOnlistenner mGridImageOnlistenner;

    public void setGridImageOnlistenner(GridImageOnlistenner gridImageOnlistenner) {
        this.mGridImageOnlistenner = gridImageOnlistenner;
    }

    public interface GridImageOnlistenner {
        public void onGridImageClick(PhotoView imgview);
    }

    @Override
    public void onGridImageClick(PhotoView imgview) {
        if (mGridImageOnlistenner != null) {
            mGridImageOnlistenner.onGridImageClick(imgview);
        }

    }
}
