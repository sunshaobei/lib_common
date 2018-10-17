package com.sunsh.baselibrary;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sunsh.baselibrary.utils.AppContextUtil;
import com.zzhoujay.richtext.RichText;

public class BaseApplication extends MultiDexApplication {

    public boolean bindWechat = false;//是否是微信绑定
    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtil.init(this);
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        RichText.initCacheDir(this);
    }
}
