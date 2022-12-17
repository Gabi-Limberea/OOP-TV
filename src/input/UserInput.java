package input;

import user.Credentials;

public final class UserInput {
    private Credentials credentials;

    /**
     * @return the credentials of the user
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * @param credentials the new credentials of the user
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }
}
