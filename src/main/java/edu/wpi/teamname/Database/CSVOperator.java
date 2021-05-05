package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVOperator {

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

    public static void writeNodeCSV(ArrayList<Node> nodes, String filePath) {
        StringBuilder nodeBuilder = new StringBuilder();

        nodeBuilder.append("nodeId,xcoord,ycoord,level,building,nodetype,type,longname,shortname\n");

        for (Node node : nodes) {

            nodeBuilder.append(node.getNodeID() + ",");
            nodeBuilder.append(node.getX() + ",");
            nodeBuilder.append(node.getY() + ",");
            nodeBuilder.append(node.getFloor() + ",");
            nodeBuilder.append(node.getBuilding() + ",");
            nodeBuilder.append(node.getNodeType() + ",");
            nodeBuilder.append(node.getLongName() + ",");
            nodeBuilder.append(node.getShortName() + "\n");

        }

        try {
            try (FileWriter fr = new FileWriter(filePath)) {
                fr.write(nodeBuilder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeEdgeCSV(ArrayList<Edge> edges, String filePath) {
        StringBuilder edgeBuilder = new StringBuilder();

        edgeBuilder.append("edgeId,startNode,endNode\n");

        for (Edge edge : edges) {

            edgeBuilder.append(edge.getEdgeID() + ",");
            edgeBuilder.append(edge.getStartNode() + ",");
            edgeBuilder.append(edge.getEndNode() + "\n");

        }

        try {
            try (FileWriter fr = new FileWriter(filePath)) {
                fr.write(edgeBuilder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
