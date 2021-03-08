import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Movable extends AnimateEntity {

    public Movable(String id,
                   Point position,
                   List<PImage> images,
                   int actionPeriod,
                   int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected int _scheduleActionsHelper() {
        return 0;
    }

    protected boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition())) {
            _moveToHelper(world, target, scheduler);
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

    protected abstract void _moveToHelper(WorldModel world, Entity target, EventScheduler scheduler);

    protected Point nextPosition(WorldModel world, Point destPos) {

        List<Point> points;

        Point pos = this.getPosition();

        Predicate<Point> canPassThrough = p ->  withinBounds(p, world.getNumRows(), world.getNumCols()) &&
                ( !world.getOccupant(p).isPresent() ||
                        world.getOccupant(p).isPresent() && !(world.getOccupant(p).get() instanceof Ore)
                );

        BiPredicate<Point, Point> withinReach = (p1, p2) -> neighbors(p1,p2);
        PathingStrategy strategy = new SingleStepPathingStrategy();

        points = strategy.computePath(pos, destPos,
            _canPassThroughHelper(world),
            withinReach,
            PathingStrategy.CARDINAL_NEIGHBORS);

        if (points.size() != 0) {
            pos = points.get(0);
        }

        return pos;
    }

    protected abstract Predicate<Point> _canPassThroughHelper(WorldModel world);


    protected static boolean withinBounds(Point p, int numRows, int numCols)
    {
        return p.getY() >= 0 && p.getY() < numRows &&
                p.getX() >= 0 && p.getX() < numCols;
    }


    protected static boolean neighbors(Point p1, Point p2)
    {
        return p1.getX()+1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()+1 == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()-1 == p2.getY();
    }

}
