package filter;

import movie.Movie;

import java.util.ArrayList;

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

        if (contains != null) {
            this.contains.applyContains(filteredMovies);
        }

        if (sort != null) {
            this.sort.applySort(filteredMovies);
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
            movies.sort((movie1, movie2) -> {
                if (this.duration != null) {
                    return sortByDuration(movie1, movie2);
                } else if (this.rating != null) {
                    return sortByRating(movie1, movie2);
                }

                return 0;
            });
        }

        private int sortByDuration(Movie movie1, Movie movie2) {
            if (SortType.getSortType(this.duration) == SortType.ASCENDING) {
                int sortResult = Integer.compare(movie1.getDuration(), movie2.getDuration());

                if (sortResult == 0 && this.rating != null) {
                    return sortByRating(movie1, movie2);
                }

                return sortResult;
            } else {
                int sortResult = Integer.compare(movie2.getDuration(), movie1.getDuration());

                if (sortResult == 0 && this.rating != null) {
                    return sortByRating(movie1, movie2);
                }

                return sortResult;
            }
        }

        private int sortByRating(Movie movie1, Movie movie2) {
            if (SortType.getSortType(this.rating) == SortType.ASCENDING) {
                return Double.compare(movie2.getRating(), movie1.getRating());
            } else {
                return Double.compare(movie1.getRating(), movie2.getRating());
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
