package ctfbot.action.ctf;

import ctfbot.CTFBotContext;
import ctfbot.Team;
import ctfbot.Weapon;
import cz.cuni.amis.pogamut.base3d.worldview.object.ILocated;
import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;

/**
 * Action ShootFlagHolder for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "ShootFlagHolderWithWeapon", description = "Shoot at the bot carrying a $team flag with a concrete weapon.")
public class ShootFlagHolderWithWeapon<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    /**
     * Constructor of the action, used during automatic instantiation.
     */
    public ShootFlagHolderWithWeapon(CONTEXT ctx) {
        super(ctx);
    }

    /**
     * Method responsible for initialization of the action. The method can be
     * passed parameters from the plan.
     */
    public void init(@Param("$weapon") Weapon weapon) {
        ctx.getWeaponry().changeWeapon(weapon.weaponType);
    }

    /**
     * Method called during each tick of the logic the action is supposed to
     * run.
     */
    public ActionResult run(@Param("$team") Team team) {
        ctx.getLog().warning("ACTION: ShootFlagHolderWithWeapon[team=" + team + "]");
        
        ILocated target = null;
        switch (team) {
            case ENEMY:
                // SEE FLAG HOLDER?
                if (ctx.getCTF().getEnemyFlag().isVisible() && ctx.getPlayers().getPlayer(ctx.getCTF().getEnemyFlag().getHolder()) != null
                       && ctx.getPlayers().getPlayer(ctx.getCTF().getEnemyFlag().getHolder()).isVisible())
                    target = ctx.getPlayers().getPlayer(ctx.getCTF().getEnemyFlag().getHolder());
                break;
            case FRIEND:
                // SEE FLAG HOLDER?
                if (ctx.getCTF().getOurFlag().isVisible() && ctx.getPlayers().getPlayer(ctx.getCTF().getOurFlag().getHolder()) != null
                       && ctx.getPlayers().getPlayer(ctx.getCTF().getOurFlag().getHolder()).isVisible())
                    target = ctx.getPlayers().getPlayer(ctx.getCTF().getOurFlag().getHolder());  
                break;
        }
        
        if (target != null) {
            ctx.getShoot().shoot(target); 
            ctx.getNavigation().setFocus(target);
            ctx.isShooting = true;
            return ActionResult.FINISHED;
        }
        
        ctx.getLog().warning("  +-- Cannot see " + team + " flag holder, not shooting!");
        
        return ActionResult.FINISHED;        
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     */
    public void done() {
    }
    
}
