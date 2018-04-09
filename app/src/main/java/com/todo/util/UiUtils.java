package com.todo.util;

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
}
