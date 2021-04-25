package edu.wpi.teamname.Algo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class GeneralSearch {
    public char initialState;
    public char solution;
    public ArrayList<String> exploredList;

    public GeneralSearch(){
        this.initialState = 'S';
        this.solution = 'G';
        this.exploredList = new ArrayList<String>();
    }

    public static void Main(){
        int iterativeDepthLimit = 0;
        int previousIterativeDepthLimit = 0;
        ArrayList<Node> listOfNodes;
        ArrayList<String> exploredList = new ArrayList<String>();
    }

    public ArrayList<Node> makeQueue(Node initialNode){
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(initialNode);

        return queue;
    }

    public Node makeInitialNode(String nodeID, char startOrTarget){
        return new Node(nodeID, startOrTarget);
    }

    public Node removeFront(ArrayList<Node> inputQueue){
        return inputQueue.remove(-1);
    }

    public int calculateHeuristicValue(Node inputNode){
        return inputNode.heuristic;
    }

    public float calculateCostValue(Node inputNode, Graph graph){
        float totalCost = 0;
        float cost = -1;
        ArrayList<Node> inputPath = graph.path;

        if (inputNode.startOrTargetNode == 'S'){
            totalCost = 0;
        } else {
            Node theNode = inputPath.get(0);
            cost = theNode.getEdges().get(inputNode.getNodeInfo().get("nodeID"));
            totalCost = graph.costOfPath + cost;
        }

        return totalCost;
    }

    public String terminalNode(Graph graph, ArrayList<String> exploredList){
        //TODO: make exploredList global
        ArrayList<Node> inputPath = graph.path;
        String termNodeName = inputPath.get(0).getNodeInfo().get("nodeID");
        exploredList.add(termNodeName);

        return termNodeName;
    }

    public void expand(Graph graph, ArrayList<String> exploredList){
        //TODO: make exploredList global
        ArrayList<Node> inputPath = graph.path;
        String termNodeName = inputPath.get(0).getNodeInfo().get("nodeID");
        ArrayList<Node> childrenNodes = new ArrayList<Node>();
        ArrayList<Node> returnNodes = new ArrayList<Node>();

        Hashtable<String, Integer> childrenEdges = inputPath.get(0).getEdges();

        Set<String> keys = childrenEdges.keySet();
        for (String key: keys){
            childrenNodes.get(key);
            //TODO finish this
        }

    }
}
