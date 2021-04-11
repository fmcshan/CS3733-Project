package edu.wpi.teamname.Algo;

/**
 * <h1>Stopwatch</h1>
 * Program for measuring runtime of functions
 * @author Emmanuel Ola
 */
public class Stopwatch {
    private final long start;

    public Stopwatch(){
        start = System.currentTimeMillis();
    }

    public double elapsedTime(){
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

}
