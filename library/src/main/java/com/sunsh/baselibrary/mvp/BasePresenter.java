package com.sunsh.baselibrary.mvp;

import android.content.Context;


/**
 * T-MVP Presenter基类
 * Created by  sunsh on 2018/3/15.
 */
public abstract class BasePresenter<M, T> {
    public Context context;
    public M mModel;
    public T mView;

    public void setVM(T v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();

    }

    public abstract void onStart();
}
