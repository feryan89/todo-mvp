package com.todo.di.module;

import android.content.res.Resources;

import com.todo.TodoApplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public final class ApplicationModule {

    public static final String MAIN_SCHEDULER = "main_scheduler";
    public static final String BACKGROUND_SCHEDULER = "background_scheduler";

    private final TodoApplication todoApplication;

    public ApplicationModule(final TodoApplication todoApplication) {
        this.todoApplication = todoApplication;
    }

    @Provides
    @Singleton
    TodoApplication provideTodoistApplication() {
        return todoApplication;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return todoApplication.getResources();
    }

    @Provides
    @Singleton
    @Named(MAIN_SCHEDULER)
    public Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @Named(BACKGROUND_SCHEDULER)
    public Scheduler provideBackgroundScheduler() {
        return Schedulers.io();
    }


    public interface Exposes {

        TodoApplication todoApplication();

        Resources resources();

        @Named(MAIN_SCHEDULER)
        Scheduler mainScheduler();

        @Named(BACKGROUND_SCHEDULER)
        Scheduler backgroundScheduler();
    }
}
