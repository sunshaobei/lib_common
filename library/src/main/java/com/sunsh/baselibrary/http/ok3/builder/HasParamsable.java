package com.sunsh.baselibrary.http.ok3.builder;

import java.util.Map;

/**
 * Created by sunsh on 18/5/30.
 */
public interface HasParamsable {
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
