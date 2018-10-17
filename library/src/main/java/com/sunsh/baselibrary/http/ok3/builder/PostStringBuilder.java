package com.sunsh.baselibrary.http.ok3.builder;



import com.sunsh.baselibrary.http.ok3.request.PostStringRequest;
import com.sunsh.baselibrary.http.ok3.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by sunsh on 18/5/30.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }


}
