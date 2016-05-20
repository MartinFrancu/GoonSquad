package ctfbot.sense.ctf;

import ctfbot.CTFBotContext;
import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;

/**
 * Sense HoldingFlag for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "HoldingFlag", description = "Do I carry enemy flag?")
public class HoldingFlag<CONTEXT extends CTFBotContext> extends ParamsSense<CONTEXT, Boolean> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public HoldingFlag(CONTEXT ctx) {
        super(ctx, Boolean.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Boolean query() {
        return ctx.getCTF().isBotCarryingEnemyFlag();
    }
}
