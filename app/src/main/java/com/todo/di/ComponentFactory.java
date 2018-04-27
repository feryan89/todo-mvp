package com.todo.di;

import com.todo.di.application.DaggerApplication;
import com.todo.di.activity.ActivityComponent;
import com.todo.di.activity.DaggerActivity;
import com.todo.di.application.ApplicationComponent;

public final class ComponentFactory {

    private ComponentFactory() {
    }

    public static ApplicationComponent createApplicationComponent(final DaggerApplication todoistApplication) {
        return ApplicationComponent.Initializer.init(todoistApplication);
    }

    public static ActivityComponent createActivityComponent(final DaggerActivity daggerActivity, final DaggerApplication todoistApplication) {
        return ActivityComponent.Initializer.init(daggerActivity, todoistApplication.getApplicationComponent());
    }

}
