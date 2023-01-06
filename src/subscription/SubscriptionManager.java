package subscription;

import movie.Movie;
import notification.Notification;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;

public final class SubscriptionManager {
    private final HashMap<String, ArrayList<User>> subscriptions;

    public SubscriptionManager() {
        subscriptions = new HashMap<>();
    }

    /**
     * @param genre the genre to which a user subscribes
     * @param user  the user who subscribes to the genre
     */
    public void addSubscriber(final String genre, final User user) {
        if (!subscriptions.containsKey(genre)) {
            subscriptions.put(genre, new ArrayList<>());
        }

        subscriptions.get(genre).add(user);
    }

    /**
     * @param genre the genre from which a user unsubscribes
     * @param user  the user who unsubscribes from the genre
     */
    public void removeSubscriber(final String genre, final User user) {
        if (!subscriptions.containsKey(genre)) {
            return;
        }

        subscriptions.get(genre).remove(user);
    }

    /**
     * @param genre   the genre for which users get notified
     * @param message the message to be sent to the users
     */
    public void notifySubscribers(final String genre, final String message, final Movie movie) {
        if (!subscriptions.containsKey(genre)) {
            return;
        }

        for (User user : subscriptions.get(genre)) {
            if (user.getAvailableMovies().contains(movie)) {

                user.receiveNotification(new Notification(movie.getName(), message));
            }
        }
    }

    /**
     * @param genre the genre for which to check the subscribers
     * @param user  the user to look for
     * @return true if the user is subscribed to the given genre
     */
    public boolean isSubscribed(final String genre, final User user) {
        if (!subscriptions.containsKey(genre)) {
            return false;
        }

        return subscriptions.get(genre).contains(user);
    }
}
