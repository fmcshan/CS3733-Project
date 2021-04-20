package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.CSVOperator;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import edu.wpi.teamname.simplify.Shutdown;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.*;


public class MapEditorGraph {
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
    private JFXButton addEdge;
    @FXML
    private JFXButton submitEdge;
    @FXML
    private Label validID1;
    @FXML
    private ImageView hospitalMap;
    @FXML
    private AnchorPane topElements;

    double mapWidth; //= 1000.0;
    double mapHeight;// = 680.0;
    double fileWidth; //= 5000.0;
    double fileHeight;// = 3400.0;
    double fileFxWidthRatio = mapWidth / fileWidth;
    double fileFxHeightRatio = mapHeight / fileHeight;
    ArrayList<Node> listOfNodes = PathFindingDatabaseManager.getInstance().getNodes();
    List<List<String>> theEdges;
    List<List<String>> theNodes;
    HashMap<String, Node> nodeMap = new HashMap<>();
    HashMap<String, Edge> edgeMap = new HashMap<>();

    public void initialize() {

        displayNodes();
        displayEdges();

        listOfNodes.forEach(n -> {
            if (((n.getFloor().equals("1") || n.getFloor().equals("G")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") ))) {
                selectNode.getItems().add(n.getNodeID());
            }
        });

        enterEdges();

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
        theNodes = allNodesData;
        theNodes.forEach(n -> {
            selectNode.getItems().add(n.get(0));
        });
    }

    @FXML
    void loadFileEdge() {
        selectEdge.getItems().clear();
        edgeMap.clear();
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
            startNode.getItems().clear();
            endNode.getItems().clear();
            startNode.getItems().add(n.get(1));
            endNode.getItems().add(n.get(2));
        });
        theEdges = allEdgesData;
        allEdgesData.forEach(n -> {
            selectEdge.getItems().add(n.get(0));
        });
    }

    public void enterEdges() {
        for (Node n : listOfNodes) {
            for (Node e : n.getEdges()) {
                if (nodeMap.containsKey(e.getNodeID()) && nodeMap.containsKey(n.getNodeID())) {
                    Edge edge = new Edge(n.getNodeID() + "_" + e.getNodeID(), n.getNodeID(), e.getNodeID());
                    if (!edgeMap.containsKey(edge.getEdgeID())) {
                        edgeMap.put(edge.getEdgeID(), edge);
                        selectEdge.getItems().add(n.getNodeID() + "_" + e.getNodeID());
                        startNode.getItems().add(n.getNodeID());
                        endNode.getItems().add(e.getNodeID());
//                        System.out.println(startNode.getItems().size());
//                        System.out.println(endNode.getItems().size());
                    }
                }
            }
        }
    }

    @FXML
    void saveNodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodeMap.values().forEach(n -> {
            nodes.add((Node) n);
        });
        CSVOperator.writeNodeCSV(nodes, nodeFile.getText()); // Write nodes to csv
    }

    @FXML
    void saveEdges() {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        edgeMap.values().forEach(e -> {
            edges.add((Edge) e);
        });
        CSVOperator.writeEdgeCSV(edges, edgeFile.getText()); // Write nodes to csv
    }

    @FXML
    public void fillFieldsNode() {
        nodeMap.values().forEach(n -> {
            if (n.getNodeID().equals(selectNode.getValue())) {
                NodeID.setText(n.getNodeID());
                X.setText(String.valueOf(n.getX()));
                Y.setText(String.valueOf(n.getY()));
                Floor.setText(n.getFloor());
                Building.setText(n.getBuilding());
                NodeType.setText(n.getNodeType());
                LongName.setText(n.getLongName());
                ShortName.setText(n.getShortName());
            }
        });
    }

    @FXML
    public void fillFieldsEdge() {
        edgeMap.values().forEach(n -> {
            if (n.getEdgeID().equals(selectEdge.getValue())) {
                EdgeID.setText(n.getEdgeID());
                startNode.setValue(n.getStartNode());
                endNode.setValue(n.getEndNode());
                System.out.println(n.getStartNode());
                System.out.println(n.getEndNode());
            }
        });
    }

    @FXML
    void changeNodeEvent() {
        if (!(submitNode.isVisible())) {
            nodeMap.values().forEach(n -> {
                if (n.getNodeID().equals(selectNode.getValue())) {
                    n.setX(Integer.parseInt(X.getText()));
                    n.setY(Integer.parseInt(Y.getText()));
                    n.setFloor(String.valueOf(Floor.getText()));
                    n.setBuilding(String.valueOf(Building.getText()));
                    n.setNodeType(String.valueOf(NodeType.getText()));
                    n.setLongName(String.valueOf(LongName.getText()));
                    n.setShortName(String.valueOf(ShortName.getText()));
                }
            });
        }
    }

    @FXML
    void changeEdgeEvent() {
        if (!(submitEdge.isVisible())) {
            edgeMap.values().forEach(e -> {
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
    }

    public void addEdge() {
        NodeID.setText("Enter NodeID");
        selectEdge.setDisable(true);
        EdgeID.setText("Enter Edge ID");
        startNode.setValue("");
        endNode.setValue("");
        submitEdge.setVisible(true);
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
        if (EdgeID.getText().equals("Enter Edge ID")) {
            validID1.setVisible(true);
            validID1.setText("Please enter a valid Edge");
        } else {
            selectEdge.getItems().add(EdgeID.getText());
            validID1.setVisible(false);
            submitEdge.setVisible(false);
            Edge newEdge = new Edge(EdgeID.getText(), startNode.getValue(), endNode.getValue());
            edgeMap.put(newEdge.getEdgeID(), newEdge);
            EdgeID.setText("");
            startNode.setValue("");
            endNode.setValue("");
            selectEdge.setDisable(false);
        }
    }

    @FXML
    void deleteNode() {
        if (submitNode.isVisible()) {
            validID.setText("Please add node first");
            validID.setVisible(true);
        } else {
            selectNode.getItems().remove(NodeID.getText());
            nodeMap.remove(NodeID.getText());
            NodeID.setText("");
            X.setText("");
            Y.setText("");
            Floor.setText("");
            Building.setText("");
            NodeType.setText("");
            ShortName.setText("");
            LongName.setText("");
        }
    }

    @FXML
    void deleteEdge() {
        if (submitEdge.isVisible()) {
            validID1.setText("Please add edge first");
            validID1.setVisible(true);
        } else {
            selectEdge.getItems().remove(EdgeID.getText());
            edgeMap.remove(EdgeID.getText());
            EdgeID.setText("");
            startNode.setValue("");
            endNode.setValue("");
        }
    }

    public void displayNodes() {

        //System.out.println("got here");
        rezisingInfo();
        // map.clear();

        for (Node n : listOfNodes) {
            if (((n.getFloor().equals("1") || n.getFloor().equals("G")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") ))) {
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

    public void displayEdges() {
        for (Node n : listOfNodes) {
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
}
