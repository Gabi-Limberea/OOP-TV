package page.types;

import input.ActionInput;
import output.Output;
import page.Page;
import page.PageAction;
import page.PageTypes;
import session.Session;

import java.util.ArrayList;

public class MovieDetailsPage extends Page implements PageAction {
    private String movieTitle;
    public MovieDetailsPage(final String movieTitle) {
        super(PageTypes.MOVIE_DETAILS.getTitle());

        this.movieTitle = movieTitle;

        ArrayList<PageTypes> connectedPages = new ArrayList<>();
        connectedPages.add(PageTypes.MOVIES);
        connectedPages.add(PageTypes.AUTHORIZED_HOME_PAGE);
        connectedPages.add(PageTypes.UPGRADES);

        super.setConnectedPages(connectedPages);
    }

    @Override
    public Output updateOnPageChange(Session session) {
        return null;
    }

    @Override
    public Output execute(Session session, ActionInput action) {
        return null;
    }
}
