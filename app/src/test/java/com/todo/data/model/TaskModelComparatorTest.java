package com.todo.data.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TaskModelComparatorTest {


    private TaskModelComparator.ByDateComparator byDateComparator;
    private TaskModelComparator.ByTitleComparator byTitleComparator;
    private TaskModelComparator.ByPriorityComparator byPriorityComparator;


    @Before
    public void setUp() throws Exception {
        byDateComparator = new TaskModelComparator.ByDateComparator();
        byTitleComparator = new TaskModelComparator.ByTitleComparator();
        byPriorityComparator = new TaskModelComparator.ByPriorityComparator();
    }

    @Test
    public void byDateComparator_shouldReturnEqual() {

        TaskModel fakeTask1 = new TaskModel("id1", "1title", 999999, TaskModel.PRIORITY_1, true);
        TaskModel fakeTask2 = new TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_2, false);

        int result = byDateComparator.compare(fakeTask1, fakeTask2);
        Assert.assertTrue("Expected to be equal", result == 0);


    }

    @Test
    public void byDateComparator_shouldReturnGreaterThan() {

        TaskModel fakeTask1 = new TaskModel("id1", "1title", 100000000, TaskModel.PRIORITY_1, true);
        TaskModel fakeTask2 = new TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_2, false);

        int result = byDateComparator.compare(fakeTask1, fakeTask2);
        Assert.assertTrue("Expected to be greater than", result >= 1);


    }

    @Test
    public void byDateComparator_shouldReturnLessThan() {

        TaskModel fakeTask1 = new TaskModel("id1", "1title", 999998, TaskModel.PRIORITY_1, true);
        TaskModel fakeTask2 = new TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_2, false);

        int result = byDateComparator.compare(fakeTask1, fakeTask2);
        Assert.assertTrue("Expected to be less than", result <= -1);

    }

    @Test
    public void byTitleComparator_shouldReturnEqual() {

        TaskModel fakeTask1 = new TaskModel("id1", "title", 999999, TaskModel.PRIORITY_1, true);
        TaskModel fakeTask2 = new TaskModel("id2", "title", 999999, TaskModel.PRIORITY_2, false);

        int result = byTitleComparator.compare(fakeTask1, fakeTask2);
        Assert.assertTrue("Expected to be equal", result == 0);


    }

    @Test
    public void byTitleComparator_shouldReturnGreaterThan() {

        TaskModel fakeTask1 = new TaskModel("id1", "2title", 100000000, TaskModel.PRIORITY_1, true);
        TaskModel fakeTask2 = new TaskModel("id2", "1title", 999999, TaskModel.PRIORITY_2, false);

        int result = byTitleComparator.compare(fakeTask1, fakeTask2);
        Assert.assertTrue("Expected to be greater than", result >= 1);


    }

    @Test
    public void byTitleComparator_shouldReturnLessThan() {

        TaskModel fakeTask1 = new TaskModel("id1", "1title", 999998, TaskModel.PRIORITY_1, true);
        TaskModel fakeTask2 = new TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_2, false);

        int result = byTitleComparator.compare(fakeTask1, fakeTask2);
        Assert.assertTrue("Expected to be less than", result <= -1);

    }

    @Test
    public void byPriorityComparator_shouldReturnEqual() {

        TaskModel fakeTask1 = new TaskModel("id1", "1title", 999999, TaskModel.PRIORITY_1, true);
        TaskModel fakeTask2 = new TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_1, false);

        int result = byPriorityComparator.compare(fakeTask1, fakeTask2);
        Assert.assertTrue("Expected to be equal", result == 0);


    }

    @Test
    public void byPriorityComparator_shouldReturnGreaterThan() {

        TaskModel fakeTask1 = new TaskModel("id1", "1title", 100000000, TaskModel.PRIORITY_2, true);
        TaskModel fakeTask2 = new TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_1, false);

        int result = byPriorityComparator.compare(fakeTask1, fakeTask2);
        Assert.assertTrue("Expected to be greater than", result >= 1);


    }

    @Test
    public void byPriorityComparator_shouldReturnLessThan() {

        TaskModel fakeTask1 = new TaskModel("id1", "1title", 999998, TaskModel.PRIORITY_1, true);
        TaskModel fakeTask2 = new TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_2, false);

        int result = byPriorityComparator.compare(fakeTask1, fakeTask2);
        Assert.assertTrue("Expected to be less than", result <= -1);

    }
}