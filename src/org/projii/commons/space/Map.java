package org.projii.commons.space;

import org.projii.commons.spaceship.Spaceship;
import org.projii.commons.spaceship.weapon.Bullet;

import java.util.List;

public class Map {

    private List<Spaceship> shipList;
    private List<Bullet> bulletList;

    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Spaceship> getShipList() {
        return shipList;
    }

    public void setShipList(List<Spaceship> shipList) {
        this.shipList = shipList;
    }

    public List<Spaceship> getBulletList() {
        return shipList;
    }

    public void setBulletList(List<Spaceship> shipList) {
        this.shipList = shipList;
    }

    public Map(int width, int height, List<Spaceship> shipList) {
        this.width = width;
        this.height = height;
        this.shipList = shipList;
    }
}
