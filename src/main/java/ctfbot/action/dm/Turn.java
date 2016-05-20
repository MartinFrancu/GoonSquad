package ctfbot.action.dm;

import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import ctfbot.CTFBotContext;

/**
 * Action Turn for Yaposh.
 * 
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name="Turn", description="Turns the bot 90 degrees.")
public class Turn<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    public Turn(CONTEXT ctx) {
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
    public ActionResult run() {
        ctx.getLog().warning("ACTION: Turn");
        
        ctx.getMove().turnHorizontal(90);
        return ActionResult.RUNNING_ONCE;
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     */
    public void done() {
    }
    
}
