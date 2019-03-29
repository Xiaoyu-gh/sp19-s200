package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
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

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        NaivePointSet nps = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6, p7));
        Point exp = new Point(5, 4);
        Point actkd = kd.nearest(5, 4);
        Point actnps = nps.nearest(5, 4);

        assertEquals(Point.distance(actkd, exp), Point.distance(actnps, exp), 0);

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
            assertEquals(Point.distance(act, p), Point.distance(exp, p), 0);
        }
    }

    private List<Point> uniformGridConstructor() {
        List<Point> a = new ArrayList<>();

        int[] x = new int[]{4, 2, 6, 10, 0, 8};
        int[] y = new int[]{0, 6, 10, 4, 2, 8};

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                a.add(new Point((double) x[i] / (double) 10, (double) y[j] / (double) 10));
            }
        }

        return a;
    }


    @Test
    public void notSoRandomTest() {
        List<Point> a = uniformGridConstructor();
        KDTree kdX = new KDTree(a);
        NaivePointSet npsX = new NaivePointSet(a);
        List<Point> queryY = xPointsListConstructor(20);

        for (Point p : queryY) {
            Point exp = npsX.nearest(p.getX(), p.getY());
            Point act = kdX.nearest(p.getX(), p.getY());
            assertEquals(Point.distance(act, p), Point.distance(exp, p), 0);
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


    private void timekdXPtsYQuries(int X, int Y) {
        List<Point> pts = xPointsListConstructor(X);
        KDTree kd = new KDTree(pts);

        Stopwatch w = new Stopwatch();
        List<Point> queries = xPointsListConstructor(Y);
        for (Point p : queries) {
            Point act = kd.nearest(p.getX(), p.getY());
        }
        System.out.println("Time elapsed for " + Y + " queries on "
                            + X + " points: " + w.elapsedTime());
    }

    @Test
    public void timewith1000Ptsand200queries() {
        timekdXPtsYQuries(100000, 20000);
    }

    @Test
    public void timekdvsnps() {
        List<Point> pts = xPointsListConstructor(100000);
        KDTree kd = new KDTree(pts);
        NaivePointSet nps = new NaivePointSet(pts);
        List<Point> queries = xPointsListConstructor(10000);

        Stopwatch npsw = new Stopwatch();
        for (Point p: queries) {
            nps.nearest(p.getX(), p.getY());
        }
        double npstime = npsw.elapsedTime();
        System.out.println("NaivePointSet on nearest: " + npstime);

        Stopwatch kdw = new Stopwatch();
        for (Point p: queries) {
            kd.nearest(p.getX(), p.getY());
        }
        double kdtime = kdw.elapsedTime();
        System.out.println("KDTree on nearest: " + kdtime);

    }
}
