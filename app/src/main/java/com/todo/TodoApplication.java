package com.todo;

import android.app.Application;
import android.content.Context;

import com.todo.di.ComponentFactory;
import com.todo.di.application.ApplicationComponent;

public class TodoApplication extends Application {

    /********* Member Fields  ********/

    private ApplicationComponent applicationComponent;

    /********* Static Methods  ********/

    public static TodoApplication from(final Context context) {
        return (TodoApplication) context.getApplicationContext();
    }

    /********* Lifecycle Methods ********/


    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = ComponentFactory.createApplicationComponent(this);
        applicationComponent.inject(this);

    }

    /********* Member Methods  ********/

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
