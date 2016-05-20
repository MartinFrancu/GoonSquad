package ctfbot;

import cz.cuni.amis.pogamut.ut2004.communication.messages.ItemType;
import cz.cuni.amis.pogamut.ut2004.communication.messages.UT2004ItemType;

/**
 * Represents either a category of items or concrete item type.
 * 
 * Weapons are not covered here, they have own enum {@link Weapon}.
 * 
 * @author Jimmy
 */
public enum Item {
    
    HEALTH(ItemType.Category.HEALTH),    
    ARMOR(ItemType.Category.ARMOR),
    SHIELD(ItemType.Category.SHIELD),
    HEALTH_PACK(UT2004ItemType.HEALTH_PACK),
    SUPER_HEALTH_PACK(UT2004ItemType.SUPER_HEALTH_PACK),
    SHIELD_PACK(UT2004ItemType.SHIELD_PACK),
    SUPER_SHIELD_PACK(UT2004ItemType.SUPER_SHIELD_PACK),
    U_DAMAGE_PACK(UT2004ItemType.U_DAMAGE_PACK)
    ;
    
    /**
     * Either {@link #type} or {@link #category} is filled.
     * May be null...
     */
    public final ItemType type;
    
    /**
     * Either {@link #type} or {@link #category} is filled.
     * May be null...
     */
    public final ItemType.Category category;
    
    private Item(ItemType.Category category) {
        this.type = null;
        this.category = category;
    }
    
    private Item(ItemType type) {
        this.type = type;
        this.category = null;
    }
    
    public String toString() {
        return type == null ? "Item[" + category.name() + "]" : type.getName();
    }
    
}
