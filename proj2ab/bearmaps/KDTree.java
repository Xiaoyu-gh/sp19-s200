package bearmaps;
import java.util.List;


/**
 * author: Shirley Zhou
 * source: Joshua Hug pseudowalk through video
 */
public class KDTree implements PointSet{
    private static final boolean hor = false;
    private static final boolean ver = true;

    private Node root;
    private int size;

    private class Node extends Comparable<Node>{
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
            size++;
            return new Node(p, ori);
        }
        //add node only if x and y does not equal to the coordinate compared
        if (n.pt.getX() != p.getX() || n.pt.getY() != p.getY()) {

            //if orientation is vertical, compare Y coordinates
            if (n.orientation == ver) {

                if (p.getX() < n.pt.getX()) {
                    n.left = addHelper(p, hor, n.left);
                } else {
                    n.right = addHelper(p, hor, n.right);
                }

            } else {
                if (p.getY() < n.pt.getY()) {
                    n.left = addHelper(p, ver, n.left);
                } else {
                    n.right = addHelper(p, ver, n.right);
                }
            }
        }
        return n;
    }

    private boolean orientationHelper(int index) {
        if (index % 2 == 0) {
            return ver;
        }
        return hor;
    }


    public KDTree(List<Point> points) {
        myPts = points;
        size = 1;
        root = new Node (points.get(0), ver);
        for (int i = 1; i < points.size(); i++) {
            add(points.get(i), hor);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        return nearestHelper(root, new Point(x, y), root).pt;

    }

    public Node nearestHelper(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }

        if (Point.distance(n.pt, goal) < Point.distance(best.pt, goal)) {
            best = n;
        }

        best = nearestHelper(n.right, goal, best);

        best = nearestHelper(n.left, goal, best);

        return best;
    }
}
