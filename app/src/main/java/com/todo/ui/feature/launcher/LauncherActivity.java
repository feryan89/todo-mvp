package com.todo.ui.feature.launcher;

import android.content.Intent;
import android.os.Bundle;

import com.todo.di.component.ActivityComponent;
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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showTasksActivity() {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
        finish();
    }

}
