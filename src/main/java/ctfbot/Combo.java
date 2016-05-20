package ctfbot;

/**
 * UT2004 COMBOs that makes sense to use for bots.
 * 
 * @author Jimmy
 */
public enum Combo {
    
    BERSERK("xGame.ComboBerserk"),
    REGENERATE("xGame.ComboDefensive");
    
    public final String command;
    
    private Combo(String command) {
        this.command = command;
    }
    
}
