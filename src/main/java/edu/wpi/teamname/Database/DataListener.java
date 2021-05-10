package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;

import java.util.ArrayList;

public interface DataListener {



    void nodesSet(ArrayList<Node> _nodes);
    void nodeAdded(Node _node);
    void nodeEdited(Node _node);
    void nodeRemoved(Node _node);

    void edgesSet(ArrayList<Edge> _edges);
    void edgeAdded(Node _node);
    void edgeEdited(Node _node);
    void edgeRemoved(Node _node);
}
