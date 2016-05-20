package ctfbot;

import cz.cuni.amis.pogamut.ut2004.communication.messages.ItemType;
import cz.cuni.amis.pogamut.ut2004.communication.messages.UT2004ItemType;

/**
 * Represents a concrete weapon within UT2004 game together with its ammo.
 * 
 * @author Jimmy
 */
public enum Weapon {
    
    SHIELD_GUN(UT2004ItemType.SHIELD_GUN, null, null),
    RIFLE(UT2004ItemType.ASSAULT_RIFLE, UT2004ItemType.ASSAULT_RIFLE_AMMO, UT2004ItemType.ASSAULT_RIFLE_GRENADE),
    BIO(UT2004ItemType.BIO_RIFLE, UT2004ItemType.BIO_RIFLE_AMMO, null),
    SHOCK(UT2004ItemType.SHOCK_RIFLE, UT2004ItemType.SHOCK_RIFLE_AMMO, null),
    FLAK(UT2004ItemType.FLAK_CANNON, UT2004ItemType.FLAK_CANNON_AMMO, null),
    MINIGUN(UT2004ItemType.MINIGUN, UT2004ItemType.MINIGUN_AMMO, null),
    LINKGUN(UT2004ItemType.LINK_GUN, UT2004ItemType.LINK_GUN_AMMO, null),
    ROCKET(UT2004ItemType.ROCKET_LAUNCHER, UT2004ItemType.ROCKET_LAUNCHER_AMMO, null),
    LIGHTNING(UT2004ItemType.LIGHTNING_GUN, UT2004ItemType.LIGHTNING_GUN_AMMO, null);

    public static Weapon getWeaponByItemType(ItemType type) {
        for (Weapon weapon : Weapon.values()) {
            if (weapon.weaponType == type) return weapon;
        }
        return null;
    }
    
    public final ItemType weaponType;
    public final ItemType priAmmoType;
    public final ItemType secAmmoType;
    
    private Weapon(ItemType weaponType, ItemType priAmmoType, ItemType secAmmoType) {
        this.weaponType = weaponType;
        this.priAmmoType = priAmmoType;
        this.secAmmoType = secAmmoType;
    }

    public String toString() {
        return weaponType.getName();
    }
    
}
