package com.todo.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Waleed Sarwar
 * @since Dec 11, 2017
 */
public final class Task implements Parcelable {

    /********* Constants Fields  ********/

    public final static int PRIORITY_1 = 1;
    public final static int PRIORITY_2 = 2;
    public final static int PRIORITY_3 = 3;
    public final static int PRIORITY_4 = 4;
    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    /********* Member Fields  ********/

    private String id;
    private String title;
    private long deadline;
    private int priority;
    private boolean completed;

    /********* Static Methods  ********/

    public static Map<String, Object> toHasHMap(Task task) {
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("id", task.getId());
        taskMap.put("title", task.getTitle());
        taskMap.put("deadline", task.getDeadline());
        taskMap.put("priority", task.getPriority());
        taskMap.put("completed", task.isCompleted());
        return taskMap;
    }


    /********* Constructors ********/

    public Task() {
    }

    protected Task(Parcel in) {
        id = in.readString();
        title = in.readString();
        deadline = in.readLong();
        priority = in.readInt();
        completed = in.readByte() != 0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeLong(deadline);
        dest.writeInt(priority);
        dest.writeByte((byte) (completed ? 1 : 0));
    }
}
