package ctfbot.action.ctf;

import ctfbot.CTFBotContext;
import ctfbot.Team;
import cz.cuni.amis.pogamut.base3d.worldview.object.ILocated;
import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;

/**
 * Action RunToBase for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "RunToBase", description = "Navigate to $team base (place where flags are positioned initially / where you can score).")
public class RunToBase<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    /**
     * Constructor of the action, used during automatic instantiation.
     */
    public RunToBase(CONTEXT ctx) {
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
    public ActionResult run(@Param("$team") Team team) {
        ctx.getLog().warning("ACTION: RunToBase[team=" + team + "]");
        
        ILocated target = null;
        switch(team) {
            case ENEMY:  target = ctx.getCTF().getEnemyBase(); break;
            case FRIEND: target = ctx.getCTF().getOurBase();   break;
        }
        
        if (ctx.getInfo().atLocation(target)) {
            ctx.getLog().warning("    +-- Arrived at the base");
            return ActionResult.RUNNING_ONCE;
        }
        
        ctx.getNavigation().navigate(target);
        
        return ActionResult.RUNNING;
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     */
    public void done() {
    }
    
}
