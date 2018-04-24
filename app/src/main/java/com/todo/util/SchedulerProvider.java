package com.todo.util;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;


/**
 * @author Waleed Sarwar
 * @since Dec 10, 2017
 */

public interface SchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
