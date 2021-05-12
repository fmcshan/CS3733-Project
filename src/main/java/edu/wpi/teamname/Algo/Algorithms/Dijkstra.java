package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.NodeCostComparator;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Dijkstra extends Algorithm{
    private PriorityQueue<Node> openNodes;

    public Dijkstra(ArrayList<Node> nodes, Node start, Node goal) {
        super(nodes, start, goal);
        openNodes = new PriorityQueue<Node>(new NodeCostComparator());
        this.process();
    }

    public Dijkstra(){
        openNodes = new PriorityQueue<>(new NodeCostComparator());
    }

    public void process(){
        start.setCostSoFar(0);
        openNodes.add(start);
        Node current = start;
        double tentativeScore = 0;

        while (!openNodes.isEmpty()) {
            if (current.getNodeID().equals(goal.getNodeID()))
                return;

            current = openNodes.poll();

            for (Node node : current.getEdges()) {
                tentativeScore = current.getCostSoFar() + AStar.distance(current, node);
                if (tentativeScore < node.getCostSoFar()){
                    node.setParent(current);
                    node.setCostSoFar(tentativeScore);
                    if (!openNodes.contains(node))
                        openNodes.add(node);
                }
            }
        }
    }

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        Dijkstra example = new Dijkstra(nodes, nodes.get(10), nodes.get(76));
        System.out.println(example.getPath().size());
    }
}
