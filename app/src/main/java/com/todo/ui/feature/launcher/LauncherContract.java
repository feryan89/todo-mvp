package com.todo.ui.feature.launcher;

import com.todo.ui.base.BaseContract;

public interface LauncherContract {

    interface View extends BaseContract.View {

        void showLoginActivity();

        void showTasksActivity();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void showNextActivity();

    }
}
