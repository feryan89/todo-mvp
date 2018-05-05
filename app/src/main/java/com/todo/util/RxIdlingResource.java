package com.todo.util;

// TODO: 29/04/2018 remove static methods and use dagger to inject the dependency
public interface RxIdlingResource {

    void increment();

    void decrement();

    boolean isIdleNow();


}
