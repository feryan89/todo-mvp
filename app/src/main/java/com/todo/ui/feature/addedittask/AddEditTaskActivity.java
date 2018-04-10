package com.todo.ui.feature.addedittask;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.todo.R;
import com.todo.data.model.Task;
import com.todo.di.component.ActivityComponent;
import com.todo.ui.base.BaseActivity;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class AddEditTaskActivity extends BaseActivity implements AddEditTaskContract.View {

    /********* Dagger Injected Fields  ********/

    @Inject
    AddEditTaskContract.Presenter presenter;

    /********* Butterknife Binded Fields  ********/

    @BindView(R.id.add_edit_task_input_edit_text_task_title)
    TextInputEditText textInputEditTextTittle;

    @BindView(R.id.add_edit_task_text_view_deadline)
    TextView textViewDeadline;

    @BindView(R.id.add_edit_task_text_view_priority)
    TextView textViewPriority;

    private Calendar calendar;
    private int startYear;
    private int startMonth;
    private int startDay;

    private long deadline;
    private int priority;

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
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {

            this.startYear = year;
            this.startMonth = monthOfYear;
            this.startDay = dayOfMonth;
            calendar.set(startYear, startMonth, startDay);

            updateDeadlineTextView(calendar.getTimeInMillis());

        }, startYear, startMonth, startDay);
        dialog.show();
    }

    @OnClick(R.id.add_edit_task_linear_layout_priority)
    public void linearLayoutPriorityClicked() {
        showSelectPriorityDialog();
    }

    @OnClick(R.id.add_edit_task_button_done)
    public void buttonDoneClicked() {
        presenter.createTask(textInputEditTextTittle.getText().toString(), deadline, priority);
    }

    /********* Member Methods  ********/

    private void init() {

        this.calendar = Calendar.getInstance();
        this.startYear = calendar.get(Calendar.YEAR);
        this.startMonth = calendar.get(Calendar.MONTH);
        this.startDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.deadline = System.currentTimeMillis();
        this.priority = Task.PRIORITY_4;
    }

    private void populateViews() {

        updateDeadlineTextView(deadline);
        updatePriorityTextView(priority);
    }

    private void updateDeadlineTextView(long deadline) {
        textViewDeadline.setText(DateUtils.getRelativeTimeSpanString(deadline, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS));
    }

    private void updatePriorityTextView(int priority) {
        this.priority = priority;
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
