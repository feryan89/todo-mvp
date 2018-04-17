package com.todo.device;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.todo.data.model.Task;
import com.todo.device.job.service.TaskReminderJobService;

public class TaskReminderSchedulerImpl implements TaskReminderScheduler {

    private final FirebaseJobDispatcher jobDispatcher;

    public TaskReminderSchedulerImpl(FirebaseJobDispatcher jobDispatcher) {
        this.jobDispatcher = jobDispatcher;
    }

    @Override
    public void scheduleTaskReminder(Task task) {
        Job job = TaskReminderJobService.createJob(jobDispatcher.newJobBuilder(), task);
        jobDispatcher.schedule(job);
    }

    @Override
    public void cancelTaskReminder(String taskId) {
        jobDispatcher.cancel(taskId);
    }
}
