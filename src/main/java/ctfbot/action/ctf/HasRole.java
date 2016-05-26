package ctfbot.action.ctf;

import ctfbot.CTFBotContext;
import cz.cuni.amis.pogamut.sposh.context.UT2004Context;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;

/**
 * Sense HasRole for Yaposh.
 *
 * @author student
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "HasRole", description = "Description of HasRole")
public class HasRole<CONTEXT extends UT2004Context> extends ParamsSense<CONTEXT, Boolean> {

    /**
     * Constructor of the sense, used during automatic instantiation. The class
     * passed to the ancestor is used to determine which query method should be
     * used by the sense.
     */
    public HasRole(CONTEXT ctx) {
        super(ctx, Boolean.class);
    }

    /**
     * Query the current value of the sense. The sense can be passed parameters
     * from the plan. Add all desired plan parameters as method parameters, e.g.
     * <tt>public Boolean query({@literal @}Param("$name") String botName,
     * {@literal @}Param("$threshold") Integer threshold)</tt>.
     */
     public Boolean query(@Param("$role") String role) {
        
        // Add code of your sense
        CTFBotContext botContext = (CTFBotContext) ctx;
        return botContext.currentRole.equals(role);
    }
}
