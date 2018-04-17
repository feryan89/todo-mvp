package com.todo.util;

import android.content.Context;
import android.view.View;

import com.todo.R;
import com.todo.data.model.Task;

public class UiUtils {

    public static int getPriorityColorRes(int priority) {

        switch (priority) {

            case Task.PRIORITY_1:
                return R.color.task_priority_1;
            case Task.PRIORITY_2:
                return R.color.task_priority_2;
            case Task.PRIORITY_3:
                return R.color.task_priority_3;
            case Task.PRIORITY_4:
                return R.color.task_priority_4;
            default:
                return R.color.all_white;
        }
    }


    public static String getPriorityString(Context context, int priority) {

        switch (priority) {

            case Task.PRIORITY_1:
                return context.getString(R.string.all_label_priority_1);
            case Task.PRIORITY_2:
                return context.getString(R.string.all_label_priority_2);
            case Task.PRIORITY_3:
                return context.getString(R.string.all_label_priority_3);
            case Task.PRIORITY_4:
                return context.getString(R.string.all_label_priority_4);
            default:
                return context.getString(R.string.all_label_priority_1);
        }
    }

    public static void disableWithAlpha(View... views) {
        for (View v : views) {
            v.setEnabled(false);
            v.setAlpha(0.2f);
        }
    }

    public static void enableWithAlpha(View... views) {
        for (View v : views) {
            v.setEnabled(true);
            v.setAlpha(1);
        }
    }

}
