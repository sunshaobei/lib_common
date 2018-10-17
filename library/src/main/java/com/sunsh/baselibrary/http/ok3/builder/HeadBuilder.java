package com.sunsh.baselibrary.http.ok3.builder;


import com.sunsh.baselibrary.http.ok3.OkHttpUtils;
import com.sunsh.baselibrary.http.ok3.request.OtherRequest;
import com.sunsh.baselibrary.http.ok3.request.RequestCall;

/**
 * Created by sunsh on 18/5/30.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
