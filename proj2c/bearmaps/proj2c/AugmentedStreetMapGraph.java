package bearmaps.proj2c;

import bearmaps.hw4.WeightedEdge;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;
import bearmaps.proj2ab.WeirdPointSet;
import org.apache.commons.math3.linear.ArrayRealVector;

import java.lang.reflect.Array;
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
    private MyTrieSet placeNames;
    private HashMap<String, List<Node>> placeInfo;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
//         You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        coordinates = new ArrayList<>();
        map = new HashMap<>();
        placeNames = new MyTrieSet();
        placeInfo = new HashMap<>();

        for (int i = 0; i < nodes.size(); i++) {
            Node currNode = nodes.get(i);
            Point currPoint = new Point(currNode.lon(), currNode.lat());
            if (this.neighbors(currNode.id()).size() != 0) {
                coordinates.add(currPoint);
                map.put(currPoint, currNode);
            }

            if (currNode.name() != null) {
                String currName = currNode.name();
                if (placeNames.contains(currName)) {
                    placeNames.add(currName);
                    List<Node> info = new ArrayList<>();
                    placeInfo.put(currName, info);
                }
                List<Node> info = placeInfo.get(currName);
                info.add(currNode);
                placeInfo.put(currName, info);
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
        return placeNames.keysWithPrefix(pre);
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

        return new LinkedList<>();
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


    private class MyTrieSet {

        private Node root;

        private class Node {
            char value;
            boolean atEnd;
            HashMap<Character, Node> kids;

            Node(char c) {
                this.value = c;
                this.atEnd = false;
                this.kids = new HashMap<>();
            }
        }

        public MyTrieSet() {

            this.root = new Node('\0');
        }


        public void clear() {
            this.root = new Node('\0');

        }

        public boolean contains(String key) {
            if (key == null || key.length() == 0) {
                return true;
            }

            Node currNode = root;
            for (int i = 0; i < key.length(); i++) {
                char curr = key.charAt(i);
                // if the current node has this letter, then go to that node and
                // don't create new node
                if (!currNode.kids.containsKey(curr)) {
                    return false;
                }
                currNode = currNode.kids.get(curr);
            }
            return currNode.atEnd;
        }

        public void add(String key) {
            if (key == null || key.length() == 0) {
                return;
            }
            Node currNode = root;
            for (int i = 0; i < key.length(); i++) {
                char curr = key.charAt(i);
                // if the current node has this letter, then go to that node and
                // don't create new node
                if (!currNode.kids.containsKey(curr)) {
                    Node newNode = new Node(curr);
                    currNode.kids.put(curr, newNode);
                }
                currNode = currNode.kids.get(curr);
            }
            currNode.atEnd = true;
        }

        public List<String> keysWithPrefix(String prefix) {
            LinkedList<String> results = new LinkedList<>();
            //traverse the trie until reaching the end;
            if (prefix == null || prefix.length() == 0) {
                return results;
            }

            Node currNode = root;
            for (int i = 0; i < prefix.length(); i++) {
                char curr = prefix.charAt(i);
                // if the current node has this letter, then go to that node and
                // don't create new node
                if (!currNode.kids.containsKey(curr)) {
                    return results;
                }
                currNode = currNode.kids.get(curr);
            }

            prefixHelper(currNode, prefix, results);
            return results;
        }

        private void prefixHelper(Node curr, String prefix, LinkedList<String> results) {
            if (curr.atEnd) {
                results.add(prefix);
            }
            for (Character c : curr.kids.keySet()) {
                Node kid = curr.kids.get(c);
                prefixHelper(kid, prefix + c, results);
            }
        }

        public String longestPrefixOf(String key) {
            String result = "";
            if (key == null || key.length() == 0) {
                return result;
            }

            Node currNode = root;
            for (int i = 0; i < key.length(); i++) {
                char curr = key.charAt(i);
                // if the current node has this letter, then go to that node and
                // don't create new node
                if (!currNode.kids.containsKey(curr)) {
                    return result;
                }
                result = result + Character.toString(curr);
                currNode = currNode.kids.get(curr);
            }
            return result;
        }
    }




}
