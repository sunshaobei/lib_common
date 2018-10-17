package com.sunsh.baselibrary;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.moduth.blockcanary.BlockCanary;
import com.sunsh.baselibrary.base.BlockAppContext;
import com.sunsh.baselibrary.base.LeakCanary;
import com.sunsh.baselibrary.utils.AppContextUtil;
import com.sunsh.baselibrary.widgets.swipeback.SwipeBackHelper;
import com.zzhoujay.richtext.RichText;

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //全局上下文
        AppContextUtil.init(this);

        //路由
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);

        //侧滑返回 内部包含taskmanager
        SwipeBackHelper.init(this, null);

        //富文本
        RichText.initCacheDir(this);

        //内存泄漏监测
        LeakCanary.init(this);
        //线程阻塞监测
        BlockCanary.install(this, new BlockAppContext()).start();
    }
}
