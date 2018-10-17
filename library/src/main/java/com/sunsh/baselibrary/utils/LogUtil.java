package com.sunsh.baselibrary.utils;

import android.text.TextUtils;
import android.util.Log;

import com.sunsh.baselibrary.BuildConfig;


public class LogUtil {

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(Thread.currentThread().getName() + "-" + (TextUtils.isEmpty(tag) ? "logUtil" : tag), msg);
        }
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void i(String msg) {
        i(null, msg);
    }


    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(Thread.currentThread().getName() + "-" + (TextUtils.isEmpty(tag) ? "logUtil" : tag), msg);
        }
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(Thread.currentThread().getName() + "-" + (TextUtils.isEmpty(tag) ? "logUtil" : tag), msg);
        }
    }
}
