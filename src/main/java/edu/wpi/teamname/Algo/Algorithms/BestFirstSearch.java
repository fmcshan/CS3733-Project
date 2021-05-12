package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.NodeAStarComparator;
import edu.wpi.teamname.Algo.Parser;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class BestFirstSearch extends Algorithm{
    PriorityQueue<Node> openNodes;

    public BestFirstSearch(ArrayList<Node> nodes, Node start, Node goal) {
        super(nodes, start, goal);
        this.openNodes = new PriorityQueue<>(new NodeAStarComparator());
        this.process();
    }

    public BestFirstSearch(){
        openNodes = new PriorityQueue<>(new NodeAStarComparator());
    }

    public void process() {
        start.setCostSoFar(0);
        openNodes.add(start);
        Node current;
        double tentativeScore = 0;

        while (!openNodes.isEmpty()){
            current = openNodes.poll();
            if (current.getNodeID().equals(goal.getNodeID())){
                return;
            }

            for (Node node : current.getEdges()) {
                if(!node.visitedFlag && !openNodes.contains(node)){
                    node.visited();
                    node.setHeuristic(calculateHeuristic(node));
                    node.updateAStarScore();
                    node.setParent(current);
                    openNodes.add(node);
                }

            }
        }
    }

    public double calculateHeuristic(Node node) {
        return AStar.distance(node, goal);
    }

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        AStar example1 = new AStar(nodes, nodes.get(Parser.indexOfNode(nodes, "FDEPT00501")), nodes.get(Parser.indexOfNode(nodes, "EINFO00101")), false);
        BFS example2 = new BFS(nodes, nodes.get(Parser.indexOfNode(nodes, "FDEPT00501")), nodes.get(Parser.indexOfNode(nodes, "EINFO00101")));
        BestFirstSearch example = new BestFirstSearch(nodes, nodes.get(Parser.indexOfNode(nodes, "FDEPT00501")), nodes.get(Parser.indexOfNode(nodes, "EINFO00101")));
//        System.out.println(example.getPath());
//        System.out.println(example.getPath().size());
//        System.out.println(example1.getPath().size());
    }
}
