package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;

import java.util.ArrayList;

/**
 * <h1>Graph Class</h1>
 * Used to support Depth-First search, and breadth-first search
 * @author Emmanuel Ola
 */
public class Graph {
    /**
     * List of edges in our graph
     */
    private ArrayList<Edge> edges;
    /**
     * List of nodes in our graph
     */
    private ArrayList<Node> nodes;

    /**
     * Create a new graph by loading all the edges, and nodes you want in the graph
     * @param edges list of edges in the graph
     * @param nodes list of nodes in the graph
     */
    public Graph(ArrayList<Edge> edges, ArrayList<Node> nodes) {
        this.edges = edges;
        this.nodes = nodes;
    }

    /**
     * Checks if node a has an edge to node b. Since edges are bidirectional, you can expect node b to also
     * have an edge to node a
     * @param a the start node
     * @param b the end node
     * @return true if a has an edge to node b
     */
    public boolean adj(Node a, Node b){
        return a.getEdges().contains(b);
    }

    /**
     * Returns a list of nodes that the provided node has an edge to
     * @param node the node whose neighbors you want
     * @return list of nodes with an edge from the provided node
     */
    public ArrayList<Node> neighbors (Node node){
        return node.getEdges();
    }

    /**
     * Adds a node to our graph
     * @param node the node you want to add to our graph
     */
    public void addNode(Node node){
        nodes.add(node);
    }

    /**
     * Removes a node from our graph
     * @param node the node you want to remove from the graph <p><b>Note: This has to be the exact same node
     *             object as the one provided in the list of nodes passed to the graph constructor</b>
     */
    public void removeNode(Node node){
        nodes.remove(node);
    }

    /**
     * Adds an edge between two nodes
     * @param a the start node
     * @param b the end node
     */
    public void addEdge(Node a, Node b){
        a.addEdge(b);
    }

    /**
     * Removes the edge between two nodes
     * @param a the start node <p><b>Note: This has to be the exact same node
     *          object as the one provided in the list of nodes passed to the graph constructor</b>
     * @param b the end node <p><b>Note: This has to be the exact same node
     *          object as the one provided in the list of nodes passed to the graph constructor</b>
     */
    public void removeEdge(Node a, Node b){
        ArrayList<Node> newAEdges = a.getEdges();
        newAEdges.remove(b);
        ArrayList<Node> newBEdges = b.getEdges();
        newBEdges.remove(b);
        a.setEdges(newAEdges);
        b.setEdges(newBEdges);
    }
}
