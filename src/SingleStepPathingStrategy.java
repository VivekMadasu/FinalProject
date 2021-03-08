import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SingleStepPathingStrategy
   implements PathingStrategy
{
   public List<Point> computePath(Point start, Point end,
      Predicate<Point> canPassThrough,
      BiPredicate<Point, Point> withinReach,
      Function<Point, Stream<Point>> potentialNeighbors)
   {
      /* Does not check withinReach.  Since only a single step is taken
       * on each call, the caller will need to check if the destination
       * has been reached.
       */
      /*
      Stream<Point> pn = potentialNeighbors.apply(start);
      // Object[] objects = pn.toArray();
      // List<Point> pl = pn.collect(Collectors.toList());
      // List<Point> pl = pn.collect(Collectors.toList());
      Stream<Point> pnc = pn.filter(canPassThrough);
      // List<Point> lppt = pnc.collect(Collectors.toList());
      // List<Point> lppt = pnc.collect(Collectors.toList());
      // Stream<Point> pncx = pnc.peek(e -> System.out.println("Filtered value: " + e));
      // pnc.peek(e -> System.out.println("Filtered value: " + e));
      Stream<Point> pncc = pnc.filter(pt ->
              !pt.equals(start)
                      && !pt.equals(end)
                      && Math.abs(end.getX() - pt.getX()) <= Math.abs(end.getX() - start.getX())
                      && Math.abs(end.getY() - pt.getY()) <= Math.abs(end.getY() - start.getY()));

      // Stream<Point> pnccone = pncc.limit(1);
      // List<Point> lp = pnccone.collect(Collectors.toList());
      List<Point> lp = pncc.collect(Collectors.toList());
      List<Point> lpone = lp.subList(0,1);
      return lpone;
       */
      Predicate<Point> isTowardsEnd = pt -> !pt.equals(start)
                      && !pt.equals(end)
                      && Math.abs(end.getX() - pt.getX()) <= Math.abs(end.getX() - start.getX())
                      && Math.abs(end.getY() - pt.getY()) <= Math.abs(end.getY() - start.getY());

      return potentialNeighbors.apply(start)
         .filter(canPassThrough)
         .filter(isTowardsEnd)
         .limit(1)
         .collect(Collectors.toList());
   }
}