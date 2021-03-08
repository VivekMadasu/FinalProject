import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class OreBlob extends Movable {


    public static final String BLOB_KEY = "blob";
    public static final String BLOB_ID_SUFFIX = " -- blob";
    public static final int BLOB_PERIOD_SCALE = 4;
    public static final int BLOB_ANIMATION_MIN = 50;
    public static final int BLOB_ANIMATION_MAX = 150;


    public OreBlob(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }




    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> blobTarget =
                world.findNearest(this.getPosition(), Vein.class);
        long nextPeriod = this.getActionPeriod();

        if (blobTarget.isPresent()) {
            Point tgtPos = blobTarget.get().getPosition();

            if (this.moveTo(world, blobTarget.get(), scheduler)) {
                Quake quake = Factory.createQuake(tgtPos,
                        imageStore.getImageList(Quake.QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                nextPeriod);
    }


    public void _moveToHelper(WorldModel world,
                             Entity target,
                             EventScheduler scheduler){
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
    }

/*
     protected Point nextPositiono(WorldModel world, Point destPos) {

         int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
         Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());
//
//         newPos = _nextPositionHelper(world, destPos, horiz, newPos);
//
//         return newPos;

        Optional<Entity> occupant = world.getOccupant(newPos);

         if (horiz == 0 || (occupant.isPresent() && !(occupant.get() instanceof Ore)))
         {
             int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
             newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);
             occupant = world.getOccupant(newPos);

             if (vert == 0 || (occupant.isPresent() && !(occupant.get() instanceof Ore)))
             {
                 newPos = this.getPosition();
             }
         }

         return newPos;
    }

 */

    protected Predicate<Point> _canPassThroughHelper(WorldModel world){
        Predicate<Point> canPassThrough = p ->  withinBounds(p, world.getNumRows(), world.getNumCols()) &&
                ( !world.getOccupant(p).isPresent() ||
                        world.getOccupant(p).isPresent() && !(world.getOccupant(p).get() instanceof Ore)
                );
        return canPassThrough;
    }

//    protected Point nextPosition(WorldModel world, Point destPos) {
//
//        List<Point> points;
//
//        Point pos = this.getPosition();
//
//        Predicate<Point> canPassThrough = p ->  withinBounds(p, world.getNumRows(), world.getNumCols()) &&
//                ( !world.getOccupant(p).isPresent() ||
//                        world.getOccupant(p).isPresent() && !(world.getOccupant(p).get() instanceof Ore)
//                );
////        Predicate<Point> canPassThrough = p ->  withinBounds(p, world);
//        BiPredicate<Point, Point> withinReach = (p1, p2) -> neighbors(p1,p2);
//        PathingStrategy strategy = new SingleStepPathingStrategy();
//
//        points = strategy.computePath(pos, destPos,
//                canPassThrough,
//                withinReach,
//                // p ->  withinBounds(p, grid) && grid[p.y][p.x] != GridValues.OBSTACLE,
//                // (p1, p2) -> neighbors(p1,p2),
//                PathingStrategy.CARDINAL_NEIGHBORS);
//        //DIAGONAL_NEIGHBORS);
//        //DIAGONAL_CARDINAL_NEIGHBORS);
//
////        if (points.size() == 0)
////        {
////            System.out.println("No path found");
////            return false;
////        }
//
//        if (points.size() != 0) {
//            pos = points.get(0);
//            // path.add(pos);
//        }
//
//        return pos;
//    }

}
