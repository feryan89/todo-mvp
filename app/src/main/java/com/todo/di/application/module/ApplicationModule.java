package com.todo.di.application.module;

import android.content.res.Resources;

import com.todo.TodoApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
