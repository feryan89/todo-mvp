package com.todo.device.notification;

import android.app.Notification;

import com.todo.data.model.TaskModel;

public interface NotificationFactory {


    String CHANNEL_ID_REMINDERS = "channel_reminders";

    String GROUP_KEY_TASK_REMINDERS = "group_key_task_reminders";

    Notification createTaskReminderNotification(TaskModel taskModel);
}
