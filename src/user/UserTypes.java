package user;

public enum UserTypes {
    STANDARD("standard"), PREMIUM("premium");

    private final String name;

    UserTypes(final String name) {
        this.name = name;
    }

    /**
     * @param name the name of the given user account type
     * @return the type of the user account associated with the given name
     */
    public static UserTypes getUserType(final String name) {
        for (UserTypes type : UserTypes.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
