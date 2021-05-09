package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.Parser;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

import java.util.ArrayList;
import java.util.Stack;

/**
 * <h1>Depth-First Search Algorithm</h1>
 * Uses depth-first search for pathfinding operations
 * @author Emmanuel Ola
 */
public class DFS extends Algorithm {

    /**
     * Constructor for DFS that takes in a list of nodes, and the start and goal node
     * @param nodes List of nodes which contains the start and goal nodes
     * @param start the start node
     * @param goal the goal node
     */
    public DFS(ArrayList<Node> nodes, Node start, Node goal) {
        super(nodes, start, goal);
        this.process();
    }

    public DFS(){}

    public ArrayList<Node> getPath() {
        Stack<Node> finalPath = new Stack<>(); //Stack containing the final path of our algorithm
        Node current = goal;
        while (current.getParent() != null && !current.getNodeID().equals(start.getNodeID())) {
            finalPath.push(current);
            current = current.getParent();
        }

        if (!(current == null)) {
            finalPath.push(start); //Pushes the starting node on to the stack
        }

        ArrayList<Node> path = new ArrayList<Node>();
        while (!finalPath.isEmpty())
            path.add(finalPath.pop());
        return path;
    }

    public void process(){
        processDFS(start);
    }

    public void processDFS(Node current){
        current.visitedFlag = true;
        if (current.equals(goal))
            return;
        for (Node neighbor : current.getEdges()) {
            if (!neighbor.visitedFlag){
                neighbor.setParent(current);
                processDFS(neighbor);
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


