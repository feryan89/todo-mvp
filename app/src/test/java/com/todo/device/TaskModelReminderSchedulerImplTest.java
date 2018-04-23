package com.todo.device;

import android.os.Bundle;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.todo.data.model.TaskModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TaskModelReminderSchedulerImplTest {


    private TaskReminderSchedulerImpl taskReminderSchedulerImpl;
    private FirebaseJobDispatcher jobDispatcher;
    private Job job;
    private TaskModel taskModel;
    private Bundle bundle;

    @Before
    public void setUp() throws Exception {
        jobDispatcher = Mockito.mock(FirebaseJobDispatcher.class);
        taskModel = Mockito.mock(TaskModel.class);
        job = Mockito.mock(Job.class);
        bundle = Mockito.mock(Bundle.class);
        taskReminderSchedulerImpl = new TaskReminderSchedulerImpl(jobDispatcher);
    }

    // TODO: 23/04/2018
    @Test
    public void scheduleTaskReminder_shouldReturnSuccess() throws Exception {

    }

}