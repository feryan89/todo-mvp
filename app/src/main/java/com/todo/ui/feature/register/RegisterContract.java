package com.todo.ui.feature.register;

import com.todo.ui.base.BaseContract;

import io.reactivex.Observable;

public interface RegisterContract {

    interface View extends BaseContract.View {

        void showLoading();

        void hideLoading();

        void showEmailError(String errorMessage);

        void hideEmailError();

        void showPasswordError(String errorMessage);

        void hidePasswordError();

        void enableRegisterButton();

        void disableRegisterButton();

        void showTasksActivity();

    }

    interface Presenter extends BaseContract.Presenter<View> {

        void bindRegisterFormObservables(Observable<String> emailObservable, Observable<String> passwordObservable);

        void register(String email,String password);

    }
}
