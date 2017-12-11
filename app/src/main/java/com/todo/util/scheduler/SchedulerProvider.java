package com.todo.util.scheduler;

import android.support.annotation.NonNull;

import rx.Scheduler;

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
