package com.todo.util;

import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author Waleed Sarwar
 * @since Dec 10, 2017
 */

public class UiSchedulersTransformerTestImpl implements UiSchedulersTransformer {


    @Override
    public CompletableTransformer applySchedulersToCompletable() {
        return it -> it.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline());
    }

    @Override
    public <T> SingleTransformer<T, T> applySchedulersToSingle() {
        return it -> it.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline());
    }

    @Override
    public <T> ObservableTransformer<T, T> applySchedulersToObservable() {
        return it -> it.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline());
    }

    @Override
    public <T> ObservableTransformer<T, T> applyObserveOnSchedulersToObservable() {
        return it -> it.observeOn(Schedulers.trampoline());

    }
}
