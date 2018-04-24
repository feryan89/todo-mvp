package com.todo.data.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class TaskModelTest {


    private static final String FAKE_TASK_ID = "id";
    private static final String FAKE_TASK_TITLE = "title";
    private static final long FAKE_TASK_DEADLINE = 999999;
    private static final long FAKE_TASK_REMINDER = 999999;
    private static final int FAKE_TASK_PRIORITY = TaskModel.PRIORITY_1;
    private static final boolean FAKE_TASK_COMPLETED = true;


    private TaskModel taskModel;

    @Before
    public void setUp() throws Exception {

        taskModel = new TaskModel();

    }

    @Test
    public void testDefaultConstructor() {

        final String id = taskModel.getId();
        final String title = taskModel.getTitle();
        final long deadline = taskModel.getDeadline();
        final long reminder = taskModel.getReminder();
        final int priority = taskModel.getPriority();
        final boolean completed = taskModel.isCompleted();

        Assert.assertEquals(id, null);
        Assert.assertEquals(title, null);
        Assert.assertEquals(deadline, 0);
        Assert.assertEquals(reminder, 0);
        Assert.assertEquals(priority, 0);
        Assert.assertEquals(completed, false);

    }

    @Test
    public void testParameterizedConstructor() {

        taskModel = new TaskModel(FAKE_TASK_ID, FAKE_TASK_TITLE, FAKE_TASK_DEADLINE, FAKE_TASK_PRIORITY, FAKE_TASK_COMPLETED);

        final String id = taskModel.getId();
        final String title = taskModel.getTitle();
        final long deadline = taskModel.getDeadline();
        final long reminder = taskModel.getReminder();
        final int priority = taskModel.getPriority();
        final boolean completed = taskModel.isCompleted();

        Assert.assertEquals(id, FAKE_TASK_ID);
        Assert.assertEquals(title, FAKE_TASK_TITLE);
        Assert.assertEquals(deadline, FAKE_TASK_DEADLINE);
        Assert.assertEquals(reminder, 0);
        Assert.assertEquals(priority, FAKE_TASK_PRIORITY);
        Assert.assertEquals(completed, FAKE_TASK_COMPLETED);

    }

    @Test
    public void testSetterMethods() {

        taskModel = new TaskModel();
        taskModel.setId(FAKE_TASK_ID);
        taskModel.setTitle(FAKE_TASK_TITLE);
        taskModel.setDeadline(FAKE_TASK_DEADLINE);
        taskModel.setReminder(FAKE_TASK_REMINDER);
        taskModel.setPriority(FAKE_TASK_PRIORITY);
        taskModel.setCompleted(FAKE_TASK_COMPLETED);

        final String id = taskModel.getId();
        final String title = taskModel.getTitle();
        final long deadline = taskModel.getDeadline();
        final long reminder = taskModel.getReminder();
        final int priority = taskModel.getPriority();
        final boolean completed = taskModel.isCompleted();

        Assert.assertEquals(id, FAKE_TASK_ID);
        Assert.assertEquals(title, FAKE_TASK_TITLE);
        Assert.assertEquals(deadline, FAKE_TASK_DEADLINE);
        Assert.assertEquals(reminder, FAKE_TASK_REMINDER);
        Assert.assertEquals(priority, FAKE_TASK_PRIORITY);
        Assert.assertEquals(completed, FAKE_TASK_COMPLETED);

    }

    @Test
    public void testGetMap() {

        taskModel = new TaskModel(FAKE_TASK_ID, FAKE_TASK_TITLE, FAKE_TASK_DEADLINE, FAKE_TASK_PRIORITY, FAKE_TASK_COMPLETED);
        taskModel.setReminder(FAKE_TASK_REMINDER);

        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("id", FAKE_TASK_ID);
        expectedMap.put("title", FAKE_TASK_TITLE);
        expectedMap.put("deadline", FAKE_TASK_DEADLINE);
        expectedMap.put("reminder", FAKE_TASK_REMINDER);
        expectedMap.put("priority", FAKE_TASK_PRIORITY);
        expectedMap.put("completed", FAKE_TASK_COMPLETED);

        Map<String, Object> actualMap = taskModel.getMap();

        Assert.assertEquals(expectedMap, actualMap);


    }
}