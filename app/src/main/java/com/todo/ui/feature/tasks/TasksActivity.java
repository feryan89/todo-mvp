package com.todo.ui.feature.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.View;

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
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public final class TasksActivity extends BaseActivity implements TasksContract.View, TaskItemTouchHelper.TaskItemTouchHelperCallback {

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


    /********* askItemTouchHelper.TaskItemTouchHelperCallback Implemented Methods ********/


    @Override
    public void onTaskDeleted(int position) {
        final Task removedTask = tasksAdapter.removeTask(position);
        showSnackBar(R.string.tasks_message_deleted, R.string.tasks_action_undo, v -> tasksAdapter.restoreTask(removedTask, position));
    }

    @Override
    public void onTaskCompleted(int position) {
        final Task removedTask = tasksAdapter.removeTask(position);
        showSnackBar(R.string.tasks_message_completed, R.string.tasks_action_undo, v -> tasksAdapter.restoreTask(removedTask, position));
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
        recyclerViewTasks.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTasks.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new TaskItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewTasks);

    }

    private void onTaskSelected(final Task task) {

    }


}
