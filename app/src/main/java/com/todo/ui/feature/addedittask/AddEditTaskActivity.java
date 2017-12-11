package com.todo.ui.feature.addedittask;

import android.os.Bundle;

import com.todo.R;
import com.todo.di.component.ActivityComponent;
import com.todo.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public final class AddEditTaskActivity extends BaseActivity implements AddEditTaskContract.View {

    /********* Dagger Injected Fields  ********/

    @Inject
    AddEditTaskContract.Presenter presenter;

    /********* Butterknife View Binding Fields  ********/

    /********* Lifecycle Methods Implementation ********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_task_activity);
        setUnbinder(ButterKnife.bind(this));
        presenter.attachView(this);
    }

    /********* DaggerActivity Inherited Methods ********/
    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /********* LauncherContract.View Inherited Methods ********/

}
