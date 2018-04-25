package com.todo.ui.feature.register;

import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.todo.R;
import com.todo.data.repository.TodoRepository;
import com.todo.ui.base.BasePresenter;
import com.todo.util.StringUtils;
import com.todo.util.validation.validator.RulesFactory;
import com.todo.util.validation.validator.RulesValidator;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

public final class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

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


    /********* RegisterContract.Presenter Inherited Methods ********/

    @Override
    public void onRegisterFormChanges(Observable<String> emailObservable, Observable<String> passwordObservable) {

        Observable<Boolean> emailValidObservable = rulesValidator.
                validate(emailObservable, rulesFactory.createEmailFieldRules())
                .compose(applySchedulersToObservable())
                .doOnNext(errorMessage -> {
                    if (stringUtils.isEmpty(errorMessage)) {
                        getView().hideEmailError();
                    } else {
                        getView().showEmailError(errorMessage);
                    }
                })
                .map(String::isEmpty);

        Observable<Boolean> passwordValidObservable = rulesValidator
                .validate(passwordObservable, rulesFactory.createPasswordFieldRules())
                .compose(applySchedulersToObservable())
                .doOnNext(errorMessage -> {
                    if (stringUtils.isEmpty(errorMessage)) {
                        getView().hidePasswordError();
                    } else {
                        getView().showPasswordError(errorMessage);
                    }
                }).map(String::isEmpty);

        addDisposable(Observable.combineLatest(emailValidObservable, passwordValidObservable,
                (emailValid, passwordValid)
                        -> emailValid && passwordValid)
                .subscribe(isValid -> {
                    if (isValid) {
                        getView().enableRegisterButton();
                    } else {
                        getView().disableRegisterButton();
                    }
                }));

    }

    @Override
    public void register(String email, String password) {
        getView().hideKeyboard();
        getView().showLoading();
        addDisposable(todoRepository.register(email, password)
                .compose(applySchedulersToCompletable())
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

}
