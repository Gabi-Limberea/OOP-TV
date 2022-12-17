package page.types;

import input.ActionInput;
import output.Output;
import page.Page;
import page.PageActionStrategy;
import page.PageFactory;
import page.PageTypes;
import session.Session;
import user.User;

public final class LoginPage extends Page implements PageActionStrategy {
    private static LoginPage instance = null;

    private LoginPage() {
        super(PageTypes.LOGIN.getTitle());
    }

    /**
     * @return the instance of the login page
     */
    public static LoginPage getInstance() {
        if (instance == null) {
            instance = new LoginPage();
        }

        return instance;
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
        return null;
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
        if (!action.getFeature().equals(Actions.LOGIN.toString())) {
            return Output.genErrorOutput();
        }

        return login(session, action);
    }

    /**
     * Try to log in the given user account. Will return error output and go to
     * the unauthorized homepage if the user account does not exist or if the
     * password is incorrect.
     *
     * @param session the session in which the action is executed
     * @param action  the action to be executed
     * @return the appropriate output
     */
    private Output login(final Session session, final ActionInput action) {
        for (User user : session.getRegisteredUsers()) {
            if (user.getCredentials().equals(action.getCredentials())) {
                session.setCurrentUser(user);
                session.setCurrentPage(
                        PageFactory.getPage(PageTypes.AUTHORIZED_HOME_PAGE.getTitle(), null, null));

                return new Output(null, session.getCurrentUser().getPurchasedMovies(),
                                  session.getCurrentUser()
                );
            }
        }

        session.setCurrentPage(
                PageFactory.getPage(PageTypes.UNAUTHORIZED_HOME_PAGE.getTitle(), null, null));
        return Output.genErrorOutput();
    }

    protected enum Actions {
        LOGIN("login");

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
