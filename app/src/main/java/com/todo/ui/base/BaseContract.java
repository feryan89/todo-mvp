package com.todo.ui.base;

/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

public interface BaseContract {

    interface View {

        void hideKeyboard();

        void showSnackBar(String message);

        void showToast(String message);

        void finishActivity();

    }

    interface Presenter<V extends BaseContract.View> {

        void attachView(V View);

        void detachView();


    }

}
