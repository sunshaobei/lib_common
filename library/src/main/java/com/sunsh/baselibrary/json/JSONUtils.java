package com.sunsh.baselibrary.json;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sunsh.baselibrary.json.serializerAdapter.BoolDefault0Adapter;
import com.sunsh.baselibrary.json.serializerAdapter.DoubleDefault0Adapter;
import com.sunsh.baselibrary.json.serializerAdapter.IntegerDefault0Adapter;
import com.sunsh.baselibrary.json.serializerAdapter.LongDefault0Adapter;
import com.sunsh.baselibrary.json.serializerAdapter.StringDefaultAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by sunsh on 2017/6/7.
 */
public class JSONUtils {
    private JSONUtils() {
        throw new UnsupportedOperationException("this method disallow to use");
    }

    private static JSONObject object = new JSONObject();

    public static JSONObject toJson(String json) throws JSONException {
        return object.getJSONObject(json);
    }

    /**
     * 对象装JSON字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return JSON.toJSON(object).toString();
    }

    /**
     * json字符串转对象
     *
     * @param json
     * @param tClass
     * @return
     */
    public static <T> T fromJson(String json, Class<T> tClass) {
        return JSON.parseObject(json, tClass);
    }

    /**
     * json字符串转List
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type type) {
        return JSON.parseObject(json, type);
    }
}
