package com.todo.device.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.todo.R;
import com.todo.data.model.Task;
import com.todo.ui.feature.tasks.TasksActivity;
import com.todo.util.UiUtils;


public final class NotificationFactoryImpl implements NotificationFactory {

    private final Context context;
    private final Resources resources;

    public NotificationFactoryImpl(final Context context, final Resources resources) {
        this.context = context;
        this.resources = resources;
    }


    @Override
    public Notification createTaskReminderNotification(Task task) {

        Intent intent = TasksActivity.getIntentForNotification(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        final String taskDetails = "Priority: " + UiUtils.getPriorityString(context, task.getPriority()) + "\n";


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification_vector_alarm)
                .setContentTitle(task.getTitle())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(taskDetails))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.all_primary))
                .setGroup(NOTIFICATION_GROUP_KEY)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        return builder.build();
    }
}
