public class Activity extends Action{

    private final ActiveEntity activeEntity;

    public Activity(
            ActiveEntity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount)
    {
        super(world, imageStore, repeatCount);
        this.activeEntity = entity;
    }


    protected ActiveEntity getActiveEntity() {
        return activeEntity;
    }

    public void executeAction(EventScheduler scheduler)
    {
        getActiveEntity().executeActivity(this.getWorld(),
                this.getImageStore(), scheduler);

    }

}
