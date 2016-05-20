package ctfbot.sense.dm;

import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import ctfbot.CTFBotContext;
import ctfbot.Weapon;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.Item;

/**
 * Sense IsPickableWeapon for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "IsPickableWeapon", description = "Is some 'weapon' spawned & reacheable & pickable?")
public class IsPickableWeapon<CONTEXT extends CTFBotContext> extends ParamsSense<CONTEXT, Boolean> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public IsPickableWeapon(CONTEXT ctx) {
        super(ctx, Boolean.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Boolean query(@Param("$weapon") Weapon weapon) {
        Item item = 
            ctx.getFwMap().getNearestItem(
                ctx.tabooItems.filter(ctx.getItems().getSpawnedItems(weapon.weaponType).values()),
                ctx.getInfo().getNearestNavPoint()
            );        
        if (item == null) return false;
        return ctx.getItems().isPickable(item);
    }
}
