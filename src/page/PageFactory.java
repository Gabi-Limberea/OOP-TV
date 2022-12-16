package page;

import movie.Movie;
import page.types.AuthorizedHomePage;
import page.types.LoginPage;
import page.types.MovieDetailsPage;
import page.types.MoviesPage;
import page.types.RegisterPage;
import page.types.UnauthorizedHomePage;
import page.types.UpgradesPage;

import java.util.ArrayList;
import java.util.Objects;

public class PageFactory {
    public static Page getPage(
            final String pageTitle, final String movieTitle,
            final ArrayList<Movie> availableMoviesForUser
                              ) {
        return switch (Objects.requireNonNull(PageTypes.getPageType(pageTitle))) {
            case LOGIN -> LoginPage.getInstance();
            case REGISTER -> RegisterPage.getInstance();
            case UNAUTHORIZED_HOME_PAGE, LOGOUT -> UnauthorizedHomePage.getInstance();
            case MOVIES -> new MoviesPage();
            case AUTHORIZED_HOME_PAGE -> AuthorizedHomePage.getInstance();
            case UPGRADES -> new UpgradesPage();
            case MOVIE_DETAILS -> {
                for (Movie movie : availableMoviesForUser) {
                    if (movie.getName().equals(movieTitle)) {
                        yield new MovieDetailsPage(movieTitle);
                    }
                }

                yield null;
            }
        };
    }
}
