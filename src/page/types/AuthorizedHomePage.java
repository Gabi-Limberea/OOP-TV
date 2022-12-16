package page.types;

import output.Output;
import page.Page;
import page.PageTypes;
import session.Session;

import java.util.ArrayList;

public class AuthorizedHomePage extends Page {
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

    public static AuthorizedHomePage getInstance() {
        if (instance == null) {
            instance = new AuthorizedHomePage();
        }

        return instance;
    }

    @Override
    public Output updateOnPageChange(Session session) {
        return null;
    }
}
