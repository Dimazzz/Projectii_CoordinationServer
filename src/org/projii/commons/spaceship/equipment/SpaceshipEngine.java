package org.projii.commons.spaceship.equipment;

public class SpaceshipEngine {

    private int maxSpeed;
    private int speedUp;
    private int handleability;
    private final int id;

    public int getId() {
        return id;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getSpeedUp() {
        return speedUp;
    }

    public int getHandleability() {
        return handleability;
    }

    public SpaceshipEngine(int id, int maxSpeed, int speedUp, int handleability) {
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.speedUp = speedUp;
        this.handleability = handleability;
    }
}
