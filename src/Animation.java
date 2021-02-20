public class Animation extends Action{

    private final AnimateEntity animateEntity;

    public Animation(
            AnimateEntity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount)
    {
        super(world, imageStore, repeatCount);
        this.animateEntity = entity;
    }

    protected AnimateEntity getAnimateEntity() {
        return animateEntity;
    }

    public void executeAction(EventScheduler scheduler)
    {
        this.getAnimateEntity().nextImage();

        if (getRepeatCount() != 1) {
            scheduler.scheduleEvent(this.getAnimateEntity(),
                    Factory.createAnimationAction(this.getAnimateEntity(),
                            Math.max(this.getRepeatCount() - 1,
                                    0)),
                        this.getAnimateEntity().getAnimationPeriod());
        }
    }


}
