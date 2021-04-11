package edu.wpi.teamname.Database;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class PathFindingDatabaseManager {
    private static final PathFindingDatabaseManager instance = new PathFindingDatabaseManager();
    private PathFindingDatabaseManager() {};
    private Firestore db;

    public void startDb() {
        initDb();
        insertNodesIntoDatabase();
        insertEdgesIntoDatabase();
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
    };

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

//    public ArrayList<List<String>> retrieveNodesFromDatabase() {
//        ArrayList<List<String>> nodes = new ArrayList<>();
//        try {
//
//            Statement statement = connection.createStatement();
//
//            ResultSet nodesData = statement.executeQuery("SELECT * FROM nodeTable");
//            //System.out.println("working!!!");
//            while (nodesData.next()) {
//                System.out.println(nodesData.getString("node_ID")
//                        + "\t" + nodesData.getString("x_coord")
//                        + "\t" + nodesData.getString("y_coord")
//                        + "\t" + nodesData.getString("floor")
//                        + "\t" + nodesData.getString("building")
//                        + "\t" + nodesData.getString("node_type")
//                        + "\t" + nodesData.getString("long_name")
//                        + "\t" + nodesData.getString("short_name"));
//
//                System.out.print(nodesData.getString("x_coord"));
////
////                System.out.print(nodesData.getString("y_coord"));
////                System.out.print(nodesData.getString("floor"));
////                System.out.print(nodesData.getString("building"));
////                System.out.print(nodesData.getString("node_type"));
////                System.out.print(nodesData.getString("long_name"));
////                System.out.print(nodesData.getString("short_name"));
//                // System.out.println(node);
//
//
//            }
//        } catch (SQLException e) {
//        }
//        return nodes;
//
//
//    }

//    public ArrayList<List<String>> retrieveEdgesFromDatabase() {
//        ArrayList<List<String>> edges = new ArrayList<>();
//        try {
//            Statement statement = connection.createStatement();
//            System.out.println("statement created");
//            ResultSet edgesData = statement.executeQuery("SELECT * FROM EDGETABLE");
//            // ResultSet edgesData = statement2.executeQuery("SELECT * FROM edgeTable");
//            System.out.println("query exeuted");
//            while (edgesData.next()) {
//                // List<String> edge = new ArrayList<>();
//                System.out.println(edgesData.getString("edge_ID")
//                        + "\t" + edgesData.getString("starting_Node")
//                        + "\t" + edgesData.getString("finishing_Node"));
//                // System.out.println("we got into the printing statement");
//
//            }
//        } catch (SQLException e) {
//            System.out.println("error accessing edges");
//            e.printStackTrace();
//        }
//        return edges;
//
//
//    }


}