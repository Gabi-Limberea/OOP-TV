package page;

import java.util.Objects;

public enum PageTypes {
    LOGIN("login"), REGISTER("register"), UNAUTHORIZED_HOME_PAGE("unauthorizedHomePage"),
    MOVIES("movies"), AUTHORIZED_HOME_PAGE("authorizedHomePage"), UPGRADES("upgrades"),
    MOVIE_DETAILS("see details"), LOGOUT("logout");

    private final String title;

    PageTypes(final String title) {
        this.title = title;
    }

    public static PageTypes getPageType(final String title) {
        for (PageTypes pageType : PageTypes.values()) {
            if (pageType.title.equals(title)) {
                return pageType;
            }
        }

        return null;
    }

    public static boolean hasAction(final String title) {
        return switch (Objects.requireNonNull(getPageType(title))) {
            case LOGIN, REGISTER, UPGRADES, MOVIES, MOVIE_DETAILS -> true;
            default -> false;
        };
    }

    public String getTitle() {
        return title;
    }
}
