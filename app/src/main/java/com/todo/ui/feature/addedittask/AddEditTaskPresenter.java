package com.todo.ui.feature.addedittask;

import android.content.res.Resources;

import com.fernandocejas.frodo.core.strings.Strings;
import com.todo.R;
import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;

import javax.inject.Inject;

public final class AddEditTaskPresenter extends BasePresenter<AddEditTaskContract.View> implements AddEditTaskContract.Presenter {

    /********* Dagger Injected Fields  ********/

    @Inject
    TodoRepository todoRepository;

    @Inject
    Resources resources;

    public AddEditTaskPresenter() {
        super();
    }

    @Override
    public void attachView(AddEditTaskContract.View view) {
        super.attachView(view);
    }

    @Override
    public void createTask(String title, long deadline, int priority) {
        if (Strings.isNotBlank(title)) {
            todoRepository.createTask(title, deadline, priority);
            getView().finishActivity();
        } else {
            getView().showSnackBar(resources.getString(R.string.add_edit_task_error_invalid_title));
        }
    }
}
