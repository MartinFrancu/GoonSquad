package ctfbot.sense.dm;

import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import ctfbot.CTFBotContext;

/**
 * Sense InState for Yaposh.
 * 
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "InState", description = "Returns current bot state 'string' previously stored by SetState action.")
public class InState<CONTEXT extends CTFBotContext> extends ParamsSense<CONTEXT, String> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public InState(CONTEXT ctx) {
        super(ctx, String.class);
    }

    /**
     * Query the current value of the sense.
     */
    public String query() {
        return ctx.state;
    }
}
