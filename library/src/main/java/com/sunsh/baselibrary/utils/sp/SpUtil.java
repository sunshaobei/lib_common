package com.sunsh.baselibrary.utils.sp;


import android.content.Context;
import android.content.SharedPreferences;

import com.sunsh.baselibrary.utils.AppContextUtil;

import java.util.HashMap;
import java.util.Map;


public class SpUtil {

    private static SpUtil instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String FILE_APP = "cifnesw";

    private SpUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE_APP,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static SpUtil getInstance() {
        if (instance == null) {
            synchronized (SpUtil.class) {
                if (instance == null) {
                    instance = new SpUtil(AppContextUtil.getContext());
                }
            }
        }
        return instance;
    }


    public static void clearInstance() {
        if (instance != null) instance = null;
    }

    public void clearSp() {
        editor.clear();
        editor.apply();
    }


    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getSharePreferences() {
        return sharedPreferences;
    }

    /**
     * 保存数据的方法，拿到数据保存数据的基本类型，然后根据类型调用不同的保存方法
     *
     * @param map
     */
    public void putMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object object = entry.getValue();
            if(null != object){
                if (object instanceof String) {
                    editor.putString(key, (String) object);
                } else if (object instanceof Integer) {
                    editor.putInt(key, (Integer) object);
                } else if (object instanceof Boolean) {
                    editor.putBoolean(key, (Boolean) object);
                } else if (object instanceof Float) {
                    editor.putFloat(key, (Float) object);
                } else if (object instanceof Long) {
                    editor.putLong(key, (Long) object);
                } else {
                    editor.putString(key, object.toString());
                }
            }

        }
        editor.apply();
    }

    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           键的值
     * @param defaultObject 默认值
     * @return
     */

    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, null);
        }
    }

    public void saveIsFirstLogin(boolean b) {
        editor.putBoolean(SpKey.IS_FIRST_LOGIN, b).apply();
    }

    /**
     * @return 获取imei
     */
    public String getImei() {
        return sharedPreferences.getString(SpKey.IMEI, SpKey.DEFAULE_VALUE_STR);
    }

    /**
     * @return 获取loginToken
     */
    public String getLoginToken() {
        return sharedPreferences.getString(SpKey.LOGINTOKEN, SpKey.DEFAULE_VALUE_STR);
    }

    /**
     * @return 获取openid
     */
    public String getOpenid() {
        return sharedPreferences.getString(SpKey.OPENID, SpKey.DEFAULE_VALUE_STR);
    }

    public String getString(String key, String s) {
        return sharedPreferences.getString(key, s);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, SpKey.DEFAULE_VALUE_STR);
    }

    public int getInt(String key, int i) {
        return sharedPreferences.getInt(key, i);
    }

    public long getLong(String key, long i) {
        return sharedPreferences.getLong(key, i);
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, SpKey.DEFAULE_VALUE_LO);
    }

    public float getFloat(String key, float i) {
        return sharedPreferences.getFloat(key, i);
    }

    public boolean getBoolean(String key, boolean b) {
        return sharedPreferences.getBoolean(key, b);
    }

    public String getNickName() {
        return sharedPreferences.getString(SpKey.NICKNAME, SpKey.DEFAULE_VALUE_STR);
    }

    public String getHeadImgUrl() {
        return sharedPreferences.getString(SpKey.HEADIMGURL, SpKey.DEFAULE_VALUE_STR);
    }

    public int getVBadge() {
        return sharedPreferences.getInt(SpKey.VBADGE, SpKey.DEFAULE_VALUE_IN);
    }

    public void putString(String key, String value) {
        editor.putString(key, value).apply();
    }

    public void putLong(String key, long l) {
        editor.putLong(key, l).apply();
    }

    public void putBoolean(String key, boolean b) {
        editor.putBoolean(key, b).apply();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(SpKey.APPUSERID, SpKey.DEFAULE_VALUE_STR);
    }

    public int getUID() {
        return sharedPreferences.getInt(SpKey.APPUID, SpKey.DEFAULE_VALUE_IN);
    }

    public void setUID(int uid) {
        editor.putInt(SpKey.APPUID, uid);
    }

    public boolean isLogin() {
        return !SpUtil.getInstance().getOpenid().isEmpty() || !SpUtil.getInstance().getLoginToken().isEmpty();
    }

    public long getVipUserId() {
        return sharedPreferences.getLong(SpKey.APPVIPUSERID, SpKey.DEFAULE_VALUE_LO);
    }

    public String getVipUserType() {
        return sharedPreferences.getString(SpKey.APPVIPUSERTYPE, SpKey.DEFAULE_VALUE_STR);
    }

    /**
     * 清空用户登录信息
     */
    public void clearUserLoginInfo() {
        //用putString的方法保存数据
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("nickname", "");
        infoMap.put("headimgurl", "");
        infoMap.put("openid", "");
        infoMap.put("loginToken", "");
        infoMap.put("liveToken", "");
        infoMap.put("unionid", "");
        infoMap.put("phone", "");
        infoMap.put("liveuserId", "");
        infoMap.put("bindWeChatUserName", "");
        infoMap.put("bindWeChatUserHead", "");
        infoMap.put("bindWeChatUnionid", "");
        infoMap.put("bindTelephone", "");
        infoMap.put("vBadge", 0);
        infoMap.put("appUserId", "");
        infoMap.put("vipUserId", SpKey.DEFAULE_VALUE_LO);
        infoMap.put("userVipType", "游客");//默认游客
        infoMap.put("showUserGiftDialog", false);
        //提交当前数据
        SpUtil.getInstance().putMap(infoMap);
    }

    /**
     * @return 获取unionid没有用手机好代替
     */
    public String getUnionId() {
        String unionid = SpUtil.getInstance().getString("unionid", "");
        String bindWeChatUnionid = SpUtil.getInstance().getString("bindWeChatUnionid", "");
        if (unionid.isEmpty()) {
            if (!bindWeChatUnionid.isEmpty()) {
                unionid = bindWeChatUnionid;
            } else {
                unionid = SpUtil.getInstance().getString("phone", "");
            }
        }
        return unionid;
    }
}
