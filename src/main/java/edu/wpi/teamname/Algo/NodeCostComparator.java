package edu.wpi.teamname.Algo;

import java.util.Comparator;

public class NodeCostComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        return Double.compare(o1.getCostSoFar(), o2.getCostSoFar());
    }
}
