package com.todo.ui.feature.tasks;

import com.todo.ui.base.BaseContract;

public interface TasksContract {

    interface View extends BaseContract.View {

        void showAddEditTaskActivity();
    }

    interface Presenter extends BaseContract.Presenter<TasksContract.View> {

        void addTask();
    }
}
