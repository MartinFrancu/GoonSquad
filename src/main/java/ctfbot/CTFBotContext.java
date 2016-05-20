package ctfbot;

import ctfbot.action.dm.ShootPlayer;
import ctfbot.tc.CTFCommItems;
import ctfbot.tc.CTFCommObjectUpdates;
import cz.cuni.amis.pogamut.sposh.context.UT2004Context;
import cz.cuni.amis.pogamut.ut2004.agent.module.utils.TabooSet;
import cz.cuni.amis.pogamut.ut2004.agent.navigation.NavigationState;
import cz.cuni.amis.pogamut.ut2004.bot.impl.UT2004Bot;
import cz.cuni.amis.pogamut.ut2004.communication.messages.ItemType;
import cz.cuni.amis.pogamut.ut2004.communication.messages.UT2004ItemType;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.Item;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.Player;
import cz.cuni.amis.utils.flag.FlagListener;

/**
 * This serves as a template bot for creating simple CTF bot using YaPOSH.
 * 
 * Many primitives are implemented so it is easy to start design the behavior using graphical
 * YaPOSH editor only.
 * 
 * If you are in NetBeans, check Projects/ctfbot project/Other sources/bot.lap plan.
 * 
 * Contains support for Team Communication, be sure to {@link StartTeamComm} first! 
 *
 * @author Jimmy
 */
public class CTFBotContext extends UT2004Context<UT2004Bot> {
    
    /** Number of the logic iteration. **/
    public int logicIteration = 0;
    /** Time of the last logic... **/
    public long lastLogic = 0;    
    /** CTF Communication - World Object Updates **/
    public CTFCommObjectUpdates commObjectUpdates;
    /** CTF Communication - Choosing Items to Pick Up **/
    public CTFCommItems commItems;
    /** Current item our bot is currently going for */
    public Item targetItem = null;
    /** Target enemy player we are currently shooting at */
    public Player targetPlayer = null;    
    /** Used to taboo items we were stuck going for or we have picked up recently */
    public TabooSet<Item> tabooItems;
    
    public String state = "";

    public CTFBotContext(UT2004Bot bot) {
        super("CTFBotContext", bot);
        // IMPORTANT: Various modules of context must be initialized.
        initialize();
        
        // INITIALIZE TEAM COMM
        commObjectUpdates = new CTFCommObjectUpdates(this);
        commItems         = new CTFCommItems(this);
        
        // INITIALIZE CUSTOM MODULES
        tabooItems = new TabooSet<Item>(bot);
        
        this.getNavigation().addStrongNavigationListener(new FlagListener<NavigationState>() {
            @Override
            public void flagChanged(NavigationState changedValue) {
                switch (changedValue) {
                    case PATH_COMPUTATION_FAILED:
                    case STUCK:
                        if (targetItem != null)
                            tabooItems.add(targetItem, 30);
                    break;
                    case TARGET_REACHED:
                        if (targetItem != null)
                            tabooItems.add(targetItem, 5);                        
                    break;
                }
            }
        });

        // HERE IS A GOOD PLACE TO INITIALIZE YOUR WEAPON PREFERENCES
        
        this.getWeaponPrefs()
                .addGeneralPref(UT2004ItemType.LIGHTNING_GUN, true)
                .addGeneralPref(UT2004ItemType.SHOCK_RIFLE, true)
                .addGeneralPref(UT2004ItemType.MINIGUN, true)                
                .addGeneralPref(UT2004ItemType.LINK_GUN, true)
                .addGeneralPref(UT2004ItemType.FLAK_CANNON, true)                                
                .addGeneralPref(UT2004ItemType.ASSAULT_RIFLE, true); 
    }

    /**
     * Here we are marking if our bot "is shooting" or "is about to start shooting"; see {@link ShootPlayer} and alikes.
     */
    public boolean isShooting;
    
    /**
     * Here we are marking what weapon our bot currently wields in its hands or is about to be changed to.
     */
    public ItemType currentWeapon;
    
    /**
     * This method is invoked before yaPOSH engine evaluation.
     */
    void logicBeforePlan() {    	        
        if (lastLogic <= 0) {
        	log.warning("---[ LOGIC ITERATION " + (++logicIteration) + " ]---");
        	lastLogic = System.currentTimeMillis();
        } else {
        	long now = System.currentTimeMillis();
        	log.warning("---[ LOGIC ITERATION " + (++logicIteration) + " | " + (now - lastLogic) + " ms delta]---");
        	lastLogic = now;
        }
       
        isShooting = info.isShooting();
        currentWeapon = weaponry.getCurrentWeapon().getType();
       
        if (info.getSelf() != null) {
        	commObjectUpdates.update();
        	commItems.update();
    	}
    }

    /**
     * This method is invoked after yaPOSH engine evaluation.
     */
    void logicAfterPlan() {       
    }
}
