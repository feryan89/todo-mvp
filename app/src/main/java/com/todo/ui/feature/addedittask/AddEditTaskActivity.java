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
import com.todo.data.model.Task;
import com.todo.di.component.ActivityComponent;
import com.todo.ui.base.BaseActivity;
import com.todo.util.CustomDateUtils;
import com.todo.util.StringUtils;
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
            task.setDeadline(calendar.getTimeInMillis());
            updateDeadlineTextView(calendar.getTimeInMillis());

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @OnClick(R.id.add_edit_task_linear_layout_priority)
    public void linearLayoutPriorityClicked() {
        showSelectPriorityDialog();
    }

    @OnCheckedChanged(R.id.add_edit_task_switch_reminder)
    public void switchRemiderChecked(boolean isChecked) {
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
                task.setReminder(calendar.getTimeInMillis());
                updateReminderTime(calendar.getTimeInMillis());

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            dialog.show();
        }
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
            calendar = Calendar.getInstance();
            task.setDeadline(calendar.getTimeInMillis());
            task.setPriority(Task.PRIORITY_4);
        }

        setReminderDisabled();

    }

    private void populateViews() {
        textInputEditTextTitle.setText(task.getTitle());
        updateDeadlineTextView(task.getDeadline());
        updatePriorityTextView(task.getPriority());
        updateReminderTime(task.getDeadline());

    }


    private void updateDeadlineTextView(long deadline) {
        textViewDeadline.setText(CustomDateUtils.getDisplayDate(deadline));
    }

    private void updatePriorityTextView(int priority) {
        task.setPriority(priority);
        textViewPriority.setText(UiUtils.getPriorityString(this, priority));
    }

    private void updateReminderTime(long deadline) {
        textViewReminderTime.setText(CustomDateUtils.getDisplayTime(deadline));
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
