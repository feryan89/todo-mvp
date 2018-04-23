package com.todo.device;

import com.todo.data.model.TaskModel;

public interface TaskReminderScheduler {

    int WINDOW = 60; // in seconds


    int scheduleTaskReminder(TaskModel taskModel);

    int cancelTaskReminder(String taskId);
}
