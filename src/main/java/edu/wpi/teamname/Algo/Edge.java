package edu.wpi.teamname.Algo;

import java.util.ArrayList;

public class Edge {

    private String edgeID;

    private String startNode;

    private String endNode;

    public Edge(String edgeID, String startNode, String endNode) {
        this.edgeID = edgeID;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public String getEdgeID() {
        return edgeID;
    }

    public String getStartNode() {
        return startNode;
    }

    public String getEndNode() {
        return endNode;
    }
}
