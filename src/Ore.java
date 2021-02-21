import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Ore extends ActiveEntity {

    private static final Random rand = new Random();

    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;




    public Ore(
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
        Point pos = this.getPosition();

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = Factory.createOreBlob(this.getId() + OreBlob.BLOB_ID_SUFFIX, pos,
                this.getActionPeriod() / OreBlob.BLOB_PERIOD_SCALE,
                OreBlob.BLOB_ANIMATION_MIN + rand.nextInt(
                        OreBlob.BLOB_ANIMATION_MAX
                                - OreBlob.BLOB_ANIMATION_MIN),
                imageStore.getImageList(OreBlob.BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }


}
