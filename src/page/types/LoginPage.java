package page.types;

import input.ActionInput;
import output.Output;
import page.Page;
import page.PageAction;
import page.PageFactory;
import page.PageTypes;
import session.Session;
import user.User;

public class LoginPage extends Page implements PageAction {
    private static LoginPage instance = null;

    private LoginPage() {
        super(PageTypes.LOGIN.getTitle());
    }

    public static LoginPage getInstance() {
        if (instance == null) {
            instance = new LoginPage();
        }

        return instance;
    }

    @Override
    public Output updateOnPageChange(final Session session) {
        return null;
    }

    @Override
    public Output execute(final Session session, final ActionInput action) {
        if (!action.getFeature().equals(Actions.LOGIN.toString())) {
            return Output.genErrorOutput();
        }

        return login(session, action);
    }

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
