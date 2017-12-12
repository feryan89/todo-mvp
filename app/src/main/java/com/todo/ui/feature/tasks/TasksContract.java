package com.todo.ui.feature.tasks;

import com.todo.data.model.Task;
import com.todo.ui.base.BaseContract;

import java.util.List;

public interface TasksContract {

    interface View extends BaseContract.View {

        void showAddEditTaskActivity();

        void showTasks(List<Task> tasks);
    }

    interface Presenter extends BaseContract.Presenter<TasksContract.View> {

        void addTask();

        void getTasks();
    }
}
