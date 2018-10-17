package com.sunsh.baselibrary.base.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class BasePageAdapter extends FragmentPagerAdapter {
    private Context context;//传入上下文
    private List<Fragment> mlist;//将Fragment放入List集合中
    private List<String> mtitles;//标题

    public BasePageAdapter(Context context, List<Fragment> mlist, List<String> mtitles, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.mlist = mlist;
        this.mtitles = mtitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mtitles.get(position);
    }

}
