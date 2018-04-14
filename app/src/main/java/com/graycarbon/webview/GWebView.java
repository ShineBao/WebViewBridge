package com.graycarbon.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by GrayCarbon on 2018.4.11.
 * Info : 自定义WebView，封装各种回调
 * 注意：使用时注意线程操作的问题
 */
public class GWebView extends WebView {

    private static final String TAG = "GWebView";

    private Context mContext;

    public GWebView(Context context) {
        super(context);
        initConfigure(context);
    }

    public GWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConfigure(context);
    }

    public GWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfigure(context);
    }

    /**
     * 初始化配置
     *
     * @param context 上下文对象
     */
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initConfigure(Context context) {
        mContext = context;
        /*    WebView 相关配置    */
        setWebViewClient(new GWebViewClient());
        setWebChromeClient(new GWebChromeClient());
        // 定义交互接口，并设定调用名称为GWebView
        addJavascriptInterface(new GWebViewInterface(this), "GWebView");
        /*    WebSettings 相关配置    */
        WebSettings settings = getSettings();
        if (settings == null) return;
        // 使能Js交互
        settings.setJavaScriptEnabled(true);
        // 设置字体缩放倍数
        settings.setTextZoom(100);
        // 开启DOM缓存
        settings.setDomStorageEnabled(true);
        // 开启数据库缓存
        settings.setDatabaseEnabled(true);
        // 自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 设置WebView的缓存模式
        // LOAD_DEFAULT :
        //
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 使能缓存
        settings.setAppCacheEnabled(true);
        // 设置最大缓存（官方已弃用）
        settings.setAppCacheMaxSize(8 * 1024 * 1024);
        // Android 私有缓存存储，若不调用该方法，WebView不会创建该目录
        settings.setAppCachePath(context.getCacheDir().getAbsolutePath());
        // 设置数据库存储路径（官方已弃用）
        // settings.setDatabasePath(context.getDatabasePath("html").getPath());
        // 去除密码自动存储（官方已弃用）
        settings.setSavePassword(false);
        // 设置页面支持缩放
        settings.setSupportZoom(true);
        // 设置UserAgent属性
        settings.setUserAgentString("");
        // 设置允许加载本地HTML文件
        settings.setAllowFileAccess(false);
        // Android 4.1之前   默认：true
        // Android 4.1及以后  默认：false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // 允许通过File URL的形式读取本地文件
            settings.setAllowFileAccessFromFileURLs(true);
            // 允许通过File URL的形式读取本地文件，包括http,https等其他源文件
            settings.setAllowUniversalAccessFromFileURLs(true);
        }


    }

    /**
     * 自定义WebViewClient类处理相关操作
     */
    private class GWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        /**
         * 官方已经不建议使用,应该使用以下那个方法
         *
         * @param view 当前WebView对象
         * @param url  url地址
         * @return boolean
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        /**
         * 证书错误的情况下处理方式
         *
         * @param view    当前webView对象
         * @param handler 证书回调
         * @param error   错误信息
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            // TODO 证书错误时，继续加载可调用以下方法
            handler.proceed();
        }
    }

    /**
     * 自定义WebChromeClient类处理相关操作
     */
    private class GWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    }


}
