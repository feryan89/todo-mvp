package com.todo.util;

import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;

public interface UiSchedulersTransformer {


    CompletableTransformer applySchedulersToCompletable();

    <T> SingleTransformer<T, T> applySchedulersToSingle();

    <T> ObservableTransformer<T, T> applySchedulersToObservable();

    <T> ObservableTransformer<T, T> applyObserveOnSchedulersToObservable();
}
