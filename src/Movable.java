import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class Movable extends AnimateEntity {

    public Movable(String id,
                   Point position,
                   List<PImage> images,
                   int actionPeriod,
                   int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public int _scheduleActionsHelper() {
        return 0;
    }

    public boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition())) {
            moveToHelper(world, target, scheduler);
            return true;
        }
        else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public abstract void moveToHelper(WorldModel world, Entity target, EventScheduler scheduler);

    protected Point nextPosition(
            WorldModel world, Point destPos){
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        newPos = _nextPositionHelper(world, destPos, horiz, newPos);

        return newPos;
    }

    protected abstract Point _nextPositionHelper(WorldModel world, Point destPos, int horiz, Point newPos);
}
