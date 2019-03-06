package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int n;
    private int t;
    private PercolationFactory pfac;
    private double[] sampleRatio;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Negative Input!");
        }
        n = N;
        t = T;
        pfac = pf;
        sampleRatio = percSimulator();

    }

    private double percSingleSimulator(Percolation perc) {
            while (!perc.percolates()) {
                int x = StdRandom.uniform(n);
                int y = StdRandom.uniform(n);
                perc.open(x, y);
            }
            double percNum = Double.valueOf(perc.numberOfOpenSites());
            return percNum / Double.valueOf(n * n);
    }

    private double[] percSimulator() {
        double[] arr = new double[t];
        for (int i = 0; i < t; i++) {
            arr[i] = percSingleSimulator(pfac.make(n));
        }
        return arr;
    }

    public double mean() {
        return StdStats.mean(sampleRatio);
    }

    public double stddev() {
        return StdStats.stddev(sampleRatio);
    }

    public double confidenceLow() {
        double confLow = mean() - 1.96 * stddev() / Math.sqrt(t);
        return confLow;
    }

    public double confidenceHigh() {
        double confHigh = mean() + 1.96 * stddev() / Math.sqrt(t);
        return confHigh;
    }
}
