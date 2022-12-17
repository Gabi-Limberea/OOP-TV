package input;

import java.util.ArrayList;

public final class MovieInput {
    private String            name;
    private int               year;
    private int               duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;

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
     * @return the year that the movie came out
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the new year that the movie came out
     */
    public void setYear(final int year) {
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
     * @return the genres that fit the movie
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * @param genres the new genres that fit the movie
     */
    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    /**
     * @return the list of actors that played in the movie
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * @param actors the new list of actors that played in the movie
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     * @return the list of countries that banned the movie
     */
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    /**
     * @param countriesBanned the new list of countries that banned the movie
     */
    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }
}
