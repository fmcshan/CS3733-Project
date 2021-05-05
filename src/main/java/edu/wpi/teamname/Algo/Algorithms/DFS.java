package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.Parser;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

import java.util.ArrayList;

/**
 * <h1>Depth-First Search Algorithm</h1>
 * Uses depth-first search for pathfinding operations
 * @author Emmanuel Ola
 */
public class DFS extends Algorithm {
    private Node start;
    private Node goal;
    private ArrayList<Node> path;


    /**
     * Constructor for DFS that takes in a list of nodes, and the start and goal node
     * @param nodes List of nodes which contains the start and goal nodes
     * @param start the start node
     * @param goal the goal node
     */
    public DFS(ArrayList<Node> nodes, Node start, Node goal) {
        super(nodes, start, goal);
        this.resetNodes(nodes);
        this.start = start;
        this.goal = goal;
        this.path = new ArrayList<>();
        this.process(start);
    }

    public DFS(){}

    /**
     * Resets the visited flag of all the nodes in the provided list of nodes
     * @param nodes List of nodes containing the start and goal node
     */
    public void resetNodes(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            node.visitedFlag = false;
        }
    }

    public void loadNodes(ArrayList<Node> nodes, Node start, Node goal){
        this.nodes = nodes;
        this.start = start;
        this.goal = goal;
    }

    /**
     * Returns a list of nodes
     * @return
     */
    @Override
    public ArrayList<Node> getPath(){
        this.process(start);
        ArrayList<Node> newPath = new ArrayList<Node>(path.subList(0, path.indexOf(goal)));
        newPath.add(goal);
        return newPath;
    }


    public void process(Node current){
        current.visitedFlag = true;
        path.add(current);
        if (current.equals(goal))
            return;
        for (Node neighbor : current.getEdges()) {
            if (!neighbor.visitedFlag){
                process(neighbor);
            }
        }
    }

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        DFS dfs= new DFS(nodes, nodes.get(Parser.indexOfNode(nodes, "CHALL010L2")), nodes.get(Parser.indexOfNode(nodes, "CHALL009L2")));
        ArrayList<Node> path = dfs.getPath();
        System.out.println(path.size());
    }
}


