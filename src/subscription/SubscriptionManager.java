package subscription;

import movie.Movie;
import notification.Notification;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class SubscriptionManager {
    private static final String                           RECOMMENDATION_MSG = "Recommendation";
    private static final String                           FAILED_RECOMMENDATION_MSG = "No recommendation";
    private final        HashMap<String, ArrayList<User>> subscriptions;

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
                user.receiveNotification(message, movie.getName());
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

    public void sendRecommendation(final User user) {
        HashMap<String, Integer> genresLiked = new HashMap<>();

        for (Movie movie : user.getLikedMovies()) {
            for (String genre : movie.getGenres()) {
                if (!genresLiked.containsKey(genre)) {
                    genresLiked.put(genre, 1);

                    continue;
                }

                genresLiked.put(genre, genresLiked.get(genre) + 1);
            }
        }

        Notification notification = new Notification(FAILED_RECOMMENDATION_MSG, RECOMMENDATION_MSG);

        while (notification.getMovieName().equals(FAILED_RECOMMENDATION_MSG)
               && !genresLiked.isEmpty()) {
            ArrayList<String> mostLikedGenres = new ArrayList<>();
            int max = 0;

            for (String genre : genresLiked.keySet()) {
                if (genresLiked.get(genre) > max) {
                    max = genresLiked.get(genre);

                    mostLikedGenres.clear();
                    mostLikedGenres.add(genre);
                } else if (genresLiked.get(genre) == max) {
                    mostLikedGenres.add(genre);
                }
            }

            mostLikedGenres.sort(String::compareTo);
            mostLikedGenres.forEach(genresLiked::remove);

            mostLikedGenres.forEach(
                    genre -> findMostPopularMovieFromGenre(user, genre, notification));
        }

        user.receiveNotification(notification.getMessage(), notification.getMovieName());
    }

    private void findMostPopularMovieFromGenre(
            final User user, final String genre, final Notification notification
                                              ) {
        ArrayList<Movie> mostLikedMovies = new ArrayList<>();

        for (Movie movie : user.getAvailableMovies()) {
            if (movie.getGenres().contains(genre) && !user.getWatchedMovies().contains(movie)) {
                mostLikedMovies.add(movie);
            }
        }


        if (!mostLikedMovies.isEmpty()) {
            mostLikedMovies.sort((movie1, movie2) -> Integer.compare(movie2.getNumLikes(),
                                                                     movie1.getNumLikes()
                                                                    ));

            notification.setMovieName(mostLikedMovies.get(0).getName());
        }
    }
}
