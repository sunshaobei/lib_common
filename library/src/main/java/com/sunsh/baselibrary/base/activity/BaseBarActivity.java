package com.sunsh.baselibrary.base.activity;


import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunsh.baselibrary.R;
import com.sunsh.baselibrary.utils.SizeUtils;

import java.util.List;

public abstract class BaseBarActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        View rootView = getLayoutInflater().inflate(R.layout.activity_bar_fragment, null);
        FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.fragment_container);
        frameLayout.addView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        super.setContentView(rootView);
        setAutoAdjustTopInsideToVisible(true);
        setToolBarBackgroundNormal();
        if (showToolBar()) {
            setToolbar();
        }
        if (resizeLayoutWithKeyboardAble()) {
            setFitsSystemWindows(true);
        }
    }

    protected boolean resizeLayoutWithKeyboardAble() {
        return true;
    }

    public void setContainerFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, fragment).commitAllowingStateLoss();
    }

    // 设置toolbar是否在页面顶部或 悬浮在布局上
    public void setAutoAdjustTopInsideToVisible(boolean adjust) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) frameLayout.getLayoutParams();
        if (adjust) {
            params.addRule(RelativeLayout.BELOW, toolBarViewResourceId());
        } else {
            params.addRule(RelativeLayout.BELOW, RelativeLayout.TRUE);
        }
        frameLayout.setLayoutParams(params);
    }

    protected void setToolbar() {
        toolbar = (Toolbar) findViewById(toolBarResourceId());
        toolbar.setTitle("");
        if (needSetSupportActionBar()) {
            setSupportActionBar(toolbar);
        }
        if (showBackIcon()) {
            setupBackViewAction();
        }
    }

    public void setTitle(String titleName) {
        TextView toolbarTitle = getTitleTextView();
        if (!TextUtils.isEmpty(titleName)) {
            toolBarBackgroundView().setVisibility(View.VISIBLE);
            toolbarTitle.setVisibility(View.VISIBLE);
            toolbarTitle.setText(titleName);
        } else {
            toolbarTitle.setVisibility(View.GONE);
        }
    }

    public void setTitleTextColor(int color) {
        getTitleTextView().setTextColor(color);
    }

    public void setTitleRightImage(int drawableId) {
        TextView textView = getTitleTextView();
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
        textView.setCompoundDrawablePadding(10);
    }

    public void setTitleOnClick(View.OnClickListener listener) {
        getTitleTextView().setOnClickListener(listener);
    }

    private TextView getTitleTextView() {
        return findViewById(R.id.toolbar_title);
    }


    protected TextView addRightItemText(String text, View.OnClickListener listener) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(R.color.text_color));
        textView.setTextSize(14);
        textView.setOnClickListener(listener);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = textView.getContext().getTheme();
        if (theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
            textView.setBackgroundResource(typedValue.resourceId);
        }
        addRightItemView(textView);
        return textView;
    }

    protected void addRightItemImageResource(int resourceId, View.OnClickListener listener) {
        ImageView rightIcon = new ImageView(this);
        rightIcon.setImageResource(resourceId);
        rightIcon.setOnClickListener(listener);
        addRightItemView(rightIcon);
    }

    protected void addRightItemView(View view) {
        getCustomItemsView().removeAllViews();
        getCustomItemsView().addView(view);
    }

    protected void addRightItemsView(List<View> itemViews) {
        getCustomItemsView().removeAllViews();
        for (View view : itemViews) {
            view.setPadding(10, 0, 10, 0);
            getCustomItemsView().addView(view);
        }
    }

    protected RelativeLayout getBackCustomItemView() {
        return (RelativeLayout) findViewById(R.id.toolbar_back_rl);
    }

    public RelativeLayout getCustomTitleView() {
        RelativeLayout titleView = (RelativeLayout) findViewById(R.id.toolbar_title_view);
        return titleView;
    }

    public LinearLayout getCustomItemsView() {
        return (LinearLayout) findViewById(R.id.custom_items_view);
    }

    protected ImageView getTitleIcon() {
        return (ImageView) findViewById(R.id.toobar_title_icon);
    }

    protected Drawable backIconDrawable() {
        return getResources().getDrawable(R.mipmap.ic_back);
    }

    private int toolBarResourceId() {
        return R.id.toolbar;
    }

    private int toolBarViewResourceId() {
        return R.id.widget_toolbar;
    }

    protected boolean needSetSupportActionBar() {
        return true;
    }

    protected boolean showBackIcon() {
        return true;
    }

    protected boolean showToolBar() {
        return true;
    }

    protected void setToolBarVisible(Boolean b) {
        toolbar = (Toolbar) findViewById(toolBarResourceId());
        toolbar.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    protected float getBarBackViewHeight() {
        return toolBarBackgroundView().getHeight();
    }

    protected void setToolBarBackgroundTransparent() {
        setToolBarBackgroundValue(0);
    }

    protected void setToolBarBackgroundNormal() {
        setToolBarBackgroundValue(255);
    }

    public void setToolBarBackgroundValue(int value) {
        toolBarBackgroundView().getBackground().mutate().setAlpha(value);
    }

    public View toolBarBackgroundView() {
        return findViewById(toolBarViewResourceId());
    }

    public View getToolBarView() {
        return findViewById(toolBarResourceId());
    }

    public void setupBackViewHidden() {
        toolbar.setNavigationIcon(null);
        toolbar.setNavigationOnClickListener(null);
    }

    private void setupBackViewAction() {
        toolbar.setNavigationIcon(backIconDrawable());
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void setNavigationOnClickListener(View.OnClickListener clickListener) {
        toolbar.setNavigationOnClickListener(clickListener);
    }


    private void barInflateMenu(int resId) {
        barClearMenu();
        toolbar.inflateMenu(resId);
    }

    public void barClearMenu() {
        toolbar.getMenu().clear();
    }

    private void barSetOnMenuItemClickListener(Toolbar.OnMenuItemClickListener listener) {
        toolbar.setOnMenuItemClickListener(listener);
    }

    public void setToolbarMenu(int resId, Toolbar.OnMenuItemClickListener listener) {
        barInflateMenu(resId);
        barSetOnMenuItemClickListener(listener);
    }

    public Menu getMenu() {
        return toolbar.getMenu();
    }

    public MenuItem findMenuItem(int id) {
        return getMenu().findItem(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setBarShadowVisible(boolean isVisible) {
        if (isVisible) {
            toolBarBackgroundView().setElevation(3);
        } else {
            toolBarBackgroundView().setElevation(0);
        }
    }

    /*********************带返回的返回drawable***********************/

    public Drawable getDefaultBackDrawable(String str) {
        if (str.length() > 2) throw new IllegalArgumentException("back text limit length 2");
        Bitmap photo = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_back);
        int width = photo.getWidth();
        int height = photo.getHeight();
        int text_width = SizeUtils.dp2px(this, 40);
        int paddingLeft = SizeUtils.dp2px(this, 10);
        int textPaddingLeft = SizeUtils.dp2px(this, 2);
        int text_size = SizeUtils.dp2px(this, 16);
        Bitmap icon = Bitmap.createBitmap(width + textPaddingLeft + text_width + paddingLeft, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(icon);
        Paint photoPaint = new Paint();
        photoPaint.setDither(true);
        photoPaint.setFilterBitmap(true);
        photoPaint.setAntiAlias(true);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        Rect src = new Rect(0, 0, photo.getWidth(), photo.getHeight());
        Rect dst = new Rect(paddingLeft, 0, width + paddingLeft, height);
        canvas.drawBitmap(photo, src, dst, photoPaint);
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
        textPaint.setTextSize(text_size);// 字体大小
        textPaint.setTypeface(Typeface.DEFAULT);// 采用默认的宽度
        textPaint.setColor(ContextCompat.getColor(this, R.color.text_color));// 采用的颜色
        //获取计算文字高度类
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        // 计算文字高度
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        // 计算文字baseline
        float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom;
        canvas.translate(width + paddingLeft + textPaddingLeft, textBaseY);
        canvas.drawText(str, 0, 0, textPaint);
        canvas.save();
        canvas.restore();
        return new BitmapDrawable(getResources(), icon);
    }
}
