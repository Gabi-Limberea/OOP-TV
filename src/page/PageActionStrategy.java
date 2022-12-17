package page;

import input.ActionInput;
import output.Output;
import session.Session;

public interface PageActionStrategy {
    /**
     * Execute the action specified by the user and generate the appropriate
     * output. Can return error output if the action is not supported by the
     * page.
     *
     * @param session the session in which the action is executed
     * @param action  the action to be executed
     * @return the appropriate output
     */
    Output execute(Session session, ActionInput action);
}
