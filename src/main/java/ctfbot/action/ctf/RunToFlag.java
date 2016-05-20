package ctfbot.action.ctf;

import ctfbot.CTFBotContext;
import ctfbot.Team;
import cz.cuni.amis.pogamut.base3d.worldview.object.ILocated;
import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.NavPoint;
import cz.cuni.amis.utils.collections.MyCollections;

/**
 * Action RunToFlag for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "RunToFlag", description = "Navigate to the location where the bot thinks the $team flag is.")
public class RunToFlag<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    private ILocated randomTarget;
    
    /**
     * Constructor of the action, used during automatic instantiation.
     */
    public RunToFlag(CONTEXT ctx) {
        super(ctx);
    }

    public void init() {
        randomTarget = MyCollections.getRandom(ctx.getWorld().getAll(NavPoint.class).values());    
    }

    /**
     * Method called during each tick of the logic the action is supposed to
     * run.
     */
    public ActionResult run(@Param("$team") Team team) {
        ctx.getLog().warning("ACTION: RunToFlag[team=" + team + "]");
        
        ILocated target = null;
        
        switch (team) {
            case ENEMY:
                // SEE FLAG HOLDER?
                if (ctx.getCTF().getEnemyFlag().isVisible() && ctx.getPlayers().getPlayer(ctx.getCTF().getEnemyFlag().getHolder()) != null
                       && ctx.getPlayers().getPlayer(ctx.getCTF().getEnemyFlag().getHolder()).isVisible())
                    target = ctx.getPlayers().getPlayer(ctx.getCTF().getEnemyFlag().getHolder());
                // FLAG AT HOME?
                else if (ctx.getCTF().isEnemyFlagHome()) target = ctx.getCTF().getEnemyBase();
                // FLAG VISIBLE?
                else if (ctx.getCTF().getEnemyFlag().isVisible()) target = ctx.getCTF().getEnemyFlag().getLocation();
                else {
                    // SEARCH FOR FLAG
                    ctx.getLog().warning("  +-- I do not know where ENEMY flag is, running to random navpoint...");
                    target = randomTarget;
                }
                break;
            case FRIEND:
                // AM I CARRYING THE FLAG?
                if (ctx.getCTF().isBotCarryingEnemyFlag()) target = ctx.getInfo().getLocation();
                // SEE FLAG HOLDER?
                else if (ctx.getCTF().getOurFlag().isVisible() && ctx.getPlayers().getPlayer(ctx.getCTF().getOurFlag().getHolder()) != null
                       && ctx.getPlayers().getPlayer(ctx.getCTF().getOurFlag().getHolder()).isVisible())
                    target = ctx.getPlayers().getPlayer(ctx.getCTF().getOurFlag().getHolder());
                // FLAG AT HOME?
                else if (ctx.getCTF().isOurFlagHome()) target = ctx.getCTF().getOurBase();
                // FLAG VISIBLE?
                else if (ctx.getCTF().getOurFlag().isVisible()) target = ctx.getCTF().getOurFlag().getLocation();
                else {
                    ctx.getLog().warning("  +-- I do not know where OUR flag is, running to random navpoint...");
                    target = randomTarget;
                }
                break;            
        }
        
        if (ctx.getInfo().atLocation(target)) {
            ctx.getLog().warning("    +-- Arrived at the location");
            return ActionResult.RUNNING_ONCE;
        }
        
        if (target != randomTarget) {
        	ctx.getNavigation().navigate(target);
        } else {
        	// RUNNING TO RANDOM TARGET
        	if (!ctx.getNavigation().isNavigating() || ctx.getNavigation().getCurrentTargetNavPoint() == null) {
        		// SWITCH TO DIFFERENT NAVPOINT ONLY IF WE HAVE TO
        		randomTarget = MyCollections.getRandom(ctx.getWorld().getAll(NavPoint.class).values());   
        		ctx.getNavigation().navigate(randomTarget);
        	}
        }
        
        return ActionResult.RUNNING;
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     */
    public void done() {
    }
    
}
