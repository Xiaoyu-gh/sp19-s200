package hw2;

public class PercolationStatsLauncher {
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(10, 20, new PercolationFactory());
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }
}
