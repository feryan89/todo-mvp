package com.todo.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.todo.di.DaggerActivity;

import butterknife.Unbinder;

public abstract class BaseActivity extends DaggerActivity implements BaseContract.View {

    /********* Member Fields  ********/

    private Unbinder unbinder;
    private View rootView;

    /********* Lifecycle Methods ********/

    @Override
    @CallSuper
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rootView = findViewById(android.R.id.content);
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    /********* BaseContract.View Inherited Methods ********/

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    /********* Member Methods  ********/

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }
}
