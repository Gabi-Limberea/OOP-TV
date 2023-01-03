package session;

import input.ActionInput;
import input.MovieInput;
import input.UserInput;
import movie.Movie;
import movie.MovieDatabase;
import output.Output;
import page.Page;
import page.PageActionStrategy;
import page.PageFactory;
import page.PageTypes;
import page.types.MovieDetailsPage;
import stack.Stack;
import subscription.SubscriptionManager;
import user.User;

import java.util.ArrayList;

public final class Session {
    private User                currentUser;
    private Page                currentPage;
    private ArrayList<User>     registeredUsers;
    private MovieDatabase       moviesDatabase;
    private ArrayList<Movie>    moviesOnPage;
    private Stack<Page>         history;
    private SubscriptionManager subscriptionManager;

    public Session(
            final ArrayList<UserInput> registeredUsers, final ArrayList<MovieInput> availableMovies
                  ) {
        currentPage = PageFactory.getPage(PageTypes.UNAUTHORIZED_HOME_PAGE.getTitle(), null, null);
        this.registeredUsers = new ArrayList<>();
        moviesDatabase = new MovieDatabase(availableMovies);
        currentUser = null;
        moviesOnPage = new ArrayList<>();
        history = new Stack<>();

        for (UserInput user : registeredUsers) {
            this.registeredUsers.add(new User(user.getCredentials()));
        }

        subscriptionManager = new SubscriptionManager();
    }

    /**
     * @return the subscription manager for this session
     */
    public SubscriptionManager getSubscriptionManager() {
        return subscriptionManager;
    }

    /**
     * @param subscriptionManager the new subscription manager for this session
     */
    public void setSubscriptionManager(final SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    /**
     * @return the pages that the user has visited
     */
    public Stack<Page> getHistory() {
        return history;
    }

    /**
     * @param history the new pages that the user has visited
     */
    public void setHistory(final Stack<Page> history) {
        this.history = history;
    }

    /**
     * @return the list of registered users
     */
    public ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }

    /**
     * @param registeredUsers the new list of registered users
     */
    public void setRegisteredUsers(final ArrayList<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    /**
     * @return the movies shown on the current page
     */
    public ArrayList<Movie> getMoviesOnPage() {
        return moviesOnPage;
    }

    /**
     * @param moviesOnPage the new movies to be shown on the current page
     */
    public void setMoviesOnPage(final ArrayList<Movie> moviesOnPage) {
        this.moviesOnPage = moviesOnPage;
    }

    /**
     * @return the list of available movies on the platform
     */
    public MovieDatabase getMoviesDatabase() {
        return moviesDatabase;
    }

    /**
     * @param moviesDatabase the new list of available movies on the platform
     */
    public void setMoviesDatabase(final MovieDatabase moviesDatabase) {
        this.moviesDatabase = moviesDatabase;
    }

    /**
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the new current user
     */
    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return the current page
     */
    public Page getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the new current page
     */
    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @param actions the list of actions to be executed
     * @return the list of outputs resulted from the execution of the actions
     */
    public ArrayList<Output> runSession(final ArrayList<ActionInput> actions) {
        ArrayList<Output> outputs = new ArrayList<>();

        for (ActionInput action : actions) {
            if (ActionTypes.getActionType(action.getType()) == ActionTypes.CHANGE_PAGE) {
                Page newPage = currentPage.changePage(PageTypes.getPageType(action.getPage()),
                                                      action.getMovie(), moviesOnPage
                                                     );

                if (newPage == null) {
                    outputs.add(Output.genErrorOutput());

                    continue;
                }

                if (PageTypes.canAddToHistory(currentPage.getTitle())) {
                    history.push(currentPage);
                }

                currentPage = newPage;

                if (PageTypes.getPageType(action.getPage()) == PageTypes.LOGOUT) {
                    currentUser = null;
                    history.clear();
                }

                Output outputOnPageChange = currentPage.updateOnPageChange(this);

                if (outputOnPageChange != null) {
                    outputs.add(outputOnPageChange);
                }
            } else if (ActionTypes.getActionType(action.getType()) == ActionTypes.ON_PAGE) {
                if (!PageTypes.hasAction(currentPage.getTitle())) {
                    outputs.add(Output.genErrorOutput());
                    continue;
                }

                PageActionStrategy pageAction = (PageActionStrategy) currentPage;
                Output actionOutput = pageAction.execute(this, action);

                if (actionOutput != null) {
                    outputs.add(actionOutput);
                }
            } else if (ActionTypes.getActionType(action.getType()) == ActionTypes.BACK) {
                if (history.isEmpty()) {
                    outputs.add(Output.genErrorOutput());

                    continue;
                }

                Page backPage;
                Page dummyPage = history.pop();
                PageTypes dummyPageType = PageTypes.getPageType(dummyPage.getTitle());

                if (dummyPageType == PageTypes.MOVIE_DETAILS) {
                    MovieDetailsPage detailPage = (MovieDetailsPage) dummyPage;

                    backPage = currentPage.changePage(dummyPageType, detailPage.getMovieTitle(),
                                                      moviesOnPage
                                                     );
                } else {
                    backPage = currentPage.changePage(dummyPageType, null, moviesOnPage);
                }

                if (backPage == null) {
                    outputs.add(Output.genErrorOutput());

                    continue;
                }

                currentPage = backPage;

                Output outputOnPageChange = currentPage.updateOnPageChange(this);

                if (outputOnPageChange != null) {
                    outputs.add(outputOnPageChange);
                }
            } else if (ActionTypes.getActionType(action.getType()) == ActionTypes.DATABASE) {
                Output actionOutput = moviesDatabase.modifyDatabase(action, subscriptionManager,
                                                                    registeredUsers
                                                                   );

                if (actionOutput != null) {
                    outputs.add(actionOutput);
                } else if (PageTypes.getPageType(currentPage.getTitle()) == PageTypes.MOVIES) {
                    moviesOnPage = currentUser.getAvailableMovies();
                }
            }
        }

        if (currentUser != null && currentUser.getCredentials().isPremium()) {
            subscriptionManager.sendRecommendation(currentUser);
            outputs.add(new Output(null, null, currentUser));
        }

        return outputs;
    }
}
