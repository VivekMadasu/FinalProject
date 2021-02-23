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

    protected void finishTransform(
            Transformable miner,
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);

    }

    protected abstract boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore);


    protected Point _nextPositionHelper(WorldModel world, Point destPos, int horiz, Point newPos) {
        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }


}
