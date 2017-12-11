package com.todo.di.module;

import com.todo.util.RxFirebaseUtils;
import com.todo.util.scheduler.SchedulerProvider;
import com.todo.util.scheduler.SchedulerProviderImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class UtilsModule {

    @Provides
    @Singleton
    RxFirebaseUtils provideRxFirebaseUtils() {
        return new RxFirebaseUtils();
    }

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return new SchedulerProviderImpl();
    }

    public interface Exposes {

        RxFirebaseUtils rxFirebaseUtils();

        SchedulerProvider schedulerProvider();

    }
}
