package com.todo.ui.feature.tasks;

import android.content.res.Resources;

import com.todo.data.model.TaskModel;
import com.todo.data.model.TaskModelComparator;
import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;

import java.util.Collections;

import javax.inject.Inject;

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
        addDisposable(todoRepository.getTasks()
                .compose(applySchedulersToObservable())
                .map(tasks -> {
                    switch (tasksSortType) {
                        case BY_DATE:
                            Collections.sort(tasks, new TaskModelComparator.ByDateComparator());
                            break;
                        case BY_PRIORITY:
                            Collections.sort(tasks, new TaskModelComparator.ByPriorityComparator());
                            break;
                        case BY_NAME:
                            Collections.sort(tasks, new TaskModelComparator.ByTitleComparator());
                            break;
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
    public void deleteTask(int position, TaskModel taskModel) {
        addDisposable(todoRepository.deleteTask(taskModel)
                .compose(applySchedulersToCompletable())
                .subscribe(this::getTasks, Timber::e));
    }

    @Override
    public void updateTask(TaskModel taskModel) {
        addDisposable(todoRepository.updateTask(taskModel)
                .compose(applySchedulersToCompletable())
                .subscribe(() -> Timber.d("TaskModel updated successfully")
                        , throwable -> {
                            getTasks();
                            Timber.e(throwable);
                        }));
    }


}
