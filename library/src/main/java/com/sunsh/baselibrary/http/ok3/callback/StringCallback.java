package com.sunsh.baselibrary.http.ok3.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by sunsh on 18/5/30.
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return response.body().string();
    }
}
