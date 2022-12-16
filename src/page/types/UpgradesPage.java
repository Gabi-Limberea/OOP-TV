package page.types;

import input.ActionInput;
import output.Output;
import page.Page;
import page.PageAction;
import page.PageTypes;
import session.Session;
import user.UserTypes;

import java.util.ArrayList;

public class UpgradesPage extends Page implements PageAction {
    private static final int PREMIUM_COST = 10;

    public UpgradesPage() {
        super(PageTypes.UPGRADES.getTitle());

        ArrayList<PageTypes> connectedPages = new ArrayList<>();
        connectedPages.add(PageTypes.AUTHORIZED_HOME_PAGE);
        connectedPages.add(PageTypes.MOVIES);
        connectedPages.add(PageTypes.LOGOUT);

        super.setConnectedPages(connectedPages);
    }

    @Override
    public Output updateOnPageChange(Session session) {
        return null;
    }

    @Override
    public Output execute(Session session, ActionInput action) {
        if (action.getFeature().equals(Actions.BUY_PREMIUM.toString())) {
            return buyPremium(session);
        }

        if (action.getFeature().equals(Actions.BUY_TOKENS.toString())) {
            return buyTokens(session, action);
        }

        return Output.genErrorOutput();
    }

    private Output buyPremium(Session session) {
        if (session.getCurrentUser().getCredentials().isPremium()
            || session.getCurrentUser().getTokensCount() < PREMIUM_COST) {
            return Output.genErrorOutput();
        }

        session.getCurrentUser().getCredentials().setAccountType(UserTypes.PREMIUM.toString());
        session.getCurrentUser().setTokensCount(
                session.getCurrentUser().getTokensCount() - PREMIUM_COST);

        return null;
    }

    private Output buyTokens(Session session, ActionInput action) {
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
