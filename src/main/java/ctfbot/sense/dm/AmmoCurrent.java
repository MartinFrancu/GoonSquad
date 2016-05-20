package ctfbot.sense.dm;

import ctfbot.CTFBotContext;
import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;

/**
 * Sense AmmoCurrent for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "AmmoCurrent", description = "How many ammo do I have for the weapon I am holding now?")
public class AmmoCurrent<CONTEXT extends CTFBotContext> extends ParamsSense<CONTEXT, Integer> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public AmmoCurrent(CONTEXT ctx) {
        super(ctx, Integer.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Integer query() {
        return ctx.getWeaponry().getCurrentAmmo();
    }
}
