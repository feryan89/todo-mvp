package com.todo.ui.base;


import android.view.View;

import rx.Observable;
import rx.Single;

/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

public interface BaseContract {

    interface View {

        void hideKeyboard();

        void showSnackBar(final String message);

        Single<Boolean> showSnackBar(final String message, final String action);

        Single<Boolean> showSnackBar(int messageRes, final int actionRes);


        void showToast(final String message);

        void finishActivity();

    }

    interface Presenter<V extends BaseContract.View> {

        void attachView(V View);

        void detachView();


    }

}
