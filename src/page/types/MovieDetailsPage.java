package page.types;

import input.ActionInput;
import movie.Movie;
import output.Output;
import page.Page;
import page.PageActionStrategy;
import page.PageTypes;
import session.Session;

import java.util.ArrayList;

public final class MovieDetailsPage extends Page implements PageActionStrategy {
    private final String movieTitle;

    public MovieDetailsPage(final String movieTitle) {
        super(PageTypes.MOVIE_DETAILS.getTitle());

        this.movieTitle = movieTitle;

        ArrayList<PageTypes> connectedPages = new ArrayList<>();
        connectedPages.add(PageTypes.MOVIES);
        connectedPages.add(PageTypes.AUTHORIZED_HOME_PAGE);
        connectedPages.add(PageTypes.UPGRADES);
        connectedPages.add(PageTypes.LOGOUT);
        connectedPages.add(PageTypes.MOVIE_DETAILS);

        super.setConnectedPages(connectedPages);
    }

    /**
     * Apply whatever changes are necessary when changing to a new page and
     * generate the appropriate output
     *
     * @param session the session to update
     * @return the output to be displayed
     */
    @Override
    public Output updateOnPageChange(final Session session) {
        ArrayList<Movie> focusedMovies = new ArrayList<>();

        for (Movie movie : session.getCurrentUser().getAvailableMovies()) {
            if (movie.getName().equals(this.movieTitle)) {
                focusedMovies.add(movie);
            }
        }

        if (focusedMovies.size() == 0) {
            return Output.genErrorOutput();
        }

        session.setMoviesOnPage(focusedMovies);
        return new Output(null, session.getMoviesOnPage(), session.getCurrentUser());
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
        return switch (Actions.getAction(action.getFeature())) {
            case PURCHASE -> purchase(session);
            case WATCH -> watch(session);
            case LIKE -> like(session);
            case RATE -> rate(session, action);
            case SUBSCRIBE -> subscribe(session, action);
            default -> Output.genErrorOutput();
        };
    }

    /**
     * Try to purchase the movie that the page is focused on.
     * Will return error output if the user does not have enough tokens for it
     * or if they already own it.
     *
     * @param session the session in which the action is executed
     * @return the appropriate output
     */
    public Output purchase(final Session session) {
        Movie movie = session.getMoviesOnPage().get(0);

        if (session.getCurrentUser().getPurchasedMovies().contains(movie)) {
            return Output.genErrorOutput();
        }

        if (!session.getCurrentUser().addPurchasedMovie(movie)) {
            return Output.genErrorOutput();
        }

        return new Output(null, session.getMoviesOnPage(), session.getCurrentUser());
    }

    /**
     * Try to watch the movie that the page is focused on.
     * Will return error output if the user does not own it.
     *
     * @param session the session in which the action is executed
     * @return the appropriate output
     */
    public Output watch(final Session session) {
        Movie movie = session.getMoviesOnPage().get(0);

        if (session.getCurrentUser().getPurchasedMovies().contains(movie)) {
            session.getCurrentUser().addWatchedMovie(movie);

            return new Output(null, session.getMoviesOnPage(), session.getCurrentUser());
        }

        return Output.genErrorOutput();
    }

    /**
     * Try to like the movie that the page is focused on.
     * Will return error output if the user already likes it or has not watched
     * the movie.
     *
     * @param session the session in which the action is executed
     * @return the appropriate output
     */
    public Output like(final Session session) {
        Movie movie = session.getMoviesOnPage().get(0);

        if (session.getCurrentUser().getPurchasedMovies().contains(movie)
            && session.getCurrentUser().getWatchedMovies().contains(movie)) {
            session.getCurrentUser().addLikedMovie(movie);
            movie.addLike();

            return new Output(null, session.getMoviesOnPage(), session.getCurrentUser());
        }

        return Output.genErrorOutput();
    }

    /**
     * Try to rate the movie that the page is focused on.
     * Will return error output if the user has not watched the movie or if the
     * rating value is invalid (not between 1 and 5).
     *
     * @param session the session in which the action is executed
     * @param action  the action to be executed
     * @return the appropriate output
     */
    public Output rate(final Session session, final ActionInput action) {
        Movie movie = session.getMoviesOnPage().get(0);

        if (session.getCurrentUser().getWatchedMovies().contains(movie)) {
            if (action.getRate() > Movie.MAX_RATING || action.getRate() < 1) {
                return Output.genErrorOutput();
            }

            movie.addRating(action.getRate(), session.getCurrentUser());
            session.getCurrentUser().addRatedMovie(movie);

            return new Output(null, session.getMoviesOnPage(), session.getCurrentUser());
        }

        return Output.genErrorOutput();
    }

    /**
     * Try to subscribe to one of the current movie's genres.
     * Will return error output if the user already has a subscription to that
     * genre or if the movie is not part of that genre.
     *
     * @param session the session in which the action is executed
     * @param action  the action to be executed
     * @return the appropriate output
     */
    public Output subscribe(final Session session, final ActionInput action) {
        Movie movie = session.getMoviesOnPage().get(0);

        if (!movie.getGenres().contains(action.getSubscribedGenre())
            || session.getSubscriptionManager().isSubscribed(action.getSubscribedGenre(),
                                                             session.getCurrentUser()
                                                            )) {
            return Output.genErrorOutput();
        }

        session.getCurrentUser().subscribeTo(
                action.getSubscribedGenre(), session.getSubscriptionManager());

        return null;
    }

    /**
     * @return the title of the movie accessed on this page
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    protected enum Actions {
        PURCHASE("purchase"), WATCH("watch"), LIKE("like"), RATE("rate"), SUBSCRIBE("subscribe"),
        NONE("none");

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

            return NONE;
        }
    }
}
