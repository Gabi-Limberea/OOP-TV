package notification;

public interface NotificationReceiver {
    /**
     * Receive a new notification.
     *
     * @param newNotification the notification to be received
     */
    void receiveNotification(Notification newNotification);
}
