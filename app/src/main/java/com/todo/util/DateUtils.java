package com.todo.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.todo.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class DateUtils {


    public static String getNow() {
        return Calendar.getInstance().getTime().toString();
    }


    public static boolean isBeforeToday(Calendar calendar) {
        return calendar.before(Calendar.getInstance());
    }

    public static boolean isToday(Calendar calendar) {
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.DAY_OF_MONTH)==calendar.get(Calendar.DAY_OF_MONTH)) {
            if (now.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)) {
                if (now.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)) {
                    return true;
                }
            }
        }

        return false;
    }

    @SuppressWarnings("WrongConstant")
    public static boolean isTomorrow(Calendar calendar) {
        Calendar now = Calendar.getInstance();
        if ((now.get(Calendar.DAY_OF_MONTH)+1)==calendar.get(Calendar.DAY_OF_MONTH)) {
            if (now.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)) {
                if (now.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)) {
                    return true;
                }
            }
        }

        return false;
    }

    @SuppressWarnings("WrongConstant")
    public static boolean isYesterday(Calendar calendar) {
        Calendar now = Calendar.getInstance();
        if ((now.get(Calendar.DAY_OF_MONTH)-1)==calendar.get(Calendar.DAY_OF_MONTH)) {
            if (now.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)) {
                if (now.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)) {
                    return true;
                }
            }
        }

        return false;
    }


    public static String getDisplayDate(@NonNull Context context,
                                        @NonNull Calendar calendar) {
        if (DateUtils.isToday(calendar)) {
            return context.getResources().getString(R.string.all_label_today);
        }
        else if (DateUtils.isTomorrow(calendar)) {
            return context.getResources().getString(R.string.all_label_tomorrow);
        }
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM", Locale.getDefault());
            return dateFormat.format(calendar.getTime());
        }
    }

    public static String getDisplayTime(int hourOfDay,
                                        int minute) {
        DateFormat dateFormat = new SimpleDateFormat("h:mm a",Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        return dateFormat.format(calendar.getTime());
    }
}
