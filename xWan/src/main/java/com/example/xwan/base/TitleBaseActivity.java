package com.example.xwan.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xwan.R;
import com.example.xwan.Util.AppConfigUtil;
import com.example.xwan.Util.LogUtil;
import com.example.xwan.Util.PicImgCompressUtil;
import com.example.xwan.Util.ViewUtil;
import com.example.xwan.View.Image.photoview.Info;
import com.example.xwan.View.Image.photoview.PhotoView;

public abstract class TitleBaseActivity extends BaseActivity {
    private ImageButton left_img, right_img;
    private TextView title_text;
    private LinearLayout contentlayout;
    private View Title_bg;
    private PhotoView big_imageView;
    private Context mContext;
    private Info Old_photoImgeInfo;
    protected Handler BaseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IMG_DELAYED:
                    ImageLoader().display(big_imageView, String.valueOf(msg.obj));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.title_base, menu);
        return true;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_title_base);
        mContext = this;
        left_img = (ImageButton) getViewId(R.id.title_left_img);
        right_img = (ImageButton) getViewId(R.id.title_right_img);
        title_text = (TextView) getViewId(R.id.title_center_tv);
        Title_bg = (View) getViewId(R.id.title_bg);
        contentlayout = (LinearLayout) getViewId(R.id.content);
        big_imageView = (PhotoView) getViewId(R.id.big_img);
        big_imageView.enable();
        big_imageView.setVisibility(View.GONE);
        contentlayout.removeAllViews();
        contentlayout.removeAllViewsInLayout();
        contentlayout.addView(initContentView(savedInstanceState,
                LayoutInflater.from(this)));
    }

    private void setPhotoView(final View odview) {
        Old_photoImgeInfo = null;
        big_imageView.enable();
        final PhotoView phview = (PhotoView) odview;
        Old_photoImgeInfo = phview.getInfo();
        big_imageView.animaFrom(Old_photoImgeInfo);
        big_imageView.setScaleType(ScaleType.FIT_CENTER);
        big_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                big_imageView.animaTo(phview.getInfo(), new Runnable() {
                    @Override
                    public void run() {
                        big_imageView.setVisibility(View.GONE);
                    }
                });

            }
        });
        big_imageView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示大图,加载本地
     *
     * @param view
     * @param id
     */
    protected void showBigImage(final View view, int id) {
        big_imageView.setImageResource(id);
        setPhotoView(view);


    }

    /**
     * 显示大图,加载网络
     *
     * @param view
     * @param url
     */
    protected void showBigImage(final View view, final String url) {
        Bitmap bitmap = ViewUtil.getViewBitmap(view);
        ViewUtil.Size size = ViewUtil.getViewSize(view);
        LogUtil.d("size",size.width+"----"+size.height);
        bitmap = Bitmap.createScaledBitmap(bitmap, size.width/2, size.height/2, false);
        big_imageView.setImageBitmap(bitmap);
        setPhotoView(view);
        Message msg = new Message();
        msg.obj = url;
        msg.what = IMG_DELAYED;
        BaseHandler.sendMessageDelayed(msg, 600);
    }
    /**
     * 显示大图,本地路径
     *
     * @param view
     * @param path
     */
    protected void showBigLocaImage(final View view, final String path) {
        big_imageView.setImageBitmap(PicImgCompressUtil.get480Bitmap(path));
        setPhotoView(view);
    }
    /**
     * 设置文本的透明度
     *
     * @param Alph
     */
    protected void setTtitleTextAlpha(float Alph) {
        title_text.setAlpha(Alph);
        Title_bg.setAlpha(Alph);
    }

    /**
     * 设置文本的字体颜色
     *
     * @param color
     */
    protected void setTtitleTextColor(int color) {
        title_text.setTextColor(color);
    }

    /**
     * 动态改变背景颜色值
     *
     * @param num
     */
    protected void setAlphaBg(double num, Boolean isTextColor) {
        double alph = num / (double) AppConfigUtil.Height_Size;
        if (alph <= 0.95 && alph > 0) {
            float nowAlph = (float) alph;
            Title_bg.setAlpha(nowAlph);
            if (!isTextColor) {
                title_text.setAlpha(nowAlph);
            } else {
                float rgbco = nowAlph * 255;
                title_text.setTextColor(Color.rgb((int) rgbco, (int) rgbco,
                        (int) rgbco));
            }

        }
    }

    /**
     * 加载内容
     *
     * @param savedInstanceState
     * @return
     */
    protected abstract View initContentView(Bundle savedInstanceState,
                                            LayoutInflater layoutInflater);

    /**
     * 改变标题栏的显示界面，图片ID不更改则传 -1
     *
     * @param imgID_left
     * @param title_Text
     * @param imgID_right
     */
    protected void setTitle(int imgID_left, String title_Text, int imgID_right) {
        if (imgID_left != -1) {
            left_img.setImageResource(imgID_left);
        }
        if (imgID_right != -1) {
            right_img.setImageResource(imgID_right);
        }
        if (title_Text != null) {
            title_text.setText(title_Text);
        }
    }

    /**
     * 返回左边的button
     */
    protected ImageButton getTitle_leftImageButton() {
        return left_img;

    }

    /**
     * 返回右边的button
     */
    protected ImageButton getTitle_rightImageButton() {
        return right_img;

    }

    /**
     * 左边Button是否显示，默认显示
     *
     * @param isVisibility
     */
    protected void isLeftTitleButtonVisibility(Boolean isVisibility) {
        if (left_img == null) {
            return;
        }
        if (isVisibility) {
            left_img.setVisibility(View.VISIBLE);
        } else {
            left_img.setVisibility(View.GONE);
        }

    }

    /**
     * 右边Button是否显示，默认显示
     *
     * @param isVisibility
     */
    protected void isRightTitleButtonVisibility(Boolean isVisibility) {
        if (right_img == null) {
            return;
        }
        if (isVisibility) {
            right_img.setVisibility(View.VISIBLE);
        } else {
            right_img.setVisibility(View.GONE);
        }

    }

    @Override
    protected void bindEvent() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (big_imageView.getVisibility() == View.VISIBLE) {
            big_imageView.animaTo(Old_photoImgeInfo, new Runnable() {
                @Override
                public void run() {
                    big_imageView.setVisibility(View.GONE);
                }
            });
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
