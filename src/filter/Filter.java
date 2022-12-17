package filter;

import movie.Movie;

import java.util.ArrayList;

public final class Filter {
    private SortBy   sort;
    private Contains contains;

    /**
     * @return the sort type
     */
    public SortBy getSort() {
        return sort;
    }

    /**
     * @param sort the new sort type
     */
    public void setSort(final SortBy sort) {
        this.sort = sort;
    }

    /**
     * @return the contains filter type
     */
    public Contains getContains() {
        return contains;
    }

    /**
     * @param contains the new contains filter type
     */
    public void setContains(final Contains contains) {
        this.contains = contains;
    }

    /**
     * Apply the given filters to the list of movies.
     *
     * @param movies the movies to be filtered
     * @return the filtered movies
     */
    public ArrayList<Movie> applyFilter(final ArrayList<Movie> movies) {
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

        SortType(final String type) {
            this.type = type;
        }

        public static SortType getSortType(final String type) {
            for (SortType sortType : SortType.values()) {
                if (sortType.type.equals(type)) {
                    return sortType;
                }
            }

            return null;
        }
    }

    protected static final class SortBy {
        private String rating;
        private String duration;

        /**
         * @return the way in which the movies are sorted by rating (ascending
         * or descending)
         */
        public String getRating() {
            return rating;
        }

        /**
         * @param rating the new way in which the movies are sorted by rating
         */
        public void setRating(final String rating) {
            this.rating = rating;
        }

        /**
         * @return the way in which the movies are sorted by duration (ascending
         * or descending)
         */
        public String getDuration() {
            return duration;
        }

        /**
         * @param duration the new way in which the movies are sorted by
         *                 duration
         */
        public void setDuration(final String duration) {
            this.duration = duration;
        }

        /**
         * Sort the movies by the given criteria (rating, duration or both).
         *
         * @param movies the movies to be sorted
         */
        public void applySort(final ArrayList<Movie> movies) {
            movies.sort((movie1, movie2) -> {
                if (this.duration != null) {
                    return sortByDuration(movie1, movie2);
                } else if (this.rating != null) {
                    return sortByRating(movie1, movie2);
                }

                return 0;
            });
        }

        /**
         * Try to compare the two given movies by duration, either ascending or
         * descending. If the movies have the same duration, compare them by
         * rating.
         *
         * @param movie1 the first movie
         * @param movie2 the second movie
         * @return a value < 0 if movie1 is in the right position compared to
         * movie2, 0 if they are equal, a value > 0 otherwise
         */
        private int sortByDuration(final Movie movie1, final Movie movie2) {
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

        /**
         * Try to compare the two given movies by rating, either ascending or
         * descending. In case of equality, leave the movies as they are.
         *
         * @param movie1 the first movie
         * @param movie2 the second movie
         * @return a value < 0 if movie1 is in the right position compared to
         * movie2, 0 if they are equal, a value > 0 otherwise
         */
        private int sortByRating(final Movie movie1, final Movie movie2) {
            if (SortType.getSortType(this.rating) == SortType.ASCENDING) {
                return Double.compare(movie2.getRating(), movie1.getRating());
            } else {
                return Double.compare(movie1.getRating(), movie2.getRating());
            }
        }
    }

    protected static final class Contains {
        private ArrayList<String> actors;
        private ArrayList<String> genre;

        /**
         * @return the actors that must be in the movies
         */
        public ArrayList<String> getActors() {
            return actors;
        }

        /**
         * @param actors the new actors that must be in the movies
         */
        public void setActors(final ArrayList<String> actors) {
            this.actors = actors;
        }

        /**
         * @return the genres that must fit the movies
         */
        public ArrayList<String> getGenre() {
            return genre;
        }

        /**
         * @param genre the new genres that must fit the movies
         */
        public void setGenre(final ArrayList<String> genre) {
            this.genre = genre;
        }

        /**
         * Remove the movies that do not contain any of the given actors and genres.
         *
         * @param movies the movies to be filtered
         */
        public void applyContains(final ArrayList<Movie> movies) {
            if (this.actors != null) {
                for (String actor : this.actors) {
                    movies.removeIf(movie -> !movie.getActors().contains(actor));
                }
            }

            if (this.genre != null) {
                for (String dummyGenre : this.genre) {
                    movies.removeIf(movie -> !movie.getGenres().contains(dummyGenre));
                }
            }
        }
    }
}
