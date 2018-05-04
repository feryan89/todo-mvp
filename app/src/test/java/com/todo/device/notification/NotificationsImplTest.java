package com.todo.device.notification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class NotificationsImplTest {

    public static final int TEST_NOTIFICATION_ID = 1000;

    private NotificationsImpl notificationsImpl;
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;

    @Before
    public void setUp() {
        notification = Mockito.mock(Notification.class);
        notificationManagerCompat = Mockito.mock(NotificationManagerCompat.class);
        notificationsImpl = new NotificationsImpl(notificationManagerCompat);
    }


    @Test
    public void shouldShowNewNotification() {

        notificationsImpl.showNotification(TEST_NOTIFICATION_ID, notification);

        Mockito.verify(notificationManagerCompat, Mockito.times(1)).notify(TEST_NOTIFICATION_ID, notification);
        Mockito.verifyNoMoreInteractions(notificationManagerCompat);

    }

    @Test
    public void shouldUpdateExistingNotification() {

        notificationsImpl.updateNotification(TEST_NOTIFICATION_ID, notification);

        Mockito.verify(notificationManagerCompat, Mockito.times(1)).notify(TEST_NOTIFICATION_ID, notification);
        Mockito.verifyNoMoreInteractions(notificationManagerCompat);
    }

    @Test
    public void shouldHideExistingNotification() {
        notificationsImpl.hideNotification(TEST_NOTIFICATION_ID);

        Mockito.verify(notificationManagerCompat, Mockito.times(1)).cancel(TEST_NOTIFICATION_ID);
        Mockito.verifyNoMoreInteractions(notificationManagerCompat);
    }

    @Test
    public void shouldHideAllNotifications() {
        notificationsImpl.hideNotifications();

        Mockito.verify(notificationManagerCompat, Mockito.times(1)).cancelAll();
        Mockito.verifyNoMoreInteractions(notificationManagerCompat);
    }

}