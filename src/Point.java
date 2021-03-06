import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public final class Point
{
    private final int x;
    private final int y;

    private int fValue;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public Optional<Entity> nearestEntity(
            List<Entity> entities)
    {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            Entity nearest = entities.get(0);
            int nearestDistance = nearest.getPosition().distanceSquared(this);

            for (Entity other : entities) {
                int otherDistance = other.getPosition().distanceSquared(this);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public int getfValue() {
        return fValue;
    }

    public void setfValue(int fValue) {
        this.fValue = fValue;
    }


    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point)other).x == this.x
                && ((Point)other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }

    public boolean adjacent(Point p2) {
        return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) || (this.y == p2.y
                && Math.abs(this.x - p2.x) == 1);
    }

    public int distanceSquared(Point p2) {
        int deltaX = this.getX() - p2.getX();
        int deltaY = this.getY() - p2.getY();

        return deltaX * deltaX + deltaY * deltaY;
    }


}
