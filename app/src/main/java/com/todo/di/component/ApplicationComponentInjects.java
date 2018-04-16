package com.todo.di.component;

import com.todo.TodoApplication;
import com.todo.device.job.service.TaskReminderJobService;

public interface ApplicationComponentInjects {

    void inject(TodoApplication todoApplication);

    void inject(TaskReminderJobService taskReminderJobService);


}
