package filter;

import movie.Movie;

import java.util.ArrayList;
import java.util.Comparator;

public class Filter {
    private SortBy   sort;
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

    @Override
    public String toString() {
        return "Filter{" + "sort=" + sort + ", contains=" + contains + '}';
    }

    public ArrayList<Movie> applyFilter(ArrayList<Movie> movies) {
        ArrayList<Movie> filteredMovies = new ArrayList<>(movies);

        if (sort != null) {
            this.sort.applySort(filteredMovies);
        }

        if (contains != null) {
            this.contains.applyContains(filteredMovies);
        }

        return filteredMovies;
    }

    protected enum SortType {
        ASCENDING("ascending"), DESCENDING("descending");

        private final String type;

        SortType(String type) {
            this.type = type;
        }

        public static SortType getSortType(String type) {
            for (SortType sortType : SortType.values()) {
                if (sortType.type.equals(type)) {
                    return sortType;
                }
            }

            return null;
        }
    }

    protected static class SortBy {
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

        public void applySort(ArrayList<Movie> movies) {
            switch (SortType.getSortType(this.rating)) {
                case ASCENDING -> movies.sort(Comparator.comparingDouble(Movie::getRating));
                case DESCENDING ->
                        movies.sort(Comparator.comparingDouble(Movie::getRating).reversed());
                case null -> {
                }
            }

            switch (SortType.getSortType(this.duration)) {
                case ASCENDING -> movies.sort(Comparator.comparingInt(Movie::getDuration));
                case DESCENDING ->
                        movies.sort(Comparator.comparingInt(Movie::getDuration).reversed());
                case null -> {
                }
            }
        }
    }

    protected static class Contains {
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

        public void applyContains(ArrayList<Movie> movies) {
            if (this.actors != null) {
                for (String actor : this.actors) {
                    movies.removeIf(movie -> !movie.getActors().contains(actor));
                }
            }

            if (this.genre != null) {
                for (String genre : this.genre) {
                    movies.removeIf(movie -> !movie.getGenres().contains(genre));
                }
            }
        }
    }
}
