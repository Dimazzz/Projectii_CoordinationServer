package org.projii.commons.spaceship.weapon;

import org.projii.commons.space.Point;
import org.projii.commons.space.RealworldObject;

public abstract class AbstractBullet extends RealworldObject {
    private int speed;
    private int damage;
    private int consumeEnergy;
    private int range;
    private Point destination;

    public AbstractBullet(int id, int speed, int damage, int consumeEnergy, int range, Point location, int rotation, int length, int width) {
        super(id);
        this.speed = speed;
        this.damage = damage;
        this.consumeEnergy = consumeEnergy;
        this.range = range;
        setLocation(location);
        setRotation(rotation);
        int a = (int) (range * Math.sin(rotation));
        int b = (int) (range * Math.cos(rotation));
        this.destination.X(location.X() + a);
        this.destination.Y(location.Y() + b);
        setLength(length);
        setWidth(width);
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public int getConsumeEnergy() {
        return consumeEnergy;
    }

    public int getDamage() {
        return damage;
    }

    public Point getDestinationPoint() {
        return destination;
    }

    public void move() {
        int a = (int) (speed * Math.sin(getRotation()));
        int b = (int) (speed * Math.cos(getRotation()));
        this.getLocation().X(this.getLocation().X() + a);
        this.getLocation().Y(this.getLocation().Y() + b);
    }

}
