package com.todo.ui.feature.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.todo.R;
import com.todo.di.component.ActivityComponent;
import com.todo.ui.base.BaseActivity;
import com.todo.ui.feature.addedittask.AddEditTaskActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public final class TasksActivity extends BaseActivity implements TasksContract.View {

    /********* Dagger Injected Fields  ********/

    @Inject
    TasksContract.Presenter presenter;

    /********* Butterknife View Binding Fields  ********/

    /********* Lifecycle Methods Implementation ********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_activity);
        setUnbinder(ButterKnife.bind(this));
        setupToolbar();
        presenter.attachView(this);
    }

    /********* DaggerActivity Inherited Methods ********/
    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /********* TasksContract.View Inherited Methods ********/

    @Override
    public void showAddEditTaskActivity() {
        Intent intent = new Intent(this, AddEditTaskActivity.class);
        startActivity(intent);
    }

    /********* Butterknife Binded Methods  ********/

    @OnClick(R.id.tasks_button_add_task)
    public void buttonAddTaskClicked() {
        presenter.addTask();
    }

    /********* Member Methods  ********/

    private void setupToolbar() {
        Toolbar myToolbar = findViewById(R.id.tasks_toolbar);
        setSupportActionBar(myToolbar);
    }

}
