package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;


/**
 * @author: Shirley Zhou
 * @source: Hug explanation video: https://www.youtube.com/watch?v=lp80raQvE5c&feature=youtu.be
 */
public class KDTreeTest {


    @Test
    public void basicTest() {
        assertEquals(1, 1);
    }

    /*
    Replica of Lecture example
     */
    @Test
    public void naiveLectureTest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        NaivePointSet nps = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6, p7));
        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        Point kdAct = kd.nearest(0, 7);
        Point npsAct = nps.nearest(0,7);
        Point exp = p6;

        assertEquals(kdAct, exp);
        assertEquals(npsAct, exp);

    }

    private List<Point> xPointsListConstructor(int n) {
        Random r = new Random();
        List<Point> a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            a.add(new Point(r.nextDouble(), r.nextDouble()));
        }
        return a;
    }


    private void randomXPtsQueriesTest(int X, int Y) {
        List<Point> a = xPointsListConstructor(X);
        KDTree kdX = new KDTree(a);
        NaivePointSet npsX = new NaivePointSet(a);
        List<Point> queryY = xPointsListConstructor(Y);

        for (Point p : queryY) {
            Point act = kdX.nearest(p.getX(), p.getY());
            Point exp = npsX.nearest(p.getX(), p.getY());
            assertEquals(act, exp);
        }
    }

    @Test
    public void random1000Pts200QueriesTest() {
        int ptsNum = 1000;
        int queriesNum = 200;
        randomXPtsQueriesTest(ptsNum, queriesNum);
    }

    @Test
    public void random10000Pts2000QueriesTest() {
        int ptsNum = 10000;
        int queriesNum = 2000;
        randomXPtsQueriesTest(ptsNum, queriesNum);
    }

    @Test
    public void random100000Pts20000QueriesTest() {
        int ptsNum = 100000;
        int queriesNum = 20000;
        randomXPtsQueriesTest(ptsNum, queriesNum);
    }
}
