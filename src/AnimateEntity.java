import processing.core.PImage;

import java.util.List;

public abstract class AnimateEntity extends ActiveEntity {

   private int animationPeriod;
   private int imageIndex = 0;


   public AnimateEntity(String id,
                        Point position,
                        List<PImage> images,
                        int actionPeriod,
                        int animationPeriod){
       super(id, position, images, actionPeriod);
       this.animationPeriod = animationPeriod;

   }


    protected int getAnimationPeriod() {
        return this.animationPeriod;
    }

    protected int getImageIndex() {
        return imageIndex;
    }

    protected void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    protected void nextImage() {
        this.setImageIndex((this.getImageIndex()+ 1) % this.getImages().size());
    }

    protected void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        super.scheduleActions(scheduler, world, imageStore);
        scheduler.scheduleEvent(this,
                Factory.createAnimationAction(this, _scheduleActionsHelper()),
                getAnimationPeriod());
    }

    protected abstract int _scheduleActionsHelper();

}
