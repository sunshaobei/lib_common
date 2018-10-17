package com.sunsh.baselibrary.http.ok3.entity;


import android.util.Log;

import com.sunsh.baselibrary.http.ok3.callback.Callback;
import com.sunsh.baselibrary.json.JSONUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sunsh on 18/5/30.
 */
public abstract class HttpCallBack<T> extends Callback<T> {

    public HttpCallBack() {
        super();
    }

    public static String errorMessage(Exception e) {
        if(e instanceof SocketTimeoutException ||
                e instanceof UnknownHostException ||
                e instanceof SSLException) {
            return "请检查网络连接是否正常";
        }
        if(e.getMessage()!=null){
            if(e.getMessage().contains("unexpected url")){
                return "请求API获取失败，请检查网络并尝试重新登录获取";
            }
        }
        return e.getLocalizedMessage();
    }

    @Override
    public void onError(Call call, Exception e,int id) {

    }

    @Override
    public T parseNetworkResponse(Response response,int id) throws Exception {

        Log.i("okhttp", "response code:" + response.code());
        if(response.code()!=200){
            throw new Exception("服务器出错啦");
        }
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            //如果用户写了泛型，就会进入这里，否者不会执行
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type beanType = parameterizedType.getActualTypeArguments()[0];
            if (beanType == String.class) {
                //如果是String类型，直接返回字符串
                return (T) response.body().string();
            } else {
                //请求是否被跳转
                if(response.isRedirect()){
                    new Exception("请检查网络连接是否正常");
                }
                //如果是 Bean List Map ，则解析完后返回
                String body = response.body().string();

                try {
                    T datas = JSONUtils.fromJson(body, beanType);
                    if (datas instanceof HttpResponse) {
                       HttpResponse httpResponse = (HttpResponse) datas;
                        if(httpResponse.getCode() == HttpErrorCodeModel.TOKEN_TIMEOUT1||httpResponse.getCode()== HttpErrorCodeModel.TOKEN_TIMEOUT2 || httpResponse.getCode()== HttpErrorCodeModel.TOKEN_TIMEOUT3 ||httpResponse.getCode()== HttpErrorCodeModel.SYSTEM_DEPLOYMENT){
                           HttpErrorCodeModel httpErrorCodeModel = new HttpErrorCodeModel();
                            httpErrorCodeModel.setCode(httpResponse.getCode());
                            httpErrorCodeModel.setError(httpResponse.getMsg());
                        }
                    }
                    return datas;
                } catch (Exception e) {
                    Log.e("okhttp", "okhttp gson error:" + e.getMessage());
                    throw new Exception("数据出问题啦");
                }
            }
        } else {
            //如果没有写泛型，直接返回Response对象
            return (T) response;
        }
    }
}
