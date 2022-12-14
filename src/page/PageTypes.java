package page;

public enum PageTypes {
    LOGIN("login"),
    REGISTER("register"),
    UNAUTHORIZED_HOME_PAGE("unauthorizedHomePage"),
    MOVIES("movies"),
    AUTHORIZED_HOME_PAGE("authorizedHomePage"),
    UPGRADES("upgrades");

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
}
