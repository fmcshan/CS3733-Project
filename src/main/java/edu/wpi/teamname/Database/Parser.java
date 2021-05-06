package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.User;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public static String parseSpace(JSONObject _space) {
        _space = _space.getJSONObject("fields");
        return _space.getString("nodeId");
    }

    public static ArrayList<String> parseSpaces(JSONArray _spaces) {
        ArrayList<String> spaces = new ArrayList<String>();
        _spaces.forEach(s -> {
            spaces.add(parseSpace((JSONObject) s));
        });
        return spaces;
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


    public static MasterServiceRequestStorage parseGiftDeliveryStorage(JSONObject _giftDeliveryStorage) {
        JSONObject giftDeliveryStorage = _giftDeliveryStorage.getJSONObject("fields");
        ArrayList<String> requestedItems = new ArrayList<String>();
        try {
            String requested = giftDeliveryStorage.getString("requestedItems");
            requested = requested.replace("\\", "");
            requested = requested.substring(1, requested.length()-1);
            requestedItems = new ArrayList<String>(Arrays.asList(requested.split(",")));
        } catch (Exception e) {e.printStackTrace();}
        return new MasterServiceRequestStorage(
                _giftDeliveryStorage.getInt("pk"),
                giftDeliveryStorage.getString("requestType"),
                giftDeliveryStorage.getString("location"),
                requestedItems,
                giftDeliveryStorage.getString("description"),
                giftDeliveryStorage.getString("requestedBy"),
                giftDeliveryStorage.getString("phone"),
                giftDeliveryStorage.getString("assignedTo"),
                giftDeliveryStorage.getBoolean("completed")
        );
    };

    public static ArrayList<MasterServiceRequestStorage> parseGiftDeliveryStorages(JSONArray _giftDeliveryStorages) {
        ArrayList<MasterServiceRequestStorage> giftDeliveryStorages = new ArrayList<MasterServiceRequestStorage>();
        _giftDeliveryStorages.forEach(r -> {
            giftDeliveryStorages.add(parseGiftDeliveryStorage((JSONObject) r));
        });
        return giftDeliveryStorages;
    }

    public static User parseUser(JSONObject _user) {
        return new User(
                null,
                _user.getString("email"),
                _user.getString("name"),
                _user.getString("id"),
                _user.getString("phone"),
                _user.getBoolean("admin"),
                _user.getBoolean("employee")
        );
    }

    public static ArrayList<User> parseUsers(JSONArray _users) {
        ArrayList<User> users = new ArrayList<User>();
        _users.forEach(u -> {
            users.add(parseUser((JSONObject) u));
        });
        return users;
    }
}
