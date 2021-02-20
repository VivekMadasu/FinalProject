import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Vein extends ActiveEntity{

    private static final Random rand = new Random();


    public Vein(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }




    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.getPosition());

        if (openPt.isPresent()) {
            Entity ore = Factory.createOre(Ore.ORE_ID_PREFIX + this.getId(), openPt.get(),
                    Ore.ORE_CORRUPT_MIN + rand.nextInt(
                            Ore.ORE_CORRUPT_MAX - Ore.ORE_CORRUPT_MIN),
                    imageStore.getImageList(WorldLoading.ORE_KEY));
            world.addEntity(ore);
            ((ActiveEntity)ore).scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }



}
