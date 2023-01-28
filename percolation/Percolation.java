/* *****************************************************************************
 *  Name:              Travis Hamilton
 *  Last modified:     January 27th, 2023
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF percolationUF;
    private final WeightedQuickUnionUF fullUF;
    private final int numRows;
    private final int numSites;
    private boolean[][] openSites;
    private int numOpenSites = 0;
    private final int virtualTop;
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Non-negative number of rows are not allowed");
        this.numRows = n;
        numSites = n * n;
        openSites = new boolean[n][n];
        percolationUF = new WeightedQuickUnionUF(n * n + 2);
        fullUF = new WeightedQuickUnionUF(n * n + 1);
        virtualBottom = numSites + 1;
        virtualTop = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkRowColumnInput(row, col);
        if (isOpen(row, col)) return;
        openSites[row - 1][col - 1] = true;
        numOpenSites++;
        int index = getIndexOfRowColumn(row, col);
        if (row == 1) {
            percolationUF.union(virtualTop, index);
            fullUF.union(virtualTop, index);
        }
        if (row == numRows)
            percolationUF.union(virtualBottom, index);
        if (isRowOrColumnInbounds(row + 1) && isOpen(row + 1, col)) {
            percolationUF.union(getIndexOfRowColumn(row + 1, col), index);
            fullUF.union(getIndexOfRowColumn(row + 1, col), index);
        }
        if (isRowOrColumnInbounds(row - 1) && isOpen(row - 1, col)) {
            percolationUF.union(getIndexOfRowColumn(row - 1, col), index);
            fullUF.union(getIndexOfRowColumn(row - 1, col), index);
        }
        if (isRowOrColumnInbounds(col + 1) && isOpen(row, col + 1)) {
            percolationUF.union(getIndexOfRowColumn(row, col + 1), index);
            fullUF.union(getIndexOfRowColumn(row, col + 1), index);
        }
        if (isRowOrColumnInbounds(col - 1) && isOpen(row, col - 1)) {
            percolationUF.union(getIndexOfRowColumn(row, col - 1), index);
            fullUF.union(getIndexOfRowColumn(row, col - 1), index);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkRowColumnInput(row, col);
        return openSites[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return fullUF.find(virtualTop) == fullUF.find(getIndexOfRowColumn(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolationUF.find(virtualTop) == percolationUF.find(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        System.out.println("Open (1,1).");
        percolation.open(1, 1);
        System.out.println("Is (1,1) open? " + percolation.isOpen(1, 1));
        System.out.println("Is (1,1) full? " + percolation.isFull(1, 1));
        System.out.println("Is (1,2) open? " + percolation.isOpen(1, 2));
        System.out.println("Is (1,2) full? " + percolation.isFull(1, 2));
        System.out.println("Open (1,2).");
        percolation.open(1, 2);
        System.out.println("Is (1,2) open? " + percolation.isOpen(1, 2));
        System.out.println("Is (1,2) full? " + percolation.isFull(1, 2));
        System.out.println("Open (3,2).");
        percolation.open(3, 2);
        System.out.println("Is (3,2) open? " + percolation.isOpen(3, 2));
        System.out.println("Is (3,2) full? " + percolation.isFull(3, 2));
        System.out.println("Open (2,2).");
        percolation.open(2, 2);
        System.out.println("Is (2,2) open? " + percolation.isOpen(2, 2));
        System.out.println("Is (2,2) full? " + percolation.isFull(2, 2));
        System.out.println("Is (3,2) full? " + percolation.isFull(3, 2));
        System.out.println("Open (4,2).");
        percolation.open(4, 2);
        System.out.println("Does it percolate? " + percolation.percolates());
        System.out.println("Open (5,2).");
        percolation.open(5, 2);
        System.out.println("Does it percolate? " + percolation.percolates());
    }

    // assigns an index to each row and column starting from upper left hand
    // corner and moving left to right across the rows. The zeroth index is
    // reserved for the virtual top node and the last index is reserved for
    // the virtual bottom node
    private int getIndexOfRowColumn(int row, int col) {
        checkRowColumnInput(row, col);
        return col + numRows * (row - 1);
    }

    private void checkRowColumnInput(int row, int column) {
        if (!isRowOrColumnInbounds(row))
            throw new IllegalArgumentException("Row out of bounds.");
        if (!isRowOrColumnInbounds(column))
            throw new IllegalArgumentException("Column out of bounds.");
    }

    private boolean isRowOrColumnInbounds(int rowOrColumn) {
        return rowOrColumn <= numRows && rowOrColumn > 0;
    }

}
