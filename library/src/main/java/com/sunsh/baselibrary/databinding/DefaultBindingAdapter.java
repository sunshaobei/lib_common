package com.sunsh.baselibrary.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import org.greenrobot.greendao.annotation.NotNull;

public class DefaultBindingAdapter {

    /**
     * textview left 图片
     *
     * @param textView
     * @param resId
     */
    @BindingAdapter("drawableLeft")
    public static void drawableLeft(TextView textView, int resId) {
        Drawable drawable = ContextCompat.getDrawable(textView.getContext(), resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * textview right 图片
     *
     * @param textView
     * @param resId
     */
    @BindingAdapter("drawableRight")
    public static void drawableRight(TextView textView, int resId) {
        Drawable drawable = ContextCompat.getDrawable(textView.getContext(), resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * textview top 图片
     *
     * @param textView
     * @param resId
     */
    @BindingAdapter("drawableTop")
    public static void drawableTop(TextView textView, int resId) {
        Drawable drawable = ContextCompat.getDrawable(textView.getContext(), resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * textview bottom 图片
     *
     * @param textView
     * @param resId
     */
    @BindingAdapter("drawableBottom")
    public static void drawableBottom(TextView textView, int resId) {
        Drawable drawable = ContextCompat.getDrawable(textView.getContext(), resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        textView.setCompoundDrawables(null, null, null, drawable);
    }

    /**
     * textview 中划线
     *
     * @param view
     * @param b
     */
    @BindingAdapter("centerLine")
    public static void centerLine(TextView view, boolean b) {
        if (b) {
            view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            view.getPaint().setFlags(0);
        }
    }

    /**
     * textView 下划线
     *
     * @param view
     * @param b
     */
    @BindingAdapter("underLine")
    public static void underLine(TextView view, boolean b) {
        if (b) {
            view.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        } else {
            view.getPaint().setFlags(0);
        }
    }

    /**
     * textview 加载富文本
     *
     * @param textView
     * @param html
     */
    @BindingAdapter("html")
    public static void html(TextView textView, @NotNull String html) {
        if (!TextUtils.isEmpty(html))
            RichText.from(html).autoPlay(true).autoFix(false).into(textView);
    }
}
