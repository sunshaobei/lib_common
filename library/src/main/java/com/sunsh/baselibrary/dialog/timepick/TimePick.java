package com.sunsh.baselibrary.dialog.timepick;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.TextUtils;

import com.sunsh.baselibrary.dialog.timepick.data.PickDate;
import com.sunsh.baselibrary.dialog.timepick.data.PickDateTiem;
import com.sunsh.baselibrary.dialog.timepick.data.PickTime;
import com.sunsh.baselibrary.dialog.timepick.listener.DatePickListener;
import com.sunsh.baselibrary.dialog.timepick.listener.DateTimePickListener;
import com.sunsh.baselibrary.dialog.timepick.listener.PickListener;
import com.sunsh.baselibrary.dialog.timepick.listener.TimePickListener;

import java.util.Calendar;
import java.util.Date;

public class TimePick {
    private Dialog dialog;


    public TimePick(Context context, PickListener pickListener) {
        // 用来获取日期和时间的
        Calendar calendar = Calendar.getInstance();
        if (pickListener instanceof TimePickListener) {
            calendar.setTime(new Date(((TimePickListener) pickListener).getTime()));
            TimePickerDialog.OnTimeSetListener timeListener = (timerPicker, hourOfDay, minute) -> ((TimePickListener) pickListener).onTimePick(new PickTime(hourOfDay, minute));
            dialog = new TimePickerDialog(context, timeListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), true); // 是否为二十四制
            String title = ((TimePickListener) pickListener).getTitle();
            if (!TextUtils.isEmpty(title)) dialog.setTitle(title);
        } else if (pickListener instanceof DatePickListener) {
            calendar.set(((DatePickListener) pickListener).getYear(),((DatePickListener) pickListener).getMonth(),((DatePickListener) pickListener).getDay());
            DatePickerDialog.OnDateSetListener dateListener = (datePicker, year, month, dayOfMonth) -> {
                // Calendar月份是从0开始,所以month要加1
                ((DatePickListener) pickListener).onDatePick(new PickDate(year, month, dayOfMonth));
            };
            dialog = new DatePickerDialog(context, dateListener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
        } else if (pickListener instanceof DateTimePickListener) {
            calendar.setTime(new Date(((DateTimePickListener) pickListener).getTime()));
            DatePickerDialog.OnDateSetListener dateListener = (datePicker, year, month, dayOfMonth) -> {
                // Calendar月份是从0开始,所以month要加1
                TimePick timePick = new TimePick(context, new TimePickListener(year + "年" + (month + 1) + "月" + dayOfMonth + "日") {
                    @Override
                    public void onTimePick(PickTime time) {
                        ((DateTimePickListener) pickListener).onDateTimePick(new PickDateTiem(year, month, dayOfMonth, time.getHourOfDay(), time.getMinute()));
                    }
                });
                timePick.show();
            };
            dialog = new DatePickerDialog(context, dateListener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            throw new IllegalArgumentException("请使用picklistener子类");
        }
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
