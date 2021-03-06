package com.sunsh.baselibrary.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.util.SimpleArrayMap;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public final class PermissionUtils {
    // Map of dangerous permissions introduced in later framework versions.
    // Used to conditionally bypass permission-hold checks on older devices.
    private static final SimpleArrayMap<String, Integer> MIN_SDK_PERMISSIONS;

    static {
        MIN_SDK_PERMISSIONS = new SimpleArrayMap<String, Integer>(8);
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", 14);
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", 20);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", 9);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.SYSTEM_ALERT_WINDOW", 23);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", 23);
    }

    private PermissionUtils() {
    }

    /**
     * Checks all given permissions have been granted.
     *
     * @param grantResults results
     * @return returns true if all permissions have been granted.
     */
    public static boolean verifyPermissions(int... grantResults) {
        if (grantResults.length == 0) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the permission exists in this SDK version
     *
     * @param permission permission
     * @return returns true if the permission exists in this SDK version
     */
    private static boolean permissionExists(String permission) {
        // Check if the permission could potentially be missing on this device
        Integer minVersion = MIN_SDK_PERMISSIONS.get(permission);
        // If null was returned from the above call, there is no need for a device API level check for the permission;
        // otherwise, we check if its minimum API level requirement is met
        return minVersion == null || Build.VERSION.SDK_INT >= minVersion;
    }

    /**
     * Returns true if the Activity or Fragment has access to all given permissions.
     *
     * @param context     context
     * @param permissions permission list
     * @return returns true if the Activity or Fragment has access to all given permissions.
     */
    public static boolean hasSelfPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (permissionExists(permission) && !hasSelfPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determine context has access to the given permission.
     * <p>
     * This is a workaround for RuntimeException of Parcel#readException.
     * For more detail, check this issue https://github.com/hotchemi/PermissionsDispatcher/issues/107
     *
     * @param context    context
     * @param permission permission
     * @return returns true if context has access to the given permission, false otherwise.
     * @see #hasSelfPermissions(Context, String...)
     */
    private static boolean hasSelfPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && "Xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
            return hasSelfPermissionForXiaomi(context, permission);
        }
        try {
            return checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        } catch (RuntimeException t) {
            return false;
        }
    }

    private static boolean hasSelfPermissionForXiaomi(Context context, String permission) {
        String permissionToOp = AppOpsManagerCompat.permissionToOp(permission);
        if (permissionToOp == null) {
            // in case of normal permissions(e.g. INTERNET)
            return true;
        }
        int noteOp = AppOpsManagerCompat.noteOp(context, permissionToOp, Process.myUid(), context.getPackageName());
        return noteOp == AppOpsManagerCompat.MODE_ALLOWED && checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Checks given permissions are needed to show rationale.
     *
     * @param activity    activity
     * @param permissions permission list
     * @return returns true if one of the permission is needed to show rationale.
     */
    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    public static String getDontAskAgainPermission(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (!hasSelfPermissions(activity, permission)&&!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return permission;
            }
        }
        return "";
    }

    public static String getShouldShowRequestPermission(Activity activity, String... permissions){
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return permission;
            }
        }
        return "";
    }

    /**
     * Checks given permissions are needed to show rationale.
     *
     * @param fragment    fragment
     * @param permissions permission list
     * @return returns true if one of the permission is needed to show rationale.
     */
    public static boolean shouldShowRequestPermissionRationale(android.support.v4.app.Fragment fragment, String... permissions) {
        for (String permission : permissions) {
            if (fragment.shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks given permissions are needed to show rationale.
     *
     * @param fragment    fragment
     * @param permissions permission list
     * @return returns true if one of the permission is needed to show rationale.
     */
    public static boolean shouldShowRequestPermissionRationale(Fragment fragment, String... permissions) {
        for (String permission : permissions) {
//            if (FragmentCompat.shouldShowRequestPermissionRationale(fragment, permission)) {
//                return true;
//            }
        }
        return false;
    }

    /**
     * Requests the provided permissions for a Fragment instance.
     *
     * @param fragment    fragment
     * @param permissions permissions list
     * @param requestCode Request code connected to the permission request
     */
    public static void requestPermissions(Fragment fragment, String[] permissions, int requestCode) {
//        FragmentCompat.requestPermissions(fragment, permissions, requestCode);
    }

    public static void openSetting(Context context) {
        if(Build.BRAND.contains("Meizu")){
            gotoMeizuPermission(context);
        }else if(Build.BRAND.contains("Huawei")){
            gotoHuaweiPermission(context);
        }else if(Build.BRAND.contains("Xiaomi")){
            gotoMiuiPermission(context);
        }else{
            gotoDefaultPermission(context);
        }
    }

    private static void gotoDefaultPermission(Context context){
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        try {
            context.startActivity(localIntent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShortToast("暂不支持该机型跳转");
        }
    }

    /**
     * 跳转到miui的权限管理页面
     */
    private static void gotoMiuiPermission(Context context) {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", context.getPackageName());
        try {
            context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            gotoDefaultPermission(context);
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //TODO:  不能写死
        intent.putExtra("packageName", "com.example.cifnews");
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoDefaultPermission(context);
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoDefaultPermission(context);
        }
    }


    public enum PermissionEnum {

        CAMERA("相机","android.permission.CAMERA", "在设置-应用-雨果网-权限中开启相机权限，以正常使用雨果网功能"),
        EXTERNAL_STORAGE("存储","android.permission.WRITE_EXTERNAL_STORAGE", "在设置-应用-雨果网-权限中开启存储空间权限，以正常使用雨果网功能"),
        READ_EXTERNAL_STORAGE("存储","android.permission.READ_EXTERNAL_STORAGE", "在设置-应用-雨果网-权限中开启存储空间权限，以正常使用雨果网功能"),
        LOCATION("位置","android.permission.ACCESS_FINE_LOCATION", "在设置-应用-雨果网-权限中开启位置权限，以正常使用雨果网功能"),
        CONTACTS("通讯录","android.permission.READ_CONTACTS", "在设置-应用-雨果网-权限中开启通讯录权限，以正常使用雨果网功能"),
        PHONE("电话","android.permission.CALL_PHONE", "在设置-应用-雨果网-权限中开启电话权限，以正常使用雨果网功能"),
        CALENDAR("日历","android.permission.READ_CALENDAR", "您已关闭日历权限"),
        SENSORS("身体传感器","android.permission.BODY_SENSORS", "您已关闭身体传感器权限"),
        RECORD_AUDIO("麦克风","android.permission.RECORD_AUDIO", "在设置-应用-雨果网-权限中开启麦克风权限，以正常使用雨果网功能"),
        SMS("短信","android.permission.READ_SMS", "您已关闭短信权限");

        private final String name;
        private final String permission;
        private final String denidStr;

        PermissionEnum(String name, String permission, String denidStr) {
            this.name = name;
            this.permission = permission;
            this.denidStr = denidStr;
        }

        public static PermissionEnum statusOf(String v) {
            for (PermissionEnum s : values()) {
                if (s.permission.equals(v)) {
                    return s;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public String permission() {
            return permission;
        }

        public String getDenidStr(){
            return denidStr;
        }
    }
}
