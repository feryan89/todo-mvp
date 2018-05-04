package com.todo.util;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;

// TODO: 29/04/2018 remove static methods and use dagger to inject the dependency
public class RxIdlingResource {

    private static final String TAG = "RxIdlingResource";

    private static RxIdlingResource INSTANCE;

    private CountingIdlingResource countingIdlingResource;


    public static void increment() {
         get().countingIdlingResource.increment();
    }

    public static void decrement() {
        get().countingIdlingResource.decrement();
    }

    public static boolean isIdleNow() {
        return get().countingIdlingResource.isIdleNow();
    }

    private RxIdlingResource() {

        countingIdlingResource = new CountingIdlingResource(TAG, true);
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }

    private static RxIdlingResource get() {
        if (INSTANCE == null) {
            INSTANCE = new RxIdlingResource();
        }
        return INSTANCE;
    }

}
