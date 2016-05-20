package ctfbot.sense.dm;

import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import ctfbot.CTFBotContext;
import ctfbot.Weapon;

/**
 * Sense HasWeapon for Yaposh.
 * 
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "HasWeapon", description = "Whether the bot has some concrete weapon.")
public class HasWeapon<CONTEXT extends CTFBotContext> extends ParamsSense<CONTEXT, Boolean> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public HasWeapon(CONTEXT ctx) {
        super(ctx, Boolean.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Boolean query(@Param("$weapon") Weapon weapon) {
        return ctx.getWeaponry().hasWeapon(weapon.weaponType);
    }
    
}
