package com.sunsh.baselibrary.adapter.recyclerview.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public  abstract class HeaderOrFootDelegate<T> {
     public Context mContext;
     private ViewHolder holder;
     public HeaderOrFootDelegate(Context context){
          this.mContext =context;
     }
     public ViewHolder getHolder(){
         if (holder==null){
             Object itemViewLayout = getItemViewLayout();
             if (itemViewLayout instanceof View) {
                 holder = ViewHolder.createViewHolder(mContext, (View) itemViewLayout);
             } else {
                 holder = ViewHolder.createViewHolder(mContext, LayoutInflater.from(mContext).inflate((Integer) itemViewLayout, null));
             }
         }
         return holder;
     }
    public abstract Object getItemViewLayout();
    public abstract void bindData(T obj);
}
