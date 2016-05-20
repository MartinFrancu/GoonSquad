package ctfbot.sense.ctf;

import ctfbot.Team;
import cz.cuni.amis.pogamut.sposh.context.UT2004Context;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;

/**
 * Sense KnowFlagLocation for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "KnowFlagLocation", description = "Do I know precise location of the flag?")
public class KnowFlagLocation<CONTEXT extends UT2004Context> extends ParamsSense<CONTEXT, Boolean> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public KnowFlagLocation(CONTEXT ctx) {
        super(ctx, Boolean.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Boolean query(@Param("$team") Team team) {
        switch (team) {
            case ENEMY:
                return ctx.getCTF().isEnemyFlagHome() || ctx.getCTF().getEnemyFlag().isVisible();
                
            case FRIEND:
                return ctx.getCTF().isOurFlagHome() || ctx.getCTF().getOurFlag().isVisible() || ctx.getCTF().isBotCarryingEnemyFlag();
            
            default:
                return false;
        }
    }
}
