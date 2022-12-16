package page.types;

import input.ActionInput;
import movie.Movie;
import output.Output;
import page.Page;
import page.PageAction;
import page.PageTypes;
import session.Session;

import java.util.ArrayList;

public class MoviesPage extends Page implements PageAction {
    public MoviesPage() {
        super(PageTypes.MOVIES.getTitle());

        ArrayList<PageTypes> connectedPages = new ArrayList<>();
        connectedPages.add(PageTypes.AUTHORIZED_HOME_PAGE);
        connectedPages.add(PageTypes.LOGOUT);
        connectedPages.add(PageTypes.MOVIE_DETAILS);

        setConnectedPages(connectedPages);
    }

    @Override
    public Output updateOnPageChange(Session session) {
        ArrayList<Movie> moviesAvailableForUser = new ArrayList<>(session.getAvailableMovies());

        moviesAvailableForUser.removeIf(
                movie -> movie.isBanned(session.getCurrentUser().getCredentials().getCountry()));

        session.setAvailableMoviesForUser(moviesAvailableForUser);

        return new Output(null, session.getAvailableMoviesForUser(), session.getCurrentUser());
    }

    @Override
    public Output execute(Session session, ActionInput action) {
        if (action.getFeature().equals(Actions.SEARCH.toString())) {
            return search(session, action);
        }

        if (action.getFeature().equals(Actions.FILTER.toString())) {
            return filter(session, action);
        }

        return Output.genErrorOutput();
    }

    private Output search(Session session, ActionInput action) {
        ArrayList<Movie> currentMoviesList = new ArrayList<>();

        for (Movie movie : session.getAvailableMoviesForUser()) {
            if (movie.getName().equals(action.getMovie())) {
                currentMoviesList.add(movie);
            }
        }

        return new Output(null, currentMoviesList, session.getCurrentUser());
    }

    private Output filter(Session session, ActionInput action) {
        ArrayList<Movie> filteredMovies = action.getFilters().applyFilter(
                session.getAvailableMoviesForUser());

        session.setAvailableMoviesForUser(filteredMovies);

        return new Output(null, session.getAvailableMoviesForUser(), session.getCurrentUser());
    }

    protected enum Actions {
        SEARCH("search"), FILTER("filter");

        private final String action;

        Actions(String action) {
            this.action = action;
        }

        @Override
        public String toString() {
            return action;
        }
    }
}
