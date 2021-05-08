package edu.wpi.teamname.Database;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.User;
import org.json.JSONArray;
import org.json.JSONObject;


public class LocalFailover {
    private static final LocalFailover instance = new LocalFailover();

    public static synchronized LocalFailover getInstance() {
        return instance;
    }

    private LocalFailover() {

    }

    private boolean failedOver = false;
    private JSONObject db;

    public boolean hasFailedOver() {
        return failedOver;
    }

    public void failOver() {
        System.out.println("Unable to establish connection to the rest API. Switching to local failover with reduced functionality.");
        System.out.println("** CREDENTIALS ARE NOT ENCRYPTED IN THE LOCAL FAILOVER DB **");
        this.failedOver = true;

        loadJson();
        parseNodes();
        parseEdges();
        parseEmployees();

        System.out.println("==== LOCAL FAILOVER ====");
        System.out.println("Reduced functionality: ");
        System.out.println("   Authentication");
        System.out.println("   Chatbot");
        System.out.println("   Slightly degraded performance\n");
        System.out.println("Credentials: ");
        System.out.println("   Username: admin@admin.com");
        System.out.println("   Password: password\n");

    }

    // Load JSON
    private void loadJson() {
        try {
            File jsonFile = new File(getClass().getResource("/edu/wpi/teamname/failover.json").toURI());
            FileReader jsonFileReader = new FileReader(jsonFile);
            JsonElement jsonElem = JsonParser.parseReader(jsonFileReader);
            this.db = new JSONObject(jsonElem.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Parse nodes from JSON
    private void parseNodes() {
        JSONArray nodeArray = db.getJSONArray("nodes");
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodeArray.forEach(jn -> {
            JSONObject n = (JSONObject) jn;
            nodes.add(new Node(
                    n.getString("i"),
                    n.getInt("x"),
                    n.getInt("y"),
                    n.getString("le"),
                    n.getString("b"),
                    n.getString("t"),
                    n.getString("lo"),
                    n.getString("s")
            ));
        });
        LocalStorage.getInstance().setNodes(nodes);
    }

    // Parse edges from JSON
    private void parseEdges() {
        JSONArray edgeArray = db.getJSONArray("edges");
        ArrayList<Edge> edges = new ArrayList<Edge>();
        edgeArray.forEach(je -> {
            JSONObject e = (JSONObject) je;
            edges.add(new Edge(
                    e.getString("i"),
                    e.getString("s"),
                    e.getString("e")
            ));
        });
        LocalStorage.getInstance().setEdges(edges);
    }

    // Parse employees from JSON
    private void parseEmployees() {
        JSONArray userArray = db.getJSONArray("users");
        ArrayList<User> users = new ArrayList<User>();
        userArray.forEach(ju -> {
            JSONObject u = (JSONObject) ju;
            users.add(new User(
                    "local",
                    "local",
                    u.getString("e"),
                    u.getString("n"),
                    u.getString("l"),
                    u.getString("p"),
                    u.getBoolean("a"),
                    u.getBoolean("e")
            ));
        });
        LocalStorage.getInstance().setUsers(users);
    }

    // Parse requests from JSON
    private void parseRequests() {
        JSONArray userArray = db.getJSONArray("users");
        ArrayList<User> users = new ArrayList<User>();
        userArray.forEach(ju -> {
            JSONObject u = (JSONObject) ju;
            users.add(new User(
                    "local",
                    "local",
                    u.getString("e"),
                    u.getString("n"),
                    u.getString("l"),
                    u.getString("p"),
                    u.getBoolean("a"),
                    u.getBoolean("e")
            ));
        });
        LocalStorage.getInstance().setUsers(users);
    }
    // Save arraylist of nodes to JSON

    // Save arraylist of edges to JSON

    // Save employees to JSON

    // Save requests to JSON

}
