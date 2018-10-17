package com.sunsh.baselibrary.widgets.pickview.singlepick;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sunsh.baselibrary.R;
import com.sunsh.baselibrary.widgets.pickview.citypickerview.PickViewUtils;
import com.sunsh.baselibrary.widgets.pickview.widget.wheel.WheelView;
import com.sunsh.baselibrary.widgets.pickview.widget.wheel.adapters.ArrayWheelAdapter;

public class SinglePickView {
    private Context context;
    private String defaultMenu;
    private int defaultPosition;
    private String[] menus;
    private WheelView wheelView;
    private PopupWindow popwindow;
    private View popupView;
    private SinglePickListener singlePickListener;
    private TextView tv_title;
    private PopupWindow.OnDismissListener dismissListener;


    public SinglePickView(Context context, String defaultMenu, String... menus) {
        this.context = context;
        this.defaultMenu = defaultMenu;
        this.menus = menus;
        init();
    }

    private void init() {
        popupView = LayoutInflater.from(context).inflate(R.layout.singlepick, null);
        wheelView = popupView.findViewById(R.id.single_pick);
        View tv_confirm = popupView.findViewById(R.id.tv_confirm);
        View tv_cancel = popupView.findViewById(R.id.tv_cancel);
        tv_title = popupView.findViewById(R.id.tv_title);
        popwindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popwindow.setAnimationStyle(R.style.pickView);
        popwindow.setBackgroundDrawable(new ColorDrawable());
        popwindow.setTouchable(true);
        popwindow.setOutsideTouchable(false);
        popwindow.setFocusable(true);
        wheelView.setCyclic(false);
        wheelView.setVisibleItems(5);
        for (int i = 0; i < menus.length; i++) {
            if (menus[i].equals(defaultMenu)) {
                defaultPosition = i;
                break;
            }
        }
        ArrayWheelAdapter adapter = new ArrayWheelAdapter<String>(context, menus);
        wheelView.setViewAdapter(adapter);
        wheelView.setCurrentItem(defaultPosition);
        wheelView.addChangingListener((wheel, oldValue, newValue) -> {
            defaultPosition = newValue;
        });
        tv_cancel.setOnClickListener(v -> {
            popwindow.dismiss();
        });
        tv_confirm.setOnClickListener(v -> {
            if (singlePickListener != null)
                singlePickListener.onSinglePick(defaultPosition, menus[defaultPosition]);
        });
        popwindow.setOnDismissListener(() -> {
            if (dismissListener != null) dismissListener.onDismiss();
            PickViewUtils.setBackgroundAlpha(context, 1f);
        });
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void setOnSinglePickListener(SinglePickListener l) {
        this.singlePickListener = l;

    }

    public void show() {
        PickViewUtils.setBackgroundAlpha(context, 0.5f);
        if (!isShow())
            popwindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
    }

    public void dismiss() {
        if (isShow()) popwindow.dismiss();
    }

    public boolean isShow() {
        return popwindow.isShowing();
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

}
