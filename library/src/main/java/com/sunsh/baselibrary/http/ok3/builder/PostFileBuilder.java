package com.sunsh.baselibrary.http.ok3.builder;



import com.sunsh.baselibrary.http.ok3.request.PostFileRequest;
import com.sunsh.baselibrary.http.ok3.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by sunsh on 18/5/30.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder>
{
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file)
    {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostFileRequest(url, tag, params, headers, file, mediaType,id).build();
    }


}
