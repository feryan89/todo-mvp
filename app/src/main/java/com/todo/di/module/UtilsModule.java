package com.todo.di.module;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import com.todo.TodoApplication;
import com.todo.device.notification.Notifications;
import com.todo.device.notification.NotificationsImpl;
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

        RxFirebaseUtils rxFirebaseUtils();

        SchedulerProvider schedulerProvider();

        Notifications notificationUtils();

    }
}
