package org.projii.commons.spaceship.equipment;

public class EnergyShield {

    private int maxCount;
    private int presentCount;

    private int regeneration;

    private int time;
    private int timeLeft;
    private final int id;

    public int getId() {
        return id;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public int Time() {
        return time;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getRegeneration() {
        return regeneration;
    }


    public EnergyShield(int id, int maxCount, int regeneration, int Time) {
        this.id = id;
        this.maxCount = maxCount;
        presentCount = this.maxCount;
        this.regeneration = regeneration;
        this.time = Time;
    }

    public void Damages(int damage) {
        if (presentCount < damage) {
            presentCount = 0;
        } else {
            presentCount -= damage;
        }
    }

    public void CreateDowload() {
        timeLeft = time;
    }

    public void Regeneration() {
        if (timeLeft == 0) {
            presentCount = presentCount + regeneration;
        } else timeLeft--;
    }

}
