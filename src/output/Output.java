package output;

import movie.Movie;
import user.User;

import java.util.ArrayList;

public final class Output {
    private static final String           ERROR = "Error";
    private              String           error;
    private              ArrayList<Movie> currentMoviesList;
    private              User             currentUser;

    public Output(
            final String error, final ArrayList<Movie> currentMoviesList, final User currentUser
                 ) {
        this.error = error;

        if (currentMoviesList != null) {
            this.currentMoviesList = new ArrayList<>();

            for (Movie movie : currentMoviesList) {
                this.currentMoviesList.add(new Movie(movie));
            }
        } else {
            this.currentMoviesList = null;
        }

        if (currentUser != null) {
            this.currentUser = new User(currentUser);
        } else {
            this.currentUser = null;
        }
    }

    /**
     * @return the default error output
     */
    public static Output genErrorOutput() {
        return new Output(ERROR, new ArrayList<>(), null);
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(final String error) {
        this.error = error;
    }

    /**
     * @return the current list of movies to be displayed
     */
    public ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    /**
     * @param currentMoviesList the new list of movies to be displayed
     */
    public void setCurrentMoviesList(final ArrayList<Movie> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    /**
     * @return the current user to be displayed
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the new user to be displayed
     */
    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }
}
