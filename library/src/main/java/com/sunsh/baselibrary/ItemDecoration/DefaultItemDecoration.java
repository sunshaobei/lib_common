package com.sunsh.baselibrary.ItemDecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DefaultItemDecoration extends RecyclerView.ItemDecoration {


    private int padingLeft;
    private int padingRiht;
    private int dividerHeight;
    private Paint mPaint;

    public DefaultItemDecoration(int padingLeft, int padingRiht, int dividerColor, int dividerHeight) {
        this.padingLeft = padingLeft;
        this.padingRiht = padingRiht;
        this.dividerHeight = dividerHeight;
        //抗锯齿画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        //填满颜色
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        //当前位置
        int itemPosition = layoutParams.getViewLayoutPosition();
        //ItemView数量
        int childCount = parent.getAdapter().getItemCount();
        outRect.set(0, 0, 0, (itemPosition != childCount - 1) ? 2 : 0);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int width = parent.getMeasuredWidth();
        //getChildCount()(ViewGroup.getChildCount) 返回的是显示层面上的“所包含的子 View 个数”。
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            //item底部的Y轴坐标+margin值
            final int y = child.getBottom() + layoutParams.bottomMargin;
            final int height = y + dividerHeight;
            if (mPaint != null) {
                c.drawRect(padingLeft, y, width - padingRiht, height, mPaint);
            }
        }

    }
}
