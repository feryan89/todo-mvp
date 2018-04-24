package com.todo.util;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtilsImpl implements com.todo.util.DateUtils {


    private CurrentTimeProvider currentTimeProvider;

    public DateUtilsImpl(CurrentTimeProvider currentTimeProvider) {
        this.currentTimeProvider = currentTimeProvider;
    }

    public String getDisplayDate(long deadline) {

        return DateUtils.getRelativeTimeSpanString(deadline, currentTimeProvider.getCurrentTimeMillis(), DateUtils.DAY_IN_MILLIS).toString();
    }

    public String getDisplayTime(long reminder) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(reminder);
        DateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

}
