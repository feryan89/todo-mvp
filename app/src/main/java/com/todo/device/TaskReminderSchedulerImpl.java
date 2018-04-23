package com.todo.device;

import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.todo.data.model.Task;
import com.todo.device.job.service.TaskReminderJobService;

import java.util.Calendar;

import static com.todo.device.job.service.TaskReminderJobService.BUNDLE_TASK_PRIORITY;
import static com.todo.device.job.service.TaskReminderJobService.BUNDLE_TASK_TITLE;

public class TaskReminderSchedulerImpl implements TaskReminderScheduler {

    private final FirebaseJobDispatcher jobDispatcher;

    public TaskReminderSchedulerImpl(FirebaseJobDispatcher jobDispatcher) {
        this.jobDispatcher = jobDispatcher;
    }

    @Override
    public int scheduleTaskReminder(Task task) {

        long reminderMs = task.getReminder();
        long nowMs = Calendar.getInstance().getTime().getTime();
        int diffSecs = (int) ((reminderMs - nowMs) / 1000);

        Bundle extras = new Bundle();
        extras.putString(BUNDLE_TASK_TITLE, task.getTitle());
        extras.putInt(BUNDLE_TASK_PRIORITY, task.getPriority());

        Job job = jobDispatcher.newJobBuilder()
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

        return jobDispatcher.schedule(job);
    }

    @Override
    public int cancelTaskReminder(String taskId) {
        return jobDispatcher.cancel(taskId);
    }
}
