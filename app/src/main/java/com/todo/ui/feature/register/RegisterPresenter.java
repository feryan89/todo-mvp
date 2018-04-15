package com.todo.ui.feature.register;

import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.todo.R;
import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;
import com.todo.ui.model.ValidationResultViewModel;
import com.todo.ui.validator.EmailValidator;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

public final class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    /********* Dagger Injected Fields  ********/

    @Inject
    TodoRepository todoRepository;

    @Inject
    Resources resources;

    @Inject
    EmailValidator emailValidator;

    /********* RegisterContract.Presenter Inherited Methods ********/

    @Override
    public void bindRegisterFormObservables(Observable<String> emailObservable, Observable<String> passwordObservable) {

        Observable<ValidationResultViewModel> emailValidObservable = emailObservable.map(s -> emailValidator.validate(s));
        Observable<ValidationResultViewModel> passwordValidObservable = passwordObservable.map(this::isPasswordValid);

        emailValidObservable.subscribe(validationResultViewModel -> {
            if (validationResultViewModel.isValid()) {
                getView().hideEmailError();
            } else {
                getView().showEmailError(validationResultViewModel.getErrorMessage());
            }
        });

        passwordValidObservable.subscribe(validationResultViewModel -> {
            if (validationResultViewModel.isValid()) {
                getView().hidePasswordError();
            } else {
                getView().showPasswordError(validationResultViewModel.getErrorMessage());
            }
        });

        Observable.combineLatest(emailValidObservable, passwordValidObservable,
                (emailValidResult, passwordValidResult) -> emailValidResult.isValid() && passwordValidResult.isValid())
                .subscribe(isValid -> {
                    if (isValid)
                        getView().enableRegisterButton();
                    else
                        getView().disableRegisterButton();
                });

    }

    @Override
    public void register(String email, String password) {
        getView().hideKeyboard();
        getView().showLoading();
        addSubscription(todoRepository.register(email, password)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(() -> {
                    getView().hideLoading();
                    getView().showTasksActivity();
                }, throwable -> {
                    Timber.e(throwable);
                    getView().hideLoading();
                    if (throwable instanceof FirebaseAuthUserCollisionException) {
                        getView().hideLoading();
                        getView().showSnackBar(resources.getString(R.string.register_error_email_already_exist));
                    }
                }));
    }

    /********* Member Methods Implementation ********/

    private ValidationResultViewModel isPasswordValid(String password) {

        if (password.isEmpty()) {
            return ValidationResultViewModel.failure(resources.getString(R.string.all_error_password_required));
        } else {
            return ValidationResultViewModel.success();
        }
    }
}
