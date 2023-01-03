package user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import movie.Movie;
import notification.Notification;
import subscription.SubscriptionManager;

import java.util.ArrayList;

public final class User {
    private static final int                     MAX_FREE_PREMIUM_MOVIES = 15;
    private              Credentials             credentials;
    private              int                     tokensCount;
    private              int                     numFreePremiumMovies;
    @JsonIgnore
    private              boolean                 firstLogin;
    private              ArrayList<Movie>        purchasedMovies;
    private              ArrayList<Movie>        watchedMovies;
    private              ArrayList<Movie>        likedMovies;
    private              ArrayList<Movie>        ratedMovies;
    private              ArrayList<Notification> notifications;
    @JsonIgnore
    private              ArrayList<Movie>        availableMovies;

    public User(final Credentials source) {
        credentials = new Credentials(source);
        tokensCount = 0;
        numFreePremiumMovies = MAX_FREE_PREMIUM_MOVIES;
        purchasedMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
        availableMovies = new ArrayList<>();
        notifications = new ArrayList<>();
        firstLogin = false;
    }

    public User(final User source) {
        credentials = new Credentials(source.getCredentials());
        tokensCount = source.getTokensCount();
        numFreePremiumMovies = source.getNumFreePremiumMovies();

        purchasedMovies = new ArrayList<>();
        for (Movie movie : source.getPurchasedMovies()) {
            purchasedMovies.add(new Movie(movie));
        }

        watchedMovies = new ArrayList<>();
        for (Movie movie : source.getWatchedMovies()) {
            watchedMovies.add(new Movie(movie));
        }

        likedMovies = new ArrayList<>();
        for (Movie movie : source.getLikedMovies()) {
            likedMovies.add(new Movie(movie));
        }

        ratedMovies = new ArrayList<>();
        for (Movie movie : source.getRatedMovies()) {
            ratedMovies.add(new Movie(movie));
        }

        if (source.firstLogin) {
            availableMovies = new ArrayList<>();
            for (Movie movie : source.getAvailableMovies()) {
                availableMovies.add(new Movie(movie));
            }

            firstLogin = true;
        }

        notifications = new ArrayList<>();
        for (Notification notification : source.getNotifications()) {
            notifications.add(new Notification(notification));
        }
    }

    /**
     * @return true if the user has logged in for the first time
     */
    public boolean isFirstLogin() {
        return firstLogin;
    }

    /**
     * @param firstLogin update whether the user has logged in for the first
     *                   time
     */
    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    /**
     * @return the list of available movies for the current user
     */
    public ArrayList<Movie> getAvailableMovies() {
        return availableMovies;
    }

    /**
     * @param availableMovies the new list of available movies for the current user
     */
    public void setAvailableMovies(final ArrayList<Movie> availableMovies) {
        this.availableMovies = availableMovies;
    }

    /**
     * @return the list of notifications for the user
     */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    /**
     * @param notifications the new list of notifications for the user
     */
    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * @return the credentials of the user
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * @param credentials the new credentials of the user
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * @return the number of tokens of the user
     */
    public int getTokensCount() {
        return tokensCount;
    }

    /**
     * @param tokensCount the new number of tokens of the user
     */
    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    /**
     * @return the number of free premium movies for the user
     */
    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    /**
     * @param numFreePremiumMovies the new number of free premium movies for the
     *                             user
     */
    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    /**
     * @return the list of purchased movies of the user
     */
    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    /**
     * @param purchasedMovies the new list of purchased movies of the user
     */
    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    /**
     * @return the list of watched movies of the user
     */
    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    /**
     * @param watchedMovies the new list of watched movies of the user
     */
    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    /**
     * @return the list of liked movies of the user
     */
    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    /**
     * @param likedMovies the new list of liked movies of the user
     */
    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    /**
     * @return the list of rated movies of the user
     */
    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    /**
     * @param ratedMovies the new list of rated movies of the user
     */
    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    /**
     * Try to purchase the given movie.
     *
     * @param movie the movie to be added to the list of purchased movies
     * @return true if the movie was added, false otherwise
     */
    public boolean addPurchasedMovie(final Movie movie) {
        if (numFreePremiumMovies > 0 && credentials.isPremium()) {
            numFreePremiumMovies--;
        } else if (tokensCount > 0) {
            tokensCount -= 2;
        } else {
            return false;
        }

        purchasedMovies.add(movie);
        return true;
    }

    /**
     * Watch the given movie.
     *
     * @param movie the movie to be added to the list of watched movies
     */
    public void addWatchedMovie(final Movie movie) {
        if (!watchedMovies.contains(movie)) {
            watchedMovies.add(movie);
        }
    }

    /**
     * Like the given movie.
     *
     * @param movie the movie to be added to the list of liked movies
     */
    public void addLikedMovie(final Movie movie) {
        likedMovies.add(movie);
    }

    /**
     * Rate the given movie.
     *
     * @param movie the movie to be added to the rated movies list
     */
    public void addRatedMovie(final Movie movie) {
        if (!ratedMovies.contains(movie)) {
            ratedMovies.add(movie);
        }
    }

    /**
     * Subscribe to a genre of movies. The user will receive notifications based
     * on the genre.
     *
     * @param genre the genre to be subscribed to
     */
    public void subscribeTo(final String genre, final SubscriptionManager subscriptionManager) {
        subscriptionManager.addSubscriber(genre, this);
    }

    /**
     * Unsubscribe from a genre of movies. The user will no longer receive
     * notifications based on the genre.
     *
     * @param genre the genre to be unsubscribed from
     */
    public void unsubscribeFrom(final String genre, final SubscriptionManager subscriptionManager) {
        subscriptionManager.removeSubscriber(genre, this);
    }

    public void receiveNotification(final String message, final String movieName) {
        notifications.add(new Notification(movieName, message));
    }
}
