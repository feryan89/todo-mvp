package com.todo.ui.feature.login;

import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.todo.R;
import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;
import com.todo.ui.model.ValidationResultViewModel;
import com.todo.ui.validator.EmailValidator;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

public final class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    /********* Dagger Injected Fields  ********/

    @Inject
    TodoRepository todoRepository;

    @Inject
    Resources resources;

    @Inject
    EmailValidator emailValidator;

    /********* Constructors ********/

    public LoginPresenter() {
        super();
    }

    /********* BaseContract.Presenter Inherited Methods ********/

    @Override
    public void attachView(LoginContract.View view) {
        super.attachView(view);

    }

    @Override
    public void detachView() {
        super.detachView();
    }

    /********* LoginContract.Presenter Inherited Methods ********/

    @Override
    public void bindLoginFormObservables(Observable<String> emailObservable, Observable<String> passwordObservable) {

        Observable<ValidationResultViewModel> emailValidObservable = emailObservable.map(s -> emailValidator.validate(s));
        Observable<ValidationResultViewModel> passwordValidObservable = passwordObservable.map(this::isPasswordValid);

        addDisposable(emailValidObservable.subscribe(validationResultViewModel -> {
            if (validationResultViewModel.isValid()) {
                getView().hideEmailError();
            } else {
                getView().showEmailError(validationResultViewModel.getErrorMessage());
            }
        }));

        addDisposable(passwordValidObservable.subscribe(validationResultViewModel -> {
            if (validationResultViewModel.isValid()) {
                getView().hidePasswordError();
            } else {
                getView().showPasswordError(validationResultViewModel.getErrorMessage());
            }
        }));

        addDisposable(Observable.combineLatest(emailValidObservable, passwordValidObservable,
                (emailValidResult, passwordValidResult)
                        -> emailValidResult.isValid() && passwordValidResult.isValid())
                .subscribe(isValid -> {
                    if (isValid)
                        getView().enableLoginButton();
                    else
                        getView().disableLoginButton();
                }));

    }

    @Override
    public void login(final String email, final String password) {
        getView().hideKeyboard();
        getView().showLoading();
        addDisposable(todoRepository.login(email, password)
                .subscribe(() -> getView().showTasksActivity(), throwable -> {
                    Timber.e(throwable);
                    if (throwable instanceof FirebaseAuthInvalidUserException
                            || throwable instanceof FirebaseAuthInvalidCredentialsException) {
                        getView().showSnackBar(resources.getString(R.string.login_error_invalid_credentials));
                    }
                    getView().hideLoading();
                }));

    }

    @Override
    public void register() {
        getView().showRegisterActivity();
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
