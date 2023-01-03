package input;

import filter.Filter;
import user.Credentials;

public final class ActionInput {
    private String      type;
    private String      page;
    private String      feature;
    private Credentials credentials;
    private String      startsWith;
    private int         count;
    private int         rate;
    private Filter      filters;
    private String      movie;
    private MovieInput  addedMovie;
    private String deletedMovie;
    private String      subscribedGenre;

    /**
     * @return the movie to be deleted
     */
    public String getDeletedMovie() {
        return deletedMovie;
    }

    /**
     * @param deletedMovie the new movie to be deleted
     */
    public void setDeletedMovie(final String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }

    /**
     * @return the genre to which to subscribe
     */
    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    /**
     * @param subscribedGenre the new genre to which to subscribe
     */
    public void setSubscribedGenre(final String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }

    /**
     * @return the movie to be added
     */
    public MovieInput getAddedMovie() {
        return addedMovie;
    }


    /**
     * @param addedMovie the new movie to be added
     */
    public void setAddedMovie(final MovieInput addedMovie) {
        this.addedMovie = addedMovie;
    }

    /**
     * @return the name of the movie
     */
    public String getMovie() {
        return movie;
    }

    /**
     * @param movie the new name of the movie
     */
    public void setMovie(final String movie) {
        this.movie = movie;
    }

    /**
     * @return the type of the action
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the new type of the action
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page the new page
     */
    public void setPage(final String page) {
        this.page = page;
    }

    /**
     * @return the feature
     */
    public String getFeature() {
        return feature;
    }

    /**
     * @param feature the new feature
     */
    public void setFeature(final String feature) {
        this.feature = feature;
    }

    /**
     * @return the credentials
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * @param credentials the new credentials
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * @return the substring to search for
     */
    public String getStartsWith() {
        return startsWith;
    }

    /**
     * @param startsWith the new substring to search for
     */
    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    /**
     * @return the number of tokens
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the new number of tokens
     */
    public void setCount(final int count) {
        this.count = count;
    }

    /**
     * @return the rating
     */
    public int getRate() {
        return rate;
    }

    /**
     * @param rate the new rating
     */
    public void setRate(final int rate) {
        this.rate = rate;
    }

    /**
     * @return the filters
     */
    public filter.Filter getFilters() {
        return filters;
    }

    /**
     * @param filters the new filters
     */
    public void setFilters(final Filter filters) {
        this.filters = filters;
    }
}
