package com.todo.ui.feature.addedittask;

import android.content.res.Resources;

import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;
import com.todo.ui.validator.EmailValidator;

import javax.inject.Inject;

public final class AddEditTaskPresenter extends BasePresenter<AddEditTaskContract.View> implements AddEditTaskContract.Presenter {

    /********* Dagger Injected Fields  ********/

    @Inject
    TodoRepository todoRepository;

    @Inject
    Resources resources;

    @Inject
    EmailValidator emailValidator;

    /********* Constructors ********/

    public AddEditTaskPresenter() {
        super();
    }

    /********* BaseContract.Presenter Interface Methods Implementation ********/

    /********* LauncherContract.Presenter Interface Methods Implementation ********/

    /********* Member Methods Implementation ********/
}
