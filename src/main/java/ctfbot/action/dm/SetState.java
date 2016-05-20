package ctfbot.action.dm;

import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import ctfbot.CTFBotContext;

/**
 * Action SetState for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "SetState", description = "Sets the state of the bot.")
public class SetState<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    /**
     * Constructor of the action, used during automatic instantiation.
     */
    public SetState(CONTEXT ctx) {
        super(ctx);
    }

    /**
     * Method responsible for initialization of the action. The method can be
     * passed parameters from the plan.
     */
    public void init() {
    }

    /**
     * Method called during each tick of the logic the action is supposed to
     * run.
     */
    public ActionResult run(@Param("$state") String state) {
        ctx.getLog().warning("ACTION: SetState[state=" + state + "]");
        
        ctx.state = state;

        return ActionResult.FINISHED;
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     */
    public void done() {
    }
    
}
