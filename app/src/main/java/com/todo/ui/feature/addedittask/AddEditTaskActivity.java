package com.todo.ui.feature.addedittask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SwitchCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.todo.R;
import com.todo.data.model.TaskModel;
import com.todo.di.activity.ActivityComponent;
import com.todo.ui.base.BaseActivity;
import com.todo.util.CurrentTimeProvider;
import com.todo.util.DateUtils;
import com.todo.util.DateUtilsImpl;
import com.todo.util.StringUtils;
import com.todo.util.StringUtilsImpl;
import com.todo.util.UiUtils;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public final class AddEditTaskActivity extends BaseActivity implements AddEditTaskContract.View {


    /********* Constants Fields  ********/

    private static final String EXTRA_TASK = "extra_task";

    /********* Dagger Injected Fields  ********/

    @Inject
    AddEditTaskContract.Presenter presenter;

    @Inject
    StringUtils stringUtils;

    @Inject
    DateUtils dateUtils;

    @Inject
    CurrentTimeProvider currentTimeProvider;


    /********* Butterknife Binded Fields  ********/

    @BindView(R.id.add_edit_task_input_edit_text_task_title)
    TextInputEditText textInputEditTextTitle;

    @BindView(R.id.add_edit_task_text_view_deadline)
    TextView textViewDeadline;

    @BindView(R.id.add_edit_task_text_view_priority)
    TextView textViewPriority;


    @BindView(R.id.add_edit_task_image_view_add_alarm)
    ImageView imageViewAddAlarm;
    @BindView(R.id.add_edit_task_text_view_reminder_label)
    TextView textViewReminderLabel;
    @BindView(R.id.add_edit_task_text_view_reminder_time)
    TextView textViewReminderTime;
    @BindView(R.id.add_edit_task_switch_reminder)
    SwitchCompat switchReminder;

    private Calendar calendar;
    private TaskModel taskModel;

    /********* Static Methods ********/


    public static void startActivity(Context context) {
        Intent starter = new Intent(context, AddEditTaskActivity.class);
        context.startActivity(starter);
    }

    public static void startActivity(Context context, TaskModel taskModel) {
        Intent starter = new Intent(context, AddEditTaskActivity.class);
        starter.putExtra(EXTRA_TASK, taskModel);
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

    /********* BaseActivity Inherited Methods ********/

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /********* Butterknife Binded Methods  ********/

    @OnClick(R.id.add_edit_task_image_view_back_arrow)
    public void imageViewBackArrowClicked() {
        super.onBackPressed();
    }


    @OnClick(R.id.add_edit_task_linear_layout_deadline)
    public void linearLayoutDeadlineClicked() {

        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {

            calendar.set(year, monthOfYear, dayOfMonth);
            taskModel.setDeadline(calendar.getTimeInMillis());
            updateDeadlineTextView(calendar.getTimeInMillis());

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @OnClick(R.id.add_edit_task_linear_layout_priority)
    public void linearLayoutPriorityClicked() {
        showSelectPriorityDialog();
    }

    @OnCheckedChanged(R.id.add_edit_task_switch_reminder)
    public void switchReminderChecked(boolean isChecked) {
        if (isChecked) {
            setReminderEnabled();
        } else {
            setReminderDisabled();
        }
    }

    @OnClick(R.id.add_edit_task_linear_layout_reminder)
    public void linearLayoutReminderClicked() {
        if (switchReminder.isChecked()) {

            TimePickerDialog dialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                taskModel.setReminder(calendar.getTimeInMillis());
                updateReminderTime(calendar.getTimeInMillis());

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            dialog.show();
        }
    }

    @OnClick(R.id.add_edit_task_button_done)
    public void buttonDoneClicked() {
        taskModel.setTitle(textInputEditTextTitle.getText().toString());
        if (stringUtils.isEmpty(taskModel.getId())) {
            presenter.createTask(taskModel);
        } else {
            presenter.updateTask(taskModel);
        }
    }

    /********* Member Methods  ********/

    private void init() {

        taskModel = getIntent().getParcelableExtra(EXTRA_TASK);
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeProvider.getCurrentTimeMillis());

        if (taskModel == null) {
            taskModel = new TaskModel();
            taskModel.setDeadline(calendar.getTimeInMillis());
            taskModel.setPriority(TaskModel.PRIORITY_4);
        } else {
            calendar.setTimeInMillis(taskModel.getDeadline());
        }

        setReminderDisabled();

    }

    private void populateViews() {
        textInputEditTextTitle.setText(taskModel.getTitle());
        updateDeadlineTextView(taskModel.getDeadline());
        updatePriorityTextView(taskModel.getPriority());
        updateReminderTime(taskModel.getDeadline());

    }


    private void updateDeadlineTextView(long deadline) {
        textViewDeadline.setText(dateUtils.getDisplayDate(deadline));
    }

    private void updatePriorityTextView(int priority) {
        taskModel.setPriority(priority);
        textViewPriority.setText(UiUtils.getPriorityString(this, priority));
    }

    private void updateReminderTime(long deadline) {
        textViewReminderTime.setText(dateUtils.getDisplayTime(deadline));
    }


    private void setReminderEnabled() {
        UiUtils.enableWithAlpha(imageViewAddAlarm, textViewReminderLabel, textViewReminderTime);
    }

    private void setReminderDisabled() {
        UiUtils.disableWithAlpha(imageViewAddAlarm, textViewReminderLabel, textViewReminderTime);
    }


    private void showSelectPriorityDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectPriorityDialog selectPriorityDialog = SelectPriorityDialog.newInstance(this::updatePriorityTextView);
        selectPriorityDialog.show(fragmentManager, SelectPriorityDialog.TAG);
    }

}
