package ctfbot.action.ctf;

import ctfbot.CTFBotContext;
import cz.cuni.amis.pogamut.sposh.context.UT2004Context;
import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;

/**
 * Action FollowHeadInFormation for Yaposh.
 *
 * @author Marci
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "FollowHeadInFormation", description = "Description of FollowHeadInFormation")
public class FollowHeadInFormation<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    /**
     * Constructor of the action, used during automatic instantiation.
     */
    public FollowHeadInFormation(CONTEXT ctx) {
        super(ctx);
    }

    /**
     * Method responsible for initialization of the action. The method can be
     * passed parameters from the plan. Add all desired plan parameters as
     * method parameters, e.g. <tt>public void init({@literal @}Param("$speed")
     * Integer runningSpeed)</tt>.
     */
    public void init() {
        // Add your initialization code
    }

    /**
     * Method called during each tick of the logic the action is supposed to
     * run.
     *
     * The method can be passed parameters from the plan, e.g. <tt>public void
     * run({@literal @}Param("$stuckTime") Double stuckTimeSecs)</tt>.
     */
    public ActionResult run() {
        // Add your progress code
        
        if(ctx.teamHead == null)
        {// there is no team head.. this should not happen, anyway: now I am team head :DDD 
            ctx.teamHead = ctx.getPlayers().getPlayer(ctx.getInfo().getId());
            // tell other players..
            ctx.setCTFMessageChangeLeader(ctx.getInfo().getId(), ctx.getInfo().getId());
            ctx.currentRole = "Attacker-Head";
            return ActionResult.FINISHED;
        }
        // if there is a head, I am going to follow him
        ctx.getNavigation().navigate(ctx.teamHead);
        return ActionResult.RUNNING;
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     *
     * The method can be passed parameters from the plan, e.g. <tt>public void
     * done({@literal @}Param("$notify") Boolean notifyTeam)</tt>.
     */
    public void done() {
        // Add your cleanup code here
    }
}
