package com.todo.di.component;

import com.todo.di.module.ApplicationModule;
import com.todo.di.module.DataModule;
import com.todo.di.module.UtilsModule;

public interface ApplicationComponentExposes extends ApplicationModule.Exposes,
                                                     UtilsModule.Exposes,
                                                     DataModule.Exposes {

}
