package page.types;

import input.ActionInput;
import output.Output;
import page.Page;
import page.PageActionStrategy;
import page.PageTypes;
import session.Session;
import user.UserTypes;

import java.util.ArrayList;

public final class UpgradesPage extends Page implements PageActionStrategy {
    private static final int PREMIUM_COST = 10;

    public UpgradesPage() {
        super(PageTypes.UPGRADES.getTitle());

        ArrayList<PageTypes> connectedPages = new ArrayList<>();
        connectedPages.add(PageTypes.AUTHORIZED_HOME_PAGE);
        connectedPages.add(PageTypes.MOVIES);
        connectedPages.add(PageTypes.LOGOUT);
        connectedPages.add(PageTypes.UPGRADES);

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
        if (action.getFeature().equals(Actions.BUY_PREMIUM.toString())) {
            return buyPremium(session);
        }

        if (action.getFeature().equals(Actions.BUY_TOKENS.toString())) {
            return buyTokens(session, action);
        }

        return Output.genErrorOutput();
    }

    /**
     * Buy a premium subscription for the user.
     *
     * @param session the session in which the action is executed
     * @return the appropriate output
     */
    private Output buyPremium(final Session session) {
        if (session.getCurrentUser().getCredentials().isPremium()
            || session.getCurrentUser().getTokensCount() < PREMIUM_COST) {
            return Output.genErrorOutput();
        }

        session.getCurrentUser().getCredentials().setAccountType(UserTypes.PREMIUM.toString());
        session.getCurrentUser().setTokensCount(
                session.getCurrentUser().getTokensCount() - PREMIUM_COST);

        return null;
    }

    /**
     * Buy tokens for the user.
     *
     * @param session the session in which the action is executed
     * @param action  the action to be executed
     * @return the appropriate output
     */
    private Output buyTokens(final Session session, final ActionInput action) {
        if (Integer.parseInt(session.getCurrentUser().getCredentials().getBalance())
            < action.getCount()) {
            return Output.genErrorOutput();
        }

        session.getCurrentUser().getCredentials().setBalance(String.valueOf(
                Integer.parseInt(session.getCurrentUser().getCredentials().getBalance())
                - action.getCount()));
        session.getCurrentUser().setTokensCount(
                session.getCurrentUser().getTokensCount() + action.getCount());

        return null;
    }

    protected enum Actions {
        BUY_PREMIUM("buy premium account"), BUY_TOKENS("buy tokens");

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
