package com.todo.ui.base;

import com.todo.util.SchedulerProvider;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


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

}
