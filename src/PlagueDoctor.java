import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class PlagueDoctor extends Movable {


    public static final String PLAGUEDOCTOR_KEY = "plaguedoctor";
    public static final int PLAGUEDOCTOR_ACTION_PERIOD = 600;
    public static final int PLAGUEDOCTOR_ANIMATION_PERIOD = 100;


    public PlagueDoctor(
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
        Optional<Entity> plagueDoctorTargetZombie =
                world.findNearest(this.getPosition(), MinerZombie.class);
        long nextPeriod = this.getActionPeriod();

        if (plagueDoctorTargetZombie.isPresent()) {

            Point tgtPos = plagueDoctorTargetZombie.get().getPosition();

            if (this.moveTo(world, plagueDoctorTargetZombie.get(), scheduler)) {
                if (world.getOccupant(plagueDoctorTargetZombie.get().getPosition()).isPresent()) {

                    MinerZombie minerZombie = (MinerZombie) (world.getOccupant(plagueDoctorTargetZombie.get().getPosition()).get());
                    minerZombie.transform(world, scheduler, imageStore);

                }

            }
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                nextPeriod);
    }


    public void _moveToHelper(WorldModel world,
                             Entity target,
                             EventScheduler scheduler){
    }


    protected Predicate<Point> _canPassThroughHelper(WorldModel world){
        Predicate<Point> canPassThrough = p ->  withinBounds(p, world.getNumRows(), world.getNumCols()) &&
                !world.isOccupied(p);
        return canPassThrough;
    }

}
