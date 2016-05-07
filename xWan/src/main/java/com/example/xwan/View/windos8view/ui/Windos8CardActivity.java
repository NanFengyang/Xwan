package com.example.xwan.View.windos8view.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.example.xwan.R;
import com.example.xwan.Util.LogUtil;
import com.example.xwan.Util.ViewUtil;
import com.example.xwan.View.Image.photoview.PhotoView;
import com.example.xwan.View.windos8view.model.DuitangInfo;
import com.example.xwan.View.windos8view.ui.LazyScrollView.OnScrollListener;
import com.example.xwan.base.TitleBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 动态规划算法
 *
 * @author la
 */
public class Windos8CardActivity extends TitleBaseActivity implements
        OnScrollListener, OnClickListener {
    private String TAG = "相关图片";
    private static final int COLUMNCOUNT = 4;
    private int columnWidth = 250;// 每个item的宽度
    private int itemHeight = 0;
    private int rowCountPerScreen = 3;
    private int cols = 4;// 当前总列数
    private ArrayList<Integer> colYs = new ArrayList<Integer>();
    private LayoutInflater mInflater;
    private RelativeLayout rootView;
    List<Point> lostPoint = new ArrayList<Point>();// 用于记录空白块的位置
    private int currentPage = 1;
    private LazyScrollView rootScroll;
    private ContentTask contentTask;
    private Context mContext;
    private View contentView;
    private int delay = 500;

    @Override
    protected View initContentView(Bundle savedInstanceState,
                                   LayoutInflater layoutInflater) {
        // TODO Auto-generated method stub
        showLoadingDialog(null);
        contentView = layoutInflater.inflate(R.layout.activity_win8_layout,
                null);
        isRightTitleButtonVisibility(false);
        setTtitleTextAlpha((float) 0.1);
        setTtitleTextColor(getResources().getColor(android.R.color.white));
        setTitle(R.drawable.icon_back, TAG, -1);
        mContext = this;
        init();
        contentTask = new ContentTask(this);
        contentTask
                .execute("http://www.duitang.com/album/1733789/masn/p/1/24/");
        getTitle_leftImageButton().setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        return contentView;
    }

    private void init() {
        rootView = (RelativeLayout) contentView.findViewById(R.id.rootView);
        rootView.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
        rootScroll = (LazyScrollView) contentView.findViewById(R.id.rootScroll);
        rootScroll.setOnScrollListener(this);
        rootScroll.getView();
        mInflater = getLayoutInflater();
        Configuration cf = this.getResources().getConfiguration();
        if (cf.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rowCountPerScreen = 3;
        } else {
            rowCountPerScreen = 6;
        }
        columnWidth = ViewUtil.getWindowsSize(mContext).width / COLUMNCOUNT;
        itemHeight = ViewUtil.getWindowsSize(mContext).height
                / rowCountPerScreen;
        for (int i = 0; i < 4; i++) {
            colYs.add(0);
        }
    }

    private synchronized void addView(View view, DuitangInfo mDuitangInfo) {
        placeBrick(view);
        PhotoView picView = (PhotoView) view.findViewById(R.id.imageView);
        picView.setTag(mDuitangInfo.getIsrc());
        picView.setOnClickListener(this);
        rootView.addView(view);
        startAnim(view);
        ImageLoader().display(picView, mDuitangInfo.getIsrc());

        dismissLoadingDialog(delay);
    }

    // 布局算法

    /**
     * 原理：动态规划
     *
     * @param view
     */
    private void placeBrick(View view) {
        LayoutParams brick = (LayoutParams) view.getLayoutParams();
        int groupCount, colSpan, rowSpan;
        List<Integer> groupY = new ArrayList<Integer>();
        List<Integer> groupColY = new ArrayList<Integer>();
        colSpan = (int) Math.ceil(brick.width / this.columnWidth);// 计算跨几列
        colSpan = Math.min(colSpan, this.cols);// 取最小的列数
        rowSpan = (int) Math.ceil(brick.height / this.itemHeight);
        LogUtil.d("VideoShowActivity", "colSpan:" + colSpan);
        if (colSpan == 1) {
            groupY = this.colYs;
            // 如果存在白块则从添加到白块中
            if (lostPoint.size() > 0 && rowSpan == 1) {
                Point point = lostPoint.get(0);
                int pTop = point.y;
                int pLeft = this.columnWidth * point.x;// 放置的left
                android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        brick.width, brick.height);
                params.leftMargin = pLeft;
                params.topMargin = pTop;
                view.setLayoutParams(params);
                lostPoint.remove(0);
                return;
            }

        } else {// 说明有跨列
            groupCount = this.cols + 1 - colSpan;// 添加item的时候列可以填充的列index
            for (int j = 0; j < groupCount; j++) {
                groupColY = this.colYs.subList(j, j + colSpan);
                groupY.add(j, Collections.max(groupColY));// 选择几个可添加的位置
            }
        }
        int minimumY;

        minimumY = Collections.min(groupY);// 取出几个可选位置中最小的添加
        int shortCol = 0;
        int len = groupY.size();
        for (int i = 0; i < len; i++) {
            if (groupY.get(i) == minimumY) {
                shortCol = i;// 获取到最小y值对应的列值
                break;
            }
        }
        int pTop = minimumY;// 这是放置的Top
        int pLeft = this.columnWidth * shortCol;// 放置的left
        android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                brick.width, brick.height);
        params.leftMargin = pLeft;
        params.topMargin = pTop;
        view.setLayoutParams(params);
        if (colSpan != 1) {
            for (int i = 0; i < this.cols; i++) {
                if (minimumY > this.colYs.get(i)) {// 出现空行
                    int y = minimumY - this.colYs.get(i);
                    for (int j = 0; j < y / itemHeight; j++) {
                        lostPoint.add(new Point(i, this.colYs.get(i)
                                + itemHeight * j));
                    }
                }
            }
        }
        int setHeight = minimumY + brick.height, setSpan = this.cols + 1 - len;
        for (int i = 0; i < setSpan; i++) {
            this.colYs.set(shortCol + i, setHeight);
        }
    }

    private class ContentTask extends
            AsyncTask<String, Integer, List<DuitangInfo>> {

        private Context mContext;

        public ContentTask(Context context) {
            super();
            mContext = context;
        }

        @Override
        protected List<DuitangInfo> doInBackground(String... params) {
            try {
                return parseNewsJSON(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<DuitangInfo> result) {
            // 动态计算ListView
            if (result != null) {
                Random r = new Random();

                for (int i = 0; i < result.size(); i++) {
                    View v = mInflater.inflate(R.layout.win8_item, null);
                    int nextInt = r.nextInt(50);
                    // 模拟分为三种情况
                    if (nextInt > 40) {
                        // 跨两列两行
                        android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                columnWidth * 2, itemHeight * 2);
                        v.setLayoutParams(params);
                    } else if (nextInt > 30) {
                        // 跨一列两行
                        android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                columnWidth, itemHeight * 2);

                        v.setLayoutParams(params);
                    } else if (nextInt > 25) {
                        // 跨两列一行
                        android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                columnWidth * 2, itemHeight);

                        v.setLayoutParams(params);
                    } else {
                        android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                columnWidth, itemHeight);

                        v.setLayoutParams(params);
                    }
                    addView(v, result.get(i));
                }
            }
        }

        @Override
        protected void onPreExecute() {
        }

        public List<DuitangInfo> parseNewsJSON(String url) throws IOException {
            List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
            String json = "";
            if (Helper.checkConnection(mContext)) {
                try {
                    json = Helper.getStringFromUrl(url);
                } catch (IOException e) {
                    Log.e("IOException is : ", e.toString());
                    e.printStackTrace();
                    return duitangs;
                }
            }
            LogUtil.d("MainActiivty", "json:" + json);

            try {
                if (null != json) {
                    JSONObject newsObject = new JSONObject(json);
                    JSONObject jsonObject = newsObject.getJSONObject("data");
                    JSONArray blogsJson = jsonObject.getJSONArray("blogs");

                    for (int i = 0; i < blogsJson.length(); i++) {
                        JSONObject newsInfoLeftObject = blogsJson
                                .getJSONObject(i);
                        DuitangInfo newsInfo1 = new DuitangInfo();
                        newsInfo1.setPhtoID(i);
                        newsInfo1
                                .setAlbid(newsInfoLeftObject.isNull("albid") ? ""
                                        : newsInfoLeftObject.getString("albid"));
                        newsInfo1
                                .setIsrc(newsInfoLeftObject.isNull("isrc") ? ""
                                        : newsInfoLeftObject.getString("isrc"));
                        newsInfo1.setMsg(newsInfoLeftObject.isNull("msg") ? ""
                                : newsInfoLeftObject.getString("msg"));
                        duitangs.add(newsInfo1);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return duitangs;
        }
    }

    private void startAnim(View v) {
        final float centerX = columnWidth / 2.0f;
        final float centerY = itemHeight / 2.0f;
        Rotate3dAnimation rotation;

        rotation = new Rotate3dAnimation(10, 0, centerX, centerY);

        rotation.setDuration(1000);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new DecelerateInterpolator());

        v.startAnimation(rotation);
    }

    @Override
    public void onBottom() {
        if (contentTask.getStatus() != AsyncTask.Status.RUNNING) {
            showLoadingDialog(null);
            contentTask = new ContentTask(this);
            contentTask
                    .execute("http://www.duitang.com/blogs/tags/hot/?page="
                            + currentPage
                            + "&tags=%E5%8A%A8%E6%BC%AB%2C%E6%89%8B%E5%8A%9E%2C%E5%8A%A8%E7%94%BB%2C%E6%B5%B7%E8%B4%BC%E7%8E%8B%2C%E6%BC%AB%E7%94%BB&_type=");
            currentPage++;
        }
    }

    @Override
    public void onAutoScroll(int l, int t, int oldl, int oldt) {
        setAlphaBg(t, false);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        if (arg0 instanceof PhotoView) {
            showBigImage(arg0, (String) arg0.getTag());
        } else {
            finish();
        }

    }

}
