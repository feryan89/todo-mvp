package com.todo.ui.feature.tasks;

import android.content.res.Resources;

import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;
import com.todo.ui.validator.EmailValidator;

import javax.inject.Inject;

public final class TasksPresenter extends BasePresenter<TasksContract.View> implements TasksContract.Presenter {

    /********* Dagger Injected Fields  ********/

    @Inject
    TodoRepository todoRepository;

    @Inject
    Resources resources;

    @Inject
    EmailValidator emailValidator;

    /********* Constructors ********/

    public TasksPresenter() {
        super();
    }

    /********* BaseContract.Presenter Interface Methods Implementation ********/

    /********* LauncherContract.Presenter Interface Methods Implementation ********/

    @Override
    public void addTask() {
        getView().showAddEditTaskActivity();
    }

    /********* Member Methods Implementation ********/

}
