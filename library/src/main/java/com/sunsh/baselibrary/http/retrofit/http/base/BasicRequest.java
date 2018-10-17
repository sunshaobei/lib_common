package com.sunsh.baselibrary.http.retrofit.http.base;


import com.sunsh.baselibrary.http.PathOnly;
import com.sunsh.baselibrary.json.JSONUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by sunsh on 18/5/30.
 */
public abstract class BasicRequest implements Serializable {

    protected transient StringBuilder urlBuilder = new StringBuilder();

    public abstract String getHttpRequestPath();

    protected transient Object object;

    public String getObjectString() {
        return JSONUtils.toJson(getObject());
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    protected String jointStr(Object object) {
        return object == null ? "" : "/" + object;
    }

    protected StringBuilder getUrlBuilder() {
        if (urlBuilder.length() > 0) {
            urlBuilder.delete(0, urlBuilder.length());
        }
        return urlBuilder;
    }

    public RequestBody body() {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        HashMap<String, String> params = modelToMap(this);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));
            }
        }
        return builder.build();
    }

    /**
     * 对象转为map
     *
     * @param requestModel
     * @return
     */
    private HashMap<String, String> modelToMap(Object requestModel) {
        HashMap<String, String> params = new HashMap<>();
        Field[] fields = requestModel.getClass().getDeclaredFields();
        for (Field field : fields) {
            PathOnly annotation = field.getAnnotation(PathOnly.class);
            if (annotation != null) continue;
            field.setAccessible(true);
            String value = null;
            try {
                if (field.get(requestModel) != null) {
                    if (field.get(requestModel) instanceof List) {
                        List list = (List) field.get(requestModel);
                        for (int i = 0; i < list.size(); i++) {
                            params.put(field.getName() + "[" + i + "]", list.get(i).toString());
                        }
                    } else {
                        value = field.get(requestModel).toString();
                        if (!value.equals("null")) {
                            params.put(field.getName(), value);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return params;
    }
}
