package input;

import java.util.ArrayList;

public final class Input {
    private ArrayList<UserInput>   users;
    private ArrayList<MovieInput>  movies;
    private ArrayList<ActionInput> actions;

    /**
     * @return the list of users
     */
    public ArrayList<UserInput> getUsers() {
        return users;
    }

    /**
     * @param users the new list of users
     */
    public void setUsers(final ArrayList<UserInput> users) {
        this.users = users;
    }

    /**
     * @return the list of movies
     */
    public ArrayList<MovieInput> getMovies() {
        return movies;
    }

    /**
     * @param movies the new list of movies
     */
    public void setMovies(final ArrayList<MovieInput> movies) {
        this.movies = movies;
    }

    /**
     * @return the list of actions
     */
    public ArrayList<ActionInput> getActions() {
        return actions;
    }

    /**
     * @param actions the new list of actions
     */
    public void setActions(final ArrayList<ActionInput> actions) {
        this.actions = actions;
    }
}
