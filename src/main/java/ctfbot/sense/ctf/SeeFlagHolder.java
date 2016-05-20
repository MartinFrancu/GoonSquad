package ctfbot.sense.ctf;

import ctfbot.Team;
import cz.cuni.amis.pogamut.sposh.context.UT2004Context;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsSense;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import cz.cuni.amis.pogamut.unreal.communication.messages.UnrealId;

/**
 * Sense SeeFlagHolder for Yaposh.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the sense. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name = "SeeFlagHolder", description = "Do I see a bot that is carrying a flag? (Reports FALSE if the holder is me.)")
public class SeeFlagHolder<CONTEXT extends UT2004Context> extends ParamsSense<CONTEXT, Boolean> {

    /**
     * Constructor of the sense, used during automatic instantiation.
     */
    public SeeFlagHolder(CONTEXT ctx) {
        super(ctx, Boolean.class);
    }

    /**
     * Query the current value of the sense.
     */
    public Boolean query(@Param("$team") Team team) {
        switch (team) {
            case ENEMY:
                return ctx.getCTF().getEnemyFlag().isVisible() && ctx.getPlayers().getPlayer(ctx.getCTF().getEnemyFlag().getHolder()) != null
                       && ctx.getPlayers().getPlayer(ctx.getCTF().getEnemyFlag().getHolder()).isVisible();
            case FRIEND:
                if (ctx.getCTF().isBotCarryingEnemyFlag()) return false;
                return ctx.getCTF().getOurFlag().isVisible() && ctx.getPlayers().getPlayer(ctx.getCTF().getOurFlag().getHolder()) != null
                       && ctx.getPlayers().getPlayer(ctx.getCTF().getOurFlag().getHolder()).isVisible();
                
            default:
                return false;
        }
        
    }
}
