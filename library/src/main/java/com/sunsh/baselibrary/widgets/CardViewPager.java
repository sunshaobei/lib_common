package com.sunsh.baselibrary.widgets;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sunsh.baselibrary.utils.SizeUtils;

import java.util.List;

public class CardViewPager extends ViewPager {

    private int padding = 70;
    //    private static final int pageMargin = 25;
    private static final float mScale = 0.6f;

    public CardViewPager(@NonNull Context context) {
        super(context);
        defaultInit();
    }

    public CardViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        defaultInit();
    }

    private void defaultInit() {
        setPadding(getPaddingLeft() == 0 ? SizeUtils.dp2px(getContext(), padding) : getPaddingLeft(), getPaddingTop(), getPaddingRight() == 0 ? SizeUtils.dp2px(getContext(), padding) : getPaddingRight(), getPaddingBottom());
//        setPageMargin(SizeUtils.dp2px(getContext(), pageMargin));
        setClipToPadding(false);
        setPageTransformer(false, new ScaleTransformer(getContext()));
    }

    private class ScaleTransformer implements ViewPager.PageTransformer {
        private Context context;
        private float elevation;

        public ScaleTransformer(Context context) {
            this.context = context;
            elevation = SizeUtils.dp2px(context, 20);
        }

        @Override
        public void transformPage(View page, float offset) {
            if (page.getWidth() == 0) {
                ((CardView) page).setCardElevation(elevation);
                page.setScaleY(1f);
            } else {
                float v = (float) getPaddingLeft() / (float) page.getWidth();
                if (offset < -1 || offset > 1) {

                } else {
                    if (offset < v) {
                        ((CardView) page).setCardElevation((1 + offset - v) * elevation);
                        page.setScaleY((float) (0.6 + 0.4 * (1 + offset - v)));
                        page.setScaleX((float) (0.6 + 0.4 * (1 + offset - v)));
                    } else {
                        ((CardView) page).setCardElevation((1 - offset + v) * elevation);
                        page.setScaleY((float) (0.6 + 0.4 * (1 - offset + v)));
                        page.setScaleX((float) (0.6 + 0.4 * (1 - offset + v)));
                    }
                }
            }
        }
    }

    public void setAdapter(@Nullable CardPagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    public static abstract class CardPagerAdapter<T> extends PagerAdapter {
        private Context context;
        private LayoutInflater inflater;
        private int layout;
        private List<T> list;

        public CardPagerAdapter(Context context, @LayoutRes int layout, List<T> list) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.layout = layout;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            CardView cardView = new CardView(context);
            cardView.setScaleY(mScale);
            cardView.setScaleX(mScale);
            View inflate = inflater.inflate(layout, null);
            cardView.addView(inflate);
            conver(cardView, list.get(position), position);
            container.addView(cardView);
            return cardView;
        }

        protected abstract void conver(CardView cardView, T t, int position);


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
