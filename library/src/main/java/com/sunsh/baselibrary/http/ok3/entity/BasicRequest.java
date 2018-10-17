package com.sunsh.baselibrary.http.ok3.entity;



import com.sunsh.baselibrary.json.JSONUtils;

import java.io.Serializable;
/**
 * Created by sunsh on 18/5/30.
 */
public abstract class BasicRequest implements Serializable {

    public abstract String $getHttpRequestPath();

    protected transient Object object;

    public String $getObjectString(){
        return JSONUtils.toJson($getObject());
    }

    public Object $getObject() {
        return object;
    }

    public void $setObject(Object object) {
        this.object = object;
    }

}
