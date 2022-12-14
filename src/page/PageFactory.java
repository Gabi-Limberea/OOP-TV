package page;

import page.types.AuthorizedHomePage;
import page.types.LoginPage;
import page.types.MoviesPage;
import page.types.RegisterPage;
import page.types.UnauthorizedHomePage;
import page.types.UpgradesPage;

public class PageFactory {
    public static Page getPage(final String pageTitle) {
        switch (pageTitle) {
        case "login":
            return new LoginPage();
        case "register":
            return new RegisterPage();
        case "unauthorizedHomePage":
            return new UnauthorizedHomePage();
        case "movies":
            return new MoviesPage();
        case "authorizedHomePage":
            return new AuthorizedHomePage();
        case "upgrades":
            return new UpgradesPage();
        default:
            return null;
        }
    }
}
