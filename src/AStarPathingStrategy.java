import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    private static int hValue(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<Point>();

        Comparator<Point> fValueCmp = (p1, p2) -> p1.getfValue() - p2.getfValue();
        PriorityQueue<Point> openList = new PriorityQueue<>(fValueCmp);
        HashSet<Point> closedList = new HashSet<>();
        Map<Point, Point> priorList = new HashMap<>();
        Map<Point, Integer> gValues = new HashMap<>();
        boolean endWithinReach = false;

        if (start.equals(end)) {
            return path;
        }

        openList.add(start);
        gValues.put(start, 0);
        start.setfValue(hValue(start, end));

        while (!openList.isEmpty()) {

            Point current = openList.poll();

            // if (current.equals(end)) {
            if (withinReach.test(current, end)) {
                priorList.put(end, current);
                endWithinReach = true;
                break;
            }

            List<Point> neighbors = potentialNeighbors.apply(current)
                                                 .filter(canPassThrough)
                                                 .collect(Collectors.toList());

            for (Point neighbor : neighbors) {
                if (closedList.contains(neighbor)) {
                    continue;
                }
                int tmpGValue = gValues.getOrDefault(current, Integer.MAX_VALUE) + 1;
                if (tmpGValue <  gValues.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    gValues.put( neighbor, tmpGValue);
                    priorList.put(neighbor, current);
                }
                if (!openList.contains(neighbor)) {
                    int fValue = gValues.getOrDefault(neighbor, Integer.MAX_VALUE)
                                 + hValue(neighbor, end);
                    neighbor.setfValue(fValue); // for priority queue
                    openList.add(neighbor);

                }

            }

            closedList.add(current);
        }


        if (endWithinReach) {
            Point current = end;
            // path.add(end);
            while (priorList.containsKey(current)) {
                current = priorList.get(current);
                if (!current.equals(start)) {
                    path.add(0, current);
                }
            }
        }
        
        return path;
    }
}
