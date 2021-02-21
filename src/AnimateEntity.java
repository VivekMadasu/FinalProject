import processing.core.PImage;

import java.util.List;

public abstract class AnimateEntity extends ActiveEntity {

   private int animationPeriod;


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


    protected void nextImage() {
        this.setImageIndex((this.getImageIndex()+ 1) % this.getImages().size());
    }
}
