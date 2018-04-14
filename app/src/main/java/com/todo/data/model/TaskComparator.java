package com.todo.data.model;

import java.util.Comparator;

public class TaskComparator {

    public static class ByDateComparator implements Comparator<Task>{

        @Override
        public int compare(Task task1, Task task2) {
            return Long.compare(task1.getDeadline(), task2.getDeadline());
        }
    }
    public static class ByPriorityComparator implements Comparator<Task>{

        @Override
        public int compare(Task task1, Task task2) {
            return Integer.compare(task1.getPriority(), task2.getPriority());
        }
    }

    public static class ByNameComparator implements Comparator<Task>{

        @Override
        public int compare(Task task1, Task task2) {
            return task1.getTitle().compareToIgnoreCase(task2.getTitle());
        }
    }
}
