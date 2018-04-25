package com.todo.ui.base;

import com.todo.util.SchedulerProvider;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.CompletableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    /********* Dagger Injected Fields  ********/

    @Inject
    protected SchedulerProvider schedulerProvider;

    /********* Member Fields  ********/

    private WeakReference<V> viewReference;
    private CompositeDisposable compositeDisposable;

    /********* Constructors ********/

    public BasePresenter() {
    }

    /********* BaseContract.Presenter Inherited Methods ********/

    @Override
    public void attachView(V view) {
        this.viewReference = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        this.viewReference = null;
        this.compositeDisposable.clear();
    }

    /********* Member Methods ********/

    protected V getView() {

        if (viewReference == null) {
            throw new IllegalStateException("View is not attached");
        }
        return viewReference.get();
    }


    protected void addDisposable(final Disposable disposable) {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    protected CompletableTransformer applySchedulersToCompletable() {
        return it -> it.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui());
    }

    protected <T> SingleTransformer<T, T> applySchedulersToSingle() {
        return it -> it.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui());
    }

    protected <T> ObservableTransformer<T, T> applySchedulersToObservable() {
        return it -> it.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui());
    }

}
