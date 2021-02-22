public abstract class Action
{

    private final WorldModel world;
    private final ImageStore imageStore;
    private final int repeatCount;

    public Action(
            WorldModel world,
            ImageStore imageStore,
            int repeatCount)
    {
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }


    protected WorldModel getWorld() {
        return world;
    }

    protected ImageStore getImageStore() {
        return imageStore;
    }

    protected int getRepeatCount() {
        return repeatCount;
    }

    protected abstract void executeAction(EventScheduler scheduler);

}
