package ctfbot.action.dm;

import ctfbot.Team;
import cz.cuni.amis.pogamut.sposh.context.UT2004Context;
import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.Player;

/**
 * Action RunToPlayer for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "RunToPlayer", description = "Starts navigating to the $team nearest visible player.")
public class RunToPlayer<CONTEXT extends UT2004Context> extends ParamsAction<CONTEXT> {

    /**
     * We decide on who to follow inside {@link #init(ctfbot.Team)}.
     */
    Player toFollow;
    
    /**
     * Constructor of the action, used during automatic instantiation.
     */
    public RunToPlayer(CONTEXT ctx) {
        super(ctx);
    }

    /**
     * Method responsible for initialization of the action. The method can be
     * passed parameters from the plan.
     */
    public void init(@Param("$team") Team team) {        
        switch (team) {
            case ENEMY:
                toFollow = ctx.getPlayers().getNearestVisibleEnemy();
                break;
            case FRIEND:
                toFollow = ctx.getPlayers().getNearestVisibleFriend();
                break;
        }
    }

    /**
     * Method called during each tick of the logic the action is supposed to
     * run.
     */
    public ActionResult run() {
        ctx.getLog().warning("ACTION: RunToPlayer");

        if (toFollow != null) {
            ctx.getNavigation().navigate(toFollow);
            if (toFollow.isVisible()) {
                ctx.getLog().warning("  +-- Running to " + toFollow.getId().getStringId() + ", path-distance = " + ctx.getNavigation().getRemainingDistance());
            } else {                            
                if (ctx.getInfo().atLocation(toFollow.getLocation())) {
                    ctx.getLog().warning("  +-- Arrived at last know location of " + toFollow.getId().getStringId());
                    return ActionResult.RUNNING_ONCE;
                } else {
                    ctx.getLog().warning("  +-- Running to last know location of " + toFollow.getId().getStringId() + ", path-distance = " + ctx.getNavigation().getRemainingDistance());
                }
            }
            return ActionResult.RUNNING;
        } 
        
        ctx.getLog().warning("  +-- Do not see any player to follow.");
        
        return ActionResult.RUNNING_ONCE;
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     */
    public void done() {
    }
    
}
