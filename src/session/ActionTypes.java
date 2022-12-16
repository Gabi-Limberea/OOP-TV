package session;

public enum ActionTypes {
    CHANGE_PAGE("change page"), ON_PAGE("on page");

    private final String title;

    ActionTypes(final String title) {
        this.title = title;
    }

    public static ActionTypes getActionType(final String title) {
        for (ActionTypes actionType : ActionTypes.values()) {
            if (actionType.title.equals(title)) {
                return actionType;
            }
        }

        return null;
    }
}
