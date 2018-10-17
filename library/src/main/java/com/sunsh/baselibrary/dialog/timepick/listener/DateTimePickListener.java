package com.sunsh.baselibrary.dialog.timepick.listener;

import com.sunsh.baselibrary.dialog.timepick.data.PickDateTiem;

public abstract class DateTimePickListener implements PickListener {
    private long time = System.currentTimeMillis();

    private String title;

    public String getTitle() {
        return title;
    }

    public long getTime() {
        return time;
    }

    public DateTimePickListener() {
    }

    public DateTimePickListener(long time, String title) {
        this.time = time;
        this.title = title;
    }

    public DateTimePickListener(long time) {
        this.time = time;
    }

    public DateTimePickListener(String title) {
        this.title = title;
    }

    public abstract void onDateTimePick(PickDateTiem time);
}
