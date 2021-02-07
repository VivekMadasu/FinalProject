public class Activity implements Action{
    private final Entity entity;
    private final WorldModel world;
    private final ImageStore imageStore;
    private final int repeatCount;



    public Entity getEntity() {
        return entity;
    }

    public WorldModel getWorld() {
        return world;
    }

    public ImageStore getImageStore() {
        return imageStore;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public Activity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }



    public void executeAction(EventScheduler scheduler)
    {
        if (entity instanceof ActiveEntity){
            ((ActiveEntity) entity).executeActivity(this.world,
                this.imageStore, scheduler);
        }
    }

}
