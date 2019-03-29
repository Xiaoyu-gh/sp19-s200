package bearmaps;
import java.util.List;


/**
 * author: Shirley Zhou
 * source: Joshua Hug pseudowalk through video
 */
public class KDTree implements PointSet {
    private static final boolean HOR = false;
    private static final boolean VER = true;

    private Node root;

    private class Node {
        private Point pt;
        private boolean orientation;

        private Node left;
        private Node right;

        private Node(Point p, boolean o) {
            pt = p;
            orientation = o;
        }

    }

    private List<Point> myPts;

    private void add(Point p, boolean ori) {
        root = addHelper(p, ori, root);
    }

    private Node addHelper(Point p, boolean ori, Node n) {


        if (n == null) {
            return new Node(p, ori);
        }
        //add node only if x and y does not equal to the coordinate compared
        if (n.pt.getX() != p.getX() || n.pt.getY() != p.getY()) {

            //if orientation is vertical, compare Y coordinates
            if (n.orientation == VER) {

                if (p.getX() < n.pt.getX()) {
                    n.left = addHelper(p, HOR, n.left);
                } else {
                    n.right = addHelper(p, HOR, n.right);
                }

            } else {
                if (p.getY() < n.pt.getY()) {
                    n.left = addHelper(p, VER, n.left);
                } else {
                    n.right = addHelper(p, VER, n.right);
                }
            }
        }
        return n;
    }

    private boolean orientationHelper(int index) {
        if (index % 2 == 0) {
            return VER;
        }
        return HOR;
    }


    public KDTree(List<Point> points) {
        myPts = points;
        root = new Node(points.get(0), VER);
        for (int i = 1; i < points.size(); i++) {
            add(points.get(i), HOR);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        return nearestHelper(root, new Point(x, y), root).pt;

    }

    private Node nearestHelper(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }

        if (Point.distance(n.pt, goal) < Point.distance(best.pt, goal)) {
            best = n;
        }

        //side[0] is goodside, side[1] is badside
        Node[] side = sideDecider(goal, n);

        best = nearestHelper(side[0], goal, best);

        if (pruningRule(goal, n, best)) {
            best = nearestHelper(side[1], goal, best);
        }

        return best;
    }

    //compares the goal point and a given node
    //return true if goal < n and false otherwise
    private boolean goalComparator(Point g, Node n) {

        //if n's orientation is vertical, compare x value
        if (n.orientation == VER) {
            return g.getX() < n.pt.getX();

            //if n's orientation is vertical, compare x value
        } else {
            return g.getY() < n.pt.getY();
        }
    }

    //returns an array of node, with 1st as good side, 2nd as bad side
    private Node[] sideDecider(Point g, Node n) {
        Node[] side = new Node[2];
        if (goalComparator(g, n)) {
            side[0] = n.left;
            side[1] = n.right;
        } else {
            side[0] = n.right;
            side[1] = n.left;
        }

        return side;
    }

    private boolean pruningRule(Point g, Node n, Node best) {
        if (n == null) {
            return false;
        }
        if (n.orientation == VER) {
            if (Point.distance(new Point(n.pt.getX(), g.getY()), g) < Point.distance(best.pt, g)) {
                return true;
            }
        } else {
            if (Point.distance(new Point(g.getX(), n.pt.getY()), g) < Point.distance(best.pt, g)) {
                return true;
            }
        }
        return false;
    }
}




//        best = nearestHelper(n.right, goal, best);
//
//        best = nearestHelper(n.left, goal, best);
