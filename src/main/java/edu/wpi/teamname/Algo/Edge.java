package edu.wpi.teamname.Algo;

import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.views.manager.Action;

import java.util.ArrayList;
import java.util.Objects;

public class Edge  {

    private String edgeID;

    private String startNode;

    private String endNode;

    public Edge(String edgeID, String startNode, String endNode) {
        this.edgeID = edgeID;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public void setEdgeID(String edgeID) {
        this.edgeID = edgeID;
    }

    public void setStartNode(String startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(String endNode) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return (startNode.equals(edge.startNode) && endNode.equals(edge.endNode)) || (startNode.equals(edge.endNode) && endNode.equals(edge.startNode));
    }

    @Override
    public int hashCode() {
        return Objects.hash(edgeID);
    }


}
