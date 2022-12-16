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

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public filter.Filter getFilters() {
        return filters;
    }

    public void setFilters(Filter filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "ActionInput{" + "type='" + type + '\'' + ", page='" + page + '\'' + ", feature='"
               + feature + '\'' + ", credentials=" + credentials + ", startsWith='" + startsWith
               + '\'' + ", count=" + count + ", rate=" + rate + ", filters=" + filters + ", movie='"
               + movie + '\'' + '}';
    }
}
