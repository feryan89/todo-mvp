package com.todo.util;

import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class UiSchedulersTransformerImpl implements UiSchedulersTransformer {

    @Override
    public CompletableTransformer applySchedulersToCompletable() {
        return it -> it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public <T> SingleTransformer<T, T> applySchedulersToSingle() {
        return it -> it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public <T> ObservableTransformer<T, T> applySchedulersToObservable() {
        return it -> it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public <T> ObservableTransformer<T, T> applyObserveOnSchedulersToObservable() {
        return it -> it.observeOn(AndroidSchedulers.mainThread());
    }

}
