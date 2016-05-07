package com.example.xwan.http;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by YangYouTao on 2016/3/24.
 */
public class HttpClient {
    private HttpUtils myHttpUtils;
    private int timeout = 5000;
    private int RetryCount = 1;

    /**
     * 初始化
     */
    public void initHttpClient() {
        if (myHttpUtils == null) {
            myHttpUtils = new HttpUtils();
        }
        myHttpUtils.configTimeout(timeout);//设置超时时间
        myHttpUtils.configCookieStore(null);
        myHttpUtils.configRequestRetryCount(RetryCount);
        myHttpUtils.configSSLSocketFactory(null);
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRetryCount() {
        return RetryCount;
    }

    public void setRetryCount(int retryCount) {
        RetryCount = retryCount;
    }

    /**
     * 发送GET请求数据
     *
     * @param url
     * @param Params
     * @param mAjaxCallBack
     */
    public void sendGet(String url, RequestParams Params, RequestCallBack mAjaxCallBack) {
        myHttpUtils.send(HttpRequest.HttpMethod.GET, url, Params, mAjaxCallBack);
    }

    /**
     * 发送POST请求数据,可上传文件和提交数据,用AjaxParams 提交参数
     * params.put("email", "test@tsz.net");
     * params.put("profile_picture", new File("/mnt/sdcard/pic.jpg")); // 上传文件
     * params.put("profile_picture2", inputStream); // 上传数据流
     * params.put("profile_picture3", new ByteArrayInputStream(bytes)); // 提交字节流
     *
     * @param url
     * @param Params
     * @param mAjaxCallBack
     */
    public void sendPost(String url, RequestParams Params, RequestCallBack mAjaxCallBack) {
        myHttpUtils.send(HttpRequest.HttpMethod.POST, url, Params, mAjaxCallBack);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param Params
     * @param localSavapath
     * @param mAjaxCallBack
     */
    public HttpHandler downloadFile(String url, RequestParams Params, String localSavapath, RequestCallBack mAjaxCallBack) {
        if (Params != null) {
            return myHttpUtils.download(url, localSavapath, Params, mAjaxCallBack);
        } else {
            return myHttpUtils.download(url, localSavapath, mAjaxCallBack);
        }
    }


}
