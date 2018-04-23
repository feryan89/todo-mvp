package com.todo.ui.feature.addedittask;

import com.todo.data.model.TaskModel;
import com.todo.ui.base.BaseContract;

public interface AddEditTaskContract {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter<View> {
        void createTask(TaskModel taskModel);
        void updateTask(TaskModel taskModel);
    }
}
