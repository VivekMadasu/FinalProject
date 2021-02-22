import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull extends Transformable{


    public MinerNotFull(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget =
                world.findNearest(this.getPosition(), Ore.class);

        if (!notFullTarget.isPresent() || !this.moveTo(world,
                notFullTarget.get(),
                scheduler)
                || !this.transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }


    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            MinerFull miner = Factory.createMinerFull(this.getId(), this.getResourceLimit(),
                    this.getPosition(), this.getActionPeriod(),
                    this.getAnimationPeriod(),
                    this.getImages());

            super.finishTransform(
                    miner,
                    world,
                    scheduler,
                    imageStore);

            return true;
        }

        return false;
    }


    public void moveToHelper(WorldModel world,
                             Entity target,
                             EventScheduler scheduler){
        setResourceCount(this.getResourceCount() + 1) ;
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
    }









}
