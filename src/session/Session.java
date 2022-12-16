package session;

import input.ActionInput;
import input.MovieInput;
import input.UserInput;
import movie.Movie;
import output.Output;
import page.Page;
import page.PageAction;
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

    public Session(ArrayList<UserInput> registeredUsers, ArrayList<MovieInput> availableMovies) {
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

    public ArrayList<Movie> getAvailableMoviesForUser() {
        return availableMoviesForUser;
    }

    public void setAvailableMoviesForUser(ArrayList<Movie> availableMoviesForUser) {
        this.availableMoviesForUser = availableMoviesForUser;
    }

    public ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(ArrayList<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public ArrayList<Movie> getAvailableMovies() {
        return availableMovies;
    }

    public void setAvailableMovies(ArrayList<Movie> availableMovies) {
        this.availableMovies = availableMovies;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

    public ArrayList<Output> runSession(ArrayList<ActionInput> actions) {
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

                PageAction pageAction = (PageAction) currentPage;
                Output actionOutput = pageAction.execute(this, action);

                if (actionOutput != null) {
                    outputs.add(actionOutput);
                }
            }
        }

        return outputs;
    }
}
