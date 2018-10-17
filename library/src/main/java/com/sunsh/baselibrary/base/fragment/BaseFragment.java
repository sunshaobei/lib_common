package com.sunsh.baselibrary.base.fragment;

import android.support.v4.app.Fragment;

import com.sunsh.baselibrary.base.LeakCanary;


public abstract class BaseFragment extends Fragment {
    //TODO init some baseconifg

    @Override
    public void onDestroy() {
        super.onDestroy();
        LeakCanary.watch(this);
    }
}
