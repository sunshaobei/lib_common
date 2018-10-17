package com.sunsh.baselibrary.base.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.sunsh.baselibrary.R;
import com.sunsh.baselibrary.utils.NetWorkUtils;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseWebActivity extends BaseActivity {

    private WebView mWebView;
    private View customView;
    private View titlelayout;
    private FrameLayout videoContainer;
    private IX5WebChromeClient.CustomViewCallback customViewCallback;
    private WebSettings webSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web);
        initWebView();
    }

    public void initWebView() {
        showLoadingView();
        videoContainer = findViewById(R.id.videoContainer);
        titlelayout = findViewById(R.id.titlelayout);
        mWebView = findViewById(R.id.mWebview);
        webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setDefaultTextEncodingName("utf-8");
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        addJsInterface();
        webSetting.setUserAgentString(getUserAgentString());//设置用户代理
        if (NetWorkUtils.isConnected(this)) {
            webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAppCacheMaxSize(1024 * 1024 * 8);
        //加载http网页图片失败
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebViewClient(getWebViewClient());

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    dismissLoadingView();
                }
            }

            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }
        });

        //依据绘制内容提前隐藏loading
        mWebView.getView().getViewTreeObserver().addOnDrawListener(() -> {
            if (mWebView.getContentHeight() > 0) {
                dismissLoadingView();
            }
        });
    }


    protected void addJsInterface() {
        Map<String, Object> map = new HashMap<>();
        addJsInterface(map);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            mWebView.addJavascriptInterface(entry.getValue(), entry.getKey());
        }
    }

    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }
        Log.e("showCustomView", "-----------------");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        customView = view;
        videoContainer.addView(customView);
        videoContainer.setVisibility(View.VISIBLE);
        titlelayout.setVisibility(View.GONE);
        mWebView.getView().setVisibility(View.GONE);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }
        Log.e("hideCustomView", "-----------------");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        customView.setVisibility(View.GONE);
        videoContainer.removeView(customView);
        videoContainer.removeAllViews();
        videoContainer.setVisibility(View.GONE);
        customView = null;
        customViewCallback.onCustomViewHidden();
        customViewCallback = null;
        titlelayout.setVisibility(View.VISIBLE);
        mWebView.getView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (customView != null) {
            hideCustomView();
        } else
            super.onBackPressed();
    }

    public String getUserAgentString() {
        return webSetting.getUserAgentString();
    }

    protected abstract void addJsInterface(Map<String, Object> map);

    protected abstract WebViewClient getWebViewClient();

}
