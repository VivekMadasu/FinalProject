import processing.core.PImage;

import java.util.List;

public abstract class Movable extends AnimateEntity {

    public Movable(String id,
                   Point position,
                   List<PImage> images,
                   int actionPeriod,
                   int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
    }



    protected abstract boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler);

    protected abstract Point nextPosition(
            WorldModel world, Point destPos);
}
