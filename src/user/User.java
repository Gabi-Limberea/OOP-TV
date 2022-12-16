package user;

import movie.Movie;

import java.util.ArrayList;

public class User {
    private Credentials      credentials;
    private int              tokensCount;
    private int              numFreePremiumMovies;
    private ArrayList<Movie> purchasedMovies;
    private ArrayList<Movie> watchedMovies;
    private ArrayList<Movie> likedMovies;
    private ArrayList<Movie> ratedMovies;

    public User(final Credentials source) {
        this.credentials = new Credentials(source);
        this.tokensCount = 0;
        this.numFreePremiumMovies = 15;
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

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

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

    public void addWatchedMovie(final Movie movie) {
        this.watchedMovies.add(movie);
    }

    public void addLikedMovie(final Movie movie) {
        this.likedMovies.add(movie);
    }

    public void addRatedMovie(final Movie movie) {
        this.ratedMovies.add(movie);
    }
}
