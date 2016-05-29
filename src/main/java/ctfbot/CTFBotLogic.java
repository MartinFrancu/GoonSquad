package ctfbot;

import ctfbot.messages.InfoType;
import ctfbot.messages.CTFMessage;
import cz.cuni.amis.pogamut.base3d.worldview.object.ILocated;
import cz.cuni.amis.pogamut.sposh.ut2004.StateSposhLogicController;
import cz.cuni.amis.pogamut.unreal.communication.messages.UnrealId;
import cz.cuni.amis.pogamut.ut2004.agent.module.sensor.AgentInfo;
import cz.cuni.amis.pogamut.ut2004.bot.IUT2004BotController;
import cz.cuni.amis.pogamut.ut2004.bot.impl.UT2004Bot;
import cz.cuni.amis.pogamut.ut2004.communication.messages.UT2004ItemType;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbcommands.Initialize;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.BotKilled;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.ConfigChange;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.GameInfo;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.InitedMessage;
import cz.cuni.amis.pogamut.ut2004.communication.messages.gbinfomessages.Player;
import cz.cuni.amis.pogamut.ut2004.utils.UT2004BotRunner;
import cz.cuni.amis.utils.Cooldown;
import cz.cuni.amis.utils.exception.PogamutException;
import java.io.IOException;
import java.util.logging.Level;

/**
 * yaPOSH logic for CTF bot.
 * 
 * @see IUT2004BotController Controller for various methods called during construction of bot.
 * 
 * @author Jimmy
 */
public class CTFBotLogic extends StateSposhLogicController<UT2004Bot, CTFBotContext> {

    //private String SPOSH_PLAN_RESOURCE = "sposh/plan/bot.lap";
    private String SPOSH_PLAN_OURS = "sposh/plan/SchematicPlan.lap";
//	private String SPOSH_PLAN_RESOURCE = "sposh/plan/ctfbot-example.lap";

    
    private boolean TryShootSlowWeapon( ILocated target)
    {
        // try shoot rocket laucher:
        {
        cz.cuni.amis.pogamut.ut2004.agent.module.sensomotoric.Weapon weapon 
                = context.getWeaponry().getWeapon(UT2004ItemType.ROCKET_LAUNCHER);
        
        if(weapon != null)
            {
                if(context.rocketLauncherCD == null)
                {
                    context.rocketLauncherCD = new Cooldown(500);
                }
                if(context.rocketLauncherCD.tryUse())
                {
                    context.getShoot().shoot(weapon, true, target);
                }
                return true;
                
            }
        }
        {
       cz.cuni.amis.pogamut.ut2004.agent.module.sensomotoric.Weapon weapon 
               = context.getWeaponry().getWeapon(UT2004ItemType.LIGHTNING_GUN);
            if(weapon != null)
            {
                if(context.lightingGunCD == null)
                {
                    context.lightingGunCD = new Cooldown(500);
                }
                if(context.lightingGunCD.tryUse())
                {
                    context.getShoot().shoot(weapon, true, target);
                }
                return true;
            }
        }
        {
       cz.cuni.amis.pogamut.ut2004.agent.module.sensomotoric.Weapon weapon 
               = context.getWeaponry().getWeapon(UT2004ItemType.SNIPER_RIFLE);
            if(weapon != null)
            {
                if(context.sniperRifleCD == null)
                {
                    context.sniperRifleCD = new Cooldown(500);
                }
                if(context.sniperRifleCD.tryUse())
                {
                    context.getShoot().shoot(weapon, true, target);
                }
                return true;
            }
        }
        return false;
    }
    
    private void ShootTheMotherF(ILocated target)
    {
        // if we got 
        
        if(TryShootSlowWeapon(target)) return;

        // otherwise shoot regularly 
        context.getShoot().shoot(context.getWeaponPrefs(),target);
        
    }
    
