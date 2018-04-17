package com.todo.di.component;

import com.todo.di.DaggerActivity;
import com.todo.di.module.ActivityModule;
import com.todo.di.module.ActivityPresenterModule;
import com.todo.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ActivityPresenterModule.class})
public interface ActivityComponent extends ActivityComponentInjects, ActivityComponentExposes {

    final class Initializer {

        private Initializer() {
        }

        public static ActivityComponent init(final DaggerActivity daggerActivity, final ApplicationComponent applicationComponent) {
            return DaggerActivityComponent.builder()
                    .applicationComponent(applicationComponent)
                    .activityModule(new ActivityModule(daggerActivity))
                    .activityPresenterModule(new ActivityPresenterModule(daggerActivity))
                    .build();
        }
    }
}
