package com.todo.ui.feature.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.todo.R;
import com.todo.data.model.Task;
import com.todo.di.component.ActivityComponent;
import com.todo.ui.base.BaseActivity;
import com.todo.ui.feature.addedittask.AddEditTaskActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class TasksActivity extends BaseActivity implements TasksContract.View {

    /********* Dagger Injected Fields  ********/

    @Inject
    TasksContract.Presenter presenter;

    /********* Butterknife View Binding Fields  ********/

    @BindView(R.id.tasks_recyclerview_tasks)
    RecyclerView recyclerViewTasks;

    private TasksAdapter tasksAdapter;

    /********* Lifecycle Methods Implementation ********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_activity);
        setUnbinder(ButterKnife.bind(this));
        initializeToolbar();
        initializeRecyclerView();
        presenter.attachView(this);
        presenter.getTasks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tasks_menu, menu);
        return true;
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

    @Override
    public void showTasks(List<Task> tasks) {
        tasksAdapter.updateTasks(tasks);
    }

    /********* Butterknife Binded Methods  ********/

    @OnClick(R.id.tasks_button_add_task)
    public void buttonAddTaskClicked() {
        presenter.addTask();
    }

    /********* Member Methods  ********/

    private void initializeToolbar() {
        Toolbar myToolbar = findViewById(R.id.tasks_toolbar);
        setSupportActionBar(myToolbar);
    }

    private void initializeRecyclerView() {

        tasksAdapter = new TasksAdapter(new ArrayList<>());
        recyclerViewTasks.setAdapter(tasksAdapter);
        tasksAdapter.onItemClick()
                .subscribe(this::onTaskSelected);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewTasks.setLayoutManager(linearLayoutManager);
        recyclerViewTasks.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private void onTaskSelected(final Task task) {

    }

}
