package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

/**
 * An implementation of Pilp's archenemy,
 * a fierce blue-colored predator who enjoys devouring pilps.
 *
 * @author Shirley Zhou
 */

public class Clorus extends Creature {

    private int g;
    private int r;
    private int b;
    private double energy;

    public Clorus (double e) {
        super("clorus");
        g = 0;
        r = 34;
        b = 231;
        energy = e;
    }

    public Color color() {
        return color(r, g, b);
    }

    public void move() {
        this.energy -= 0.03;
        if (this.energy < 0) {
            this.energy = 0;
        }
    }

    public void attack(Creature o) {
        this.energy += o.energy();
    }

    public Clorus replicate() {
        Clorus offSpring = new Clorus(this.energy/2);
        this.energy = offSpring.energy;
        offSpring.r = this.r;
        offSpring.g = this.g;
        offSpring.b = this.b;
        return offSpring;
    }

    public void stay() {
        this.energy -= 0.01;
        if (this.energy <0) {
            this.energy = 0;
        }
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {

        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        boolean anyPlip = false;

        //Rule 1: if no empty squares, stay.
        for (Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {
            Direction key = entry.getKey();
            Occupant value = entry.getValue();
            if (value.name() == "empty") {
                emptyNeighbors.add(key);
            } else if (value.name() == "plip") {
                plipNeighbors.add(key);
                anyPlip = true;
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        //Rule 2: if Pilps are seen, attack one of them randomly.
        if (anyPlip) {
            Random ind = new Random();
            int randInd = ind.nextInt(plipNeighbors.size());
            Direction[] plipNeighborsArray = plipNeighbors.toArray(new Direction[plipNeighbors.size()]);
            Direction dir = plipNeighborsArray[randInd];
            return new Action(Action.ActionType.ATTACK, dir);
        }

        //Rule 3: if Clorus.energy >= 1, then replicate to a random empty square.
        if (this.energy >= 1) {
            Random ind = new Random();
            int randInd = ind.nextInt(emptyNeighbors.size());
            Direction[] emptyNeighborsArray = emptyNeighbors.toArray(new Direction[emptyNeighbors.size()]);
            Direction dir = emptyNeighborsArray[randInd];
            return new Action(Action.ActionType.REPLICATE, dir);
        }

        //Rule 4: if none of the above is true, move to a random empty square/
        Random ind = new Random();
        int randInd = ind.nextInt(emptyNeighbors.size());
        Direction[] emptyNeighborsArray = emptyNeighbors.toArray(new Direction[emptyNeighbors.size()]);
        Direction dir = emptyNeighborsArray[randInd];
        return new Action(Action.ActionType.MOVE, dir);
    }

    public double energy() {
        return energy;
    }
}
