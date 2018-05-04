package com.todo.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.todo.R;
import com.todo.di.activity.DaggerActivity;

import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends DaggerActivity implements BaseContract.View {

    /********* Member Fields  ********/

    private Unbinder unbinder;
    private View rootView;
    private Snackbar snackbar;
    private CompositeDisposable compositeDisposable;

    /********* Lifecycle Methods ********/

    @Override
    @CallSuper
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rootView = findViewById(R.id.root_view);
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (compositeDisposable != null) {
            compositeDisposable.clear();
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
    public void showSnackBar(final String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Single<Boolean> showSnackBar(final String message, final String action) {

        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(SingleEmitter<Boolean> emitter) {
                if (snackbar != null) {
                    snackbar.setText(message);
                    snackbar.setDuration(Snackbar.LENGTH_LONG);
                } else {
                    snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);

                }
                snackbar.setAction(action, v -> emitter.onSuccess(Boolean.TRUE));
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if (!emitter.isDisposed()) {
                            emitter.onSuccess(Boolean.FALSE);
                        }
                    }
                });
                snackbar.show();
            }
        });

    }

    @Override
    public Single<Boolean> showSnackBar(@StringRes int messageRes, @StringRes int actionRes) {

        return showSnackBar(getString(messageRes), getString(actionRes));
    }


    @Override
    public void finishActivity() {
        finish();
    }

    /********* Member Methods  ********/

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    protected void addDisposable(final Disposable disposable) {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

}
