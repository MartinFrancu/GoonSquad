package ctfbot.action.dm;

import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import ctfbot.CTFBotContext;

/**
 * Action StopShooting for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name="StopShooting", description="Stop shooting.")
public class StopShooting<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    public StopShooting(CONTEXT ctx) {
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
        ctx.getLog().warning("ACTION: StopShooting");
        
        ctx.getShoot().stopShooting();
        ctx.getNavigation().setFocus(null);
        ctx.isShooting = false;        
        
        return ActionResult.FINISHED;
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     */
    public void done() {        
    }
    
}
