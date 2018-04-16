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


    private final TodoApplication todoApplication;

    public ApplicationModule(final TodoApplication todoApplication) {
        this.todoApplication = todoApplication;
    }

    @Provides
    @Singleton
    TodoApplication provideTodoApplication() {
        return todoApplication;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return todoApplication.getResources();
    }


    public interface Exposes {

        TodoApplication todoApplication();

        Resources resources();
    }
}
