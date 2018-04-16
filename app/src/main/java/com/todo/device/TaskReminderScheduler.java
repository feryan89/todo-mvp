package com.todo.device;

import com.todo.data.model.Task;

public interface TaskReminderScheduler {

    public static int WINDOW = 60; // in seconds


    void scheduleTaskReminder(Task task);

    void cancelTaskReminder(String taskId);
}
