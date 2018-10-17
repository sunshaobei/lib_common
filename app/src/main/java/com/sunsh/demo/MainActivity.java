package com.sunsh.demo;

import android.os.Bundle;

import com.sunsh.baselibrary.base.activity.BaseBarActivity;

public class MainActivity extends BaseBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getClass().getSimpleName());
    }
}
