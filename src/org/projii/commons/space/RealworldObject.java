package org.projii.commons.space;

public abstract class RealworldObject {

    private final int id;
    private int length;
    private int width;
    private Point location;
    private int rotation;

    protected RealworldObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }


}
