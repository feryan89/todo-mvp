package com.todo.ui.feature.login;

import com.todo.ui.base.BaseContract;

import io.reactivex.Observable;


public interface LoginContract {

    interface View extends BaseContract.View {

        void showLoading();

        void hideLoading();

        void showEmailError(String errorMessage);

        void hideEmailError();

        void showPasswordError(String errorMessage);

        void hidePasswordError();

        void enableLoginButton();

        void disableLoginButton();

        void showRegisterActivity();

        void showTasksActivity();

    }

    interface Presenter extends BaseContract.Presenter<LoginContract.View> {

        void bindLoginFormObservables(Observable<String> emailObservable, Observable<String> passwordObservable);

        void login(String email, String password);

        void register();

    }
}
