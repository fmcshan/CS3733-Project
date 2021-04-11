package edu.wpi.teamname.Database;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.Parser;
import org.json.JSONArray;
import org.json.JSONObject;


public class PathFindingDatabaseManager {
    private static final PathFindingDatabaseManager instance = new PathFindingDatabaseManager();

    private PathFindingDatabaseManager() {
    }

    ;
    private Firestore db;

    public void startDb() {
        initDb();
        System.out.println(getNodes());
    }

    public static synchronized PathFindingDatabaseManager getInstance() {
        return instance;
    }

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

    private void insertNodesIntoDatabase() {
        List<List<String>> allNodesData = CSVReader.readFile(System.getProperty("user.dir") + "/L1Nodes.csv");
        Set<List<String>> nodesDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(nodesDataAsSet);

        WriteBatch batch = db.batch();

        batches(allNodesData, 400).forEach(batchedNodeData -> {
            for (List<String> singleNodeData : batchedNodeData) {
                try {
                    DocumentReference nodeRef = db.collection("nodes").document(singleNodeData.get(0));
                    Map<String, Object> node = new HashMap<>();
                    node.put("x", singleNodeData.get(1));
                    node.put("y", singleNodeData.get(2));
                    node.put("level", singleNodeData.get(3));
                    node.put("building", singleNodeData.get(4));
                    node.put("type", singleNodeData.get(5));
                    node.put("longName", singleNodeData.get(6));
                    node.put("shortName", singleNodeData.get(7));

                    batch.set(nodeRef, node);

                } catch (Exception e) {
                    System.out.println("error inserting node into the batch");
                    e.printStackTrace();
                }
            }

            ApiFuture<List<WriteResult>> future = batch.commit();
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    ;

    public void insertEdgesIntoDatabase() {
        List<List<String>> allEdgesData = CSVReader.readFile(System.getProperty("user.dir") + "/L1Edges.csv");
        Set<List<String>> edgesDataAsSet = new HashSet<>(allEdgesData); // to avoid duplicate elements
        allEdgesData.clear();
        allEdgesData.addAll(edgesDataAsSet);

        WriteBatch batch = db.batch();
        batches(allEdgesData, 400).forEach(batchedEdgeData -> {
            for (List<String> singleNodeData : batchedEdgeData) {
                try {
                    DocumentReference edgeRef = db.collection("edges").document(singleNodeData.get(0));
                    Map<String, Object> edge = new HashMap<>();
                    edge.put("start", singleNodeData.get(1));
                    edge.put("end", singleNodeData.get(2));

                    batch.set(edgeRef, edge);

                } catch (Exception e) {
                    System.out.println("error inserting edge into the batch");
                    e.printStackTrace();
                }
            }

            ApiFuture<List<WriteResult>> future = batch.commit();
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

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

    private JSONArray getNodesInteral() {
        ArrayList<List<String>> nodes = new ArrayList<>();
        try {
            JSONObject nodeList = getRequestJson("https://us-central1-software-engineering-3733.cloudfunctions.net/get-nodes?cache=true");
            return nodeList.getJSONArray("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    private JSONArray getEdgesInternal() {
        try {
            JSONObject nodeList = getRequestJson("https://us-central1-software-engineering-3733.cloudfunctions.net/get-edges?cache=true");
            return nodeList.getJSONArray("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    private static ArrayList<Node> parseNodes(JSONArray nodesData, JSONArray edgeData) {
        ArrayList<Node> nodesList = new ArrayList<>();
        HashMap<String, Node> nodeMap = new HashMap<String, Node>();
        for(int n = 0; n < nodesData.length(); n++)
        {
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

        for(int n = 0; n < edgeData.length(); n++)
        {
            JSONObject edge = edgeData.getJSONObject(n);
            if (nodeMap.containsKey(edge.getString("start")) && nodeMap.containsKey(edge.getString("end"))) {
                Node startNode = nodeMap.get(edge.getString("start"));
                Node endNode = nodeMap.get(edge.getString("start"));
                startNode.addEdges(endNode, 1);
            }
        }
        return nodesList;
    }

    public ArrayList<Node> getNodes() {
        JSONArray nodes = getNodesInteral();
        JSONArray edges = getEdgesInternal();
        return parseNodes(nodes, edges);
    }
}