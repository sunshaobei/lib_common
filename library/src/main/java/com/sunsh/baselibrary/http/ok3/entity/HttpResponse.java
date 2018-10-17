package com.sunsh.baselibrary.http.ok3.entity;

import java.io.Serializable;

/**
 * Created by sunsh on 18/5/30.
 */
public class HttpResponse<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg != null ? msg : "";
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return getCode() == 200;
//                && data != null;
    }

    public boolean isSuccessWithDataNull() {
        return getCode() == 0;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}
