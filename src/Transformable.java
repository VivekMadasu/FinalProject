import processing.core.PImage;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Transformable extends Movable {

    private final int resourceLimit;
    private int resourceCount;

    public Transformable(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);

        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }

    protected int getResourceLimit() {
        return resourceLimit;
    }

    protected int getResourceCount() {
        return resourceCount;
    }

    protected void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    protected void finishTransform(
            Transformable miner,
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);

    }

    protected abstract boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore);


    protected Point nextPositiono(WorldModel world, Point destPos) {

        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

//        newPos = _nextPositionHelper(world, destPos, horiz, newPos);
//
//        return newPos;
        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }


    protected Point nextPosition(WorldModel world, Point destPos) {

        List<Point> points;

        Point pos = this.getPosition();

//        Predicate<Point> canPassThrough = p ->  withinBounds(p, world.getNumRows(), world.getNumCols()) &&
//                                            !world.isOccupied(p);
        Predicate<Point> canPassThrough = p ->  withinBounds(p, world);
        BiPredicate<Point, Point> withinReach = (p1, p2) -> neighbors(p1,p2);
        PathingStrategy strategy = new SingleStepPathingStrategy();

        points = strategy.computePath(pos, destPos,
                canPassThrough,
                withinReach,
                // p ->  withinBounds(p, grid) && grid[p.y][p.x] != GridValues.OBSTACLE,
                // (p1, p2) -> neighbors(p1,p2),
                PathingStrategy.CARDINAL_NEIGHBORS);
        //DIAGONAL_NEIGHBORS);
        //DIAGONAL_CARDINAL_NEIGHBORS);

//        if (points.size() == 0)
//        {
//            System.out.println("No path found");
//            return false;
//        }

        if (points.size() != 0) {
            pos = points.get(0);
            // path.add(pos);
        }

        return pos;
    }



}
