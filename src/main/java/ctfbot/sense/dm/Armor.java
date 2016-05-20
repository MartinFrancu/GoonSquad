package ctfbot.sense.dm;

import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import ctfbot.CTFBotContext;

/**
 * Sense Armor for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name="Armor", description="How much armor do I have?")
public class Armor<CONTEXT extends CTFBotContext> extends ParamsSense<CONTEXT, Integer> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public Armor(CONTEXT ctx) {
        super(ctx, Integer.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Integer query() {
        return ctx.getInfo().getArmor();        
    }
}
