package com.sunsh.baselibrary.widgets;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.sunsh.baselibrary.utils.AppContextUtil;

/**
 * 工具类
 * Created by hzwangchenyan on 2016/1/6.
 */
public class ScreenUtils {

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) AppContextUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        boolean isPortrait = dm.widthPixels < dm.heightPixels;
        return isPortrait ? dm.widthPixels : dm.heightPixels;
    }


    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) AppContextUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        boolean isPortrait = dm.widthPixels < dm.heightPixels;

        return isPortrait ? dm.heightPixels : dm.widthPixels;
    }

    /**
     *  像素密度
     * @return
     */
    public static int getDensityDpi() {
        WindowManager wm = (WindowManager) AppContextUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return dm.densityDpi;
    }

    /**
     *  密度
     * @return
     */
    public static float getDensity() {
        WindowManager wm = (WindowManager) AppContextUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return dm.density;
    }


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = AppContextUtil.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = AppContextUtil.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
