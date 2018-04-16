package com.todo.device.notification;

import android.app.Notification;
import android.app.PendingIntent;

import com.todo.data.model.Task;

public interface NotificationFactory {


    public static String NOTIFICATION_GROUP_KEY = "task_reminders";

    Notification createTaskReminderNotification(Task task);
}
