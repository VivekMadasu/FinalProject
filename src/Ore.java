import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Ore implements Entity, ActiveEntity {

    private static final Random rand = new Random();

    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;


    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private int resourceCount;
    private final int actionPeriod;
    private final int animationPeriod;

    public Ore(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public PImage getCurrentImage() {
        return getImages().get(getImageIndex());
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;

    }

    public List<PImage> getImages() {
        return images;
    }

    public int getImageIndex() {
        return imageIndex;
    }


    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }


    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Point pos = this.position;

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Entity blob = Factory.createOreBlob(this.id + OreBlob.BLOB_ID_SUFFIX, pos,
                this.actionPeriod / OreBlob.BLOB_PERIOD_SCALE,
                OreBlob.BLOB_ANIMATION_MIN + rand.nextInt(
                        OreBlob.BLOB_ANIMATION_MAX
                                - OreBlob.BLOB_ANIMATION_MIN),
                imageStore.getImageList(OreBlob.BLOB_KEY));

        world.addEntity(blob);
        ((ActiveEntity)blob).scheduleActions(scheduler, world, imageStore);
    }



    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
                scheduler.scheduleEvent(this,
                        Factory.createActivityAction(this, world, imageStore),
                        this.actionPeriod);


    }







}
