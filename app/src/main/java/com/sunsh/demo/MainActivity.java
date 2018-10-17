package com.sunsh.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunsh.baselibrary.base.activity.BaseBarActivity;
import com.sunsh.baselibrary.widgets.overscrollview.SwipeMenuRecyclerView;

public class MainActivity extends BaseBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getClass().getSimpleName());
        SwipeMenuRecyclerView viewById = findViewById(R.id.rv);
        viewById.setLayoutManager(new LinearLayoutManager(this));
        viewById.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new ViewHolder(new TextView(MainActivity.this));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ((TextView) viewHolder.itemView).setText(i + "");
            }

            @Override
            public int getItemCount() {
                return 200;
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
