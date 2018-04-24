package com.todo.ui.feature.launcher;

import android.os.Bundle;

import com.todo.di.activity.ActivityComponent;
import com.todo.ui.base.BaseActivity;
import com.todo.ui.feature.login.LoginActivity;
import com.todo.ui.feature.tasks.TasksActivity;

import javax.inject.Inject;

public final class LauncherActivity extends BaseActivity implements LauncherContract.View {

    /********* Dagger Injected Fields  ********/

    @Inject
    LauncherContract.Presenter presenter;

    /********* Lifecycle Methods Implementation ********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attachView(this);
        presenter.showNextActivity();
    }

    /********* DaggerActivity Inherited Methods ********/

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /********* LauncherContract.View Inherited Methods ********/

    @Override
    public void showLoginActivity() {
        LoginActivity.startActivity(this);
        finish();
    }

    @Override
    public void showTasksActivity() {
        TasksActivity.startActivity(this);
        finish();
    }

}
