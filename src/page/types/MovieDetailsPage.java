package page.types;

import input.ActionInput;
import movie.Movie;
import output.Output;
import page.Page;
import page.PageAction;
import page.PageTypes;
import session.Session;

import java.util.ArrayList;

public class MovieDetailsPage extends Page implements PageAction {
    private final String movieTitle;

    public MovieDetailsPage(final String movieTitle) {
        super(PageTypes.MOVIE_DETAILS.getTitle());

        this.movieTitle = movieTitle;

        ArrayList<PageTypes> connectedPages = new ArrayList<>();
        connectedPages.add(PageTypes.MOVIES);
        connectedPages.add(PageTypes.AUTHORIZED_HOME_PAGE);
        connectedPages.add(PageTypes.UPGRADES);
        connectedPages.add(PageTypes.LOGOUT);

        super.setConnectedPages(connectedPages);
    }

    @Override
    public Output updateOnPageChange(Session session) {
        ArrayList<Movie> focusedMovies = new ArrayList<>();

        for (Movie movie : session.getAvailableMovies()) {
            if (movie.getName().equals(this.movieTitle)) {
                focusedMovies.add(movie);
            }
        }

        if (focusedMovies.size() == 0) {
            return Output.genErrorOutput();
        }

        session.setAvailableMoviesForUser(focusedMovies);
        return new Output(null, session.getAvailableMoviesForUser(), session.getCurrentUser());
    }

    @Override
    public Output execute(Session session, ActionInput action) {
        return switch (Actions.getAction(action.getFeature())) {
            case PURCHASE -> purchase(session);
            case WATCH -> watch(session);
            case LIKE -> like(session);
            case RATE -> rate(session, action);
            case null -> Output.genErrorOutput();
        };
    }

    public Output purchase(Session session) {
        Movie movie = session.getAvailableMoviesForUser().get(0);

        if (session.getCurrentUser().getPurchasedMovies().contains(movie)) {
            return Output.genErrorOutput();
        }

        if (!session.getCurrentUser().addPurchasedMovie(movie)) return Output.genErrorOutput();

        return new Output(null, session.getAvailableMoviesForUser(), session.getCurrentUser());
    }

    public Output watch(Session session) {
        Movie movie = session.getAvailableMoviesForUser().get(0);

        if (session.getCurrentUser().getPurchasedMovies().contains(movie)) {
            session.getCurrentUser().addWatchedMovie(movie);

            return new Output(null, session.getAvailableMoviesForUser(), session.getCurrentUser());
        }

        return Output.genErrorOutput();
    }

    public Output like(Session session) {
        Movie movie = session.getAvailableMoviesForUser().get(0);

        if (session.getCurrentUser().getPurchasedMovies().contains(movie)
            && session.getCurrentUser().getWatchedMovies().contains(movie)) {
            session.getCurrentUser().addLikedMovie(movie);
            movie.addLike();

            return new Output(null, session.getAvailableMoviesForUser(), session.getCurrentUser());
        }

        return Output.genErrorOutput();
    }

    public Output rate(Session session, ActionInput action) {
        Movie movie = session.getAvailableMoviesForUser().get(0);

        if (session.getCurrentUser().getWatchedMovies().contains(movie)) {
            if (action.getRate() > Movie.MAX_RATING || action.getRate() < 1) {
                return Output.genErrorOutput();
            }

            movie.addRating(action.getRate());
            session.getCurrentUser().addRatedMovie(movie);

            return new Output(null, session.getAvailableMoviesForUser(), session.getCurrentUser());
        }

        return Output.genErrorOutput();
    }

    protected enum Actions {
        PURCHASE("purchase"), WATCH("watch"), LIKE("like"), RATE("rate");

        private final String action;

        Actions(final String action) {
            this.action = action;
        }

        public static Actions getAction(final String action) {
            for (Actions actionType : Actions.values()) {
                if (actionType.action.equals(action)) {
                    return actionType;
                }
            }

            return null;
        }
    }
}
