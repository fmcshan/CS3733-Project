package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.App;
import edu.wpi.teamname.Database.CSVOperator;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.simplify.Shutdown;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class MapEditorGraphOld  {

    @FXML
    private Label validID;
    @FXML
    private JFXComboBox<String> selectNode;
    @FXML
    private JFXComboBox<String> selectEdge;
    @FXML
    private JFXTextField edgeFile;
    @FXML
    private JFXButton submitNode;
    @FXML
    private JFXTextField nodeFile;
    @FXML
    private JFXTextField NodeID;
    @FXML
    private JFXTextField X;
    @FXML
    private JFXTextField Y;
    @FXML
    private JFXTextField Floor;
    @FXML
    private JFXTextField Building;
    @FXML
    private JFXTextField NodeType;
    @FXML
    private JFXTextField ShortName;
    @FXML
    private JFXTextField LongName;
    @FXML
    private JFXTextField EdgeID;
    @FXML
    private JFXComboBox<String> startNode;
    @FXML
    private JFXComboBox<String> endNode;
    @FXML
    private JFXButton submitEdge;
    @FXML
    private Label validID1;
    @FXML
    private ImageView hospitalMap;
    @FXML
    private AnchorPane topElements;
    @FXML
    private DefaultPage defaultPage; // DefaultPage.fxml controller

    double mapWidth; //= 1000.0;
    double mapHeight;// = 680.0;
    double fileWidth; //= 5000.0;
    double fileHeight;// = 3400.0;
    double fileFxWidthRatio = mapWidth / fileWidth;
    double fileFxHeightRatio = mapHeight / fileHeight;
    boolean fetchFromDatabase = true;
    Set<Node> nodeSet;
    List<List<String>> theEdges;
    List<List<String>> theNodes;
    HashMap<String, Node> nodeMap = new HashMap<>();
    HashMap<String, Edge> edgeMap = new HashMap<>();
    HashSet<Edge> edgeSet = new HashSet<Edge>();
    Line selectedEdge;
    Node editedNode = new Node("1", 1, 1);

    public void initialize() {
        if (fetchFromDatabase) {

            ArrayList<Node> nodes = LocalStorage.getInstance().getNodes(); //importing nodes from database

            nodeSet = new HashSet<>(nodes); //making a set of the nodes
            fetchFromDatabase = false;
        }
        displayNodes();  //displays the nodes from the database
        enterEdges();
        displayEdges();
        nodeSet.forEach(n -> {
            if ((n.getFloor().equals("1") || n.getFloor().equals("G") || n.getFloor().equals("")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") || n.getBuilding().equals(""))) {
                selectNode.getItems().add(n.getNodeID());
            }
        });
        submitEdge.setVisible(false);
        validID1.setVisible(false);
        validID.setVisible(false);
        submitNode.setVisible(false);
    }

    @FXML
    void loadFileNode() {
        selectNode.getItems().clear();
        nodeMap.clear();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV Files", "csv");
        chooser.setFileFilter(filter);
        int chosenVal = chooser.showOpenDialog(null);
        List<List<String>> allNodesData = CSVOperator.readFile(chooser.getSelectedFile().getAbsolutePath()); // Load new CSV
        Set<List<String>> NodeDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(NodeDataAsSet);
        allNodesData.forEach(n -> {
            Node newNode = new Node(n.get(0), Integer.parseInt(n.get(1)), Integer.parseInt(n.get(2)), n.get(3), n.get(4), n.get(5), n.get(6), n.get(7));
            nodeMap.put(newNode.getNodeID(), newNode);
        });


     PathFindingDatabaseManager.getInstance().insertNodeCsvIntoDatabase(chooser.getSelectedFile().getAbsolutePath());  //LoadCSVOfNodesToDatabase(allNodesData);
      //  fetchFromDatabase= true;
        topElements.getChildren().clear();
        ArrayList<Node> nodes= LocalStorage.getInstance().getNodes();
        nodeSet = new HashSet<>(nodes);
        nodeSet.forEach(n -> {
            if ((n.getFloor().equals("1") || n.getFloor().equals("G") || n.getFloor().equals("")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") || n.getBuilding().equals(""))) {
                selectNode.getItems().add(n.getNodeID());

            }

        });
        displayNodes();
        displayEdges();
        enterEdges();
    }

    @FXML
    void loadFileEdge() {
        startNode.getItems().clear();
        endNode.getItems().clear();
        selectEdge.getItems().clear();
        edgeMap.clear();
        edgeSet.clear();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV Files", "csv");
        chooser.setFileFilter(filter);
        int chosenVal = chooser.showOpenDialog(null);
        List<List<String>> allEdgesData = CSVOperator.readFile(chooser.getSelectedFile().getAbsolutePath()); // Load new CSV
        Set<List<String>> edgesDataAsSet = new HashSet<>(allEdgesData); // to avoid duplicate elements
        allEdgesData.clear();
        allEdgesData.addAll(edgesDataAsSet);
        allEdgesData.forEach(n -> {
            Edge newEdge = new Edge(n.get(0), n.get(1), n.get(2));
            edgeMap.put(newEdge.getEdgeID(), newEdge);
            edgeSet.add(newEdge);
            //System.out.println("1: " + n.get(0) + " 2: " + n.get(1)+  " 3: " + n.get(2));
            startNode.getItems().add(n.get(1));
            endNode.getItems().add(n.get(2));
        });
        theEdges = allEdgesData;
        allEdgesData.forEach(n -> {
            selectEdge.getItems().add(n.get(0));
        });
        PathFindingDatabaseManager.getInstance().insertEdgeCsvIntoDatabase(chooser.getSelectedFile().getAbsolutePath());
        //LocalStorage.getInstance().setEdges();
        topElements.getChildren().clear();
        ArrayList<Node> nodes= LocalStorage.getInstance().getNodes();
        nodeSet = new HashSet<>(nodes);
        displayNodes();
        displayEdges();
    }

    public void enterEdges() {
        ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
        nodeSet = new HashSet<>(nodes);
        ArrayList<Edge> edges = LocalStorage.getInstance().getEdges();

        edges.forEach(e -> {
            selectEdge.getItems().add(e.getStartNode() + "_" + e.getEndNode());
            edgeMap.put(e.getEdgeID(), e);
            edgeSet.add(e);
        });

        nodeSet.forEach(n -> {
            startNode.getItems().add(n.getNodeID());
            endNode.getItems().add(n.getNodeID());
        });

//        for (Node n : nodeSet) { //goes through the nodes in nodeSet
//            startNode.getItems().add(n.getNodeID()); //adds the nodes to startnode combobox
//            endNode.getItems().add(n.getNodeID()); //adds the nodes to the end combobox
//            for (Node e : n.getEdges()) { //goes through each edge in each node
//                if (nodeMap.containsKey(e.getNodeID()) && nodeMap.containsKey(n.getNodeID())) { //if the edge has two nodes in the nodemap
//                    Edge edge = new Edge(n.getNodeID() + "_" + e.getNodeID(), n.getNodeID(), e.getNodeID()); //add the edge
//                    if (!edgeMap.containsKey(edge.getEdgeID()) && !edgeMap.containsKey(edge.getEndNode()+"_"+edge.getStartNode())) {
//                        edgeMap.put(edge.getEdgeID(), edge);
//                        edgeSet.add(edge);
//                        selectEdge.getItems().add(n.getNodeID() + "_" + e.getNodeID());
////                        System.out.println(startNode.getItems().size());
////                        System.out.println(endNode.getItems().size());
//                    }
//                }
//            }
//        }
    }

    @FXML
    void saveNodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodeMap.values().forEach(n -> {
            nodes.add((Node) n);
        });
        if(nodeFile.getText()!= null){
        CSVOperator.writeNodeCSV(nodes, nodeFile.getText()); // Write nodes to csv
        PathFindingDatabaseManager.getInstance().insertNodeListIntoDatabase(nodes);}
    }

    @FXML
    void saveEdges() {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        edgeMap.values().forEach(e -> {
            edges.add((Edge) e);
        });
        CSVOperator.writeEdgeCSV(edges, edgeFile.getText()); // Write nodes to csv

  PathFindingDatabaseManager.getInstance().insertEdgeListIntoDatabase(edges);
    }

    @FXML
    public void fillFieldsNode() {
        nodeSet.forEach(n -> {
            if (n.getNodeID().equals(selectNode.getValue())) {
                NodeID.setText(n.getNodeID());
                X.setText(String.valueOf(n.getX()));
                Y.setText(String.valueOf(n.getY()));
                Floor.setText(n.getFloor());
                Building.setText(n.getBuilding());
                NodeType.setText(n.getNodeType());
                LongName.setText(n.getLongName());
                ShortName.setText(n.getShortName());
                topElements.getChildren().clear();
                displayNodes();
                displayEdges();
                displayNewNodes(n);
            }
        });
    }

    @FXML
    public void fillFieldsEdge() {
        edgeSet.forEach(n -> {
            if (n.getEdgeID().equals(selectEdge.getValue())) {
                startNode.setValue(n.getStartNode());
                endNode.setValue(n.getEndNode());
                //FINFO00101
                //FHALL02901

                //EHALL02801
//                //EHALL02501
                if (nodeMap.containsKey(n.getStartNode()))
                {
                    //System.out.println("contains "+ n.getStartNode());
                }
                if (nodeMap.containsKey(n.getEndNode()))
                {
                    //System.out.println("contains "+ n.getEndNode());
                }
                topElements.getChildren().clear();
                displayNodes();
                displayEdges();
                displaySelectedEdge(n.getStartNode(),n.getEndNode());
            }
        });
    }

    @FXML
    void changeNodeEvent() {
        if (!(submitNode.isVisible())) {
            nodeSet.forEach(n -> {
                if (n.getNodeID().equals(selectNode.getValue())) {
         editedNode  = new Node (n.getNodeID(),n.getX(),n.getY(), n.getFloor(),n.getBuilding(),n.getNodeType(),n.getLongName(),n.getShortName());
         editedNode.setEdges(n.getEdges());

                    n.setX(Integer.parseInt(X.getText()));
                    n.setY(Integer.parseInt(Y.getText()));
                    n.setFloor(String.valueOf(Floor.getText()));
                    n.setBuilding(String.valueOf(Building.getText()));
                    n.setNodeType(String.valueOf(NodeType.getText()));
                    n.setLongName(String.valueOf(LongName.getText()));
                    n.setShortName(String.valueOf(ShortName.getText()));

                    //editedNode = n;
                    //topElements.getChildren().clear();


                    topElements.getChildren().clear();
                    displayNodes();
                    displayEdges();
                    displayNewNodes(n);

                }
            });
        }
    }

    public void revertNodeChanges() {
        if (!editedNode.getNodeID().equals("1")) {
            nodeMap.replace(editedNode.getNodeID(), editedNode);

            nodeMap.get(editedNode.getNodeID()).setX(editedNode.getX());
            nodeMap.get(editedNode.getNodeID()).setY(editedNode.getY());
            nodeMap.get(editedNode.getNodeID()).setFloor(editedNode.getFloor());
            nodeMap.get(editedNode.getNodeID()).setBuilding(editedNode.getBuilding());
            nodeMap.get(editedNode.getNodeID()).setNodeType(editedNode.getNodeType());
            nodeMap.get(editedNode.getNodeID()).setLongName(editedNode.getLongName());
            nodeMap.get(editedNode.getNodeID()).setShortName(editedNode.getShortName());

            nodeSet = nodeMap.values().stream().collect(Collectors.toSet());

            topElements.getChildren().clear();
            displayNodes();
            displayEdges();


        }
    }

    @FXML
    void changeEdgeEvent() {
        if (!(submitEdge.isVisible())) {
            edgeSet.forEach(e -> {
                if (e.getEdgeID().equals(selectEdge.getValue())) {
                    e.setStartNode(String.valueOf(startNode.getValue()));
                    e.setEndNode(String.valueOf(endNode.getValue()));
//                    System.out.println(startNode);
//                    System.out.println(endNode);
                }
            });
        }
    }

    public void addNode() {
        // TODO revert all changes to nodeMap when "submit edit node" button wasn't pressed
//        for (Node n : nodeSet) {
//            if( nodeMap.containsKey(n.getNodeID())){
//                System.out.println("here");
//            nodeMap.get(n.getNodeID()).setX(n.getX());
//            nodeMap.get(n.getNodeID()).setY(n.getY());
//            nodeMap.get(n.getNodeID()).setFloor(n.getFloor());
//            nodeMap.get(n.getNodeID()).setBuilding(n.getBuilding());
//            nodeMap.get(n.getNodeID()).setNodeType(n.getNodeType());
//            nodeMap.get(n.getNodeID()).setLongName(n.getLongName());
//            nodeMap.get(n.getNodeID()).setShortName(n.getShortName());}
//        }
        revertNodeChanges();
//        Node newNode = new Node(NodeID.getText(), Integer.parseInt(X.getText()), Integer.parseInt(Y.getText()), Floor.getText(), Building.getText(), NodeType.getText(), LongName.getText(), ShortName.getText());
//        nodeMap.put(newNode.getNodeID(), newNode);
//        System.out.println(newNode.getNodeID());
//        nodeSet.add(newNode);
//        Submit.getInstance().addNode(newNode);
//        //AddNodetoDatabase(just takes in a node)
//        //fetchFromDatabase =true;
//        displayNodes();
//        displayNewNodes(newNode);

        selectNode.setDisable(true);
        NodeID.setText("Enter Node ID");
        X.setText("");
        Y.setText("");
        Floor.setText("");
        Building.setText("");
        NodeType.setText("");
        ShortName.setText("");
        LongName.setText("");
        submitNode.setVisible(true);
        topElements.getChildren().clear();
        displayNodes();
        displayEdges();
        // Node newNode = new Node( );

    }

    public void addEdge() {
        NodeID.setText("Enter NodeID");
        selectEdge.setDisable(true);
        startNode.setValue("");
        endNode.setValue("");
        submitEdge.setVisible(true);
        // AddEdgeToDatabase("")
    }

    public void submitNode() {
        if (NodeID.getText().equals("Enter Node ID") || X.getText().equals("") || Y.getText().equals("")) {
            validID.setText("Please enter a valid Node");
            validID.setVisible(true);
        } else {
            selectNode.getItems().add(NodeID.getText());
            validID.setVisible(false);
            submitNode.setVisible(false);
            Node newNode = new Node(NodeID.getText(), Integer.parseInt(X.getText()), Integer.parseInt(Y.getText()), Floor.getText(), Building.getText(), NodeType.getText(), LongName.getText(), ShortName.getText());
            nodeMap.put(newNode.getNodeID(), newNode);
           // System.out.println(newNode.getNodeID());
            nodeSet.add(newNode);
            Submit.getInstance().addNode(newNode);
//            topElements.getChildren().clear();
//            ArrayList<Node> nodes= LocalStorage.getInstance().getNodes();
//            nodeSet = new HashSet<>(nodes);
           // displayNodes();
          //  displayEdges();

            startNode.getItems().add(newNode.getNodeID());
            endNode.getItems().add(newNode.getNodeID());
            //AddNodetoDatabase(just takes in a node)
            //fetchFromDatabase =true;
            displayNewNodes(newNode);
            NodeID.setText("");
            X.setText("");
            Y.setText("");
            Floor.setText("");
            Building.setText("");
            NodeType.setText("");
            ShortName.setText("");
            LongName.setText("");
            selectNode.setDisable(false);
        }
    }

    public void submitEdge() {
            validID1.setVisible(false);
            submitEdge.setVisible(false);
            String theID = (startNode.getValue() + "_" + endNode.getValue());
            selectEdge.getItems().add(theID);
            Edge newEdge = new Edge(theID, startNode.getValue(), endNode.getValue());
            edgeMap.put(newEdge.getEdgeID(), newEdge);
            edgeSet.add(newEdge);
            Submit.getInstance().addEdge(newEdge);
            topElements.getChildren().clear();
            ArrayList<Node> nodes= LocalStorage.getInstance().getNodes();
            nodeSet = new HashSet<>(nodes);
            topElements.getChildren().clear();
            displayNodes();
            displayEdges();
           // System.out.println(startNode.getValue());
           // System.out.println(endNode.getValue());
            displaySelectedEdge(String.valueOf(startNode.getValue()), String.valueOf(endNode.getValue()));
            //nodeMap.put(startNode)
            startNode.setValue("");
            endNode.setValue("");
            selectEdge.setDisable(false);


        // AddEdgeToDatabase(EdgeID.getText());
        // fetchFromDatabase = true;
        // topElements.getChildren().clear();
//        displayNodes();
//        displayEdges();
        // displayEdges();
    }

    @FXML
    void deleteNode() {
        if (submitNode.isVisible()) {
            validID.setText("Please add node first");
            validID.setVisible(true);
        } else {

            //nodeMap.remove(NodeID.getText());
            // System.out.println(NodeID.getText());
            Submit.getInstance().removeNode(nodeMap.get(NodeID.getText()));
            nodeSet.remove(nodeMap.get(NodeID.getText()));
           // nodeMap.remove(nodeMap.get(NodeID.getText()));

//            for(Node n: nodeSet){
//                if(n.getNodeID().equals(NodeID.getText())){
//                    System.out.println("did not get removed");
//                }
//            }

            nodeMap.remove(NodeID.getText());
            selectNode.getItems().remove(NodeID.getText());
            NodeID.setText("");
            X.setText("");
            Y.setText("");
            Floor.setText("");
            Building.setText("");
            NodeType.setText("");
            ShortName.setText("");
            LongName.setText("");
        }
        topElements.getChildren().clear();
        displayNodes();
        displayEdges();
       // System.out.println("reached here");
    }

    @FXML
    void deleteEdge() {
        if (submitEdge.isVisible()) {
            validID1.setText("Please add edge first");
            validID1.setVisible(true);
        } else {
            Submit.getInstance().removeEdge(edgeMap.get(selectEdge.getValue()));
            ArrayList<Node> nodes= LocalStorage.getInstance().getNodes();
            nodeSet = new HashSet<>(nodes);
            //System.out.println(edgeSet.contains(edgeMap.get(selectEdge.getValue())));
            edgeSet.remove(edgeMap.get(selectEdge.getValue()));
            //System.out.println(edgeSet.contains(edgeMap.get(selectEdge.getValue())));
            edgeMap.remove(selectEdge.getValue());
            selectEdge.getItems().remove(selectEdge.getValue());
            startNode.setValue("");
            endNode.setValue("");
            topElements.getChildren().clear();
            displayNodes();
            displayEdges();
        }
    }

    public void displayNodes() {

        //System.out.println("got here");
        rezisingInfo();
        // map.clear();
        if (!nodeMap.isEmpty()) {
            nodeMap.clear();
        }

        for (Node n : nodeSet) {
            //System.out.println(n.getEdges());
//            boolean display =  true;
//            if (n.getNodeID().equals(editedNode.getNodeID())) {
//               display = false;
//            }
            if ((n.getFloor().equals("1") || n.getFloor().equals("G") || n.getFloor().equals("")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") || n.getBuilding().equals(""))) {

                nodeMap.put(n.getNodeID(), n);
                Circle circle = new Circle(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio, 8);
                //System.out.println(fileFxWidthRatio);
                // System.out.println(fileFxHeightRatio);
                // circle = (Circle) clickNode(circle, n);
                circle.setFill(Color.OLIVE);
                topElements.getChildren().add(circle);
                //   System.out.println("ADDED");

            }
        }
    }

//    public void displayEdges() {
//        for (Node n : nodeSet) {
//            for (Node e : n.getEdges()) {
//                if (nodeMap.containsKey(e.getNodeID()) && nodeMap.containsKey(n.getNodeID())) {
//                    Line edge = LineBuilder.create().startX(n.getX() * fileFxWidthRatio).startY(n.getY() * fileFxHeightRatio).endX(nodeMap.get(e.getNodeID()).getX() * fileFxWidthRatio).endY(nodeMap.get(e.getNodeID()).getY() * fileFxHeightRatio).stroke(Color.BLUE).strokeWidth(3).build();
//                    topElements.getChildren().add(edge);
//                }
//            }
//        }
//    }

    public void displayEdges() {
      edgeSet.forEach(e -> {
          if(nodeMap.containsKey(e.getStartNode()) && nodeMap.containsKey(e.getEndNode())){
              Line edge = LineBuilder.create().startX(nodeMap.get(e.getStartNode()).getX() * fileFxWidthRatio).startY(nodeMap.get(e.getStartNode()).getY() * fileFxHeightRatio).endX(nodeMap.get(e.getEndNode()).getX() * fileFxWidthRatio).endY(nodeMap.get(e.getEndNode()).getY() * fileFxHeightRatio).stroke(Color.BLUE).strokeWidth(3).build();
              topElements.getChildren().add(edge);
          }
      });
    }

    public void displayNewEdges() {
        for (Node n : nodeSet) {
            for (Node e : n.getEdges()) {
                if (nodeMap.containsKey(e.getNodeID()) && nodeMap.containsKey(n.getNodeID())) {
                    Line edge = LineBuilder.create().startX(n.getX() * fileFxWidthRatio).startY(n.getY() * fileFxHeightRatio).endX(nodeMap.get(e.getNodeID()).getX() * fileFxWidthRatio).endY(nodeMap.get(e.getNodeID()).getY() * fileFxHeightRatio).stroke(Color.BLUE).strokeWidth(3).build();
                    topElements.getChildren().add(edge);
                }
            }
        }
    }

    public void rezisingInfo() {
        mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
        //System.out.println("mapWidth: " + mapWidth);
        mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
        // System.out.println("mapHeight: " + mapHeight);
        fileWidth = hospitalMap.getImage().getWidth();
        //System.out.println("fileWidth: " + fileWidth);
        fileHeight = hospitalMap.getImage().getHeight();
        // System.out.println("fileHeight: " + fileHeight);
        fileFxWidthRatio = mapWidth / fileWidth;
        fileFxHeightRatio = mapHeight / fileHeight;
    }

    public void exitApplication() {
        Shutdown.getInstance().exit();
    }

    public void displayNewNodes(Node n) {

        //System.out.println("got here");
        rezisingInfo();
        // map.clear();


        nodeMap.put(n.getNodeID(), n);
        Circle circle = new Circle(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio, 8);
        //System.out.println(fileFxWidthRatio);
        // System.out.println(fileFxHeightRatio);
        // circle = (Circle) clickNode(circle, n);
        circle.setFill(Color.RED);
        topElements.getChildren().add(circle);
        //   System.out.println("ADDED");

    }

    public void displaySelectedEdge(String startNodeID, String endNodeID) {


        // topElements.getChildren().clear();

       // System.out.println("got here");
        if(edgeMap.containsKey(startNodeID+ "_"+ endNodeID)) {
            Line line = LineBuilder.create().startX(nodeMap.get(startNodeID).getX() * fileFxWidthRatio).startY(nodeMap.get(startNodeID).getY() * fileFxHeightRatio).endX(nodeMap.get(endNodeID).getX() * fileFxWidthRatio).endY(nodeMap.get(endNodeID).getY() * fileFxHeightRatio).stroke(Color.RED).strokeWidth(3).build();
            topElements.getChildren().add(line);
            selectedEdge = line;
       }
        //topElements.getChildren()
    }

    public void submitEditedNode() {
//        nodeMap.values().forEach(n -> {
//            if (n.getNodeID().equals(selectNode.getValue())) {
//                n.setX(Integer.parseInt(X.getText()));
//                n.setY(Integer.parseInt(Y.getText()));
//                n.setFloor(String.valueOf(Floor.getText()));
//                n.setBuilding(String.valueOf(Building.getText()));
//                n.setNodeType(String.valueOf(NodeType.getText()));
//                n.setLongName(String.valueOf(LongName.getText()));
//                n.setShortName(String.valueOf(ShortName.getText()));
//                topElements.getChildren().clear();
//                displayNodes();
//                displayEdges();
//                displayNewNodes(n);
//            }
        // });
        Submit.getInstance().editNode(nodeMap.get(editedNode.getNodeID()));

        editedNode = new Node("1", 1, 1);

    }

    public void submitEditedEdge() {
        edgeSet.forEach(e -> {
            if (e.getEdgeID().equals(selectEdge.getValue())) {
                String oldID = selectEdge.getValue(); //saving old ID
                selectEdge.getItems().remove(oldID); //removing from combo box
                e.setEdgeID(startNode.getValue() + "_" + endNode.getValue()); //setting to new ID name
                selectEdge.getItems().add(e.getEdgeID()); //adding to combo box
//                edgeMap.get(oldID).setEdgeID(EdgeID.getText());
                edgeMap.get(oldID).setStartNode(startNode.getValue());  //updating start node in edge set
                edgeMap.get(oldID).setEndNode(endNode.getValue());   //updating end node
                e.setEndNode(startNode.getValue());
                e.setEndNode(endNode.getValue());
                topElements.getChildren().clear();
                displayNodes();
                displayEdges();
            }
        });
    }

    public void returnHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/DefaultPage.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
//            if (AuthenticationManager.getInstance().isAuthenticated()) {
//                SceneManager.getInstance().getDefaultPage().closeWindows();
//            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
