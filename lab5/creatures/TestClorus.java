package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;


/**
 * A test file for the Clorus class
 *
 * @author Shirley Zhou
 */
public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(1.74);
        Plip p = new Plip(2);

        assertEquals(1.74, c.energy(), 0);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.71, c.energy(), 0);
        c.move();
        assertEquals(1.68, c.energy(), 0);
        c.stay();
        assertEquals(1.67, c.energy(), 0);
        c.stay();
        assertEquals(1.66, c.energy(), 0);
        c.attack(p);
        assertEquals(3.66,c.energy(),0);
    }

    @Test
    public void testReplicate() {
        Clorus c1 = new Clorus(2.5);
        Clorus c2 = c1.replicate();
        Clorus c3 = c1;
        assertEquals(c1.energy(), c2.energy(), 0);
        assertEquals(c1,c3);
        assertNotEquals(c1,c2);
    }

    /**
     * @source: TestPlip testChoose()
     */

    @Test
    public void testAction(){

        //Test 1a: No empty space - impassible --> A good clorus should stay
        Clorus c = new Clorus(1.8);
        HashMap<Direction, Occupant> impass = new HashMap<Direction, Occupant>();
        impass.put(Direction.TOP, new Impassible());
        impass.put(Direction.BOTTOM, new Impassible());
        impass.put(Direction.LEFT, new Impassible());
        impass.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(impass);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        //Test 1b: No empty space - Pilp present --> A good clorus should stay

        Plip p1 = new Plip(1);
        HashMap<Direction, Occupant> notEmptyButPilp = new HashMap<Direction, Occupant>();
        notEmptyButPilp.put(Direction.TOP, new Impassible());
        notEmptyButPilp.put(Direction.BOTTOM, p1);
        notEmptyButPilp.put(Direction.LEFT, new Impassible());
        notEmptyButPilp.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(notEmptyButPilp);
        expected = new Action(Action.ActionType.STAY);
        Action unexpected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        assertEquals(expected, actual);
        assertNotEquals(unexpected, actual);

        //Test 2a: 1 empty 1 pilp --> A good clorus should attack

        HashMap<Direction, Occupant> emptyOnePlip = new HashMap<Direction, Occupant>();
        emptyOnePlip.put(Direction.TOP, new Empty());
        emptyOnePlip.put(Direction.BOTTOM, new Impassible());
        emptyOnePlip.put(Direction.LEFT, p1);
        emptyOnePlip.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(emptyOnePlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.LEFT);

        assertEquals(expected, actual);


        //Test 3: 1 empty no pilp, energy >=1 --> A good clorus should replicate
        HashMap<Direction, Occupant> oneEmpty = new HashMap<Direction, Occupant>();
        oneEmpty.put(Direction.TOP, new Impassible());
        oneEmpty.put(Direction.BOTTOM, new Impassible());
        oneEmpty.put(Direction.LEFT, new Impassible());
        oneEmpty.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(oneEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.RIGHT);

        assertEquals(expected, actual);

        //Test 4: 1 empty no pilp, energy <1 --> A good clorus should move
        Clorus c2 = new Clorus(0.6);

        actual = c2.chooseAction(oneEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.RIGHT);
        unexpected = new Action(Action.ActionType.REPLICATE, Direction.RIGHT);

        assertEquals(expected, actual);
        assertNotEquals(unexpected, actual);
    }
}
