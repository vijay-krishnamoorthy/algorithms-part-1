import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private int ceiling;
    private int floor;
    private WeightedQuickUnionUF qc;
    private boolean[][] percolationChart;
    private int openedSites;

    public Percolation(int n){
        if(n<1) throw new IllegalArgumentException("Please enter a valid grid size");
        // setting the grid size as n
        size = n;
        // ceiling
        ceiling = 0;
        floor = n * n + 1;
        qc = new WeightedQuickUnionUF(n * n + 2);
        percolationChart = new boolean[n][n];
        openedSites = 0;
    }

    public void open(int row, int col){
        // check the boundary conditions
        if(row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Column or Row index is out of bound in open method");
        }
        // opening a site
        percolationChart[row-1][col-1] = true;
        // increasing the opened site count
        ++openedSites;
        // if the opened site is first row or in the ceiling then union the path
        if(row == 1)
            qc.union(findIndex(row,col), ceiling);
        // if the opened site is last row or in the floor then union the path
        if(row == size)
            qc.union(findIndex(row,col), floor);
        // if the row is in the middle somewhere, check the opened status of the top cell, if so, union the path
        if(row > 1 && isOpen(row-1, col))
            qc.union(findIndex(row,col) , findIndex(row-1,col));
        // if the row is in the middle somewhere, check the opened status of the bottom cell, if so, union the path
        if(row < size && isOpen(row+1, col))
            qc.union(findIndex(row,col), findIndex(row+1,col));
        // if the col is in the middle somewhere, check the opened status of the right cell, if so, union the path
        if(col > 1 && isOpen(row,col-1))
            qc.union(findIndex(row, col),findIndex(row,col-1));
        // if the col is in the middle somewhere, check the opened status of the left cell, if so, union the path
        if(col < size && isOpen(row,col+1))
            qc.union(findIndex(row, col),findIndex(row,col+1));
    }

    public int findIndex(int row,int col){
        // check the boundary conditions
        if(row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Column or Row index is out of bound in findIndex method");
        }
        return size * (row-1) + col;
    }

    public boolean isOpen(int row,int col){
        // check the boundary conditions
        if(row < 1 || row > size || col < 1 || col > size){
            throw new IllegalArgumentException("Column or Row index is out of bound in isOpen method");
        }
        return percolationChart[row-1][col-1];
    }

    public boolean isFull(int row, int col){
        // check the boundary conditions
        if(row < 1 || row > size || col < 1 || col > size){
            throw new IllegalArgumentException("Column or Row index is out of bound in isFull method");
        }
        // return the if the site is connected
        return qc.find(ceiling) == qc.find(findIndex(row,col));
    }

    public int numberOfOpenSites(){
        // return the no of sites opened
        return openedSites;
    }

    public boolean percolates(){
        // return the ceiling and floor are connected or not
        return qc.find(ceiling) == qc.find(floor);
    }

    public static void main(String args[]){

    }

}
