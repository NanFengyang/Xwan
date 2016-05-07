package com.example.xwan.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.xwan.R;
import com.example.xwan.Util.LogUtil;
import com.example.xwan.Util.ViewUtil;
import com.example.xwan.View.SweetAlert.SweetAlertDialog;
import com.lidroid.xutils.BitmapUtils;

public abstract class BaseActivity extends Activity {
    private SweetAlertDialog mLoadingDialog;
    public static final int CANNEL_DIALOG = 1;
    public static final int DELAYED = 2;
    public static final int REFRESH = 3;
    public static final int LOADMORE = 4;
    public static final int IMG_DELAYED = 5;
    public static final int COMMENT_CODE = 6;
    public static final String COMMENT_KEY = "key";
    private int errorImgid = R.drawable.test_main_list_item1;
    private BitmapUtils finalBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageLoader(this);
        initView(savedInstanceState);
        bindEvent();
    }

    /**
     * 图片加载器初始化
     *
     * @param context
     */
    private void initImageLoader(Context context) {
        if (finalBitmap == null) {
            finalBitmap = new BitmapUtils(context);
            ViewUtil.Size size = ViewUtil.getWindowsSize(context);
            finalBitmap.configDefaultBitmapMaxSize(size.width, size.height);
            finalBitmap.configDefaultLoadFailedImage(errorImgid);
            finalBitmap.configMemoryCacheEnabled(true);
            finalBitmap.configDiskCacheEnabled(true);
            finalBitmap.configDefaultLoadingImage(errorImgid);
        }
    }

    /**
     * 返回图片加载器，让子类操作
     *
     * @return
     */
    protected BitmapUtils ImageLoader() {
        return finalBitmap;
    }

    @SuppressLint("HandlerLeak")
    protected Handler MyHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case CANNEL_DIALOG:
                    dismissLoadingDialog();
                    break;

                default:
                    break;
            }
        }

    };

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 事件绑定
     */
    protected abstract void bindEvent();

    /**
     * id隐射
     *
     * @return
     */
    protected View getViewId(int id) {
        return findViewById(id);
    }

    /**
     * id隐射,父子关系
     *
     * @return
     */
    protected View getViewId(View view, int id) {
        return view.findViewById(id);
    }

    /**
     * 加载等待框
     */
    public void showLoadingDialog(String loadingtext) {
        if (loadingtext == null) {
            loadingtext = "加载中...";
        }
        mLoadingDialog = new SweetAlertDialog(this,
                SweetAlertDialog.PROGRESS_TYPE);
        mLoadingDialog.getProgressHelper().setBarColor(
                getResources().getColor(R.color.loading_text_color));
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setTitleText(loadingtext);
        mLoadingDialog.show();
    }

    /**
     * 正常提示弹出框
     *
     * @param text
     */
    public void showCusomDialog(String text) {
        mLoadingDialog = new SweetAlertDialog(this,
                SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        mLoadingDialog.getProgressHelper().setBarColor(
                getResources().getColor(R.color.loading_text_color));
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setTitleText(text);
        mLoadingDialog.show();
    }

    /**
     * 加载成功 弹出框
     *
     * @param text
     */
    public void showSuccessDialog(String text) {
        mLoadingDialog = new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE);
        mLoadingDialog.getProgressHelper().setBarColor(
                getResources().getColor(R.color.material_deep_teal_50));
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setTitleText(text);
        mLoadingDialog.show();
    }

    /**
     * 错误提示框
     *
     * @param text
     */
    public void showErrorDialog(String text) {
        mLoadingDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        mLoadingDialog.getProgressHelper().setBarColor(
                getResources().getColor(R.color.red_btn_bg_pressed_color));
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setTitleText(text);
        mLoadingDialog.show();
    }

    /**
     * 警告提示框
     *
     * @param text
     */
    public void showWarningDialog(String text) {
        mLoadingDialog = new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE);
        mLoadingDialog.getProgressHelper().setBarColor(
                getResources().getColor(R.color.warning_stroke_color));
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setTitleText(text);
        mLoadingDialog.show();
    }

    /**
     * 单个选择，警告提示框，确认框
     */
    public void showWarningSigleDialog(String title, String ContentText,
                                       String ConfirmText, final String trueTitle,
                                       final String trueContentText) {
        final SweetAlertDialog WarningDialog = new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE);
        WarningDialog.getProgressHelper().setBarColor(
                getResources().getColor(R.color.warning_stroke_color));
        WarningDialog.setTitleText(title);
        WarningDialog.setContentText(ContentText);
        WarningDialog.setConfirmText(ConfirmText);
        WarningDialog
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        WarningDialog.setTitleText(trueTitle)
                                .setContentText(trueContentText)
                                .setConfirmText("确认")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                });
        WarningDialog.show();

    }

    /**
     * 双重选择，警告提示框，确认框
     */
    public void showWarningDoubleDialog(String title, String ContentText,
                                        String CancelText, String ConfirmText, final String trueTitle,
                                        final String trueContentText, final String cancelTitle,
                                        final String cancelContentText) {
        final SweetAlertDialog WarningDialog = new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE);
        WarningDialog.getProgressHelper().setBarColor(
                getResources().getColor(R.color.warning_stroke_color));
        WarningDialog.setTitleText(title);
        WarningDialog.setContentText(ContentText);
        WarningDialog.setCancelText(CancelText);
        WarningDialog.setConfirmText(ConfirmText);
        WarningDialog
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        WarningDialog.setTitleText(trueTitle)
                                .setContentText(trueContentText)
                                .setConfirmText("确认")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                });
        WarningDialog
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance, keep widget user
                        // state, reset them if you need
                        sDialog.setTitleText(cancelTitle)
                                .setContentText(cancelContentText)
                                .setConfirmText("确认").showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                    }
                });
        WarningDialog.show();

    }

    /**
     * 一般提示框
     *
     * @param text
     */
    public void showNormalDialog(String text) {
        mLoadingDialog = new SweetAlertDialog(this,
                SweetAlertDialog.NORMAL_TYPE);
        mLoadingDialog.getProgressHelper().setBarColor(
                getResources().getColor(R.color.loading_text_color));
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setTitleText(text);
        mLoadingDialog.show();
    }

    /**
     * 让提示框消失
     */
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    public void dismissLoadingDialog(int delay) {
        if (delay <= 0) {
            delay = 2000;
        }
        MyHandler.sendEmptyMessageDelayed(CANNEL_DIALOG, delay);
    }

    @Override
    protected void onResume() {
        LogUtil.d("activity", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        LogUtil.d("activity", "onPause");
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        LogUtil.d("activity", "onDestroy");
        super.onDestroy();
    }
}
