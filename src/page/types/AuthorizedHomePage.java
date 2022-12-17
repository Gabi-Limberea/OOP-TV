package page.types;

import output.Output;
import page.Page;
import page.PageTypes;
import session.Session;

import java.util.ArrayList;

public final class AuthorizedHomePage extends Page {
    private static AuthorizedHomePage instance = null;

    private AuthorizedHomePage() {
        super(PageTypes.AUTHORIZED_HOME_PAGE.getTitle());

        ArrayList<PageTypes> connectedPages = new ArrayList<>();
        connectedPages.add(PageTypes.MOVIES);
        connectedPages.add(PageTypes.UPGRADES);
        connectedPages.add(PageTypes.MOVIE_DETAILS);
        connectedPages.add(PageTypes.LOGOUT);


        super.setConnectedPages(connectedPages);
    }

    /**
     * @return the instance of the authorized home page
     */
    public static AuthorizedHomePage getInstance() {
        if (instance == null) {
            instance = new AuthorizedHomePage();
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
}
