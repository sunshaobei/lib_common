package com.sunsh.baselibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sunsh.baselibrary.mvp.BaseModel;
import com.sunsh.baselibrary.mvp.BasePresenter;
import com.sunsh.baselibrary.mvp.BaseView;
import com.sunsh.baselibrary.mvp.TUtil;

/**
 * Activity基类
 * Created by sunsh on 2018/5/29.
 */
public abstract class MvpActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {
    public T mPresenter;
    public E mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(getLayoutID());
        initIntentData(getIntent());
        initListener();
    }

    protected void init() {
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
    }

    protected void initIntentData(Intent intent) {

    }

    public abstract int getLayoutID();


    protected abstract void initListener();


}
