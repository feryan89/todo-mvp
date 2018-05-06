package com.todo.ui.feature.tasks;

import android.content.res.Resources;

import com.todo.BuildConfig;
import com.todo.data.model.TaskModel;
import com.todo.data.model.TaskModelComparator;
import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;

import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.functions.Action;
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
        tasksSortType = TasksSortType.BY_DATE;
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
                .compose(uiSchedulersTransformer.applySchedulersToObservable())
                .map(tasks -> {
                    switch (tasksSortType) {
                        case BY_PRIORITY:
                            Collections.sort(tasks, new TaskModelComparator.ByPriorityComparator());
                            break;
                        case BY_TITLE:
                            Collections.sort(tasks, new TaskModelComparator.ByTitleComparator());
                            break;
                        case BY_DATE:
                        default:
                            Collections.sort(tasks, new TaskModelComparator.ByDateComparator());
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
    public void logout() {
        if (BuildConfig.DEBUG) {
            addDisposable(todoRepository.deleteTasks().subscribe(() -> {
                todoRepository.logout();
                getView().showLoginUi();
            }));
        } else {
            todoRepository.logout();
            getView().showLoginUi();
        }
    }

    @Override
    public void deleteTask(TaskModel taskModel) {
        addDisposable(todoRepository.deleteTask(taskModel)
                .compose(uiSchedulersTransformer.applySchedulersToCompletable())
                .subscribe(this::getTasks, Timber::e));
    }

    @Override
    public void updateTask(TaskModel taskModel) {
        addDisposable(todoRepository.updateTask(taskModel)
                .compose(uiSchedulersTransformer.applySchedulersToCompletable())
                .subscribe(() -> Timber.d("TaskModel updated successfully")
                        , throwable -> {
                            getTasks();
                            Timber.e(throwable);
                        }));
    }


}
