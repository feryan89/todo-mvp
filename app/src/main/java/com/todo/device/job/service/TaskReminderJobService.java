package com.todo.device.job.service;

import android.app.PendingIntent;
import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
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
import com.todo.util.scheduler.SchedulerProvider;

import java.util.Calendar;

import javax.inject.Inject;

public final class TaskReminderJobService extends JobService {

    private static final int TASK_REMINDER_NOTIFICATION_ID = 1832;
    private static final String BUNDLE_TASK = "bundle_task";


    @Inject
    Notifications notifications;

    @Inject
    NotificationFactory notificationFactory;


    public static Job createJob(Job.Builder jobBuilder, Task task) {
        long reminderMs = task.getReminder();
        long nowMs = Calendar.getInstance().getTime().getTime();
        int diffSecs = (int) ((reminderMs - nowMs) / 1000);

        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_TASK, task);

        return jobBuilder
                .setService(TaskReminderJobService.class)
                .setTag(task.getId())
                .setRecurring(false)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(diffSecs, (diffSecs + 60)))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setExtras(new Bundle())
                .build();

    }


    @Override
    public void onCreate() {
        super.onCreate();
        TodoApplication.from(getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        Bundle bundle = job.getExtras();
        if (bundle != null) {
            Task task = bundle.getParcelable(BUNDLE_TASK);
            showTaskReminderNotification(task);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    private void showTaskReminderNotification(Task task) {
        notifications.showNotification(TASK_REMINDER_NOTIFICATION_ID, notificationFactory.createTaskReminderNotification(task));
    }
}




