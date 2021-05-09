package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.NodeAStarComparator;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * <h1>Breadth-First Search Algorithm</h1>
 * Uses breadth-first search for pathfinding
 */
public class BFS extends Algorithm {
    PriorityQueue<Node> openNodes;

    public BFS(ArrayList<Node> nodes, Node start, Node goal) {
        super(nodes, start, goal);
        this.openNodes = new PriorityQueue<>(new NodeAStarComparator());
    }

    public BFS(){
        this.openNodes = new PriorityQueue<>(new NodeAStarComparator());
    }

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



    public void process() {
        Node temp = start;
        temp.visited();
        openNodes.add(temp);
        Node current;

        while (!openNodes.isEmpty()){
            current = openNodes.poll();
            if (current.equals(goal)){
                return;
            }
            for (Node node : current.getEdges()) {
                if (!node.visitedFlag && !openNodes.contains(node)){
                    node.visited();
                    node.setParent(current);
                    openNodes.add(node);
                }
            }
        }
    }

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        BFS bfs = new BFS(nodes, nodes.get(10), nodes.get(76));
        ArrayList<Node> path = bfs.getPath();
        System.out.println(path.size());
    }
}
