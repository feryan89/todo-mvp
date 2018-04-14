package com.todo.ui.feature.tasks;

import android.content.res.Resources;

import com.todo.data.model.Task;
import com.todo.data.model.TaskComparator;
import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;
import rx.functions.Func2;
import timber.log.Timber;

public final class TasksPresenter extends BasePresenter<TasksContract.View> implements TasksContract.Presenter {

    /********* Dagger Injected Fields  ********/

    @Inject
    TodoRepository todoRepository;

    @Inject
    Resources resources;

    private TasksSortType tasksSortType;

    /********* Constructors ********/

    public TasksPresenter() {
        super();
    }

    @Override
    public void setTasksSortType(TasksSortType tasksSortType) {
        this.tasksSortType = tasksSortType;
    }

    /********* LauncherContract.Presenter Interface Methods ********/

    @Override
    public void addTask() {
        getView().showAddEditTaskActivity();
    }

    @Override
    public void getTasks() {
        addSubscription(todoRepository.getTasks()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .map(tasks -> {
                    switch (tasksSortType) {
                        case BY_DATE:
                            Collections.sort(tasks, new TaskComparator.ByDateComparator());
                        case BY_PRIORITY:
                            Collections.sort(tasks, new TaskComparator.ByPriorityComparator());
                        case BY_NAME:
                            Collections.sort(tasks, new TaskComparator.ByNameComparator());
                    }
                    return tasks;
                })
                .subscribe(tasks -> {
                    if (tasks.isEmpty()) {
                        getView().showTasksEmptyView();
                    } else {
                        getView().hideTasksEmptyView();
                        getView().showTasks(tasks);
                    }
                }, Timber::e));
    }

    @Override
    public void deleteTask(int position, Task task) {
        addSubscription(todoRepository.deleteTask(task)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::getTasks
                        , Timber::e));
    }

    @Override
    public void updateTask(Task task) {
        addSubscription(todoRepository.updateTask(task)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(() -> Timber.d("Task updated successfully")
                        , throwable -> {
                            getTasks();
                            Timber.e(throwable);
                        }));
    }


}
