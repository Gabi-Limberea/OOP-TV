package movie;

import input.ActionInput;
import input.MovieInput;
import output.Output;
import subscription.SubscriptionManager;
import user.User;

import java.util.ArrayList;

public class MovieDatabase {
    private ArrayList<Movie> availableMovies;

    public MovieDatabase(final ArrayList<MovieInput> movies) {
        availableMovies = new ArrayList<>();

        for (MovieInput movie : movies) {
            availableMovies.add(new Movie(movie));
        }
    }

    /**
     * @return the list of available movies
     */
    public ArrayList<Movie> getAvailableMovies() {
        return availableMovies;
    }

    /**
     * @param availableMovies the new list of available movies
     */
    public void setAvailableMovies(final ArrayList<Movie> availableMovies) {
        this.availableMovies = availableMovies;
    }

    /**
     * Find and set the movies which are available for the given user
     *
     * @param user the user for which to find movies
     */
    public void findAvailableMoviesForUser(final User user) {
        for (Movie movie : availableMovies) {
            if (movie.isBanned(user.getCredentials().getCountry())) {
                continue;
            }

            user.getAvailableMovies().add(movie);
        }
    }

    /**
     * Add a new movie to the database
     *
     * @param movie the movie to be added to the database
     * @return true if the movie was added successfully
     */
    private boolean addMovie(final Movie movie) {
        if (availableMovies.contains(movie)) {
            return false;
        }

        return availableMovies.add(movie);
    }

    /**
     * Remove a movie from the database
     *
     * @param movie the movie to be removed
     * @return true if the movie was removed successfully
     */
    private boolean removeMovie(final String movie) {
        return availableMovies.removeIf(dummy -> dummy.getName().equals(movie));
    }

    /**
     * Modify the contents of the database.
     *
     * @param action the action to be performed
     * @return the appropriate output
     */
    public Output modifyDatabase(
            final ActionInput action, final SubscriptionManager subscriptionManager,
            final ArrayList<User> registeredUsers
                                ) {
        Movie movie = null;

        Output output = switch (Actions.getActionType(action.getFeature())) {
            case ADD_MOVIE -> {
                movie = new Movie(action.getAddedMovie());

                yield addMovie(movie) ? null : Output.genErrorOutput();
            }
            case REMOVE_MOVIE -> {
                movie = getMovie(action.getDeletedMovie());

                yield removeMovie(action.getDeletedMovie()) ? null : Output.genErrorOutput();
            }
            default -> Output.genErrorOutput();
        };

        if (movie != null) {
            for (String genre : movie.getGenres()) {
                subscriptionManager.notifySubscribers(genre, action.getFeature().toUpperCase(),
                                                      movie
                                                     );
            }
        }

        return output;
    }

    /**
     * Find the movie with the given name in the database.
     *
     * @param movieName the name of the movie to be found
     * @return the movie with the given name
     */
    public Movie getMovie(final String movieName) {
        for (Movie movie : availableMovies) {
            if (movie.getName().equals(movieName)) {
                return movie;
            }
        }

        return null;
    }

    protected enum Actions {
        ADD_MOVIE("add"), REMOVE_MOVIE("delete"), NONE("none");

        private final String title;

        Actions(final String title) {
            this.title = title;
        }

        public static Actions getActionType(final String action) {
            for (Actions actionType : Actions.values()) {
                if (actionType.title.equals(action)) {
                    return actionType;
                }
            }

            return NONE;
        }
    }
}
