package com.sunsh.baselibrary;

import android.content.Intent;
import android.os.Bundle;



public abstract class CommonActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        initIntentData(getIntent());
        initView();
        initListener();
        initData();
    }

    public abstract int getLayoutID();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    protected void initIntentData(Intent intent) {
    }
}
