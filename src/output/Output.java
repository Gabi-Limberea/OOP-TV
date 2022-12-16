package output;

import movie.Movie;
import user.User;

import java.util.ArrayList;

public class Output {
    private static final String           ERROR = "Error";
    private              String           error;
    private              ArrayList<Movie> currentMoviesList;
    private              User             currentUser;

    public Output(String error, ArrayList<Movie> currentMoviesList, User currentUser) {
        this.error = error;

        if (currentMoviesList != null) {
            this.currentMoviesList = new ArrayList<>(currentMoviesList);
        } else {
            this.currentMoviesList = null;
        }

        if (currentUser != null) {
            this.currentUser = new User(currentUser);
        } else {
            this.currentUser = null;
        }
    }

    public static Output genErrorOutput() {
        return new Output(ERROR, new ArrayList<>(), null);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(ArrayList<Movie> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
