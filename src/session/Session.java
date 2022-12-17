package session;

import input.ActionInput;
import input.MovieInput;
import input.UserInput;
import movie.Movie;
import output.Output;
import page.Page;
import page.PageActionStrategy;
import page.PageFactory;
import page.PageTypes;
import user.User;

import java.util.ArrayList;

public final class Session {
    private ArrayList<User> registeredUsers;
    private ArrayList<Movie> availableMoviesForUser;
    private ArrayList<Movie> availableMovies;
    private User currentUser;
    private Page currentPage;

    public Session(
            final ArrayList<UserInput> registeredUsers, final ArrayList<MovieInput> availableMovies
                  ) {
        currentPage = PageFactory.getPage(PageTypes.UNAUTHORIZED_HOME_PAGE.getTitle(), null, null);
        this.registeredUsers = new ArrayList<>();
        this.availableMovies = new ArrayList<>();
        this.currentUser = null;

        for (UserInput user : registeredUsers) {
            this.registeredUsers.add(new User(user.getCredentials()));
        }

        for (MovieInput movie : availableMovies) {
            this.availableMovies.add(new Movie(movie));
        }
    }

    /**
     * @return the list of available movies for the current user
     */
    public ArrayList<Movie> getAvailableMoviesForUser() {
        return availableMoviesForUser;
    }

    /**
     * @param availableMoviesForUser the new list of available movies for the
     *                               current user
     */
    public void setAvailableMoviesForUser(final ArrayList<Movie> availableMoviesForUser) {
        this.availableMoviesForUser = availableMoviesForUser;
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
     * @return the list of available movies on the platform
     */
    public ArrayList<Movie> getAvailableMovies() {
        return availableMovies;
    }

    /**
     * @param availableMovies the new list of available movies on the platform
     */
    public void setAvailableMovies(final ArrayList<Movie> availableMovies) {
        this.availableMovies = availableMovies;
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
                                                      action.getMovie(), this.availableMoviesForUser
                                                     );

                if (newPage == null) {
                    outputs.add(Output.genErrorOutput());

                    continue;
                }

                currentPage = newPage;

                if (PageTypes.getPageType(action.getPage()) == PageTypes.LOGOUT) {
                    currentUser = null;
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
            }
        }

        return outputs;
    }
}
