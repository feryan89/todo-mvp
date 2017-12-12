package com.todo.ui.feature.tasks;

import android.content.res.Resources;

import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;
import com.todo.ui.validator.EmailValidator;

import javax.inject.Inject;

import timber.log.Timber;

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

    /********* LauncherContract.Presenter Interface Methods ********/

    @Override
    public void addTask() {
        getView().showAddEditTaskActivity();
    }

    @Override
    public void getTasks() {
        todoRepository.getTasks()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(tasks -> {
                    getView().showTasks(tasks);
                }, Timber::e);
    }


}
