import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    int noOfTrials = 0;
    double[] trialRunResults;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n<=0 || trials <= 0)
            throw new IllegalArgumentException("Please enter a valid input");
        noOfTrials = trials;
        trialRunResults = new double[trials];
        for(int i=0;i<noOfTrials;i++){
            int openedSites = 0;
            Percolation p = new Percolation(n);
            while(!p.percolates()){
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if(!p.isOpen(row,col)){
                    p.open(row,col);
                    openedSites++;
                }
            }
            trialRunResults[i] = (double) openedSites / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(trialRunResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(trialRunResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - ( 1.96 * stddev() ) / Math.sqrt(noOfTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + ( 1.96 * stddev() ) / Math.sqrt(noOfTrials);
    }

    // test client (see below)
    public static void main(String[] args){

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n,trials);
        StdOut.println("Mean = " + stats.mean());
        StdOut.println("Stddev = " + stats.stddev());
        StdOut.println("95% Confidence Interval = " + stats.confidenceHi() + " , " + stats.confidenceLo());


    }
}
