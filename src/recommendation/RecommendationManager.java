package recommendation;

import movie.Movie;
import notification.Notification;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class RecommendationManager {
    private static final String RECOMMENDATION_MSG = "Recommendation";
    private static final String NO_RECOMMENDATION  = "No recommendation";

    public RecommendationManager() {

    }

    /**
     * Try to recommend a movie to the user based on the user's preferences.
     *
     * @param user the user which will receive the recommendation
     */
    public void sendRecommendation(final User user) {
        HashMap<String, Integer> genresLiked = new HashMap<>();

        for (Movie movie : user.getLikedMovies()) {
            for (String genre : movie.getGenres()) {
                if (!genresLiked.containsKey(genre)) {
                    genresLiked.put(genre, 1);

                    continue;
                }

                genresLiked.put(genre, genresLiked.get(genre) + 1);
            }
        }

        Notification notification = new Notification(NO_RECOMMENDATION, RECOMMENDATION_MSG);

        while (notification.getMovieName().equals(NO_RECOMMENDATION) && !genresLiked.isEmpty()) {
            ArrayList<String> mostLikedGenres = new ArrayList<>();
            int max = 0;

            for (String genre : genresLiked.keySet()) {
                if (genresLiked.get(genre) > max) {
                    max = genresLiked.get(genre);

                    mostLikedGenres.clear();
                    mostLikedGenres.add(genre);
                } else if (genresLiked.get(genre) == max) {
                    mostLikedGenres.add(genre);
                }
            }

            mostLikedGenres.sort(String::compareTo);
            mostLikedGenres.forEach(genresLiked::remove);

            mostLikedGenres.forEach(
                    genre -> findMostPopularMovieFromGenre(user, genre, notification));
        }

        user.receiveNotification(
                new Notification(notification.getMovieName(), notification.getMessage()));
    }

    /**
     * Try to find the most popular movie from a given genre that the user likes
     * and that he hasn't seen yet. If such a movie is found, the notification
     * is updated with the movie's name.
     *
     * @param user         the user to whom the notification is sent
     * @param genre        the genre from which the movie is chosen
     * @param notification the notification to be sent
     */
    private void findMostPopularMovieFromGenre(
            final User user, final String genre, final Notification notification
                                              ) {
        ArrayList<Movie> mostLikedMovies = new ArrayList<>();

        for (Movie movie : user.getAvailableMovies()) {
            if (movie.getGenres().contains(genre) && !user.getWatchedMovies().contains(movie)) {
                mostLikedMovies.add(movie);
            }
        }


        if (!mostLikedMovies.isEmpty()) {
            mostLikedMovies.sort((movie1, movie2) -> Integer.compare(movie2.getNumLikes(),
                                                                     movie1.getNumLikes()
                                                                    ));

            notification.setMovieName(mostLikedMovies.get(0).getName());
        }
    }
}
