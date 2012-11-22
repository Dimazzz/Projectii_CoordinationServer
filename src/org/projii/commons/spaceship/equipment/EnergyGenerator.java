package org.projii.commons.spaceship.equipment;

public class EnergyGenerator {

    private int maxCount;
    private int presentCount;
    private int regeneration;
    private int id;

    public EnergyGenerator(int id, int maxCount, int regeneration) {
        this.id = id;
        this.maxCount = maxCount;
        presentCount = this.maxCount;
        this.regeneration = regeneration;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return maxCount;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public int getRegeneration() {
        return regeneration;
    }

    public void useEnergy(int use) {
        presentCount = presentCount - use;
    }

    public void regenerate() {
        presentCount = presentCount + regeneration;
    }
}
