package com.todo.device.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.todo.R;
import com.todo.data.model.TaskModel;
import com.todo.ui.feature.tasks.TasksActivity;
import com.todo.util.UiUtils;

import static android.content.Context.NOTIFICATION_SERVICE;


public final class NotificationFactoryImpl implements NotificationFactory {

    private final Context context;

    public NotificationFactoryImpl(final Context context) {
        this.context = context;
        createNotificationChannels();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannels() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            if (notificationManager != null && notificationManager.getNotificationChannel(CHANNEL_ID_REMINDERS) == null) {

                CharSequence name = context.getString(R.string.notification_channel_name_reminders);
                String description = context.getString(R.string.notification_channel_description_reminders);
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID_REMINDERS, name, NotificationManager.IMPORTANCE_DEFAULT);
                mChannel.setDescription(description);
                notificationManager.createNotificationChannel(mChannel);

            }
        }
    }

    @Override
    public Notification createTaskReminderNotification(TaskModel taskModel) {

        Intent intent = TasksActivity.getIntentForNotification(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        final String taskDetails = "Priority: " + UiUtils.getPriorityString(context, taskModel.getPriority()) + "\n";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_REMINDERS)
                .setSmallIcon(R.drawable.notification_vector_alarm)
                .setContentTitle(taskModel.getTitle())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(taskDetails))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.all_primary))
                .setGroup(GROUP_KEY_TASK_REMINDERS)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        return builder.build();
    }
}
