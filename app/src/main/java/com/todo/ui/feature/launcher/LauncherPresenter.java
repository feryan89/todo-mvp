package com.todo.ui.feature.launcher;

import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;

import javax.inject.Inject;

import timber.log.Timber;

public final class LauncherPresenter extends BasePresenter<LauncherContract.View> implements LauncherContract.Presenter {

    /********* Dagger Injected Fields  ********/

    @Inject
    TodoRepository todoRepository;

    /********* Constructors ********/

    public LauncherPresenter() {
        super();
    }

    /********* LauncherContract.Presenter Interface Methods Implementation ********/

    @Override
    public void showNextActivity() {
        addDisposable(todoRepository.isUserLoggedIn()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(isUserLoggedIn -> {
                    if (isUserLoggedIn) {
                        getView().showTasksActivity();
                    } else {
                        getView().showLoginActivity();
                    }
                }, Timber::e));
    }
}
