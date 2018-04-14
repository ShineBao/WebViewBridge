package com.graycarbon.webviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.graycarbon.webview.GWebView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mRootView;
    private GWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootView = findViewById(R.id.ll_main);
        mWebView = new GWebView(this);
        initWebView();
        mRootView.addView(mWebView);
    }

    private void initWebView() {
        mWebView.loadUrl("file:///android_asset/test.html");

        Toast.makeText(this, "已加载页面", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null)
            mRootView.removeView(mWebView);
        super.onDestroy();
    }
}
