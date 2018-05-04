package com.todo.ui.feature.register;

import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.todo.R;
import com.todo.data.repository.TodoRepository;
import com.todo.util.StringUtils;
import com.todo.util.StringUtilsImpl;
import com.todo.util.UiSchedulersTransformer;
import com.todo.util.UiSchedulersTransformerTestImpl;
import com.todo.util.validation.validator.RulesFactory;
import com.todo.util.validation.validator.RulesValidator;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RegisterPresenterTest {

    private static final String FAKE_FIELD_ERROR = "fake_field_error";
    private static final String FAKE_EMAIL = "fake_email@android.com";
    private static final String FAKE_PASSWORD = "fake_password";
    private static final String FAKE_EMAIL_EXIST_ERROR = "email_exist_error";

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
    private RegisterPresenter registerPresenter;

    private RegisterContract.View view;

    @Before
    public void setUp() {

        view = mock(RegisterContract.View.class);
        registerPresenter = new RegisterPresenter();
        registerPresenter.attachView(view);
        initMocks(this);

        fakeEmailObservable = Observable.just(FAKE_EMAIL);
        fakePasswordObservable = Observable.just(FAKE_PASSWORD);

        when(rulesFactory.createEmailFieldRules()).thenReturn(Collections.emptyList());
        when(rulesFactory.createPasswordFieldRules()).thenReturn(Collections.emptyList());
    }

    @Test
    public void validateEmail_validEmail_shouldHideEmailErrorAndReturnTrue() {

        // Setup conditions of the test
        when(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(""));

        // Execute the code under test
        TestObserver<Boolean> testObserver = registerPresenter.validateEmail(fakeEmailObservable).test();

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
        TestObserver<Boolean> testObserver = registerPresenter.validateEmail(fakeEmailObservable).test();

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
        TestObserver<Boolean> testObserver = registerPresenter.validatePassword(fakePasswordObservable).test();

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
        TestObserver<Boolean> testObserver = registerPresenter.validatePassword(fakePasswordObservable).test();

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
        registerPresenter.enableOrDisableRegisterButton(Observable.just(Boolean.TRUE), Observable.just(Boolean.TRUE));

        // Make assertions on the results
        verify(view).enableRegisterButton();

    }

    @Test
    public void enableOrDisable_invalidEmailValidPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        registerPresenter.enableOrDisableRegisterButton(Observable.just(Boolean.FALSE), Observable.just(Boolean.TRUE));

        // Make assertions on the results
        verify(view).disableRegisterButton();

    }

    @Test
    public void enableOrDisable_validEmailInvalidPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        registerPresenter.enableOrDisableRegisterButton(Observable.just(Boolean.TRUE), Observable.just(Boolean.FALSE));

        // Make assertions on the results
        verify(view).disableRegisterButton();

    }

    @Test
    public void enableOrDisable_invalidEmailPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        registerPresenter.enableOrDisableRegisterButton(Observable.just(Boolean.FALSE), Observable.just(Boolean.FALSE));

        // Make assertions on the results
        verify(view).disableRegisterButton();

    }

    @Test
    public void register_validEmailPassword_shouldShowTaskActivity() {

        // Setup conditions of the test
        when(todoRepository.register(FAKE_EMAIL, FAKE_PASSWORD)).thenReturn(Completable.complete());

        // Execute the code under test
        registerPresenter.register(FAKE_EMAIL, FAKE_PASSWORD);

        // Make assertions on the results
        verify(view).showTasksActivity();
    }

    @Test
    public void register_emailExist_shouldShowSnackBarWithError() {

        // Setup conditions of the test
        Exception emailExistException = Mockito.mock(FirebaseAuthUserCollisionException.class);
        when(todoRepository.register(FAKE_EMAIL, FAKE_PASSWORD)).thenReturn(Completable.error(emailExistException));
        Mockito.when(resources.getString(R.string.register_error_email_already_exist)).thenReturn(FAKE_EMAIL_EXIST_ERROR);

        // Execute the code under test
        registerPresenter.register(FAKE_EMAIL, FAKE_PASSWORD);

        // Make assertions on the results
        verify(view).hideLoading();
        verify(view).showSnackBar(FAKE_EMAIL_EXIST_ERROR);
    }

    @Test
    public void register_validEmailAndPassword_shouldHideLoadingUnknownError() {

        // Setup conditions of the test

        Exception unknownException = Mockito.mock(Exception.class);
        when(todoRepository.register(FAKE_EMAIL, FAKE_PASSWORD)).thenReturn(Completable.error(unknownException));

        // Execute the code under test
        registerPresenter.register(FAKE_EMAIL, FAKE_PASSWORD);

        // Make assertions on the results
        verify(view).hideLoading();
    }
}