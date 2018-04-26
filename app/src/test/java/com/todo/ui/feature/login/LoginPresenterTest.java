package com.todo.ui.feature.login;

import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.todo.data.repository.TodoRepository;
import com.todo.util.SchedulerProviderTestImpl;
import com.todo.util.SchedulerProvider;
import com.todo.util.StringUtils;
import com.todo.util.StringUtilsImpl;
import com.todo.util.validation.validator.RulesFactory;
import com.todo.util.validation.validator.RulesValidator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Collections;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class LoginPresenterTest {

    private static final String FAKE_FIELD_ERROR = "fake_field_error";
    private static final String FAKE_EMAIL = "fake_email@android.com";
    private static final String FAKE_PASSWORD = "fake_password";
    private static final String FAKE_INVALID_CREDENTIALS_ERROR = "fake_invalid_credentials_error";


    private Observable<String> fakeEmailObservable;
    private Observable<String> fakePasswordObservable;

    @Spy
    private SchedulerProvider schedulerProvider = new SchedulerProviderTestImpl();

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private Resources resources;

    @Spy
    private StringUtils stringUtils = new StringUtilsImpl();

    @Mock
    private RulesValidator rulesValidator;

    @Mock
    private RulesFactory rulesFactory;

    @InjectMocks
    private LoginPresenter loginPresenter;

    private LoginContract.View view;


    @Before
    public void setUp() {

        view = Mockito.mock(LoginContract.View.class);
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(view);
        MockitoAnnotations.initMocks(this);

        fakeEmailObservable = Observable.just(FAKE_EMAIL);
        fakePasswordObservable = Observable.just(FAKE_PASSWORD);

        Mockito.when(rulesFactory.createEmailFieldRules()).thenReturn(Collections.emptyList());
        Mockito.when(rulesFactory.createPasswordFieldRules()).thenReturn(Collections.emptyList());
    }

    @After
    public void tearDown() {
        loginPresenter.detachView();
    }

    @Test
    public void onLoginFormChanges_validEmailPassword_shouldEnableLoginButton() {

        Mockito.when(rulesValidator.validate(Mockito.any(), Mockito.anyList())).thenReturn(Observable.just(""));


        loginPresenter.onLoginFormChanges(fakeEmailObservable, fakePasswordObservable);

        Mockito.verify(view, Mockito.times(1)).hideEmailError();
        Mockito.verify(view, Mockito.times(1)).hidePasswordError();
        Mockito.verify(view, Mockito.times(1)).enableLoginButton();

    }

    @Test
    public void onLoginFormChanges_invalidEmailPassword_shouldDisableLoginButton() {

        Mockito.when(rulesValidator.validate(Mockito.any(), Mockito.anyList())).thenReturn(Observable.just(FAKE_FIELD_ERROR));

        loginPresenter.onLoginFormChanges(fakeEmailObservable, fakePasswordObservable);

        Mockito.verify(view, Mockito.times(1)).showEmailError(FAKE_FIELD_ERROR);
        Mockito.verify(view, Mockito.times(1)).showPasswordError(FAKE_FIELD_ERROR);
        Mockito.verify(view, Mockito.times(1)).disableLoginButton();

    }

    @Test
    public void login_validCredentials_showShowTaskActivity() {

        Mockito.when(todoRepository.login(Mockito.anyString(), Mockito.anyString())).thenReturn(Completable.complete());

        loginPresenter.login(FAKE_EMAIL, FAKE_PASSWORD);

        Mockito.verify(view, Mockito.times(1)).hideKeyboard();
        Mockito.verify(view, Mockito.times(1)).showLoading();
        Mockito.verify(view, Mockito.times(1)).showTasksActivity();


    }

    @Test
    public void login_invalidCredentials_showSnackBarWithError() {
        Exception invalidCredentials = Mockito.mock(FirebaseAuthInvalidCredentialsException.class);
        Mockito.when(todoRepository.login(Mockito.anyString(), Mockito.anyString())).thenReturn(Completable.error(invalidCredentials));
        Mockito.when(resources.getString(Mockito.anyInt())).thenReturn(FAKE_INVALID_CREDENTIALS_ERROR);

        loginPresenter.login(FAKE_EMAIL, FAKE_PASSWORD);

        Mockito.verify(view, Mockito.times(1)).hideKeyboard();
        Mockito.verify(view, Mockito.times(1)).showLoading();
        Mockito.verify(view, Mockito.times(1)).showSnackBar(FAKE_INVALID_CREDENTIALS_ERROR);


    }

    @Test
    public void register_showShowRegisterActivity() {
        loginPresenter.register();
        Mockito.verify(view, Mockito.times(1)).showRegisterActivity();

    }


}