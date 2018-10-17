package com.sunsh.baselibrary.dialog.timepick.listener;

import com.sunsh.baselibrary.dialog.timepick.data.PickTime;

public abstract class TimePickListener implements PickListener {
    private long time = System.currentTimeMillis();

    private String title;

    public String getTitle() {
        return title;
    }

    public long getTime() {
        return time;
    }

    public TimePickListener() {
    }

    public TimePickListener(long time, String title) {
        this.time = time;
        this.title = title;
    }

    public TimePickListener(long time) {
        this.time = time;
    }

    public TimePickListener(String title) {
        this.title = title;
    }

    public abstract void onTimePick(PickTime time);
}
