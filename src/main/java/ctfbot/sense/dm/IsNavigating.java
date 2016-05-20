package ctfbot.sense.dm;

import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import ctfbot.CTFBotContext;

/**
 * Sense IsNavigating for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name="IsNavigating", description="Is our bot currently navigating somewhere?")
public class IsNavigating<CONTEXT extends CTFBotContext> extends ParamsSense<CONTEXT, Boolean> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public IsNavigating(CONTEXT ctx) {
        super(ctx, Boolean.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Boolean query() {
        return ctx.getNavigation().isNavigating();
    }
}
