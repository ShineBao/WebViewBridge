package com.graycarbon.webview;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GrayCarbon on 2018.4.11.
 * Info : 该类中封装与Js交互的收发方法
 */
public class GWebViewInterface {

    private static final String TAG = "GWebViewInterface";

    private GWebView mWebView;
    private Context mContext;

    GWebViewInterface(GWebView webView) {
        this.mWebView = webView;
        this.mContext = webView.getContext();
    }

    /**
     * JS调用，下发数据
     *
     * @param json 格式为：{"action":"toast","params":{"message":"Hello Word!"}}
     */
    @JavascriptInterface
    public void getDataFromJs(String json) {
        Log.i(TAG, "getDataFromJs: \n" + GWebViewUtil.formatJsonData(json));
        // TODO 先解析json格式是否正确，再处理json数据
        try {
            JSONObject rootObject = new JSONObject(json);
            String action = rootObject.getString("action");
            JSONObject paramsObject = rootObject.getJSONObject("params");
            switch (action) {
                case "toast":
                    sendDataToJs("{\"action\":\"toast_return\",\"params\":{\"msg\":\"Hello Word!\"}}");
                    break;
            }
        } catch (JSONException e) {
            // 异常证明解析json格式错误
            e.printStackTrace();
        }
    }

    /**
     * 向Js发送数据，需要Js指定具体的方法
     * 注意回调线程
     *
     * @param json 格式为：{"action":"toast_return","params":{"message":"Hello Word!"}}，return表示
     *             页面调用getDataFromJs的方法后，作为对应action的返回参数
     */
    public void sendDataToJs(final String json) {
        if (mWebView != null) {
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:getDataFromNative(" + json + ")");
                }
            });
        }
    }

}
