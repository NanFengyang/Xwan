package com.example.xwan.View.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.xwan.Info.CommentInfo;
import com.example.xwan.Info.ImageInfo;
import com.example.xwan.R;
import com.example.xwan.View.Image.photoview.PhotoView;
import com.example.xwan.View.Image.shapeimageview.RoundedImageView;

import java.util.List;

/**
 * Created by YangYouTao on 2016/3/19.
 * 评论内容适配器
 */
public class CommetnAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private List<CommentInfo> commentInfoList;
    private LayoutInflater mLayoutInflater;
    private Context context;

    public CommetnAdapter(Context context, List<CommentInfo> list) {
        this.commentInfoList = list;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return commentInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold myViewHold = null;
        if (convertView == null) {
            myViewHold = new ViewHold();
            convertView = mLayoutInflater.inflate(R.layout.comment_itemlayout, null);
            myViewHold.commentContext = (TextView) convertView.findViewById(R.id.comment_content);
            myViewHold.from_heard = (RoundedImageView) convertView.findViewById(R.id.comment_heard);
            myViewHold.fromName = (TextView) convertView.findViewById(R.id.comment_name);
            myViewHold.imggrid = (GridView) convertView.findViewById(R.id.comment_imgs);
            convertView.setTag(myViewHold);
        } else {
            myViewHold = (ViewHold) convertView.getTag();
        }
        myViewHold.commentContext.setText(commentInfoList.get(position).getCommentContent_text());
        myViewHold.imggrid.setAdapter(new GridAdapter(commentInfoList.get(position).getCommentImgUrls()));
        myViewHold.imggrid.setOnItemClickListener(this);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PhotoView imgview = (PhotoView) view;
        if (mGridImageOnlistenner != null) {
            mGridImageOnlistenner.onGridImageClick(imgview);
        }
    }

    class ViewHold {
        RoundedImageView from_heard;
        TextView fromName;
        GridView imggrid;
        TextView commentContext;
    }

    /**
     * 图片适配器
     */
    class GridAdapter extends BaseAdapter {
        private List<ImageInfo> imgs;

        public GridAdapter(List<ImageInfo> imgs) {
            this.imgs = imgs;
        }

        @Override
        public int getCount() {
            return this.imgs == null ? 0 : imgs.size();
        }

        @Override
        public Object getItem(int i) {
            return imgs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.img_photo_layout, null);
            PhotoView imgview = (PhotoView) view;
            AbsListView.LayoutParams absla = new AbsListView.LayoutParams(180, 180);
            imgview.setLayoutParams(absla);
            imgview.setImageResource(R.drawable.test_main_grid_item6);
            imgview.setTag(R.drawable.test_main_grid_item6);
            return view;
        }
    }

    private GridImageOnlistenner mGridImageOnlistenner;

    public void setGridImageOnlistenner(GridImageOnlistenner gridImageOnlistenner) {
        this.mGridImageOnlistenner = gridImageOnlistenner;
    }

    interface GridImageOnlistenner {
        public void onGridImageClick(PhotoView imgview);
    }
}
