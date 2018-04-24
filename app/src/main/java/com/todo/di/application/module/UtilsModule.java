package com.todo.di.application.module;

import android.support.v4.app.NotificationManagerCompat;

import com.todo.TodoApplication;
import com.todo.device.notification.Notifications;
import com.todo.device.notification.NotificationsImpl;
import com.todo.util.CurrentTimeProvider;
import com.todo.util.CurrentTimeProviderImpl;
import com.todo.util.DateUtils;
import com.todo.util.DateUtilsImpl;
import com.todo.util.RxFirebaseUtils;
import com.todo.util.RxFirebaseUtilsImpl;
import com.todo.util.SchedulerProvider;
import com.todo.util.SchedulerProviderImpl;
import com.todo.util.StringUtils;
import com.todo.util.StringUtilsImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class UtilsModule {

    @Provides
    @Singleton
    CurrentTimeProvider provideCurrentTimeProvider() {
        return new CurrentTimeProviderImpl();
    }

    @Provides
    @Singleton
    DateUtils provideDateUtils(CurrentTimeProvider currentTimeProvider) {
        return new DateUtilsImpl(currentTimeProvider);
    }

    @Provides
    @Singleton
    RxFirebaseUtils provideRxFirebaseUtils() {
        return new RxFirebaseUtilsImpl();
    }

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return new SchedulerProviderImpl();
    }


    @Provides
    @Singleton
    StringUtils stringUtils() {
        return new StringUtilsImpl();
    }


    @Provides
    @Singleton
    Notifications provideNotifications(final NotificationManagerCompat notificationManagerCompat) {
        return new NotificationsImpl(notificationManagerCompat);
    }

    @Provides
    @Singleton
    NotificationManagerCompat provideNotificationManagerCompat(final TodoApplication application) {
        return NotificationManagerCompat.from(application.getApplicationContext());
    }


    public interface Exposes {

        CurrentTimeProvider currentTimeProvider();

        DateUtils dateUtils();

        RxFirebaseUtils rxFirebaseUtils();

        SchedulerProvider schedulerProvider();

        StringUtils stringUtils();

        Notifications notificationUtils();

    }
}
