package com.sunsh.baselibrary.widgets.overscroll;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class OverScrollWithHeadLayout extends OverScrollLayout{

    public OverScrollWithHeadLayout(Context context) {
        super(context);
    }

    public OverScrollWithHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void moveCountent(int distance) {
        if (distance>=0){
            LayoutParams layoutParams = headView.getLayoutParams();
            layoutParams.height = headOriginHeight+ distance;
            headView.setLayoutParams(layoutParams);
        }
        if (distance<=0){
            pullContentLayout.setTranslationY(distance);
        }
    }

    private View headView;
    private int headOriginHeight;

    public void setHeadView(View headView) {
        this.headView = headView;
        int intw = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int inth = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        headView.measure(intw, inth);
        headOriginHeight = headView.getMeasuredHeight();

    }

}