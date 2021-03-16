import processing.core.PImage;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Transformable extends Movable {

    private final int resourceLimit;
    private int resourceCount;

    private int savedActionPeriod;


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

    public int getSavedActionPeriod() {
        return savedActionPeriod;
    }

    public void setSavedActionPeriod(int savedActionPeriod) {
        this.savedActionPeriod = savedActionPeriod;
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


    protected Predicate<Point> _canPassThroughHelper(WorldModel world){
        Predicate<Point> canPassThrough = p ->  withinBounds(p, world.getNumRows(), world.getNumCols()) &&
                !world.isOccupied(p);
        return canPassThrough;
    }

}
