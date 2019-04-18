package bearmaps.test;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestAutocomplete {

    private static final String OSM_DB_PATH = "../library-sp19/data/proj2c_xml/berkeley-2019.osm.xml";
    private static AugmentedStreetMapGraph graph;
    private static boolean initialized = false;

    @Before
    public void setUp() throws Exception {
        if (initialized) {
            return;
        }
        graph = new AugmentedStreetMapGraph(OSM_DB_PATH);
        initialized = true;

    }

    @Test
    public void testPrefix() {
        String prefix1 = "u";
        List<String> actual = graph.getLocationsByPrefix(prefix1);
        List<String> expected = List.of("Upper Playground", "Upper Rockridge", "Upper Edwards 591 Dam", "UPS Store",
                "Uptown Market", "U Cafe", "UA Berkeley 7", "Urban Fitness Oakland", "Urban Ore Donations",
                "Urban Outfitters", "Urbann Turbann", "US Post Office", "US Marine Corps", "USAGain", "USE Credit Union",
                "Used Computers", "UC - Parking and Transportation", "UC Berkeley Campus Police",
                "UC Berkeley Visitor Center", "UC Berkeley Institute of Transportation Studies (ITS)", "UC Storage",
                "UCB Parking", "Udupi Palace", "Uzen Japanese Cuisine", "Underwood", "Unique Styling Center",
                "UNIQLO Bay Street", "United Postal Business Center", "United States Post Office",
                "University Press Books", "University & MLK (Valero Gas)", "University Av & Acton St",
                "University Av & Bonar St", "University Av & Sacramento St", "University Av & San Pablo Av",
                "University Av & Shattuck Av", "University Av & California St", "University Av & Curtis St",
                "University Av & 4th", "University Av & 6th", "University Av & 9th", "University Av & Grant St",
                "University Av & Martin Luther King Jr Way", "University Av & Mc Gee St",
                "University Avenue / Sacramento Street - Chevron", "University Avenue & Martin Luther King Jr Way",
                "University Avenue Cooperative Homes", "University Avenue/MLK Jr Way - Valero",
                "University Reservoir", "University Christian Church of Berkeley", "University Eye Center",
                "University Hair Care", "University YWCA", "University Lutheran Chapel","Union Bank");

        System.out.println("The following are not included in the actual output:");
        for(String i : expected) {
            if (!actual.contains(i)){
                System.out.println(i);
            }
        }

        System.out.println("The following are not included in the Autograder output:");
        for(String i : actual) {
            if (!expected.contains(i)) {
                System.out.println(i);
            }
        }

    }

    @Test
    public void testLocationbyPre() {
        String name = "telegraph av  stuart st";
        List<Map<String, Object>> actual = graph.getLocations(name);
        for (Map<String, Object> i : actual) {
            System.out.println("name=" + i.get("name"));
            System.out.println("lon=" + i.get("lon"));
            System.out.println("lat=" + i.get("lat"));
            System.out.println("id=" + i.get("id"));
        }
    }
}
