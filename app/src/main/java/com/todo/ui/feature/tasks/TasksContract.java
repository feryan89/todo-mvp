package com.todo.ui.feature.tasks;

import com.todo.data.model.TaskModel;
import com.todo.ui.base.BaseContract;

import java.util.List;

public interface TasksContract {

    interface View extends BaseContract.View {

        void showAddEditTaskActivity();

        void showTasks(List<TaskModel> taskModels);

        void showTasksEmptyView();

        void hideTasksEmptyView();

    }

    interface Presenter extends BaseContract.Presenter<TasksContract.View> {


        void setTasksSortType(TasksSortType tasksSortType);

        void addTask();

        void getTasks();

        void deleteTask(int position,TaskModel taskModel);

        void updateTask(TaskModel taskModel);
    }
}
