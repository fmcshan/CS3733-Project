package edu.wpi.teamname.Database;

import com.google.api.client.json.Json;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;


public class LocalFailover implements DataListener {
    private static final LocalFailover instance = new LocalFailover();
    private boolean failedOver = false;
    private JSONObject db;
    private URI filePath;

    private LocalFailover() {

    }

    public static synchronized LocalFailover getInstance() {
        return instance;
    }

    public boolean hasFailedOver() {
        return failedOver;
    }

    public void failOver() {
        if (this.failedOver) {
            return;
        }
        LocalStorage.getInstance().addListener(this);
        System.out.println("Unable to establish connection to the rest API. Switching to local failover with reduced functionality.");
        System.out.println("** CREDENTIALS ARE NOT ENCRYPTED IN THE LOCAL FAILOVER DB **");
        this.failedOver = true;

        refreshData();

        System.out.println("==== LOCAL FAILOVER ====");
        System.out.println("Reduced functionality: ");
        System.out.println("   Authentication");
        System.out.println("   Chatbot");
        System.out.println("   Revision History");
        System.out.println("   Slightly degraded performance\n");
        System.out.println("Credentials: ");
        System.out.println("   Username: admin@admin.com");
        System.out.println("   Password: password\n");

    }

    public void refreshData() {
        loadJson();
        parseNodes();
        parseEdges();
        LocalStorage.getInstance().linkEdges();
        parseEmployees();
        parseRequests();
        parseCheckins();
        parseSpaces();
    }

