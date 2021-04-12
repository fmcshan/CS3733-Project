package edu.wpi.teamname.Algo;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * <h1>Node</h1>
 * This class contains information about each node
 * @author Emmanuel Ola, Bryan Gass, Frank McShan
 */
public class Node {
    /**
     * Hashtable of nodes connected to this node
     */
    private ArrayList<Node> edges;
    /**
     * The Node's ID
     */
    private String nodeID; //Node's ID
    /**
     * The parent node. Useful for backtracking
     */
    private Node parent; //for backtracking
    /**
     * The Node's full name
     */
    private String fullName;
    /**
     * The Node's short name
     */
    private String shortName;
    /**
     * THe floor the node is located on
     */
    private String floor;
    /**
     * The building the node is located in
     */
    private String building;
    /**
     * The nodeType for the node
     */
    private String nodeType;
    /**
     * Boolean showing if the node has been visited. Useful for Graph traversal
     */
    public boolean visitedFlag;
    /**
     * X coordinate value
     */
    private int x;
    /**
     * Y coordinate value
     */
    private int y;
    /**
     * Distance to node
     */
    private double heuristic;
    /**
     * The cost of the cheapest path to this node currently known. The default value is positive infinity
     */
    private double costSoFar;
    /**
     * The sum of the costSoFar and the heuristic. The default value is positive infinity
     */
    private double AStarScore;

    /**
     * Minimal constructor that loads the x, and y coordinates, as well as the edges connected to the constructor
     *
     * @param nodeID nodeID for this node
     * @param x      x value of the node's location
     * @param y      y value of the node's location
     * @param edges  Hashtable representing node and its associated cost
     */
    public Node(String nodeID, int x, int y, ArrayList<Node> edges) {
        //coordinates
        this.x = x;
        this.y = y;

        //edges
        this.edges = edges;

        //nodeID
        this.nodeID = nodeID;
        this.costSoFar = Double.POSITIVE_INFINITY;
        this.AStarScore = Double.POSITIVE_INFINITY;
    }

    /**Minimal constructor that loads the x, and y coordinates without loading the edges
     * @param nodeID nodeID for this node
     * @param x x value of the node's location
     * @param y y value of the node's location
     */
    public Node(String nodeID, int x, int y) {
        this.nodeID = nodeID;
        this.x = x;
        this.y = y;
        this.edges = new ArrayList<Node>();
        this.costSoFar = Double.POSITIVE_INFINITY;
        this.AStarScore = Double.POSITIVE_INFINITY;
    }

    /**
     * Overloaded constructor that loads all of the info from the original constructor
     * as well as other extraneous info related to a node
     * @param nodeID    nodeID for this node
     * @param fullName the full name of the node
     * @param shortName the short name of the node
     * @param floor the floor the node is located on
     * @param building the building the node is located in
     * @param nodeType the node's type
     * @param x         x value of the node's location
     * @param y         y value of the node's location
     */
    public Node(String nodeID, int x, int y, String floor, String building, String nodeType, String fullName, String shortName) {
        //coordinates
        this.x = x;
        this.y = y;

        //edges
        this.edges = new ArrayList<>();

        //nodeInfo
        this.nodeID = nodeID;
        this.fullName = fullName;
        this.shortName = shortName;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;

        this.heuristic = 0;
        this.visitedFlag = false;
        this.costSoFar = Double.POSITIVE_INFINITY;
        this.AStarScore = Double.POSITIVE_INFINITY;
    }

    /**
     * @return the sum of the cost so far to this node and the heuristic
     */
    public double getAStarScore() {
        return AStarScore;
    }

    /**
     * Updates the AStarScore for this node by summing the heuristic and the costSoFar to this node.
     */
    public void updateAStarScore() {
        this.AStarScore = this.heuristic + costSoFar;
    }

    /**
     * @return the cost of the cheapest path to this node so far known
     */
    public double getCostSoFar() {
        return costSoFar;
    }

    /**
     * Changes the value of the cost of the cheapest path to this node
     * @param costSoFar the cost of the cheapest path to this node fron the start
     */
    public void setCostSoFar(double costSoFar) {
        this.costSoFar = costSoFar;
    }

    /**
     * @return hashtable that contains the x and y coordinates for the given node
     */
    public Hashtable<String, Integer> getCoords() {
        Hashtable<String, Integer> xyCoord = new Hashtable<String, Integer>();
        xyCoord.put("x", this.x);
        xyCoord.put("y", this.y);

        return xyCoord;
    }

    /** <b>Note:</b> You have to call Node.getNodeInfo().get() with the info you're trying to receive to retrieve it
     * from the hashtable
     * @return hashtable that contains the nodeID, floor, building, nodeType, fullName and shortName for the given node
     */
    public Hashtable<String, String> getNodeInfo() {
        Hashtable<String, String> nodeInfo = new Hashtable<String, String>();
        nodeInfo.put("nodeID", this.nodeID);
        nodeInfo.put("floor", this.floor);
        nodeInfo.put("building", this.building);
        nodeInfo.put("nodeType", this.nodeType);
        nodeInfo.put("fullName", this.fullName);
        nodeInfo.put("shortName", this.shortName);

        return nodeInfo;
    }

    /**
     * Retrieves the heuristic value
     *
     * @return Distance to node
     */
    public double getHeuristic() {
        return heuristic;
    }

    /**
     * Sets the heuristic value
     * @param heuristic the heuristic value of this node
     */
    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Changes the visitedFlag to true when this node has been visited
     */
    public void visited() {
        this.visitedFlag = true;
    }

    /**
     * Retrieves the Parent for this node
     *
     * @return Parent node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Changes the parent node for this node and sets hasParent to true
     *
     * @param parent the parent node
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Adds an edge to the current node
     * @param aNode Node used to add an edge from current Node
     *
     */
    public void addEdge(Node aNode) {
        this.edges.add(aNode);
        aNode.edges.add(this);
    }

    /**
     * Retrieves all the nodes connected to this node
     *
     * @return An ArrayList of nodes connected to this node
     */
    public ArrayList<Node> getEdges() {
        return this.edges;
    }

    /**
     * gets the nodeID for this node
     *
     * @return the nodeID
     */
    public String getNodeID() {
        return nodeID;
    }
}
