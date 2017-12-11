package com.todo.ui.base;

import com.todo.util.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    /********* Dagger Injected Fields  ********/

    @Inject
    protected SchedulerProvider schedulerProvider;

    /********* Member Fields  ********/

    private WeakReference<V> viewReference;
    private CompositeSubscription subscriptions;

    /********* Constructors ********/

    public BasePresenter() {
        subscriptions = new CompositeSubscription();
    }

    /********* BaseContract.Presenter Inherited Methods ********/

    @Override
    public void attachView(V view) {
        this.viewReference = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        this.viewReference = null;
    }

    /********* Member Methods ********/

    protected V getView() {

        if (viewReference == null) {
            throw new IllegalStateException("View is not attached");
        }
        return viewReference.get();
    }

    protected void addSubscription(final Subscription subscription) {
        if (subscriptions == null || subscriptions.isUnsubscribed()) {
            subscriptions = new CompositeSubscription();
        }
        subscriptions.add(subscription);
    }

}
