import processing.core.PImage;

import java.util.List;
import java.util.Optional;


public class MinerZombie extends Transformable {

    public static final String ZOMBIE_KEY = "zombie";
    public static final int ZOMBIE_ACTION_PERIOD = 777;

    public MinerZombie(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, 0, 0, actionPeriod, animationPeriod);
    }


    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> zombieMinerTarget =
                world.findNearest(this.getPosition(), MinerNotFull.class);

        if (zombieMinerTarget.isPresent() && this.moveTo(world,
                zombieMinerTarget.get(), scheduler))
        {
            // make the miner a new zombie
            MinerNotFull miner = (MinerNotFull) (world.getOccupant(zombieMinerTarget.get().getPosition()).get());
            miner.setSavedActionPeriod(miner.getSavedActionPeriod());
            miner.transformToZombie(world, scheduler, imageStore);
            // this.transform(world, scheduler, imageStore);
        }

        scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());

    }


    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        MinerNotFull miner = Factory.createMinerNotFull(this.getId(), this.getResourceLimit(),
                this.getPosition(), this.getSavedActionPeriod(),
                this.getAnimationPeriod(),
                imageStore.getImageList(WorldLoading.MINER_KEY));

        super.finishTransform(
                miner,
                world,
                scheduler,
                imageStore);

        return true; // always transform

    }

    public void _moveToHelper(WorldModel world,
                             Entity target,
                             EventScheduler scheduler){
    }


}
