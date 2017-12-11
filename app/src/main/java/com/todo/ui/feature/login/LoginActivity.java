package com.todo.ui.feature.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.todo.R;
import com.todo.di.component.ActivityComponent;
import com.todo.ui.base.BaseActivity;
import com.todo.ui.feature.register.RegisterActivity;
import com.todo.ui.feature.tasks.TasksActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public final class LoginActivity extends BaseActivity implements LoginContract.View {

    /********* Dagger Injected Fields  ********/

    @Inject
    LoginContract.Presenter presenter;

    /********* Butterknife View Binding Fields  ********/

    @BindView(R.id.login_linear_layout_form)
    LinearLayout linearLayoutForm;

    @BindView(R.id.login_input_layout_email)
    TextInputLayout inputLayoutEmail;

    @BindView(R.id.login_input_edit_text_email)
    TextInputEditText inputEditTextEmail;

    @BindView(R.id.login_input_layout_password)
    TextInputLayout inputLayoutPassword;

    @BindView(R.id.login_input_edit_text_password)
    EditText inputEditTextPassword;

    @BindView(R.id.login_button_login)
    Button buttonLogin;

    @BindView(R.id.login_progress_bar)
    ProgressBar progressBar;

    /********* Lifecycle Methods Implementation ********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setUnbinder(ButterKnife.bind(this));
        presenter.attachView(this);
        presenter.bindLoginFormObservables(getEmailObservable(), getPasswordObservable());
    }

    /********* DaggerActivity Inherited Methods ********/
    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /********* LoginContract.View Inherited Methods ********/

    @Override
    public void showLoading() {
        linearLayoutForm.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        linearLayoutForm.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmailError(String errorMessage) {
        inputLayoutEmail.setError(errorMessage);
    }

    @Override
    public void hideEmailError() {
        inputLayoutEmail.setError(null);
    }

    @Override
    public void showPasswordError(String errorMessage) {
        inputLayoutPassword.setError(errorMessage);
    }

    @Override
    public void hidePasswordError() {
        inputLayoutPassword.setError(null);
    }

    @Override
    public void enableLoginButton() {
        buttonLogin.setEnabled(true);
    }

    @Override
    public void disableLoginButton() {
        buttonLogin.setEnabled(false);
    }

    @Override
    public void showRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTasksActivity() {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
        finish();
    }

    /********* Butterknife Binded Methods  ********/

    @OnClick(R.id.login_button_login)
    public void buttonLoginClicked() {
        presenter.login(inputEditTextEmail.getText().toString(), inputEditTextPassword.getText().toString());
    }

    @OnClick(R.id.login_button_register)
    public void buttonRegisterClicked() {
        presenter.register();
    }

    /********* Member Methods  ********/

    private Observable<String> getEmailObservable() {
        return RxTextView.textChanges(inputEditTextEmail).skip(1).map(CharSequence::toString);
    }

    private Observable<String> getPasswordObservable() {
        return RxTextView.textChanges(inputEditTextPassword).skip(1).map(CharSequence::toString);
    }

}
