package com.sunsh.baselibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Random;

/**
 * Created by Administrator on 2016/8/9.
 */

public class DeviceUtils {

    /**
     * 获取应用程序的IMEI号
     */
    public static String getIMEI(Context context) {
        if (context == null) {
            Log.e("YQY", "getIMEI  context为空");
        }
//        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
//        if (null == ANDROID_ID || ANDROID_ID.isEmpty()) {
//            String imei = "imei" + System.currentTimeMillis() + new Random().nextInt(9999999);
//            return imei;
//        }
        String imei = "imei" + System.currentTimeMillis() + new Random().nextInt(9999999);
        Log.e("YQY", "IMEI标识：" + imei);
        return imei;
    }

    /**
     * 获取设备的唯一标识，deviceId
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "";
        } else {
            return deviceId;
        }
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取设备的系统版本号
     */
    public static int getDeviceSDK() {
        int sdk = android.os.Build.VERSION.SDK_INT;
        Log.e("YQY", "设备版本：" + sdk);
        return sdk;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取设备的型号
     */
    public static String getDeviceName() {
        String model = android.os.Build.MODEL;
        Log.e("YQY", "设备型号：" + model);
        return model;
    }

    /**
     * @return 判断是否是小米手机
     */
    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        Log.e("小米", "isMIUI-------" + manufacturer);
        if ("xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    /**
     * @return 判断SD卡是否存在
     */
    public static boolean existSDCard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

}
