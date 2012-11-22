package org.projii.commons.spaceship;

import org.projii.commons.space.Point;
import org.projii.commons.space.RealworldObject;
import org.projii.commons.spaceship.equipment.EnergyGenerator;
import org.projii.commons.spaceship.equipment.EnergyShield;
import org.projii.commons.spaceship.equipment.SpaceshipEngine;
import org.projii.commons.spaceship.weapon.Weapon;

public class Spaceship extends RealworldObject {
    private int health;
    private int presentHealth;
    private int armor;
    private int speed;
    private final int id;
    private final int weaponAmount;
    private Weapon[] weapons;
    private EnergyGenerator generator;
    private SpaceshipEngine engine;
    private EnergyShield energyShield;


    public Spaceship(int id, int health, int armor, EnergyGenerator generator, SpaceshipEngine engine, EnergyShield energyShield,
                     int length, int width, int WEAPON_AMOUNT, Weapon[] weapons) {
        super(id);
        this.id = id;
        this.presentHealth = this.health = health;
        this.armor = armor;
        this.generator = generator;
        this.engine = engine;
        this.energyShield = energyShield;
        setLength(length);
        setWidth(width);
        this.weaponAmount = WEAPON_AMOUNT;
        this.weapons = weapons;
    }

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public int getPresentHealth() {
        return presentHealth;
    }

    public int getArmor() {
        return armor;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getWeaponAmount() {
        return weaponAmount;
    }

    public EnergyGenerator getGenerator() {
        return generator;
    }

    public SpaceshipEngine getEngine() {
        return engine;
    }

    public EnergyShield getEnergyShield() {
        return energyShield;
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    public void fire() {
        for (int i = 0; i <= weaponAmount - 1; i++) {
            if (generator.getPresentCount() >= weapons[i].getBullet().getConsumeEnergy()) {
                weapons[i].fire(getLocation(), getRotation());
                generator.useEnergy(weapons[i].getBullet().getConsumeEnergy());
            }
        }
    }

    public void move(Point p, int rotation) {
        setLocation(p);
        setRotation(rotation);
    }

    public void demage(int damage) {
        if (energyShield.getPresentCount() > 0) {
            energyShield.Damages(damage);
        } else {
            health -= damage - armor;
        }
        energyShield.CreateDowload();
    }

    public void forTimer() {
        energyShield.Regeneration();
        generator.regenerate();
        for (int i = 0; i <= weaponAmount - 1; i++) {
            weapons[i].Download();
        }
    }

    public Boolean equals(Spaceship ship) {
        Boolean result = true;
        if (id != ship.getId())
            return false;
        if (health != ship.getHealth())
            return false;
        if (armor != ship.getArmor())
            return false;
        if (this.getLength() != ship.getLength())
            return false;
        if (this.getWidth() != ship.getWidth())
            return false;
        if (weaponAmount != ship.getWeaponAmount())
            return false;
        if (this.generator.getId() != ship.getGenerator().getId())
            return false;
        if (this.generator.getCount() != ship.getGenerator().getCount())
            return false;
        if (this.generator.getRegeneration() != ship.getGenerator().getRegeneration())
            return false;
        if (this.engine.getId() != ship.getEngine().getId())
            return false;
        if (this.engine.getMaxSpeed() != ship.getEngine().getMaxSpeed())
            return false;
        if (this.engine.getSpeedUp() != ship.getEngine().getSpeedUp())
            return false;
        if (this.engine.getHandleability() != ship.getEngine().getHandleability())
            return false;
        if (this.energyShield.getId() != ship.getEnergyShield().getId())
            return false;
        if (this.energyShield.getMaxCount() != ship.getEnergyShield().getMaxCount())
            return false;
        if (this.energyShield.getRegeneration() != ship.getEnergyShield().getRegeneration())
            return false;
        if (this.energyShield.Time() != ship.getEnergyShield().Time())
            return false;

        Weapon[] anotherShipWeapons = ship.getWeapons();
        for (int i = 0; i < weapons.length; i++) {
            if (weapons[i].getId() != anotherShipWeapons[i].getId())
                return false;
            if (weapons[i].getCd() != anotherShipWeapons[i].getCd())
                return false;
            if (weapons[i].getRate() != anotherShipWeapons[i].getRate())
                return false;
            if (weapons[i].getBullet().getSpeed() != anotherShipWeapons[i].getBullet().getSpeed())
                return false;
            if (weapons[i].getBullet().getDamage() != anotherShipWeapons[i].getBullet().getDamage())
                return false;
            if (weapons[i].getBullet().getConsumeEnergy() != anotherShipWeapons[i].getBullet().getConsumeEnergy())
                return false;
            if (weapons[i].getBullet().getRange() != anotherShipWeapons[i].getBullet().getRange())
                return false;
            if (weapons[i].getBullet().getLength() != anotherShipWeapons[i].getBullet().getLength())
                return false;
            if (weapons[i].getBullet().getWidth() != anotherShipWeapons[i].getBullet().getWidth())
                return false;
        }
        return result;
    }
}
