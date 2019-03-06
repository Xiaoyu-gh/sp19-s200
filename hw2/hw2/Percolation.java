package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private boolean[][] grid;
    private WeightedQuickUnionUF data;
    private WeightedQuickUnionUF skyOnly;
    private int topSite;
    private int bottomSite;
    private int openSite;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Negative Input!");
        }
        n = N;
        grid = new boolean[N][N];
        data = new WeightedQuickUnionUF(N * N + 2);
        skyOnly = new WeightedQuickUnionUF(N * N + 1);
        topSite = N * N;
        bottomSite = N * N + 1;
        openSite = 0;
    }

    public void open(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IllegalArgumentException("Input out of range!");
        }

        int index = xyto1D(row, col);
        int left = xyto1D(row, col - 1);
        int right = xyto1D(row, col + 1);
        int up = xyto1D(row - 1, col);
        int down = xyto1D(row + 1, col);

        boolean leftExist = (col != 0);
        boolean rightExist = (col != (n - 1));
        boolean upExist = (row != 0);
        boolean downExist = (row != (n - 1));

        if (!grid[row][col]) {
            grid[row][col] = true;
            openSite++;
            if (row == 0) {
                data.union(index, topSite);
                skyOnly.union(index, topSite);
            }
            if (row == n - 1) {
                data.union(index, bottomSite);
            }
            if (leftExist && grid[row][col - 1]) {
                data.union(index, left);
                skyOnly.union(index, left);
            }
            if (rightExist && grid[row][col + 1]) {
                data.union(index, right);
                skyOnly.union(index, right);
            }
            if (upExist && grid[row - 1][col]) {
                data.union(index, up);
                skyOnly.union(index, up);
            }
            if (downExist && grid[row + 1][col]) {
                data.union(index, down);
                skyOnly.union(index, down);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
            throw new IllegalArgumentException("Input out of range!");
        }

        if (grid[row][col]) {
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
            throw new IllegalArgumentException("Input out of range!");
        }

        int index = xyto1D(row, col);
        if (data.connected(index, topSite) && skyOnly.connected(index, topSite)) {
            return true;
        }
        return false;

    }

    public int numberOfOpenSites() {
        return openSite;
    }

    public boolean percolates() {
        if (data.connected(topSite, bottomSite)) {
            return true;
        }
        return false;
    }

    private int xyto1D(int row, int col) {
        return row * n + col;
    }

    public static void main(String[] args) {

    }

}
