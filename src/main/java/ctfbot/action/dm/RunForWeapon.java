package ctfbot.action.dm;

import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.Item;
import ctfbot.CTFBotContext;
import ctfbot.Weapon;

/**
 * Action RunForWeapon for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "RunForWeapon", description = "Navigate to the nearest $weapon; navigating only if <weapon> is believed to be spawned & reachable & pickable.")
public class RunForWeapon<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    /**
     * Constructor of the action, used during automatic instantiation.
     */
    public RunForWeapon(CONTEXT ctx) {
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
        ctx.getLog().warning("ACTION: RunForWeapon[weapon=" + weapon + "]");
        
        Item item = ctx.getFwMap().getNearestItem(
                        ctx.tabooItems.filter(ctx.getItems().getSpawnedItems(weapon.weaponType).values()),
                        ctx.getInfo().getNearestNavPoint()
                    );
        
        if (item == null) {
            ctx.getLog().warning("  +-- " + weapon  + " not spawned or reachable.");
            ctx.getNavigation().stopNavigation();
            return ActionResult.RUNNING_ONCE;
        }

        if (!ctx.getItems().isPickable(item)) {
            ctx.getLog().warning("  +-- " + weapon  + " -> " + item.getId().getStringId() + " is not pickable.");
            ctx.getNavigation().stopNavigation();
            return ActionResult.RUNNING_ONCE;
        }
        
        ctx.targetItem = item;
        ctx.getNavigation().navigate(item);
        
        ctx.getLog().warning("  +-- Running for " + item.getId().getStringId() + ", path-distance = " + ctx.getNavigation().getRemainingDistance());
        
        return ActionResult.RUNNING;
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     */
    public void done() {
        ctx.targetItem = null;
    }
    
}
