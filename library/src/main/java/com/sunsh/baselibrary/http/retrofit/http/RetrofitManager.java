package com.sunsh.baselibrary.http.retrofit.http;


import com.sunsh.baselibrary.http.HttpsUtils;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;
import com.sunsh.baselibrary.http.retrofit.http.config.HttpConfig;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by sunsh on 2018/5/29.
 */
public class RetrofitManager {

    private static RetrofitManager mRetrofitManager;
    public static final String HTTP_MEDIA_TYPE = "application/json ; charset=utf-8";
    private static Retrofit mRetrofit;

    private RetrofitManager() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(new HttpsUtils.TrustAllHostnameVerifier())
//                .addInterceptor(InterceptorUtil.tokenInterceptor())
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                .build();
        //添加gson转换器
        //添加rxjava转换器
        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
    }

    public static Retrofit getInstence() {
        //双重检查锁
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (mRetrofitManager == null)
                    mRetrofitManager = new RetrofitManager();
            }
        }
        return mRetrofit;
    }

    /**
     * 获得想要的 retrofit service
     * @param clazz    想要的 retrofit service 接口，Retrofit会代理生成对应的实体类
     * @param <T>      api service
     * @return
     */
    private static <T> T initService(Class<T> clazz) {
        return getInstence().create(clazz);
    }

}
