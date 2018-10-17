package com.sunsh.baselibrary.base;

import android.webkit.JavascriptInterface;

import java.util.logging.Handler;

/**
 * @author Administrator
 * @name com.example.cifnews
 * @class name：com.sunsh.baselibrary.base
 * @class describe
 * @time 2018/9/27 16:33
 * @change
 * @chang time
 * @class describe
 */
public class CustomJsInterface {

    @JavascriptInterface
    public void Config(final String content) {

    }

    /**
     * 登录
     */
    @JavascriptInterface
    public void noLogin() {
        new android.os.Handler().post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
