package com.todo.di.application;

import com.todo.TodoApplication;
import com.todo.device.job.service.TaskReminderJobService;

public interface ApplicationComponentInjects {

    void inject(TodoApplication todoApplication);

    void inject(TaskReminderJobService taskReminderJobService);


}
