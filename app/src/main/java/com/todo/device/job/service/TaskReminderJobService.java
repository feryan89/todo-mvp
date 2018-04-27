package com.todo.device.job.service;

import android.os.Bundle;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.todo.di.application.DaggerApplication;
import com.todo.data.model.TaskModel;
import com.todo.device.notification.NotificationFactory;
import com.todo.device.notification.Notifications;

import javax.inject.Inject;

public final class TaskReminderJobService extends JobService {

    private static final int TASK_REMINDER_NOTIFICATION_ID = 1832;

    public static final String BUNDLE_TASK_TITLE = "bundle_task_title";
    public static final String BUNDLE_TASK_PRIORITY = "bundle_task_priority";


    @Inject
    Notifications notifications;

    @Inject
    NotificationFactory notificationFactory;



    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplication.from(getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        Bundle extras = job.getExtras();
        if (extras != null) {
            TaskModel taskModel = getTask(extras);
            showTaskReminderNotification(taskModel);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    private TaskModel getTask(Bundle extras) {
        TaskModel taskModel = new TaskModel();
        taskModel.setTitle(extras.getString(BUNDLE_TASK_TITLE));
        taskModel.setPriority(extras.getInt(BUNDLE_TASK_PRIORITY));
        return taskModel;
    }

    private void showTaskReminderNotification(TaskModel taskModel) {
        notifications.showNotification(TASK_REMINDER_NOTIFICATION_ID, notificationFactory.createTaskReminderNotification(taskModel));
    }
}




