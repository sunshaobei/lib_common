package com.sunsh.baselibrary.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sunsh.baselibrary.R;
import com.sunsh.baselibrary.utils.StatusBarUtil;

public abstract class BaseDialog extends Dialog {

    private View view;
    protected Context mContext;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        view = LayoutInflater.from(getContext()).inflate(getLayoutId(), null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        Window window = getWindow();
        // 设置显示动画
        if (getWindowAnimation() != 0)
            window.setWindowAnimations(getWindowAnimation());
        WindowManager.LayoutParams wl = window.getAttributes();
//        wl.x = 0;
//        wl.y = window.getWindowManager().getDefaultDisplay().getHeight() - UIUtil.getNavigationBarHeight((Activity) mContext);
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(wl);
        StatusBarUtil.darkMode(getWindow(), Color.TRANSPARENT, 0.2f, true);
        initView();
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return view.findViewById(id);
    }

    protected abstract void initView();

    public abstract int getLayoutId();

    public int getWindowAnimation() {
        return 0;
    }

}
