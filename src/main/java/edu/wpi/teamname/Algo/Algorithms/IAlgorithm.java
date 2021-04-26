package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;

import java.util.ArrayList;

/**
 * <h1>Algorithm Interface</h1>
 * @author Emmanuel Ola
 */
public interface IAlgorithm {

    /**
     * Returns a list of nodes representing a path from one node to another
     * @return list of nodes representing the path from one node to another
     */
    public ArrayList<Node> getPath();
}
