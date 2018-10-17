package com.sunsh.baselibrary.http.ok3.entity;

/**
 * Created by sunsh on 18/5/30.
 */

public class HttpErrorCodeModel {

    public static final int TOKEN_TIMEOUT1 = 401;
    public static final int TOKEN_TIMEOUT2 = 403;
    public static final int TOKEN_TIMEOUT3 = 405;

    public static final int SYSTEM_DEPLOYMENT = 503;

    private int code;
    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
