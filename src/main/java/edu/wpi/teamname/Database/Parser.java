package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    public static Node parseNode(JSONObject _node) {
        return new Node(
                _node.getString("nodeId"),
                Integer.parseInt(_node.getString("x")),
                Integer.parseInt(_node.getString("y")),
                _node.getString("level"),
                _node.getString("building"),
                _node.getString("nodeType"),
                _node.getString("longName"),
                _node.getString("shortName")
        );
    };

    public static ArrayList<Node> parseNodes(JSONArray _nodes) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        _nodes.forEach(n -> {
            nodes.add(parseNode((JSONObject) n));
        });
        return nodes;
    };

    public static Edge parseEdge(JSONObject _edge) {
        return new Edge(
                _edge.getString("edgeId"),
                _edge.getString("startNode"),
                _edge.getString("endNode")
        );
    };

    public static ArrayList<Edge> parseEdges(JSONArray _edges) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        _edges.forEach(e -> {
            edges.add(parseEdge((JSONObject) e));
        });
        return edges;
    };

    public static ArrayList<Node> parseNodes(JSONObject _data) {
        HashMap<String, ArrayList<Node>> edgeMap = new HashMap<String, ArrayList<Node>>();
        HashMap<String, Node> nodeMap = new HashMap<>();
        ArrayList<Node> nodes = parseNodes(_data.getJSONArray("nodes"));
        ArrayList<Edge> edges = parseEdges(_data.getJSONArray("edges"));

        nodes.forEach(n -> {
            nodeMap.put(n.getNodeID(), n);
        });

        edges.forEach(e -> {
            if (nodeMap.containsKey(e.getStartNode()) && nodeMap.containsKey(e.getEndNode())) {
                nodeMap.get(e.getStartNode()).addEdge(nodeMap.get(e.getEndNode()));
            }
        });

        return nodes;
    };
}
