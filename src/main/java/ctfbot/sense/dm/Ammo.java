package ctfbot.sense.dm;

import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import ctfbot.CTFBotContext;
import ctfbot.Weapon;

/**
 * How much ammunition does bot have in its current weapon.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name="Ammo", description="How many ammo do I have for the weapon?")
public class Ammo<CONTEXT extends CTFBotContext> extends ParamsSense<CONTEXT, Integer> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public Ammo(CONTEXT ctx) {
        super(ctx, Integer.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Integer query(@Param("$weapon") Weapon weapon) {
        return ctx.getWeaponry().getAmmo(weapon.weaponType);
    }
}
