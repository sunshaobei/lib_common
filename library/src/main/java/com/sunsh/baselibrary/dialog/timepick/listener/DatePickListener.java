package com.sunsh.baselibrary.dialog.timepick.listener;

import com.sunsh.baselibrary.dialog.timepick.data.PickDate;

import java.util.Calendar;

public abstract class DatePickListener implements PickListener {

    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private int month = Calendar.getInstance().get(Calendar.MONTH);
    private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    public DatePickListener() {
    }

    public DatePickListener(int year,int month,int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public abstract void onDatePick(PickDate date);
}
