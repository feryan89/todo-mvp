package com.todo.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Waleed Sarwar
 * @since Dec 11, 2017
 */
public final class TaskModel implements Parcelable {

    /********* Constants Fields  ********/

    public final static int PRIORITY_1 = 1;
    public final static int PRIORITY_2 = 2;
    public final static int PRIORITY_3 = 3;
    public final static int PRIORITY_4 = 4;
    public static final Creator<TaskModel> CREATOR = new Creator<TaskModel>() {
        @Override
        public TaskModel createFromParcel(Parcel source) {
            return new TaskModel(source);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };
    /********* Member Fields  ********/

    private String id;
    private String title;
    private long deadline;
    private long reminder;
    private int priority;
    private boolean completed;

    /********* Static Methods  ********/

    public static Map<String, Object> toHasHMap(TaskModel taskModel) {
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("id", taskModel.getId());
        taskMap.put("title", taskModel.getTitle());
        taskMap.put("deadline", taskModel.getDeadline());
        taskMap.put("priority", taskModel.getPriority());
        taskMap.put("completed", taskModel.isCompleted());
        return taskMap;
    }

    /********* Constructors ********/

    public TaskModel() {
    }

    public TaskModel(String id, String title, long deadline, int priority, boolean completed) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = completed;
    }

    protected TaskModel(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.deadline = in.readLong();
        this.reminder = in.readLong();
        this.priority = in.readInt();
        this.completed = in.readByte() != 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeLong(this.deadline);
        dest.writeLong(this.reminder);
        dest.writeInt(this.priority);
        dest.writeByte(this.completed ? (byte) 1 : (byte) 0);
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

    public long getReminder() {
        return reminder;
    }

    public void setReminder(long reminder) {
        this.reminder = reminder;
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


}
