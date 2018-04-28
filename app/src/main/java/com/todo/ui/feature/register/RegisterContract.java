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

        Observable<Boolean> validateEmail(Observable<String> emailObservable);

        Observable<Boolean> validatePassword(Observable<String> passwordObservable);

        void enableOrDisableRegisterButton(Observable<Boolean> validateEmailObservable, Observable<Boolean> validatePasswordObservable);

        void register(String email, String password);

    }
}
