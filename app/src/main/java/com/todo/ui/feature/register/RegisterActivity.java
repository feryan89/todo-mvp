package com.todo.ui.feature.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.todo.R;
import com.todo.di.component.ActivityComponent;
import com.todo.ui.base.BaseActivity;
import com.todo.ui.feature.tasks.TasksActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

public final class RegisterActivity extends BaseActivity implements RegisterContract.View {

    /********* Dagger Injected Fields  ********/

    @Inject
    RegisterContract.Presenter presenter;

    /********* Butterknife Binded Fields  ********/

    @BindView(R.id.register_group_form)
    Group groupForm;

    @BindView(R.id.register_input_layout_email)
    TextInputLayout inputLayoutEmail;

    @BindView(R.id.register_input_edit_text_email)
    TextInputEditText inputEditTextEmail;

    @BindView(R.id.register_input_layout_password)
    TextInputLayout inputLayoutPassword;

    @BindView(R.id.register_input_edit_text_password)
    TextInputEditText inputEditTextPassword;

    @BindView(R.id.register_button_register)
    Button buttonRegister;

    @BindView(R.id.register_progress_bar)
    ProgressBar progressBar;

    /********* Static Methods ********/


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    /********* Lifecycle Methods ********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        setUnbinder(ButterKnife.bind(this));
        initializeToolbar();
        presenter.attachView(this);
        presenter.bindRegisterFormObservables(getEmailObservable(), getPasswordObservable());
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();

    }

    /********* BaseActivity Inherited Methods ********/

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }


    /********* Activity Inherited Methods ********/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /********* RegisterContract.View Inherited Methods ********/

    @Override
    public void showLoading() {
        groupForm.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        groupForm.setVisibility(View.VISIBLE);
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
    public void enableRegisterButton() {
        buttonRegister.setEnabled(true);
    }

    @Override
    public void disableRegisterButton() {
        buttonRegister.setEnabled(false);
    }

    @Override
    public void showTasksActivity() {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }

    /********* Butterknife Binded Methods  ********/

    @OnClick(R.id.register_button_register)
    public void buttonRegisterClicked() {
        presenter.register(inputEditTextEmail.getText().toString(), inputEditTextPassword.getText().toString());
    }

    /********* Member Methods  ********/

    private void initializeToolbar() {
        Toolbar myToolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private Observable<String> getEmailObservable() {
        return RxTextView.textChanges(inputEditTextEmail).skip(1).map(CharSequence::toString);
    }

    private Observable<String> getPasswordObservable() {
        return RxTextView.textChanges(inputEditTextPassword).skip(1).map(CharSequence::toString);
    }

}
