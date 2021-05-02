package edu.wpi.teamname.Algo.Algorithms;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.NodeCostComparator;
import edu.wpi.teamname.Algo.Parser;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.SocketManager;
import edu.wpi.teamname.simplify.Config;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Djikstra {
    private ArrayList<Node> nodes;
    private Node start;
    private Node goal;
    private PriorityQueue<Node> openNodes;

    public Djikstra(ArrayList<Node> nodes, Node start, Node goal) {
        this.resetNodes(nodes);
        this.start = start;
        start.setCostSoFar(0);
        this.goal = goal;
        openNodes = new PriorityQueue<Node>(new NodeCostComparator());
        this.process();
    }

    private void resetNodes(ArrayList<Node> nodes){
        for (Node node : nodes) {
            if (node.getParent() != null) {
                node.setParent(null);
            }
            node.setCostSoFar(Double.POSITIVE_INFINITY);
        }
    }

    public ArrayList<Node> getPath() {
        Stack<Node> finalPath = new Stack<>(); //Stack containing the final path of our algorithm
        Node current = goal;
        while (current.getParent() != null && !current.getNodeID().equals(start.getNodeID())) {
            finalPath.push(current);
            current = current.getParent();
        }
        finalPath.push(start); //Pushes the starting node on to the stack

        ArrayList<Node> path = new ArrayList<Node>();
        while (!finalPath.isEmpty())
            path.add(finalPath.pop());
        return path;
    }

    public ArrayList<ArrayList<Node>> getAllFloorPaths() {
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<ArrayList<Node>> paths = new ArrayList<>();
        String currentfloor = start.getFloor();
        for (Node node : this.getPath()) {
            if (node.getFloor().equals(currentfloor))
                nodes.add(node);
            else {
                currentfloor = node.getFloor();
                paths.add(nodes);
                nodes = new ArrayList<>();
                nodes.add(node);
            }
        }
        paths.add(nodes);
        return paths;
    }

    public ArrayList<ArrayList<Node>> getFloorPaths(String floor) {
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<ArrayList<Node>> paths = new ArrayList<>();
        for (ArrayList<Node> floorPath : getAllFloorPaths()) {
            if (floorPath.get(0).getFloor().equals(floor))
                paths.add(floorPath);
        }
        return paths;
    }

    public ArrayList<Node> getFloorNodes(String floor) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (Node node : this.getPath()) {
            if (node.getFloor().equals(floor))
                nodes.add(node);
        }
        return nodes;
    }

    public void process(){
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
        Djikstra example = new Djikstra(nodes, nodes.get(10), nodes.get(76));
        System.out.println(example.getPath().size());
    }
}
