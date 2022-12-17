package page.types;

import input.ActionInput;
import movie.Movie;
import output.Output;
import page.Page;
import page.PageActionStrategy;
import page.PageTypes;
import session.Session;

import java.util.ArrayList;

public final class MoviesPage extends Page implements PageActionStrategy {
    public MoviesPage() {
        super(PageTypes.MOVIES.getTitle());

        ArrayList<PageTypes> connectedPages = new ArrayList<>();
        connectedPages.add(PageTypes.AUTHORIZED_HOME_PAGE);
        connectedPages.add(PageTypes.LOGOUT);
        connectedPages.add(PageTypes.MOVIE_DETAILS);
        connectedPages.add(PageTypes.UPGRADES);
        connectedPages.add(PageTypes.MOVIES);

        setConnectedPages(connectedPages);
    }

    /**
     * Apply whatever changes are necessary when changing to a new page and generate the appropriate
     * output
     *
     * @param session the session to update
     * @return the output to be displayed
     */
    @Override
    public Output updateOnPageChange(final Session session) {
        ArrayList<Movie> moviesAvailableForUser = new ArrayList<>(session.getAvailableMovies());

        moviesAvailableForUser.removeIf(
                movie -> movie.isBanned(session.getCurrentUser().getCredentials().getCountry()));

        session.setAvailableMoviesForUser(moviesAvailableForUser);

        return new Output(null, session.getAvailableMoviesForUser(), session.getCurrentUser());
    }

    /**
     * Execute the action specified by the user and generate the appropriate
     * output. Can return error output if the action is not supported by the
     * page.
     *
     * @param session the session in which the action is executed
     * @param action  the action to be executed
     * @return the appropriate output
     */
    @Override
    public Output execute(final Session session, final ActionInput action) {
        if (action.getFeature().equals(Actions.SEARCH.toString())) {
            return search(session, action);
        }

        if (action.getFeature().equals(Actions.FILTER.toString())) {
            return filter(session, action);
        }

        return Output.genErrorOutput();
    }

    /**
     * Search for a movie based on whether the title starts with a given string.
     *
     * @param session the session in which the action is executed
     * @param action  the action to be executed
     * @return the appropriate output
     */
    private Output search(final Session session, final ActionInput action) {
        ArrayList<Movie> currentMoviesList = new ArrayList<>();

        for (Movie movie : session.getAvailableMoviesForUser()) {
            if (movie.getName().startsWith(action.getStartsWith())) {
                currentMoviesList.add(movie);
            }
        }

        return new Output(null, currentMoviesList, session.getCurrentUser());
    }

    /**
     * Filter the list of movies based on the given filters.
     *
     * @param session the session in which the action is executed
     * @param action  the action to be executed
     * @return the appropriate output
     */
    private Output filter(final Session session, final ActionInput action) {
        ArrayList<Movie> allMoviesAvailableForUser = new ArrayList<>(session.getAvailableMovies());

        allMoviesAvailableForUser.removeIf(
                movie -> movie.isBanned(session.getCurrentUser().getCredentials().getCountry()));

        ArrayList<Movie> filteredMovies = action.getFilters().applyFilter(
                allMoviesAvailableForUser);

        session.setAvailableMoviesForUser(filteredMovies);

        return new Output(null, session.getAvailableMoviesForUser(), session.getCurrentUser());
    }

    protected enum Actions {
        SEARCH("search"), FILTER("filter");

        private final String action;

        Actions(final String action) {
            this.action = action;
        }

        @Override
        public String toString() {
            return action;
        }
    }
}
