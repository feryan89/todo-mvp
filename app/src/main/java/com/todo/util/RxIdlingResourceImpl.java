package com.todo.util;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;

public class RxIdlingResourceImpl implements RxIdlingResource {

    private static final String TAG = "RxIdlingResource";

    private static RxIdlingResourceImpl INSTANCE;

    private CountingIdlingResource countingIdlingResource;


    public RxIdlingResourceImpl() {

        countingIdlingResource = new CountingIdlingResource(TAG, true);
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }

    private static RxIdlingResourceImpl get() {
        if (INSTANCE == null) {
            INSTANCE = new RxIdlingResourceImpl();
        }
        return INSTANCE;
    }

    @Override
    public void increment() {
        countingIdlingResource.increment();
    }

    @Override
    public void decrement() {
        countingIdlingResource.decrement();
    }

    @Override
    public boolean isIdleNow() {
        return countingIdlingResource.isIdleNow();
    }
}
