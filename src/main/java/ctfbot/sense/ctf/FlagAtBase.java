package ctfbot.sense.ctf;

import ctfbot.CTFBotContext;
import ctfbot.Team;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;

/**
 * Sense FlagAtBase for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "FlagAtBase", description = "Is flag at its base?")
public class FlagAtBase<CONTEXT extends CTFBotContext> extends ParamsSense<CONTEXT, Boolean> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public FlagAtBase(CONTEXT ctx) {
        super(ctx, Boolean.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Boolean query(@Param("$team") Team team) {
        if (team == Team.FRIEND) {
            return ctx.getCTF().isOurFlagHome();
        }
        if (team == Team.ENEMY) {
            return ctx.getCTF().isEnemyFlagHome();
        }
        return false;
    }
}
