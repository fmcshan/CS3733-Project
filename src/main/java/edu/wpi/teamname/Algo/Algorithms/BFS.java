package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.NodeComparator;
import edu.wpi.teamname.Algo.Parser;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * <h1>Breadth-First Search Algorithm</h1>
 * Uses breadth-first search for pathfinding
 */
public class BFS implements IAlgorithm {
    private Node start;
    private Node goal;
    private ArrayList<Node> path;
    private PriorityQueue<Node> openNodes;

    public BFS(ArrayList<Node> nodes, Node start, Node goal) {
        this.resetNodes(nodes);
        this.start = start;
        this.goal = goal;
        this.path = new ArrayList<>();
        this.openNodes = new PriorityQueue<>(new NodeComparator());
        this.process();
    }

    @Override
    public ArrayList<Node> getPath(){
        return path;
    }

    private void resetNodes(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            node.visitedFlag = false;
        }
    }

    public void process() {
        Node current = start;
        current.visited();
        openNodes.add(current);
        Node next;
        path.add(start);

        while (!openNodes.isEmpty()){
            next = openNodes.poll();
            if (next.equals(goal)){
                path.add(goal);
                return;
            }
            for (Node edge : next.getEdges()) {
                if (!edge.visitedFlag){
                    edge.visited();
                    path.add(edge);
                    openNodes.add(edge);
                }
            }
        }
    }

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        BFS bfs = new BFS(nodes, nodes.get(Parser.indexOfNode(nodes, "CHALL010L2")), nodes.get(Parser.indexOfNode(nodes, "CHALL009L2")));
        ArrayList<Node> path = bfs.getPath();
        System.out.println(path.size());
    }
}
