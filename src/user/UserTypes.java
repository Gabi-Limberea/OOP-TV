package user;

public enum UserTypes {
    STANDARD("standard"), PREMIUM("premium");

    private final String userType;

    UserTypes(final String userType) {
        this.userType = userType;
    }

    public static UserTypes getUserType(final String userType) {
        for (UserTypes type : UserTypes.values()) {
            if (type.userType.equals(userType)) {
                return type;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.userType;
    }
}
