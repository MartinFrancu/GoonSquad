package ctfbot.sense.dm;

import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import ctfbot.CTFBotContext;
import ctfbot.Team;
import cz.cuni.amis.pogamut.sposh.executor.Param;

/**
 * Sense SeePlayer for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "SeePlayer", description = "Do I see a player?")
public class SeePlayer<CONTEXT extends CTFBotContext> extends ParamsSense<CONTEXT, Boolean> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public SeePlayer(CONTEXT ctx) {
        super(ctx, Boolean.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Boolean query(@Param("$team") Team team) {
        switch (team) {
            case ENEMY: return ctx.getPlayers().canSeeEnemies();
            case FRIEND: return ctx.getPlayers().canSeeFriends();
            default:
                return false;
        }
    }    
}
