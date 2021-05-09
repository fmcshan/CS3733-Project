package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.NodeAStarComparator;
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

    public ArrayList<Node> getPath() {
        Stack<Node> finalPath = new Stack<>(); //Stack containing the final path of our algorithm
        Node current = goal;
        while (current.getParent() != null && !current.getNodeID().equals(start.getNodeID())) {
            finalPath.push(current);
            current = current.getParent();
        }

        if (!(current == null))
            finalPath.push(start); //Pushes the starting node on to the stack

        ArrayList<Node> path = new ArrayList<Node>();
        while (!finalPath.isEmpty())
            path.add(finalPath.pop());
        return path;
    }

    public void resetNodes(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            if (node.getParent() != null) {
                node.setParent(null);
                node.visitedFlag = false;
            }
            node.setCostSoFar(0);
        }
    }


    public void loadNodes(ArrayList<Node> nodes, Node start, Node goal){
        this.nodes = nodes;
        this.start = start;
        this.goal = goal;
    }

    public void process() {
        openNodes.add(start);
        Node current = start;
        double tentativeScore = 0;

        while (!openNodes.isEmpty()){
            if (current.getNodeID().equals(goal.getNodeID())){
                return;
            }

            current = openNodes.poll();
            for (Node node : current.getEdges()) {
                tentativeScore = AStar.distance(current, goal);
                if (AStar.distance(node, goal) <= tentativeScore){
                    node.setParent(current);
                    node.setHeuristic(calculateHeuristic(node));
                    node.updateAStarScore();
                }
                if (!openNodes.contains(node) && !node.visitedFlag)
                    openNodes.add(node);
            }
            current.visited();
        }
    }

    public double calculateHeuristic(Node node) {
        return AStar.distance(node, goal);
    }

    public static void main(String[] args) {
        Config.getInstance().setEnv("staging"); // dev staging production
        SocketManager.getInstance().startDataSocket();
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        BestFirstSearch example = new BestFirstSearch(nodes, nodes.get(10), nodes.get(76));
        System.out.println(example.getPath());
        System.out.println(example.getPath().size());
    }
}
