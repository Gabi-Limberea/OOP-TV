package output;

import movie.Movie;
import user.User;

import java.util.ArrayList;

public class Output {
    private String error;
    private ArrayList<Movie> currentMovieList;
    private User currentUser;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<Movie> getCurrentMovieList() {
        return currentMovieList;
    }

    public void setCurrentMovieList(ArrayList<Movie> currentMovieList) {
        this.currentMovieList = currentMovieList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
