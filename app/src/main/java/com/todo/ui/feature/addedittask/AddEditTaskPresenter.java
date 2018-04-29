package com.todo.ui.feature.addedittask;

import android.content.res.Resources;
import android.support.annotation.VisibleForTesting;

import com.todo.R;
import com.todo.data.model.TaskModel;
import com.todo.data.repository.TodoRepository;
import com.todo.device.TaskReminderScheduler;
import com.todo.ui.base.BasePresenter;
import com.todo.util.StringUtils;
import com.todo.util.StringUtilsImpl;

import javax.inject.Inject;

public final class AddEditTaskPresenter extends BasePresenter<AddEditTaskContract.View> implements AddEditTaskContract.Presenter {

    /********* Dagger Injected Fields  ********/

    @Inject
    TodoRepository todoRepository;

    @Inject
    TaskReminderScheduler taskReminderScheduler;

    @Inject
    StringUtils stringUtils;

    @Inject
    Resources resources;

    public AddEditTaskPresenter() {
        super();
    }

    @Override
    public void attachView(AddEditTaskContract.View view) {
        super.attachView(view);
    }

    @Override
    public void createTask(TaskModel taskModel) {
        if (stringUtils.isNotEmpty(taskModel.getTitle())) {
            addDisposable(todoRepository.createTask(taskModel).subscribe(this::scheduleReminder));
            getView().finishActivity();
        } else {
            getView().showSnackBar(resources.getString(R.string.add_edit_task_error_invalid_title));
        }
    }

    @Override
    public void updateTask(TaskModel taskModel) {
        if (stringUtils.isNotEmpty(taskModel.getTitle())) {
            todoRepository.updateTask(taskModel);
            getView().finishActivity();
        } else {
            getView().showSnackBar(resources.getString(R.string.add_edit_task_error_invalid_title));
        }
    }
    @VisibleForTesting
    protected void scheduleReminder(TaskModel taskModel) {
        if (taskModel.getReminder() > 0) {
            taskReminderScheduler.scheduleTaskReminder(taskModel);
        }
    }
}
