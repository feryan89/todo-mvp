package com.todo.device;

import android.os.Bundle;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.todo.data.model.Task;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TaskReminderSchedulerImplTest {


    private TaskReminderSchedulerImpl taskReminderSchedulerImpl;
    private FirebaseJobDispatcher jobDispatcher;
    private Job job;
    private Task task;
    private Bundle bundle;

    @Before
    public void setUp() throws Exception {
        jobDispatcher = Mockito.mock(FirebaseJobDispatcher.class);
        task = Mockito.mock(Task.class);
        job = Mockito.mock(Job.class);
        bundle = Mockito.mock(Bundle.class);
        taskReminderSchedulerImpl = new TaskReminderSchedulerImpl(jobDispatcher);
    }

    // TODO: 23/04/2018
    @Test
    public void scheduleTaskReminder_shouldReturnSuccess() throws Exception {

    }

}