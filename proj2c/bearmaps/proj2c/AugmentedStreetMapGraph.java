package bearmaps.proj2c;

import bearmaps.hw4.WeightedEdge;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;
import bearmaps.proj2ab.WeirdPointSet;
import org.apache.commons.math3.linear.ArrayRealVector;

import java.lang.reflect.Array;
import edu.princeton.cs.algs4.TrieSET;
import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private List<Point> coordinates;
    private WeirdPointSet tree;
    private HashMap<Point, Node> map;

    private TrieSET placeNames;
    private HashMap<String, ArrayList<Node>> placeInfo;
    private HashMap<String, String> nameConvert;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
//         You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        coordinates = new ArrayList<>();
        map = new HashMap<>();

        placeNames = new TrieSET();
        placeInfo = new HashMap<>();
        nameConvert = new HashMap<>();


        for (int i = 0; i < nodes.size(); i++) {
            Node currNode = nodes.get(i);
            Point currPoint = new Point(currNode.lon(), currNode.lat());
            if (this.neighbors(currNode.id()).size() != 0) {
                coordinates.add(currPoint);
                map.put(currPoint, currNode);
            }


            if (currNode.name() != null) {
                String currName = currNode.name();
                String currNameLC = cleanString(currName);
                nameConvert.put(currNameLC, currName);
                if (!placeNames.contains(currNameLC)) {
                    placeNames.add(currNameLC);
                    ArrayList<Node> info = new ArrayList<>();
                    info.add(currNode);
                    placeInfo.put(currNameLC, info);
                } else {
                    ArrayList<Node> oldInfo = placeInfo.get(currNameLC);
                    ArrayList<Node> newInfo = new ArrayList<>();
                    newInfo.addAll(oldInfo);
//                    for (Node info : oldInfo) {
//                        newInfo.add(info);
//                    }
                    newInfo.add(currNode);
                    placeInfo.put(currName, newInfo);
                }
            }

        }

         tree = new WeirdPointSet(coordinates);
    }



    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {

        Point closestPoint = tree.nearest(lon, lat);
        return map.get(closestPoint).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String pre = cleanString(prefix);
        List<String> result = new ArrayList<>();
        for (String name: placeNames.keysWithPrefix(pre)) {
            result.add(nameConvert.get(name));
        }
        return result;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String locName = cleanString(locationName);
        List<Map<String, Object>> locs = new ArrayList<>();
        List<Node> nodes = placeInfo.get(locName);
        for (Node n : nodes) {
            locs.add(infoHelper(n));
        }
        return locs;
//        return new LinkedList<>();
    }

    private Map<String, Object> infoHelper(Node n) {
        Map<String, Object> result = new HashMap<>();
        result.put("lat", n.lat());
        result.put("lon", n.lon());
        result.put("name", n.name());
        result.put("id", n.id());
        return result;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
