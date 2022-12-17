package page.types;

import input.ActionInput;
import output.Output;
import page.Page;
import page.PageActionStrategy;
import page.PageFactory;
import page.PageTypes;
import session.Session;
import user.User;

public final class RegisterPage extends Page implements PageActionStrategy {
    private static RegisterPage instance = null;

    private RegisterPage() {
        super(PageTypes.REGISTER.getTitle());
    }

    /**
     * @return instance of registration page
     */
    public static RegisterPage getInstance() {
        if (instance == null) {
            instance = new RegisterPage();
        }

        return instance;
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
        if (!action.getFeature().equals(Actions.REGISTER.toString())) {
            return Output.genErrorOutput();
        }

        return register(session, action);
    }

    /**
     * Try to register a new user. Will return error and go to the unauthorized
     * homepage if a user with the same username already exists.
     *
     * @param session the session in which the action is executed
     * @param action  the action to be executed
     * @return the appropriate output
     */
    public Output register(final Session session, final ActionInput action) {
        for (User user : session.getRegisteredUsers()) {
            if (user.getCredentials().getName().equals(action.getCredentials().getName())) {
                session.setCurrentPage(
                        PageFactory.getPage(PageTypes.UNAUTHORIZED_HOME_PAGE.getTitle(), null,
                                            null
                                           ));

                return Output.genErrorOutput();
            }
        }

        User newUser = new User(action.getCredentials());

        session.getRegisteredUsers().add(newUser);
        session.setCurrentPage(
                PageFactory.getPage(PageTypes.AUTHORIZED_HOME_PAGE.getTitle(), null, null));
        session.setCurrentUser(newUser);

        return new Output(
                null, session.getCurrentUser().getPurchasedMovies(), session.getCurrentUser());
    }


    protected enum Actions {
        REGISTER("register");

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
