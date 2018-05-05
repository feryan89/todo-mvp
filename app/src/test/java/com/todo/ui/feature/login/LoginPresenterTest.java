package com.todo.ui.feature.login;

import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.todo.data.repository.TodoRepository;
import com.todo.util.StringUtils;
import com.todo.util.StringUtilsImpl;
import com.todo.util.UiSchedulersTransformer;
import com.todo.util.UiSchedulersTransformerTestImpl;
import com.todo.util.validation.validator.RulesFactory;
import com.todo.util.validation.validator.RulesValidator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Collections;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginPresenterTest {

    private static final String FAKE_FIELD_ERROR = "fake_field_error";
    private static final String FAKE_EMAIL = "fake_email@android.com";
    private static final String FAKE_PASSWORD = "fake_password";
    private static final String FAKE_INVALID_CREDENTIALS_ERROR = "fake_invalid_credentials_error";


    private Observable<String> fakeEmailObservable;
    private Observable<String> fakePasswordObservable;

    @Spy
    private UiSchedulersTransformer uiSchedulersTransformer = new UiSchedulersTransformerTestImpl();

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
        initMocks(this);

        fakeEmailObservable = Observable.just(FAKE_EMAIL);
        fakePasswordObservable = Observable.just(FAKE_PASSWORD);

        when(rulesFactory.createEmailFieldRules()).thenReturn(Collections.emptyList());
        when(rulesFactory.createPasswordFieldRules()).thenReturn(Collections.emptyList());

    }

    @After
    public void tearDown() {
        loginPresenter.detachView();
    }

    @Test
    public void validateEmail_validEmail_shouldHideEmailErrorAndReturnTrue() {

        // Setup conditions of the test
        when(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(""));

        // Execute the code under test
        TestObserver<Boolean> testObserver = loginPresenter.validateEmail(fakeEmailObservable).test();

        // Make assertions on the results
        verify(view).hideEmailError();
        testObserver.assertNoErrors();
        testObserver.assertValue(Boolean.TRUE);
        testObserver.assertComplete();
        testObserver.dispose();


    }

    @Test
    public void validateEmail_invalidEmail_shouldShowEmailErrorAndReturnFalse() {

        // Setup conditions of the test
        when(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(FAKE_FIELD_ERROR));

        // Execute the code under test
        TestObserver<Boolean> testObserver = loginPresenter.validateEmail(fakeEmailObservable).test();

        // Make assertions on the results
        verify(view).showEmailError(FAKE_FIELD_ERROR);
        testObserver.assertNoErrors();
        testObserver.assertValue(Boolean.FALSE);
        testObserver.assertComplete();
        testObserver.dispose();


    }

    @Test
    public void validatePassword_validEmail_shouldHidePasswordErrorAndReturnTrue() {

        // Setup conditions of the test
        when(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(""));

        // Execute the code under test
        TestObserver<Boolean> testObserver = loginPresenter.validatePassword(fakePasswordObservable).test();

        // Make assertions on the results
        verify(view).hidePasswordError();
        testObserver.assertNoErrors();
        testObserver.assertValue(Boolean.TRUE);
        testObserver.assertComplete();
        testObserver.dispose();


    }

    @Test
    public void validatePassword_invalidPassword_shouldShowPasswordErrorAndReturnFalse() {

        // Setup conditions of the test
        when(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(FAKE_FIELD_ERROR));

        // Execute the code under test
        TestObserver<Boolean> testObserver = loginPresenter.validatePassword(fakePasswordObservable).test();

        // Make assertions on the results
        verify(view).showPasswordError(FAKE_FIELD_ERROR);
        testObserver.assertNoErrors();
        testObserver.assertValue(Boolean.FALSE);
        testObserver.assertComplete();
        testObserver.dispose();


    }

    @Test
    public void enableOrDisable_validEmailPassword_shouldEnableRegisterButton() {

        // Execute the code under test
        loginPresenter.enableOrDisableLoginButton(Observable.just(Boolean.TRUE), Observable.just(Boolean.TRUE));

        // Make assertions on the results
        verify(view).enableLoginButton();

    }

    @Test
    public void enableOrDisable_invalidEmailValidPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        loginPresenter.enableOrDisableLoginButton(Observable.just(Boolean.FALSE), Observable.just(Boolean.TRUE));

        // Make assertions on the results
        verify(view).disableLoginButton();

    }

    @Test
    public void enableOrDisable_validEmailInvalidPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        loginPresenter.enableOrDisableLoginButton(Observable.just(Boolean.TRUE), Observable.just(Boolean.FALSE));

        // Make assertions on the results
        verify(view).disableLoginButton();

    }

    @Test
    public void enableOrDisable_invalidEmailPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        loginPresenter.enableOrDisableLoginButton(Observable.just(Boolean.FALSE), Observable.just(Boolean.FALSE));

        // Make assertions on the results
        verify(view).disableLoginButton();

    }

    @Test
    public void login_validCredentials_showShowTaskActivity() {

        when(todoRepository.login(anyString(), anyString())).thenReturn(Completable.complete());

        loginPresenter.login(FAKE_EMAIL, FAKE_PASSWORD);

        Mockito.verify(view, Mockito.times(1)).hideKeyboard();
        Mockito.verify(view, Mockito.times(1)).showLoading();
        Mockito.verify(view, Mockito.times(1)).showTasksActivity();


    }

    @Test
    public void login_invalidCredentials_showSnackBarWithError() {

        Exception invalidCredentials = Mockito.mock(FirebaseAuthInvalidCredentialsException.class);
        when(todoRepository.login(anyString(), anyString())).thenReturn(Completable.error(invalidCredentials));
        when(resources.getString(Mockito.anyInt())).thenReturn(FAKE_INVALID_CREDENTIALS_ERROR);

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