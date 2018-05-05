package com.todo.ui.feature.login;

import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.todo.R;
import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;
import com.todo.util.RxIdlingResource;
import com.todo.util.StringUtils;
import com.todo.util.validation.validator.RulesFactory;
import com.todo.util.validation.validator.RulesValidator;

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
    StringUtils stringUtils;

    @Inject
    RulesValidator rulesValidator;

    @Inject
    RulesFactory rulesFactory;

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
    public Observable<Boolean> validateEmail(Observable<String> emailObservable) {
        return rulesValidator.
                validate(emailObservable, rulesFactory.createEmailFieldRules())
                .compose(uiSchedulersTransformer.applyObserveOnSchedulersToObservable())
                .doOnNext(errorMessage -> {
                    if (stringUtils.isEmpty(errorMessage)) {
                        getView().hideEmailError();
                    } else {
                        getView().showEmailError(errorMessage);
                    }
                })
                .map(String::isEmpty);
    }

    @Override
    public Observable<Boolean> validatePassword(Observable<String> passwordObservable) {
        return rulesValidator
                .validate(passwordObservable, rulesFactory.createPasswordFieldRules())
                .compose(uiSchedulersTransformer.applyObserveOnSchedulersToObservable())
                .doOnNext(errorMessage -> {
                    if (stringUtils.isEmpty(errorMessage)) {
                        getView().hidePasswordError();
                    } else {
                        getView().showPasswordError(errorMessage);
                    }
                }).map(String::isEmpty);
    }

    @Override
    public void enableOrDisableLoginButton(Observable<Boolean> validateEmailObservable, Observable<Boolean> validatePasswordObservable) {

        addDisposable(Observable.combineLatest(validateEmailObservable, validatePasswordObservable,
                (emailValid, passwordValid)
                        -> emailValid && passwordValid)
                .subscribe(isValid -> {
                    if (isValid) {
                        getView().enableLoginButton();
                    } else {
                        getView().disableLoginButton();
                    }
                }));
    }


    @Override
    public void login(final String email, final String password) {

        // ideally your testing related code should not be here
        getView().hideKeyboard();
        getView().showLoading();

        addDisposable(todoRepository.login(email, password)
                .compose(uiSchedulersTransformer.applySchedulersToCompletable())
                .subscribe(() -> getView().showTasksActivity(), throwable -> {
                    getView().hideLoading();
                    Timber.e(throwable);
                    if (throwable instanceof FirebaseAuthInvalidUserException
                            || throwable instanceof FirebaseAuthInvalidCredentialsException) {
                        getView().showSnackBar(resources.getString(R.string.login_error_invalid_credentials));
                    }
                }));

    }

    @Override
    public void register() {
        getView().showRegisterActivity();
    }


}
