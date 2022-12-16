package page;

import input.ActionInput;
import output.Output;
import session.Session;

public interface PageAction {
    Output execute(Session session, ActionInput action);
}
