package ctfbot.sense.ctf;

import ctfbot.Team;
import cz.cuni.amis.pogamut.sposh.context.UT2004Context;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;

/**
 * Sense SeeFlag for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "SeeFlag", description = "Do I see a flag? (Reports FALSE, if the querried flag is carrired by this bot.)")
public class SeeFlag<CONTEXT extends UT2004Context> extends ParamsSense<CONTEXT, Boolean> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public SeeFlag(CONTEXT ctx) {
        super(ctx, Boolean.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Boolean query(@Param("$team") Team team) {
        if (team == Team.ENEMY) {
            return ctx.getCTF().getEnemyFlag().isVisible();
        }
        if (team == Team.FRIEND) {
            if (ctx.getCTF().isBotCarryingEnemyFlag()) return false;
            return ctx.getCTF().getOurFlag().isVisible();
        }
        return false;
    }
}
