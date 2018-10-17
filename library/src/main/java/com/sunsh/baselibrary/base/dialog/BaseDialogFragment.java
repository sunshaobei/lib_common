package com.sunsh.baselibrary.base.dialog;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sunsh.baselibrary.R;
import com.sunsh.baselibrary.utils.SizeUtils;
import com.sunsh.baselibrary.utils.StatusBarUtil;

public abstract class BaseDialogFragment extends DialogFragment {

    protected Context mContext;
    private final View rootView;

    public BaseDialogFragment(Context context) {
        this.mContext = context;
        rootView = LayoutInflater.from(context).inflate(getLayoutId(), null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        StatusBarUtil.darkMode(window, Color.TRANSPARENT, 0.2f, false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        // 设置显示动画
        if (getWindowAnimation() != 0)
            window.setWindowAnimations(getWindowAnimation());
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        if (!isAdded())
            super.show(manager, tag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.dialog_fragment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViews(rootView);
        return rootView;
    }

    public void show(FragmentManager manager) {
        if (!this.isAdded())
            show(manager, getClass().getSimpleName());
    }

    public <T extends View> T findViewById(int id) {
        return rootView.findViewById(id);
    }

    protected abstract void initViews(View rootView);

    public abstract int getLayoutId();

    public int getWindowAnimation() {
        return 0;
    }


}
