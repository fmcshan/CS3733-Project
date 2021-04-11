package edu.wpi.teamname.Algo;

import java.util.Comparator;

/**
 * <h1>Node Comparator</h1>
 * Implements the original Comparator class but specifically compares two nodes according to their AStarScore
 * @author Emmanuel Ola
 */
public class NodeComparator implements Comparator<Node> {
    /** Compares two nodes
     * @param a the first node to be compared
     * @param b the second node to be compared
     * @return -1 if the first node has a smaller AStarScore than the second,
     * 0 if they're equal, and 1 if the first node has a larger AStarScore
     */
    @Override
    public int compare(Node a, Node b) {
        return Double.compare(a.getAStarScore(), b.getAStarScore());
    }
}