    // Load JSON
    private void loadJson() {
        try {
            this.filePath = getClass().getResource("/edu/wpi/teamname/failover.json").toURI();
            File jsonFile = new File(filePath);
            FileReader jsonFileReader = new FileReader(jsonFile);
            JsonElement jsonElem = JsonParser.parseReader(jsonFileReader);
            this.db = new JSONObject(jsonElem.toString());
            jsonFileReader.close();
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
                    u.getString("id"),
                    u.getString("id"),
                    u.getString("e"),
                    u.getString("n"),
                    u.getString("l"),
                    u.getString("p"),
                    u.getBoolean("a"),
                    u.getBoolean("em"),
                    u.getString("pa")
            ));
        });
        LocalStorage.getInstance().setUsers(users);
    }

    // Parse requests from JSON
    private void parseRequests() {
        JSONArray requestArray = db.getJSONArray("requests");
        ArrayList<MasterServiceRequestStorage> requests = new ArrayList<MasterServiceRequestStorage>();
        requestArray.forEach(ju -> {
            JSONObject r = (JSONObject) ju;
            requests.add(new MasterServiceRequestStorage(
                    r.getInt("id"),
                    r.getString("type"),
                    r.getString("location"),
                    new ArrayList<String>(Arrays.asList(r.getString("requestedItems").split(","))),
                    r.getString("description"),
                    r.getString("requestedBy"),
                    r.getString("contact"),
                    r.getString("assignTo"),
                    r.getBoolean("completed")
            ));
        });
        LocalStorage.getInstance().setMasterStorages(requests);
    }

    // Parse checkins from JSON
    private void parseCheckins() {
        JSONArray checkinArray = db.getJSONArray("checkins");
        ArrayList<UserRegistration> checkins = new ArrayList<UserRegistration>();
        checkinArray.forEach(jr -> {
            JSONObject r = (JSONObject) jr;
            checkins.add(new UserRegistration(
                    r.getString("uuid"),
                    r.getString("name"),
                    r.getString("date"),
                    r.getLong("submittedAt"),
                    new ArrayList<String>(Arrays.asList(r.getString("reasonsForVisit").split(","))),
                    r.getString("phoneNumber"),
                    r.getBoolean("acknowledged"),
                    r.getDouble("acknowledgedAt"),
                    r.getBoolean("cleared"),
                    r.getInt("rating"),
                    r.getString("details")
            ));
        });
        LocalStorage.getInstance().setRegistrations(checkins);
    }

    private void parseSpaces() {
        ArrayList<String> spaces = new ArrayList<String>();
        JSONArray spacesJson = db.getJSONArray("spaces");
        spacesJson.forEach(j -> {
            JSONObject sj = (JSONObject) j;
            spaces.add(sj.getString("n"));
        });
        LocalStorage.getInstance().setReservedParkingSpaces(spaces);
    }


    private void saveDb() {
        try {
            FileWriter file = new FileWriter(new File(filePath), false);
            file.write(this.db.toString());
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save arraylist of nodes to JSON
    public void setNodes(ArrayList<Node> _nodes) {
        JSONArray newNodes = new JSONArray();
        _nodes.forEach(n -> {
            JSONObject newNode = new JSONObject();
            newNode.put("i", n.getNodeID());
            newNode.put("b", n.getBuilding());
            newNode.put("t", n.getNodeType());
            newNode.put("le", String.valueOf(n.getFloor()));
            newNode.put("lo", n.getLongName());
            newNode.put("s", n.getShortName());
            newNode.put("x", n.getX());
            newNode.put("y", n.getY());

            newNodes.put(newNode);
        });

        this.db.put("nodes", newNodes);
        saveDb();
    }

    // Save arraylist of edges to JSON
    public void setEdges(ArrayList<Edge> _edges) {
        JSONArray newEdges = new JSONArray();
        _edges.forEach(n -> {
            JSONObject newEdge = new JSONObject();
            newEdge.put("i", n.getEdgeID());
            newEdge.put("s", n.getStartNode());
            newEdge.put("e", n.getEndNode());

            newEdges.put(newEdge);
        });

        this.db.put("edges", newEdges);
        saveDb();
    }

    public void setSpaces(ArrayList<String> _spaces) {
        JSONArray spacesJson = db.getJSONArray("spaces");
        _spaces.forEach(r -> {
            JSONObject space = new JSONObject();
            space.put("n", r);
            spacesJson.put(space);
        });
        db.put("spaces", spacesJson);
    }

    @Override
    public void nodesSet(ArrayList<Node> _nodes) {
        setNodes(_nodes);
    }

    @Override
    public void edgesSet(ArrayList<Edge> _edges) {
        setEdges(_edges);
    }

    // Save employees to JSON
    public void setUsers(ArrayList<User> _users) {
        JSONArray newUsers = new JSONArray();
        _users.forEach(u -> {
            JSONObject newUser = new JSONObject();
            newUser.put("id", u.getLocalId());
            newUser.put("re", u.getLocalId());
            newUser.put("e", u.getEmail());
            newUser.put("n", u.getName());
            newUser.put("l", u.getLocalId());
            newUser.put("p", u.getPhone());
            newUser.put("a", u.isAdmin());
            newUser.put("em", u.isEmployee());
            newUser.put("pa", u.getPassword());

            newUsers.put(newUser);
        });

        this.db.put("users", newUsers);
        saveDb();
        refreshData();
        Initiator.getInstance().triggerUserRefresh();
    }

    private String arrayListToString(ArrayList<String> _toParse) {
        if (_toParse.size() > 0) {
            StringBuilder nameBuilder = new StringBuilder();

            for (String n : _toParse) {
                nameBuilder.append("'").append(n.replace("'", "''")).append("',");
            }
            nameBuilder.deleteCharAt(nameBuilder.length() - 1);
            return nameBuilder.toString();

        } else {
            return "";
        }
    }

    // Save requests to JSON
    public void setCheckins(ArrayList<UserRegistration> _checkins) {
        JSONArray newCheckins = new JSONArray();
        _checkins.forEach(c -> {
            JSONObject newCheckin = new JSONObject();
            newCheckin.put("uuid", c.getUuid());
            newCheckin.put("name", c.getName());
            newCheckin.put("date", c.getDate());
            newCheckin.put("submittedAt", c.getSubmittedAt());
            newCheckin.put("reasonsForVisit", arrayListToString(c.getReasonsForVisit()));
            newCheckin.put("phoneNumber", c.getPhoneNumber());
            newCheckin.put("acknowledged", c.isAcknowledged());
            newCheckin.put("acknowledgedAt", c.getAcknowledgedAt());
            newCheckin.put("cleared", c.getCleared());
            newCheckin.put("rating", c.getRating());
            newCheckin.put("details", c.getDetails());
            newCheckin.put("submittedAt", c.getSubmittedAt());

            newCheckins.put(newCheckin);
        });

        this.db.put("checkins", newCheckins);
        saveDb();
        refreshData();
        Initiator.getInstance().triggerRegistrationRefresh();
    }

}
