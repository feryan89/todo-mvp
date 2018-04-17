package com.todo.device.job.service;

import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.todo.TodoApplication;
import com.todo.data.model.Task;
import com.todo.device.notification.NotificationFactory;
import com.todo.device.notification.Notifications;

import java.util.Calendar;

import javax.inject.Inject;

public final class TaskReminderJobService extends JobService {

    private static final int TASK_REMINDER_NOTIFICATION_ID = 1832;

    private static final String BUNDLE_TASK_TITLE = "bundle_task_title";
    private static final String BUNDLE_TASK_PRIORITY = "bundle_task_priority";


    @Inject
    Notifications notifications;

    @Inject
    NotificationFactory notificationFactory;


    public static Job createJob(Job.Builder jobBuilder, Task task) {
        long reminderMs = task.getReminder();
        long nowMs = Calendar.getInstance().getTime().getTime();
        int diffSecs = (int) ((reminderMs - nowMs) / 1000);

        Bundle extras = new Bundle();
        extras.putString(BUNDLE_TASK_TITLE, task.getTitle());
        extras.putInt(BUNDLE_TASK_PRIORITY, task.getPriority());

        return jobBuilder
                .setService(TaskReminderJobService.class)
                .setTag(task.getId())
                .setRecurring(false)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(diffSecs, (diffSecs + 60)))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setExtras(extras)
                .build();

    }


    @Override
    public void onCreate() {
        super.onCreate();
        TodoApplication.from(getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        Bundle extras = job.getExtras();
        if (extras != null) {
            Task task = getTask(extras);
            showTaskReminderNotification(task);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    private Task getTask(Bundle extras) {
        Task task = new Task();
        task.setTitle(extras.getString(BUNDLE_TASK_TITLE));
        task.setPriority(extras.getInt(BUNDLE_TASK_PRIORITY));
        return task;
    }

    private void showTaskReminderNotification(Task task) {
        notifications.showNotification(TASK_REMINDER_NOTIFICATION_ID, notificationFactory.createTaskReminderNotification(task));
    }
}




