package bearmaps;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import java.util.Random;


/**
 * @author: Shirley Zhou
 */


public class NaivePointSetTest {


    @Test
    /**
     * @source @Proj2b test example
     */
    public void sanityNearestTest() {

        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        double expX = ret.getX(); // evaluates to 3.3
        double expY = ret.getY(); // evaluates to 4.4

        assertEquals(expX, p2.getX(), 0);
        assertEquals(expY, p2.getY(), 0);
    }

    @Test
    public void sanityRandomTest() {
        Random r = new Random();
        List<Point> a = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            a.add(new Point(r.nextDouble(), r.nextDouble()));
        }
        NaivePointSet aa = new NaivePointSet(a);
        for (int i = 0; i < 200; i++) {
            Point x = a.get(i);
            Point n = aa.nearest(x.getX(), x.getY());
            assertEquals(n.getX(), x.getX(), 0);
            assertEquals(n.getY(), x.getY(), 0);
        }

    }
}
