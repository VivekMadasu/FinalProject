import processing.core.PImage;

import java.util.List;

public abstract class Transformable extends Movable {

    private final int resourceLimit;
    private int resourceCount;

    public Transformable(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);

        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }

    protected int getResourceLimit() {
        return resourceLimit;
    }

    protected int getResourceCount() {
        return resourceCount;
    }

    protected void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    protected abstract boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore);
}
