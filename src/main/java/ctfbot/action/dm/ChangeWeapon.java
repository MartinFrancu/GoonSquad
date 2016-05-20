package ctfbot.action.dm;

import cz.cuni.amis.pogamut.sposh.executor.*;
import ctfbot.CTFBotContext;
import ctfbot.Weapon;

/**
 * Sense ChangeWeapon for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "ChangeWeapon", description = "Changes the $weapon in bot's hands.")
public class ChangeWeapon<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    /**
     * Constructor of the sense, used during automatic instantiation. The class
     * passed to the ancestor is used to determine which query method should be
     * used by the sense.
     */
    public ChangeWeapon(CONTEXT ctx) {
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
    public ActionResult run(@Param("$weapon") Weapon weapon) {
        ctx.getLog().warning("ACTION: ChangeWeapon[weapon=" + weapon + "]");
        
        ctx.currentWeapon = weapon.weaponType;
        
        ctx.getWeaponry().changeWeapon(weapon.weaponType);
        
        return ActionResult.FINISHED;        
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     */
    public void done() {
    }
    
}
