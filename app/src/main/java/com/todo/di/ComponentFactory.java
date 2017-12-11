package com.todo.di;

import com.todo.TodoApplication;
import com.todo.di.component.ActivityComponent;
import com.todo.di.component.ApplicationComponent;

public final class ComponentFactory {

    private ComponentFactory() {
    }

    public static ApplicationComponent createApplicationComponent(final TodoApplication todoistApplication) {
        return ApplicationComponent.Initializer.init(todoistApplication);
    }

    public static ActivityComponent createActivityComponent(final DaggerActivity daggerActivity, final TodoApplication todoistApplication) {
        return ActivityComponent.Initializer.init(daggerActivity, todoistApplication.getApplicationComponent());
    }

}
