package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Parser {
    public static Node parseNode(JSONObject _node) {
        _node = _node.getJSONObject("fields");
        return new Node(
                _node.getString("nodeId"),
                _node.getInt("x"),
                _node.getInt("y"),
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
        _edge = _edge.getJSONObject("fields");
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

    public static UserRegistration parseUserRegistration(JSONObject _registration) {
        _registration = _registration.getJSONObject("fields");
        ArrayList<String> reasonList = new ArrayList<String>();
        try {
            String reasons = _registration.getString("reasons");
            reasons = reasons.replace("\\", "").substring(1, reasons.length()-1);
            reasonList = new ArrayList<String>(Arrays.asList(reasons.split(",")));
        } catch (Exception e) {e.printStackTrace();}
        return new UserRegistration(
                _registration.getString("name"),
                _registration.getString("date"),
                reasonList,
                _registration.getString("phone"),
                _registration.getBoolean("ack"),
                _registration.getDouble("ackTime")
        );
    };

    public static ArrayList<UserRegistration> parseUserRegistrations(JSONArray _registrations) {
        ArrayList<UserRegistration> registrations = new ArrayList<UserRegistration>();
        _registrations.forEach(r -> {
            registrations.add(parseUserRegistration((JSONObject) r));
        });
        return registrations;
    }
}
