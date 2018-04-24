package com.todo.di.application.module;

import android.content.ComponentName;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.todo.TodoApplication;
import com.todo.device.TaskReminderScheduler;
import com.todo.device.TaskReminderSchedulerImpl;
import com.todo.device.job.service.TaskReminderJobService;
import com.todo.device.notification.NotificationFactory;
import com.todo.device.notification.NotificationFactoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ServiceModule {


    @Provides
    @Singleton
    FirebaseJobDispatcher provideFirebaseJobDispatcher(final TodoApplication todoApplication) {
        return new FirebaseJobDispatcher(new GooglePlayDriver(todoApplication));
    }

    @Provides
    @Singleton
    TaskReminderScheduler provideTaskReminderScheduler(final FirebaseJobDispatcher jobDispatcher) {
        return new TaskReminderSchedulerImpl(jobDispatcher);
    }

    @Provides
    @Singleton
    NotificationFactory provideNotificationFactory(final TodoApplication application) {
        return new NotificationFactoryImpl(application.getApplicationContext());
    }

    @Provides
    @Singleton
    ComponentName provideTaskReminderJobService(final TodoApplication application) {
        return new ComponentName(application.getApplicationContext(), TaskReminderJobService.class);
    }


    public interface Exposes {

        TaskReminderScheduler taskReminderScheduler();

    }
}
