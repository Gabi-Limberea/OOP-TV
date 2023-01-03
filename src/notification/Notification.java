package notification;

import java.util.Objects;

public final class Notification {
    private String movieName;
    private String message;

    public Notification(final String movieName, final String message) {
        this.movieName = movieName;
        this.message = message;
    }

    public Notification(final Notification source) {
        movieName = source.movieName;
        message = source.message;
    }

    /**
     * @return the name of the movie for which the notification is sent
     */
    public String getMovieName() {
        return movieName;
    }

    /**
     * @param movieName the new name of the movie for which the notification is
     *                  sent
     */
    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }

    /**
     * @return the message of the notification
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the new message of the notification
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Notification that = (Notification) o;
        return Objects.equals(movieName, that.movieName) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieName, message);
    }
}
