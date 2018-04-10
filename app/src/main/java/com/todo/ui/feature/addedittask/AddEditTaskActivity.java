package com.todo.ui.feature.addedittask;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.todo.R;
import com.todo.data.model.Task;
import com.todo.di.component.ActivityComponent;
import com.todo.ui.base.BaseActivity;
import com.todo.util.StringUtils;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class AddEditTaskActivity extends BaseActivity implements AddEditTaskContract.View {


    /********* Constants Fields  ********/

    private static final String EXTRA_TASK = "extra_task";

    /********* Dagger Injected Fields  ********/

    @Inject
    AddEditTaskContract.Presenter presenter;

    /********* Butterknife Binded Fields  ********/

    @BindView(R.id.add_edit_task_input_edit_text_task_title)
    TextInputEditText textInputEditTextTitle;

    @BindView(R.id.add_edit_task_text_view_deadline)
    TextView textViewDeadline;

    @BindView(R.id.add_edit_task_text_view_priority)
    TextView textViewPriority;

    private Task task;

    /********* Static Methods ********/


    public static void startActivity(Context context) {
        Intent starter = new Intent(context, AddEditTaskActivity.class);
        context.startActivity(starter);
    }

    public static void startActivity(Context context, Task task) {
        Intent starter = new Intent(context, AddEditTaskActivity.class);
        starter.putExtra(EXTRA_TASK, task);
        context.startActivity(starter);
    }


    /********* Lifecycle Methods ********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_task_activity);
        setUnbinder(ButterKnife.bind(this));
        init();
        populateViews();
        presenter.attachView(this);
    }

    /********* AddEditTaskContract.View Inherited Methods ********/

    /********* BaseActivity Inherited Methods ********/

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /********* Butterknife Binded Methods  ********/

    @OnClick(R.id.add_edit_task_linear_layout_deadline)
    public void linearLayoutDeadlineClicked() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(task.getDeadline());
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {

            calendar.set(year, monthOfYear, dayOfMonth);
            updateDeadlineTextView(calendar.getTimeInMillis());

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @OnClick(R.id.add_edit_task_linear_layout_priority)
    public void linearLayoutPriorityClicked() {
        showSelectPriorityDialog();
    }

    @OnClick(R.id.add_edit_task_button_done)
    public void buttonDoneClicked() {
        task.setTitle(textInputEditTextTitle.getText().toString());
        if (StringUtils.isEmpty(task.getId())) {
            presenter.createTask(task);
        } else {
            presenter.updateTask(task);
        }
    }

    /********* Member Methods  ********/

    private void init() {

        task = getIntent().getParcelableExtra(EXTRA_TASK);

        if (task == null) {
            task = new Task();
            task.setDeadline(System.currentTimeMillis());
            task.setPriority(Task.PRIORITY_4);
        }
    }

    private void populateViews() {
        textInputEditTextTitle.setText(task.getTitle());
        updateDeadlineTextView(task.getDeadline());
        updatePriorityTextView(task.getPriority());

    }

    private void updateDeadlineTextView(long deadline) {
        textViewDeadline.setText(DateUtils.getRelativeTimeSpanString(deadline, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS));
    }

    private void updatePriorityTextView(int priority) {
        task.setPriority(priority);
        switch (priority) {
            case Task.PRIORITY_1:
                textViewPriority.setText(R.string.all_label_priority_1);
                break;
            case Task.PRIORITY_2:
                textViewPriority.setText(R.string.all_label_priority_2);
                break;
            case Task.PRIORITY_3:
                textViewPriority.setText(R.string.all_label_priority_3);
                break;
            case Task.PRIORITY_4:
                textViewPriority.setText(R.string.all_label_priority_4);
                break;
        }
    }

    private void showSelectPriorityDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectPriorityDialog selectPriorityDialog = SelectPriorityDialog.newInstance(this::updatePriorityTextView);
        selectPriorityDialog.show(fragmentManager, SelectPriorityDialog.TAG);
    }

}