    private void dealWithShooting()
    {        
        // first check If I can shoot enemy flagholder
        UnrealId flagHolder =  context.getCTF().getEnemyFlag().getHolder();
        if((flagHolder != null) && 
           (context.getVisibility().isVisible(context.getPlayers().getPlayer(flagHolder))) )
        {// we can see the flag holder SHOOT HIM!!!, In a spare time we should report him as a team target.
            // make him my personal enemy:
            context.targetPlayer = context.getPlayers().getPlayer(flagHolder);
            ShootTheMotherF(context.targetPlayer);
            return;
        }
        // check if I can shoot team target
        if( (context.teamTargetPlayer != null) && 
            (context.getVisibility().isVisible(context.teamTargetPlayer)) )
        {// shoot the team target
            // make him my target:
            context.targetPlayer = context.teamTargetPlayer;
            ShootTheMotherF(context.targetPlayer);
            return;
        }
        
        if( (context.targetPlayer != null) && ((context.targetPlayer).isVisible()))
        {// i can see player I ve decided to shoot at:
            ShootTheMotherF(context.targetPlayer);
            return;
        }
        
        // can I see any enemy? 
        Player AnEnemy = context.getPlayers().getNearestVisibleEnemy();
        if( (AnEnemy != null))
        { 
            context.targetPlayer = AnEnemy;
            ShootTheMotherF(context.targetPlayer);
            // a should I shoot him?
            // or should I preserve ammo?
            return;
            
        }
         // If I have nothing to shoot, I should stop shooting.
        context.targetPlayer = null;
        context.getShoot().stopShooting();
    }
   
    
    @Override
    protected String getPlan() throws IOException {
        return getPlanFromResource(SPOSH_PLAN_OURS);
       // return getPlanFromResource(SPOSH_PLAN_RESOURCE);                
    }

    /**
     * Create context that can be accessed in every state primitive.
     *
     * @return new context of this bot.
     */
    @Override
    protected CTFBotContext createContext() {
        return new CTFBotContext(bot);
    }

    static int botCount = 0;
    
    /**
     * Initialize command of the bot, called during initial handshake, init can
     * set things like name of bot, its skin, skill, team ect.
     *
     * @see Initialize
     * @return
     */
    
    
    @Override
    public Initialize getInitializeCommand() {
        int team = (botCount++ % 2 == 0) ? AgentInfo.TEAM_BLUE : AgentInfo.TEAM_RED;
        String teamStr = (team == AgentInfo.TEAM_BLUE ? "BLUE" : "RED");
        return new Initialize().setName("CTFBot[" + teamStr + "]-" + (botCount-1)).setTeam(team);
    }

    @Override
    public void botInitialized(GameInfo gameInfo, ConfigChange currentConfig, InitedMessage init) {
        super.botInitialized(gameInfo, currentConfig, init);
        log.setLevel(Level.INFO);
        bot.getLogger().getCategory(SPOSH_LOG_CATEGORY).setLevel(Level.WARNING);
        bot.getLogger().getCategory("Yylex").setLevel(Level.OFF);
        
    }

    @Override
    public void botKilled(BotKilled event) {
        // 
        context.reportedIn = false;
        if(context.currentRole.equals("Defender"))
        {
        --context.defenderCount;
        }
        if(context.currentRole.equals("Attacker-Head"))
        {  
             // send message that leader died..
            context.setCTFMessageRoleChagned(context.getInfo().getId(), InfoType.HEAD_DIED);
        }
         if(context.currentRole.equals("Attacker-Tail"))
        {
           context.setCTFMessageRoleChagned(context.getInfo().getId(), InfoType.TAIL_DIED);
        }
         context.currentRole = "Defender"; // all starts as defender
    }
    

    
    //Example usage of Location message
    public void sendCTFMessageFriend() {
        
        if(!context.getTCClient().isConnected()){
		return;
		}
        context.getTCClient().sendToTeamOthers(
                new CTFMessage(
                    bot.getLocation(), 
                    context.getInfo().getId(), 
                    InfoType.FRIEND, 
                    context.getInfo().getId()
                 ));
    }

    @Override
    protected void logicBeforePlan() {
        sendCTFMessageFriend();
        getContext().logicBeforePlan();
        super.logicBeforePlan();
        // 
    }

    @Override
    protected void logicAfterPlan() {
        super.logicAfterPlan();
        getContext().logicAfterPlan(); 
        /// here deal with shooting...
        
        this.dealWithShooting();
        
        
    }    

    public static void main(String[] args) throws PogamutException {
        new UT2004BotRunner(CTFBotLogic.class, "CTF-TC-Bot").setMain(true).setLogLevel(Level.WARNING).startAgents(6);
    }
    
}
