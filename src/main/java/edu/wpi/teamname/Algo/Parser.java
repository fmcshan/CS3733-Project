package edu.wpi.teamname.Algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Parser</h1>
 * This program parses csv files into nodes ArrayLists containing info about each node.
 * It also loads the edges connected to each node
 * @author Emmanuel Ola
 */

public class Parser {
    /**
     * Method to read in csv files and convert them to a list of row elements
     * containing info for each file
     * @param path Path to the csv file
     * @author Freud Oulon
     * @return A nested list of strings representing the different rows of data from the csv files
     */
    public static List<List<String>> readFile(String path) {
        List<List<String>> allElements = new ArrayList<>();
        List<String> elementData;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            boolean skip = true;
            while ((line = br.readLine()) != null) {
                if (skip) {
                    skip = false;
                } else {
                    String[] values = line.split(",");
                    elementData = Arrays.asList(values);
                    allElements.add(elementData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allElements;
    }

    /**
     * Static method that reads the data passed from Parser.readFile() and converts
     * them into an ArrayList of nodes that information about each node.
     * It also loads the edges of each node while it parses the data
     * @author emmanuelola
     * @return ArrayList of bidirectional nodes in the CSV file
     */
    public static ArrayList<Node> loadNodesandEdges() {
        List<List<String>> nodesData = Parser.readFile(System.getProperty("user.dir") + "/nodes.csv");
        List<List<String>> edgesData = Parser.readFile(System.getProperty("user.dir") + "/edges.csv");
        ArrayList<Node> nodesList = new ArrayList<>(); //Instantiate the Arraylist of nodes
        //Load all the nodes into nodesList
        for (List<String> edgesDatum : edgesData) {
            String nodeID1 = edgesDatum.get(1);
            loadNode(nodesData, nodesList, nodeID1);
            String nodeID2 = edgesDatum.get(2);
            loadNode(nodesData, nodesList, nodeID2);
        }
        //Loads the edges for each node
        for (Node node : nodesList) {
            for (List<String> edgesDatum : edgesData) {
                if (node.getNodeID().equals(edgesDatum.get(1))){
                    node.addEdge(nodesList.get(Parser.indexOfNode(nodesList, edgesDatum.get(2))));
                    nodesList.get(Parser.indexOfNode(nodesList, edgesDatum.get(2))).addEdge(node);
                }
            }
        }
        return nodesList;
    }

    /**
     * A helper method that loads all the data regarding a node into the node
     * @param nodesData the raw nodes data provided by the CSV reader
     * @param nodesList the list of nodes processed by the parser
     * @param nodeID1 the nodeID of the node we want to load (from the edges data)
     */
    private static void loadNode(List<List<String>> nodesData, ArrayList<Node> nodesList, String nodeID1) {
        if (Parser.indexOfNode(nodesList, nodeID1) == -1){
            for (int i = 0; i < nodesData.size(); i++) {
                List<String> nodeData = nodesData.get(i);
                String nodeID = nodeData.get(0);
                if (nodeID.equals(nodeID1)){
                    int x = Integer.valueOf(nodeData.get(1));
                    int y = Integer.valueOf(nodeData.get(2));
                    String floor = nodeData.get(3);
                    String building = nodeData.get(4);
                    String nodeType = nodeData.get(5);
                    String longName = nodeData.get(6);
                    String shortName = nodeData.get(7);
                    nodesList.add(new Node(nodeID, x, y, floor, building, nodeType, longName, shortName));
                }
            }
        }
    }

    /**
     * Retrieves the index of a node within an ArrayList of nodes, when provided the nodeID for the node.
     * @param nodes ArrayList of nodes
     * @param nodeID the Node ID of the node you're looking for
     * @author Emmanuel Ola
     * @return index of provided node, or -1 if the node isn't in the list
     */
    public static int indexOfNode(ArrayList<Node> nodes, String nodeID) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getNodeID().equals(nodeID))
                return i;
        }
        return -1;
    }
}


