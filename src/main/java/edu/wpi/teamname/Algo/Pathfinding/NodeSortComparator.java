package edu.wpi.teamname.Algo.Pathfinding;

import edu.wpi.teamname.Algo.Node;

import java.util.Comparator;

public class NodeSortComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        return String.CASE_INSENSITIVE_ORDER.compare(o1.getLongName(), o2.getLongName());
    }
}
