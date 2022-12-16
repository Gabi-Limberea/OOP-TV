package page.types;

import output.Output;
import page.Page;
import page.PageTypes;
import session.Session;

import java.util.ArrayList;

public class UnauthorizedHomePage extends Page {
    private static UnauthorizedHomePage instance = null;

    private UnauthorizedHomePage() {
        super(PageTypes.UNAUTHORIZED_HOME_PAGE.getTitle());

        ArrayList<PageTypes> connectedPages = new ArrayList<>();
        connectedPages.add(PageTypes.LOGIN);
        connectedPages.add(PageTypes.REGISTER);

        super.setConnectedPages(connectedPages);
    }

    public static UnauthorizedHomePage getInstance() {
        if (instance == null) {
            instance = new UnauthorizedHomePage();
        }

        return instance;
    }

    @Override
    public Output updateOnPageChange(Session session) {
        return null;
    }
}
