package com.sunsh.baselibrary.base;

import android.app.Application;

import com.squareup.leakcanary.RefWatcher;

public class LeakCanary {

    private static RefWatcher refWatcher;

    public static void init(Application application) {
        refWatcher = com.squareup.leakcanary.LeakCanary.install(application);
    }

    public static void watch(Object obj) {
        refWatcher.watch(obj);
    }
}
