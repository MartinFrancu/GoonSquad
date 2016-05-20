package ctfbot.action.dm;

import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.NavPoint;
import cz.cuni.amis.utils.collections.MyCollections;
import ctfbot.CTFBotContext;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;

/**
 * Action for walking randomly around the map.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "RunRandomly", description = "Run to random navpoint in the map.")
public class RunRandomly<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    NavPoint navPoint;
    
    public RunRandomly(CONTEXT ctx) {
        super(ctx);
    }

    /**
     * Method responsible for initialization of the action. The method can be
     * passed parameters from the plan.
     */
    public void init() {        
        navPoint = MyCollections.getRandom(ctx.getWorld().getAll(NavPoint.class).values());
        ctx.getNavigation().navigate(navPoint);
    }

    /**
     * Method called during each tick of the logic the action is supposed to
     * run.
     */
    public ActionResult run() {
        ctx.getLog().warning("ACTION: RunRandomly");
        
        if (ctx.getNavigation().isNavigating()) {
            ctx.getLog().warning("  +-- Running for " + navPoint.getId().getStringId() + ", path-distance = " + ctx.getNavigation().getRemainingDistance());
            return ActionResult.RUNNING;
        }
        
        return ActionResult.FINISHED;
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     */
    public void done() {
    }
    
}
