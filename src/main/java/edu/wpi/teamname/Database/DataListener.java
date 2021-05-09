package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;

import java.util.ArrayList;

public interface DataListener {
    void nodesSet(ArrayList<Node> _nodes);
    void edgesSet(ArrayList<Edge> _edges);
}
