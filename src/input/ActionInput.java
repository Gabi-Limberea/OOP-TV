package input;

import com.fasterxml.jackson.annotation.JsonInclude;
import user.Credentials;

import java.util.ArrayList;

public final class ActionInput {
    private String type;
    private String page;
    private String feature;
    private Credentials credentials;
    private String startsWith;
    private int count;
    private int rate;
    private Filter filters;

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

    public Filter getFilters() {
        return filters;
    }

    public void setFilters(Filter filters) {
        this.filters = filters;
    }

    private static class Filter {
        private SortBy sort;
        private Contains contains;

        public SortBy getSort() {
            return sort;
        }

        public void setSort(SortBy sort) {
            this.sort = sort;
        }

        public Contains getContains() {
            return contains;
        }

        public void setContains(Contains contains) {
            this.contains = contains;
        }

        private static class SortBy {
            private String rating;
            private String duration;

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }
        }

        private static class Contains {
            private ArrayList<String> actors;
            private ArrayList<String> genre;

            public ArrayList<String> getActors() {
                return actors;
            }

            public void setActors(ArrayList<String> actors) {
                this.actors = actors;
            }

            public ArrayList<String> getGenre() {
                return genre;
            }

            public void setGenre(ArrayList<String> genre) {
                this.genre = genre;
            }
        }
    }
}
