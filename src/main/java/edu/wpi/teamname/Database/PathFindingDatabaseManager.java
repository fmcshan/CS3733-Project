package edu.wpi.teamname.Database;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteBatch;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.simplify.Config;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class PathFindingDatabaseManager {
    private static final PathFindingDatabaseManager instance = new PathFindingDatabaseManager();
    private Firestore db;
    private String SERVER_URL = Config.getInstance().getServerUrl();

    private PathFindingDatabaseManager() {
    }

    public static synchronized PathFindingDatabaseManager getInstance() {
        return instance;
    }

    /**
     * Splits a list into a list of lists with a maximum size of length
     *
     * @param source Source list
     * @param length Maximum list length/batch size
     * @return List of lists with a maximum size of length
     */
    private static <T> Stream<List<T>> batches(List<T> source, int length) {
        if (length <= 0)
            throw new IllegalArgumentException("length = " + length);
        int size = source.size();
        if (size <= 0)
            return Stream.empty();
        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(
                n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
    }

    private static ArrayList<Node> parseNodes(JSONArray nodesData, JSONArray edgeData) {
        ArrayList<Node> nodesList = new ArrayList<>();
        HashMap<String, Node> nodeMap = new HashMap<String, Node>();
        for (int n = 0; n < nodesData.length(); n++) {
            JSONObject node = nodesData.getJSONObject(n);
            Node newNode = new Node(
                    node.getString("id"),
                    Integer.parseInt(node.getString("x")),
                    Integer.parseInt(node.getString("y")),
                    node.getString("level"),
                    node.getString("building"),
                    node.getString("type"),
                    node.getString("longName"),
                    node.getString("shortName")
            );
            nodeMap.put(node.getString("id"), newNode);
            nodesList.add(newNode);
        }

        for (int n = 0; n < edgeData.length(); n++) {
            JSONObject edge = edgeData.getJSONObject(n);
            if (nodeMap.containsKey(edge.getString("start")) && nodeMap.containsKey(edge.getString("end"))) {
                Node startNode = nodeMap.get(edge.getString("start"));
                Node endNode = nodeMap.get(edge.getString("end"));
                startNode.addEdge(endNode);
                endNode.addEdge(startNode);
            }
        }

        return nodesList;
    }

    public void startDb() {
        initDb();
    }

    /**
     * Initializes the firestore database
     */
    private void initDb() {
        try {
            InputStream serviceAccount = new FileInputStream(System.getProperty("user.dir") + "/3733-firebase-service-account.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();

        } catch (Exception e) {
            System.out.println("error initializing firebase.");
        }
    }

    public void insertNodeListIntoDatabase(ArrayList<Node> _nodes) {
        JSONObject data = new JSONObject();
        JSONArray nodes = new JSONArray();
        _nodes.forEach(n -> {
            JSONObject node = new JSONObject();
            node.put("id", n.getNodeID());
            node.put("x", String.valueOf(n.getX()));
            node.put("y", String.valueOf(n.getY()));
            node.put("level", n.getFloor());
            node.put("building", n.getBuilding());
            node.put("type", n.getNodeType());
            node.put("longName", n.getLongName());
            node.put("shortName", n.getShortName());
            nodes.put(node);
        });

        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("nodes", nodes.toString());

        Change change = new Change("load_nodes", data.getString("CHANGE_ID"));
        change.setNodes(_nodes);
        change.setEdges(LocalStorage.getInstance().getEdges());
        ChangeManager.getInstance().processChange(change);

        AsynchronousTask task = new AsynchronousTask(SERVER_URL + "/api/load-nodes", data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    /**
     * Read the provided CSV and insert nodes into the database
     */
    public void insertNodeCsvIntoDatabase(String file) {
        List<List<String>> allNodesData = CSVOperator.readFile(file);
        Set<List<String>> nodesDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(nodesDataAsSet);

        ArrayList<Node> nodeList = new ArrayList<Node>();
        allNodesData.forEach(n -> {
            nodeList.add(new Node(
                    n.get(0),
                    Integer.parseInt(n.get(1)),
                    Integer.parseInt(n.get(2)),
                    n.get(3),
                    n.get(4),
                    n.get(5),
                    n.get(6),
                    n.get(7)
            ));
        });
        insertNodeListIntoDatabase(nodeList);
    }

    public void insertEdgeListIntoDatabase(ArrayList<Edge> _edges) {
        JSONObject data = new JSONObject();
        JSONArray edges = new JSONArray();
        _edges.forEach(e -> {
            JSONObject edge = new JSONObject();
            edge.put("id", e.getEdgeID());
            edge.put("x", e.getStartNode());
            edge.put("y", e.getEndNode());
            edges.put(edge);
        });

        data.put("CHANGE_ID", UUID.randomUUID().toString());
        data.put("edges", edges.toString());

        Change change = new Change("load_edges", data.getString("CHANGE_ID"));
        change.setNodes(LocalStorage.getInstance().getNodes());
        change.setEdges(_edges);
        ChangeManager.getInstance().processChange(change);

        AsynchronousTask task = new AsynchronousTask(SERVER_URL + "/api/load-nodes", data, "POST");
        AsynchronousQueue.getInstance().add(task);
    }

    /**
     * Read the provided CSV and insert edges into the database
     */
    public void insertEdgeCsvIntoDatabase(String file) {
        List<List<String>> allEdgesData = CSVOperator.readFile(file);
        Set<List<String>> edgesDataAsSet = new HashSet<>(allEdgesData); // to avoid duplicate elements
        allEdgesData.clear();
        allEdgesData.addAll(edgesDataAsSet);

        ArrayList<Edge> edgeList = new ArrayList<Edge>();
        allEdgesData.forEach(n -> {
            edgeList.add(new Edge(
                    n.get(0),
                    n.get(1),
                    n.get(2)
            ));
        });
        insertEdgeListIntoDatabase(edgeList);
    }

    /**
     * Performs a get request and returns the response as a json object
     *
     * @param _url The URL to get
     * @return A json object
     * @throws IOException
     */
    public JSONObject getRequestJson(String _url) throws IOException {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new JSONObject(response.toString());
    }

    /**
     * Nodes are handled internally as a json object
     *
     * @return A json array of nodes.
     */
    private JSONArray getNodesInteral() {
        ArrayList<List<String>> nodes = new ArrayList<>();
        try {
            JSONObject nodeList = getRequestJson("https://us-central1-software-engineering-3733.cloudfunctions.net/get-nodes");
            return nodeList.getJSONArray("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    /**
     * Edges are handled internally as a json object
     *
     * @return A json array of edges.
     */
    private JSONArray getEdgesInternal() {
        try {
            JSONObject nodeList = getRequestJson("https://us-central1-software-engineering-3733.cloudfunctions.net/get-edges");
            return nodeList.getJSONArray("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    /**
     * Return an arraylist of node classes
     *
     * @return An arraylist of node classes
     */
    public ArrayList<Node> getNodes() {
        JSONArray nodes = getNodesInteral();
        JSONArray edges = getEdgesInternal();
        return parseNodes(nodes, edges);
    }
}