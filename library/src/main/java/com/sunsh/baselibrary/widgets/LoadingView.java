package com.sunsh.baselibrary.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sunsh.baselibrary.R;


public class LoadingView extends FrameLayout {

    private ImageView imageView;

    public LoadingView(@NonNull Context context) {
        super(context);
        init();
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setOnClickListener(v -> {
        });
        setBackgroundColor(Color.WHITE);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        imageView = new ImageView(getContext());
        imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.loading_bg));
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgParams.gravity = Gravity.CENTER_HORIZONTAL;
        linearLayout.addView(imageView, imgParams);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        this.addView(linearLayout, layoutParams);
    }

    public void showLoading() {
        showLoading(null);
    }

    public void showLoading(int color) {
        showLoading(null, color);
    }

    public void showLoading(@Nullable Drawable drawable) {
        showLoading(drawable, Color.WHITE);
    }

    public void showLoading(Drawable drawable, int color) {
        setVisibility(VISIBLE);
        setBackgroundColor(color);
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
        try {
            AnimationDrawable animatorDrawable = (AnimationDrawable) imageView.getBackground();
            animatorDrawable.start();
        } catch (Exception e) {

        }
    }

    public void dismissLoading() {
        try {
            AnimationDrawable animatorDrawable = (AnimationDrawable) imageView.getDrawable();
            animatorDrawable.stop();
        } catch (Exception e) {

        }
        setVisibility(GONE);
    }


}
