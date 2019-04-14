package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private HashMap<Vertex, Double> heuristics;
    private DoubleMapPQ<Vertex> fringe;

    private List<WeightedEdge<Vertex>> neighbor;

    private SolverOutcome outcome;
    private List<Vertex> solution;
    private int numDequeue;
    private double sumWeight;
    private double expTime;


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {

        Stopwatch exp = new Stopwatch();

        //create distTo, edgeTo, heuristics map
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        heuristics = new HashMap<>();

        distTo.put(start, 0.0);
        neighbor = input.neighbors(start);
        heuristics.put(start, heuHelper(input, start, end));


        //create and initialize a PQ object for fringe
        fringe = new DoubleMapPQ<>();
        fringe.add(start, heuristics.get(start));

        while (fringe.size() != 0) {
            Vertex currV = fringe.getSmallest();
            if (currV.equals(end)) {
                break;
            }
            fringe.removeSmallest();
            numDequeue += 1;
            neighbor = input.neighbors(currV);
            relax(currV, end, neighbor, input);
            expTime = exp.elapsedTime();
            if (expTime > timeout) {
                break;
            }
        }

        expTime = exp.elapsedTime();
        solution = new ArrayList<>();

        if (expTime > timeout) {
            outcome = SolverOutcome.TIMEOUT;
            sumWeight = 0;
        } else if (fringe.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
            sumWeight = 0;
        } else {
            outcome = SolverOutcome.SOLVED;
            sumWeight = distTo.get(end);
            solution = solutionHelper(start, end);
        }
    }

    private List<Vertex> solutionHelper(Vertex start, Vertex end) {
        Vertex curr = end;
        solution = new ArrayList<>();
        solution.add(end);
        while (!curr.equals(start)) {
            curr = edgeTo.get(curr);
            solution.add(0, curr);
        }
        return solution;
    }

    private double heuHelper(AStarGraph<Vertex> g, Vertex start, Vertex end) {
        return g.estimatedDistanceToGoal(start, end);
    }

    private void relax(Vertex v, Vertex end, List<WeightedEdge<Vertex>> l,
                       AStarGraph<Vertex> input) {

        //for each neighbor of vertex v
        // update distTo, edgeTo, heuristics, and fringe
        for (WeightedEdge<Vertex> e : l) {
            Vertex curr = e.to();
            Double newDist = e.weight() + distTo.get(v);
            if (!distTo.containsKey(curr)) {
                edgeTo.put(curr, v);
                Double heu = heuHelper(input, curr, end);
                heuristics.put(curr, heu);
                distTo.put(curr, newDist);
                fringe.add(curr, heu + newDist);
            }
            Double currheu = heuristics.get(curr);
            if (newDist < distTo.get(curr)) {
                distTo.replace(curr, newDist);
                if (!fringe.contains(curr)) {
                    fringe.add(curr, currheu + newDist);
                } else {
                    fringe.changePriority(curr, currheu + newDist);
                }
            }
        }

    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return sumWeight;
    }

    @Override
    public int numStatesExplored() {
        return numDequeue;
    }

    @Override
    public double explorationTime() {
        return expTime;
    }
}
