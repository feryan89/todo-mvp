package com.todo.util;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomDateUtils {

    public static String getDisplayDate(long deadline) {

        return DateUtils.getRelativeTimeSpanString(deadline, System.currentTimeMillis(), android.text.format.DateUtils.DAY_IN_MILLIS).toString();
    }

    public static String getDisplayTime(long reminder) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(reminder);
        DateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}
