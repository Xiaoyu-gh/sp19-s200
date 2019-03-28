package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {

    private final List<Point> pts;

    public NaivePointSet(List<Point> points) {
        pts = points;
    }


    @Override
    public Point nearest(double x, double y) {
        Point sub = new Point(x, y);
        Point nearest = pts.get(0);
        for (int i = 1; i < pts.size(); i++) {
            Point curr = pts.get(i);
            if (Point.distance(sub, curr) < Point.distance(sub, nearest)) {
                nearest = curr;
            }
        }
        return nearest;


    }
}
