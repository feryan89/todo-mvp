package com.todo.ui.feature.tasks;

import android.content.res.Resources;

import com.todo.data.model.Task;
import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;

import javax.inject.Inject;

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
                .flatMapIterable(tasks -> tasks)
                .sorted((task1, task2) -> {
                    switch (tasksSortType) {
                        case BY_DATE:
                            return Long.compare(task1.getDeadline(), task2.getDeadline());
                        case BY_PRIORITY:
                            return Integer.compare(task1.getPriority(), task2.getPriority());
                        case BY_NAME:
                            return task1.getTitle().compareToIgnoreCase(task2.getTitle());
                    }
                    return null;
                })
                .toList()
                .subscribe(tasks -> getView().showTasks(tasks), Timber::e));
    }

    @Override
    public void deleteTask(int position, Task task) {
        addSubscription(todoRepository.deleteTask(task)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(() -> Timber.d("Task deleted successfully")
                        , throwable -> {
                            getTasks();
                            Timber.e(throwable);
                        }));
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
