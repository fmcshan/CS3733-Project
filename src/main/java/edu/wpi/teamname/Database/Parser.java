package edu.wpi.teamname.Database;

import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.views.manager.Event;
import edu.wpi.teamname.views.manager.Snapshot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

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

    public static Node parseNode2(JSONObject _node) {
        return new Node(
                _node.getString("i"),
                _node.getInt("x"),
                _node.getInt("y"),
                _node.getString("le"),
                _node.getString("b"),
                _node.getString("t"),
                _node.getString("lo"),
                _node.getString("s")

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

    public static ArrayList<Node> parseNodes2(JSONArray _nodes) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        _nodes.forEach(n -> {
            nodes.add(parseNode2((JSONObject) n));
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

    public static Edge parseEdge2(JSONObject _edge) throws Exception{
//        if(_edge.isNull())
        if(_edge.isNull("i")){
            throw new Exception(" null");}
        return new Edge(
                _edge.getString("i"),
                _edge.getString("s"),
                _edge.getString("e")
        );
    };

    public static ArrayList<Edge> parseEdges(JSONArray _edges) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        _edges.forEach(e -> {
            edges.add(parseEdge((JSONObject) e));
        });
        return edges;
    };
    public static ArrayList<Edge> parseEdges2(JSONArray _edges) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        _edges.forEach(e -> {
            edges.add(parseEdge2((JSONObject) e));
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

    public static ArrayList<Node> parseNodes3(JSONObject _data) {
        ArrayList<Node> nodes = parseNodes2(_data.getJSONArray("nodes"));
        return nodes;
    };

    public static ArrayList<Edge> parseEdges3(JSONObject _data) {
        ArrayList<Edge> edges = parseEdges2(_data.getJSONArray("edges"));
        return edges;
    };

    public static UserRegistration parseUserRegistration(JSONObject _registration) {
        JSONObject registration = _registration.getJSONObject("fields");
        ArrayList<String> reasonList = new ArrayList<String>();
        try {
            String reasons = registration.getString("reasons");
            reasons = reasons.replace("\\", "").substring(1, reasons.length()-1);
            reasonList = new ArrayList<String>(Arrays.asList(reasons.split(",")));
        } catch (Exception e) {e.printStackTrace();}
        return new UserRegistration(
                _registration.getInt("pk"),
                registration.getString("name"),
                registration.getString("date"),
                reasonList,
                registration.getString("phone"),
                registration.getBoolean("ack"),
                registration.getDouble("ackTime"),
                registration.getBoolean("cleared"),
                registration.getInt("rating"),
                registration.getString("details")
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

    public static ArrayList<Snapshot> parseSnapshots(JSONArray _snapShots) {
        ArrayList<Snapshot> snapShots = new ArrayList<Snapshot>();
        _snapShots.forEach(s -> {
            snapShots.add(parseSnapshot((JSONObject) s));
        });
        return snapShots;
    }

    public static ArrayList<Event> parseEvents(JSONArray _event) {
        ArrayList<Event> events = new ArrayList<Event>();
        _event.forEach(s -> {
            events.add(parseEvent((JSONObject) s));
        });
        return events;
    }

    public static Event parseEvent(JSONObject _event) {
        //System.out.println( _snapShot.getString("author"));
       // System.out.println( _event.("node"));
//        System.out.println("contains node: "+ _event.has("node"));
//        System.out.println("contains edge: "+_event.has("edge") );

        for (Iterator<String> it = _event.keys(); it.hasNext(); ) {
            String key = it.next();
            if (_event.get(key) instanceof JSONObject) {
                // Yes, it contains at least one JSONObject, whose key is `key`
                System.out.println(key);
            }
        }
        JSONObject node = new JSONObject();
        JSONObject edge = new JSONObject();
        if(!(_event.get("node").equals(null))){
        node = (JSONObject)_event.get("node");}
        if(!(_event.get("edge").equals(null))){
        edge = (JSONObject)_event.get("edge");}
        return new Event(
                _event.getString("id"),
                _event.getString("snapshot"),
                _event.getString("author"),
                _event.getString("date"),
                _event.getString("event"),
              //  new Node("aa", 2,3),
                 parseNode2(node),
            //    new Edge("aa", "a","a")
               parseEdge2(edge)
        );
    }

    public static Snapshot parseSnapshot(JSONObject _snapShot) {
        //System.out.println( _snapShot.getString("author"));

        return new Snapshot(
                _snapShot.getString("id"),
                _snapShot.getString("author"),
                _snapShot.getString("date"),
                parseNodes3(_snapShot.getJSONObject("data")),
                parseEdges3(_snapShot.getJSONObject("data"))
        );
    }
}
