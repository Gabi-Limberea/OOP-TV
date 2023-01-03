package movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import input.MovieInput;
import user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public final class Movie {
    public static final int                    MAX_RATING = 5;
    private             String                 name;
    private             String                 year;
    private             int                    duration;
    private             ArrayList<String>      genres;
    private             ArrayList<String>      actors;
    private             ArrayList<String>      countriesBanned;
    private             int                    numLikes;
    private             int                    numRatings;
    @JsonSerialize(using = RatingSerializer.class)
    private             double                 rating;
    @JsonIgnore
    private             HashMap<User, Integer> ratings;

    public Movie(final MovieInput source) {
        name = source.getName();
        year = String.valueOf(source.getYear());
        duration = source.getDuration();
        genres = source.getGenres();
        actors = source.getActors();
        countriesBanned = source.getCountriesBanned();
        rating = 0.00f;
        numLikes = 0;
        numRatings = 0;
        ratings = new HashMap<>();
    }

    public Movie(final Movie source) {
        name = source.getName();
        year = source.getYear();
        duration = source.getDuration();
        genres = new ArrayList<>(source.getGenres());
        actors = new ArrayList<>(source.getActors());
        countriesBanned = new ArrayList<>(source.getCountriesBanned());
        rating = source.getRating();
        numLikes = source.getNumLikes();
        numRatings = source.getNumRatings();
        ratings = new HashMap<>(source.getRatings());
    }

    /**
     * @return the list of ratings that the movie has received
     */
    public HashMap<User, Integer> getRatings() {
        return ratings;
    }

    /**
     * @param ratings the new list of ratings that the movie has received
     */
    public void setRatings(final HashMap<User, Integer> ratings) {
        this.ratings = ratings;
    }

    /**
     * @return the name of the movie
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the new name of the movie
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the year in which the movie was released
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the new year in which the movie was released
     */
    public void setYear(final String year) {
        this.year = year;
    }

    /**
     * @return the duration of the movie
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the new duration of the movie
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /**
     * @return the list of genres that the movie belongs to
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * @param genres the new list of genres that the movie belongs to
     */
    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    /**
     * @return the list of actors that have played in the movie
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * @param actors the new list of actors that have played in the movie
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     * @return the list of countries that have banned the movie
     */
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    /**
     * @param countriesBanned the new list of countries that have banned the movie
     */
    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    /**
     * @return the overall rating of the movie
     */
    public double getRating() {
        return rating;
    }

    /**
     * @param rating the new overall rating of the movie
     */
    public void setRating(final double rating) {
        this.rating = rating;
    }

    /**
     * @return the number of likes that the movie has received
     */
    public int getNumLikes() {
        return numLikes;
    }

    /**
     * @param numLikes the new number of likes that the movie has received
     */
    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    /**
     * @return the number of ratings that the movie has received
     */
    public int getNumRatings() {
        return numRatings;
    }

    /**
     * @param numRatings the new number of ratings that the movie has received
     */
    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    /**
     * @param country the country to be checked
     * @return true if the movie is banned in the given country, false otherwise
     */
    public boolean isBanned(final String country) {
        return countriesBanned.contains(country);
    }

    /**
     * Add a like to the movie
     */
    public void addLike() {
        numLikes++;
    }

    /**
     * Adds a rating for the movie and recalculates the overall rating from scratch
     *
     * @param newRating the new rating to be added to the movie
     */
    public void addRating(final int newRating, final User user) {
        Integer oldValue = ratings.put(user, newRating);

        if (oldValue == null) {
            numRatings++;
        }

        double sum = 0;
        for (Integer dummyRating : ratings.values()) {
            sum += dummyRating;
        }

        rating = sum / numRatings;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Movie movie = (Movie) o;
        return Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private static class RatingSerializer extends JsonSerializer<Double> {
        @Override
        public void serialize(
                final Double value, final JsonGenerator gen, final SerializerProvider serializers
                             ) throws IOException {
            gen.writeNumber(String.format("%.2f", value));
        }
    }
}
