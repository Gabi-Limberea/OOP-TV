package session;

import page.Page;
import page.PageFactory;
import user.User;

public final class Session {
    private static Session instance = null;
    private User currentUser;
    private Page currentPage;

    private Session() {
        instance = new Session();
        currentPage = PageFactory.getPage("unauthorizedHomePage");
    }

    public static Session getInstance() {
        if (instance == null) {
            return new Session();
        }

        return instance;
    }
}
