package com.todo.data.model;

import java.io.Serializable;
import java.util.Comparator;

public class TaskModelComparator {

    public static class ByDateComparator implements Comparator<TaskModel>, Serializable {

        @Override
        public int compare(TaskModel taskModel1, TaskModel taskModel2) {
            return Long.compare(taskModel1.getDeadline(), taskModel2.getDeadline());
        }
    }

    public static class ByPriorityComparator implements Comparator<TaskModel>, Serializable {

        @Override
        public int compare(TaskModel taskModel1, TaskModel taskModel2) {
            return Integer.compare(taskModel1.getPriority(), taskModel2.getPriority());
        }
    }

    public static class ByNameComparator implements Comparator<TaskModel>, Serializable {

        @Override
        public int compare(TaskModel taskModel1, TaskModel taskModel2) {
            return taskModel1.getTitle().compareToIgnoreCase(taskModel2.getTitle());
        }
    }
}
