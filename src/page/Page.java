package page;

import movie.Movie;
import output.Output;
import session.Session;

import java.util.ArrayList;

public abstract class Page {
    private String               title;
    private ArrayList<PageTypes> connectedPages;

    public Page(final String title) {
        this.title = title;
        this.connectedPages = new ArrayList<>();
    }

    public ArrayList<PageTypes> getConnectedPages() {
        return connectedPages;
    }

    public void setConnectedPages(final ArrayList<PageTypes> connectedPages) {
        this.connectedPages = connectedPages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Page changePage(
            final PageTypes pageType, final String movieTitle,
            final ArrayList<Movie> availableMoviesForUser
                          ) {
        if (connectedPages.contains(pageType)) {
            return PageFactory.getPage(pageType.getTitle(), movieTitle, availableMoviesForUser);
        }

        return null;
    }

    public abstract Output updateOnPageChange(final Session session);
}
