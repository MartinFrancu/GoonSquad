package ctfbot.action.dm;

import cz.cuni.amis.pogamut.sposh.executor.ActionResult;
import cz.cuni.amis.pogamut.sposh.executor.Param;
import cz.cuni.amis.pogamut.sposh.executor.ParamsAction;
import cz.cuni.amis.pogamut.sposh.executor.PrimitiveInfo;
import ctfbot.Combo;
import ctfbot.CTFBotContext;

/**
 * Do adrenaline combo of input type.
 *
 * @author Jimmy
 * 
 * @param <CONTEXT> Context class of the action. It's an shared object used by
 * all primitives. it is used as a shared memory and for interaction with the
 * environment.
 */
@PrimitiveInfo(name="AdrenalineCombo", description="Do an adrenaline combo of given $type. You can do the combo only with adrenaline >= 100!")
public class AdrenalineCombo<CONTEXT extends CTFBotContext> extends ParamsAction<CONTEXT> {

    public AdrenalineCombo(CONTEXT ctx) {
        super(ctx);
    }

    /**
     * Method responsible for initialization of the action. The method can be
     * passed parameters from the plan.
     */
    public void init() {        
    }

    /**
     * Method called during each tick of the logic the action is supposed to
     * run.
     */
    public ActionResult run(@Param("$type") Combo type) {
        ctx.getLog().warning("ACTION: AdrenalineCombo[type=" + type + "]");
        
        ctx.getBody().getAction().startCombo(type.command);
        return ActionResult.RUNNING_ONCE;        
    }

    /**
     * Method called once engine decides that another action should be run,
     * place you cleanup code here.
     */
    public void done() {
        
    }
}
