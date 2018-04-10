package com.todo.data.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Waleed Sarwar
 * @since Dec 11, 2017
 */
public final class Task {

    /********* Constants Fields  ********/

    public final static int PRIORITY_1 = 1;
    public final static int PRIORITY_2 = 2;
    public final static int PRIORITY_3 = 3;
    public final static int PRIORITY_4 = 4;

    /********* Member Fields  ********/

    private String id;
    private String title;
    private long deadline;
    private int priority;
    private boolean completed;

    /********* Constructors ********/

    public Task() {
    }

    public Task(String id, String title, long deadline, int priority, boolean completed) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = completed;
    }

    /********* Member Methods  ********/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    public static Map<String, Object> toHasHMap(Task task) {
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("id", task.getId());
        taskMap.put("title", task.getTitle());
        taskMap.put("deadline", task.getDeadline());
        taskMap.put("priority", task.getPriority());
        taskMap.put("completed", task.isCompleted());
        return taskMap;
    }
}
