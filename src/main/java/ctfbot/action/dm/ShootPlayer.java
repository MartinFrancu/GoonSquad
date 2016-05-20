package ctfbot.action.dm;

import ctfbot.CTFBotContext;
import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.Player;

/**
 * Action ShootPlayer for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name="ShootPlayer", description="Starts shooting a nearest enemy player, if is visible.")
public class ShootPlayer<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    public ShootPlayer(CONTEXT ctx) {
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
        if (ctx.getPlayers().canSeeEnemies()) {
            Player player = ctx.getPlayers().getNearestVisibleEnemy();
            ctx.getNavigation().setFocus(ctx.getPlayers().getNearestVisibleEnemy());
            ctx.getLog().warning("ACTION: ShootPlayer");
            ctx.getLog().warning("  +-- Shooting at " + player.getName() + ".");
            ctx.getShoot().shoot(player);
            ctx.isShooting = true;
        } else {
            ctx.getLog().warning("ACTION: ShootPlayer");
            ctx.getLog().warning("  +-- Cannot shoot as there is no visible enemy.");
            ctx.getShoot().stopShooting();
            ctx.isShooting = false;
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
