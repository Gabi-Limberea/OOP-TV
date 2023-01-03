package session;

public enum ActionTypes {
    CHANGE_PAGE("change page"), ON_PAGE("on page"), DATABASE("database"),
    BACK("back"), NONE("none");

    private final String name;

    ActionTypes(final String name) {
        this.name = name;
    }

    /**
     * @param name the name of the action type
     * @return the action type associated with the name
     */
    public static ActionTypes getActionType(final String name) {
        for (ActionTypes actionType : ActionTypes.values()) {
            if (actionType.name.equals(name)) {
                return actionType;
            }
        }

        return NONE;
    }
}
