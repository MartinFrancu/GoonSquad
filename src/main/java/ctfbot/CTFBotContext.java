package ctfbot;

import ctfbot.action.dm.ShootPlayer;
import static ctfbot.messages.InfoType.ENEMY;
import static ctfbot.messages.InfoType.ENEMY_FLAG;
import static ctfbot.messages.InfoType.FRIEND;
import static ctfbot.messages.InfoType.OUR_FLAG;
import ctfbot.messages.CTFMessage;
import ctfbot.messages.InfoType;
import ctfbot.tc.CTFCommItems;
import ctfbot.tc.CTFCommObjectUpdates;
import cz.cuni.amis.pogamut.base.communication.worldview.listener.annotation.EventListener;
import cz.cuni.amis.pogamut.sposh.context.UT2004Context;
import cz.cuni.amis.pogamut.unreal.communication.messages.UnrealId;
import cz.cuni.amis.pogamut.ut2004.agent.module.utils.TabooSet;
import cz.cuni.amis.pogamut.ut2004.agent.navigation.NavigationState;
import cz.cuni.amis.pogamut.ut2004.agent.navigation.UT2004PathAutoFixer;
import cz.cuni.amis.pogamut.ut2004.agent.navigation.stuckdetector.UT2004DistanceStuckDetector;
import cz.cuni.amis.pogamut.ut2004.agent.navigation.stuckdetector.UT2004TimeStuckDetector;
import cz.cuni.amis.pogamut.ut2004.bot.impl.UT2004Bot;
import cz.cuni.amis.pogamut.ut2004.communication.messages.ItemType;
import cz.cuni.amis.pogamut.ut2004.communication.messages.UT2004ItemType;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.Item;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.NavPoint;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.Player;
import cz.cuni.amis.utils.Cooldown;
import cz.cuni.amis.utils.flag.FlagListener;
import java.util.logging.Level;

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
    
    /** Current bot role. */
    public String currentRole = "Defender";
    /** Used to taboo items we were stuck going for or we have picked up recently */
    public TabooSet<Item> tabooItems;
    
    public TabooSet<NavPoint> tabooNavPoints;
    
    
    
    ///////////////////////////////////////////////////////////////////////////
    /// TEAM ATRIBUTES:
   // public String state = "Defender";
    public Player teamHead = null;
    
    
    ///////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // SHOOTING ATRIBUTES:
    /** Target enemy player we are currently shooting at */
    public Player targetPlayer = null; 
    /** This is target of team effort. */
    public Player teamTargetPlayer = null;
    /**cooldowns for slow shooting weapons*/
    public Cooldown rocketLauncherCD = null; 
    public Cooldown lightingGunCD = null; 
    public Cooldown sniperRifleCD = null; 
     /**
     * Here we are marking if our bot "is shooting" or "is about to start shooting"; see {@link ShootPlayer} and alikes.
     */
    public boolean isShooting;
    
    /**
     * Here we are marking what weapon our bot currently wields in its hands or is about to be changed to.
     */
    public ItemType currentWeapon;
    
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * AutoFixer monitors movement of agent and when it detects faulty
     * connection in the waypoints, it removes them from navigation logic.
     */
    
    UT2004PathAutoFixer autoFixer;

    public CTFBotContext(UT2004Bot bot) {
        super("CTFBotContext", bot);
        // IMPORTANT: Various modules of context must be initialized.
        initialize();
        autoFixer = new UT2004PathAutoFixer(bot, this.getNavigation().getPathExecutor(), fwMap, aStar, navBuilder);
        // INITIALIZE TEAM COMM
        commObjectUpdates = new CTFCommObjectUpdates(this);
        commItems         = new CTFCommItems(this);
        
        // INITIALIZE CUSTOM MODULES
        tabooItems = new TabooSet<Item>(bot);
        tabooNavPoints = new TabooSet<NavPoint>(bot);
        
        //Add stuck detectors
        this.getNavigation().getPathExecutor().addStuckDetector(new UT2004TimeStuckDetector(bot, 3.0, 10.0));
        this.getNavigation().getPathExecutor().addStuckDetector(new UT2004DistanceStuckDetector(bot));
        
        this.getNavigation().addStrongNavigationListener(new FlagListener<NavigationState>() {
            @Override
            public void flagChanged(NavigationState changedValue) {
                switch (changedValue) {
                    case PATH_COMPUTATION_FAILED:
                    case STUCK:
                        if (targetItem != null){
                            tabooItems.add(targetItem, 30);
                            //add taboonavpoints
                        }
                            
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
    
     //Example usage of Location message
    public void setCTFMessageChangeLeader(UnrealId oldLeader, UnrealId newLeader) 
    {
        
        if(!getTCClient().isConnected())
        {
            /// we should log this
            log.log(Level.SEVERE, ">>>>>>>>>>>>>>>>>>>>>>>>>could not find TCclient!!!");
		return;
	
        }
        getTCClient().sendToTeamOthers
                (
                    new CTFMessage
                    (
                        bot.getLocation(), 
                        oldLeader, 
                        InfoType.LEADER_DIED, 
                        newLeader // Id of new leader...
                    )
                );
    }
    
    
    
         /*
     TCMessage handling for different type of messages
     */
    
    //Location Message Handle
    //locationMessage.getUnrealId()
    //locationMessage.getLocation()
    //locationMessage.getInfoType()
    @EventListener(eventClass = CTFMessage.class)
    public void onCTFMessageReceive(CTFMessage message){
           
        switch(message.getInfoType()){
                case FRIEND:
                        //handle friend location receive
                    break;
                case ENEMY:
                        //handle enemy location receive
                    break;

                case OUR_FLAG:
                       //handle our flag location receive 
                    break;
                case ENEMY_FLAG:
                        //handle enemy flag location receive
                    break;
                case LETS_KILL_THIS_ONE:
                    this.teamTargetPlayer = this.getPlayers().getPlayer(message.getTargetId());
                    break;
                case LEADER_DIED:
                    this.teamHead = this.getPlayers().getPlayer(message.getTargetId()); //target Id is the new leader
                    if(teamHead.getId() == this.getInfo().getId())
                    {
                        this.currentRole = "Attacker-Head";
                    }
                default:
                    log.log(Level.SEVERE, ">>>>>>>>>>>>>>>>>>>>>> Unknown message type.");
        }
	   
    }
}
