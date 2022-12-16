package page.types;

import input.ActionInput;
import output.Output;
import page.Page;
import page.PageAction;
import page.PageFactory;
import page.PageTypes;
import session.Session;
import user.User;

public class RegisterPage extends Page implements PageAction {
    private static RegisterPage instance = null;

    private RegisterPage() {
        super(PageTypes.REGISTER.getTitle());
    }

    public static RegisterPage getInstance() {
        if (instance == null) {
            instance = new RegisterPage();
        }

        return instance;
    }

    @Override
    public Output updateOnPageChange(Session session) {
        return null;
    }

    @Override
    public Output execute(final Session session, final ActionInput action) {
        if (!action.getFeature().equals(Actions.REGISTER.toString())) {
            return Output.genErrorOutput();
        }

        return register(session, action);
    }

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
