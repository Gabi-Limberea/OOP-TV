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

    /**
     * @return the types of pages that are accessible from this page
     */
    public ArrayList<PageTypes> getConnectedPages() {
        return connectedPages;
    }

    /**
     * @param connectedPages the types of pages that are accessible from this page
     */
    public void setConnectedPages(final ArrayList<PageTypes> connectedPages) {
        this.connectedPages = connectedPages;
    }

    /**
     * @return the title of this page
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title of this page
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @param pageType               the type of page to change to
     * @param movieTitle             the title of the movie when changing to the movie details page
     * @param availableMoviesForUser the movies that are available for the user
     * @return the new page
     */
    public Page changePage(
            final PageTypes pageType, final String movieTitle,
            final ArrayList<Movie> availableMoviesForUser
                          ) {
        if (connectedPages.contains(pageType)) {
            return PageFactory.getPage(pageType.getTitle(), movieTitle, availableMoviesForUser);
        }

        return null;
    }

    /**
     * Apply whatever changes are necessary when changing to a new page and generate the appropriate
     * output
     *
     * @param session the session to update
     * @return the output to be displayed
     */
    public abstract Output updateOnPageChange(Session session);
}
