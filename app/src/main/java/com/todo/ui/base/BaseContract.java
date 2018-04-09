package com.todo.ui.base;


import android.view.View;

import rx.Observable;

/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

public interface BaseContract {

    interface View {

        void hideKeyboard();

        void showSnackBar(final String message);

        void showSnackBar(final String message, final String action, android.view.View.OnClickListener onClickListener);

        void showSnackBar(int messageRes, final int actionRes, android.view.View.OnClickListener onClickListener);


        void showToast(final String message);

        void finishActivity();

    }

    interface Presenter<V extends BaseContract.View> {

        void attachView(V View);

        void detachView();


    }

}
