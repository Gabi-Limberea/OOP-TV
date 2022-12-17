package page.types;

import output.Output;
import page.Page;
import page.PageTypes;
import session.Session;

import java.util.ArrayList;

public final class UnauthorizedHomePage extends Page {
    private static UnauthorizedHomePage instance = null;

    private UnauthorizedHomePage() {
        super(PageTypes.UNAUTHORIZED_HOME_PAGE.getTitle());

        ArrayList<PageTypes> connectedPages = new ArrayList<>();
        connectedPages.add(PageTypes.LOGIN);
        connectedPages.add(PageTypes.REGISTER);

        super.setConnectedPages(connectedPages);
    }

    /**
     * @return the instance of the unauthorized home page
     */
    public static UnauthorizedHomePage getInstance() {
        if (instance == null) {
            instance = new UnauthorizedHomePage();
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
}
