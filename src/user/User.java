package user;

import movie.Movie;

import java.util.ArrayList;

public final class User {
    private static final int              MAX_FREE_PREMIUM_MOVIES = 15;
    private              Credentials      credentials;
    private              int              tokensCount;
    private              int              numFreePremiumMovies;
    private              ArrayList<Movie> purchasedMovies;
    private              ArrayList<Movie> watchedMovies;
    private              ArrayList<Movie> likedMovies;
    private              ArrayList<Movie> ratedMovies;

    public User(final Credentials source) {
        this.credentials = new Credentials(source);
        this.tokensCount = 0;
        this.numFreePremiumMovies = MAX_FREE_PREMIUM_MOVIES;
        this.purchasedMovies = new ArrayList<>();
        this.watchedMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
        this.ratedMovies = new ArrayList<>();
    }

    public User(final User source) {
        this.credentials = new Credentials(source.getCredentials());
        this.tokensCount = source.getTokensCount();
        this.numFreePremiumMovies = source.getNumFreePremiumMovies();

        this.purchasedMovies = new ArrayList<>();
        for (Movie movie : source.getPurchasedMovies()) {
            this.purchasedMovies.add(new Movie(movie));
        }

        this.watchedMovies = new ArrayList<>();
        for (Movie movie : source.getWatchedMovies()) {
            this.watchedMovies.add(new Movie(movie));
        }

        this.likedMovies = new ArrayList<>();
        for (Movie movie : source.getLikedMovies()) {
            this.likedMovies.add(new Movie(movie));
        }

        this.ratedMovies = new ArrayList<>();
        for (Movie movie : source.getRatedMovies()) {
            this.ratedMovies.add(new Movie(movie));
        }
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
        if (this.numFreePremiumMovies > 0 && credentials.isPremium()) {
            this.numFreePremiumMovies--;
        } else if (this.tokensCount > 0) {
            this.tokensCount -= 2;
        } else {
            return false;
        }

        this.purchasedMovies.add(movie);
        return true;
    }

    /**
     * Watch the given movie.
     *
     * @param movie the movie to be added to the list of watched movies
     */
    public void addWatchedMovie(final Movie movie) {
        this.watchedMovies.add(movie);
    }

    /**
     * Like the given movie.
     *
     * @param movie the movie to be added to the list of liked movies
     */
    public void addLikedMovie(final Movie movie) {
        this.likedMovies.add(movie);
    }

    /**
     * Rate the given movie.
     *
     * @param movie the movie to be added to the rated movies list
     */
    public void addRatedMovie(final Movie movie) {
        this.ratedMovies.add(movie);
    }
}
