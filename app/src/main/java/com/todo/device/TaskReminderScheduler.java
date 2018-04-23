package com.todo.device;

import com.todo.data.model.Task;

public interface TaskReminderScheduler {

    int WINDOW = 60; // in seconds


    int scheduleTaskReminder(Task task);

    int cancelTaskReminder(String taskId);
}
