package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
    private JFXTextField StartNode;
    @FXML
    private JFXTextField EndNode;
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
    void saveNodes() {

    }

    @FXML
    void loadFileNode() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV Files", "csv");
        chooser.setFileFilter(filter);
        int chosenVal = chooser.showOpenDialog(null);
        List<List<String>> allNodesData = CSVOperator.readFile(chooser.getSelectedFile().getAbsolutePath()); // Load new CSV
        Set<List<String>> NodeDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(NodeDataAsSet);
        theNodes = allNodesData;
        theNodes.forEach(n -> {
            selectNode.getItems().add(n.get(0));
        });
    }

    @FXML
    void loadFileEdge() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV Files", "csv");
        chooser.setFileFilter(filter);
        int chosenVal = chooser.showOpenDialog(null);
        List<List<String>> allEdgesData = CSVOperator.readFile(chooser.getSelectedFile().getAbsolutePath()); // Load new CSV
        Set<List<String>> edgesDataAsSet = new HashSet<>(allEdgesData); // to avoid duplicate elements
        allEdgesData.clear();
        allEdgesData.addAll(edgesDataAsSet);
        theEdges = allEdgesData;
        allEdgesData.forEach(n -> {
            selectEdge.getItems().add(n.get(0));
        });
    }

    public void enterEdges() {
        for (Node n : listOfNodes) {
            for (Node e : n.getEdges()) {
                if (nodeMap.containsKey(e.getNodeID()) && nodeMap.containsKey(n.getNodeID())) {
                    selectEdge.getItems().add(n.getNodeID() + "_" + e.getNodeID());
                }
            }
        }
    }

    @FXML
    public void fillFieldsNode() {
        theNodes.forEach(n -> {
            if (n.get(0).equals(selectNode.getValue())) {
                NodeID.setText(n.get(0));
                X.setText(String.valueOf(n.get(1)));
                Y.setText(String.valueOf(n.get(2)));
                Floor.setText(n.get(3));
                Building.setText(n.get(4));
                NodeType.setText(n.get(5));
                ShortName.setText(n.get(7));
                LongName.setText(n.get(6));
            }
        });
    }

    @FXML
    void changeNodeEvent() {
        if (!(submitNode.isVisible())) {
            theNodes.forEach(n -> {
                if (n.get(0).equals(selectNode.getValue())) {
                    n.set(1, String.valueOf(X.getText()));
                    n.set(2, String.valueOf(Y.getText()));
                    n.set(3, String.valueOf(Floor.getText()));
                    n.set(4, String.valueOf(Building.getText()));
                    n.set(5, String.valueOf(NodeType.getText()));
                    n.set(7, String.valueOf(ShortName.getText()));
                    n.set(6, String.valueOf(LongName.getText()));
                }

            });
        }

    }


    @FXML
    public void fillFieldsEdge() {
        theEdges.forEach(n -> {
            if (n.get(0).equals(selectEdge.getValue())) {
                EdgeID.setText(n.get(0));
                StartNode.setText(n.get(1));
                EndNode.setText(n.get(2));

            }
        });
    }

    @FXML
    void changeEdgeEvent() {
        if (!(submitEdge.isVisible())) {
            theEdges.forEach(e -> {
                if (e.get(0).equals(selectEdge.getValue())) {
                    e.set(1, String.valueOf(StartNode.getText()));
                    e.set(2, String.valueOf(EndNode.getText()));
                }
            });
        }
    }

    public void LongName(ActionEvent actionEvent) {
    }

    public void ShortName(ActionEvent actionEvent) {
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

    public void submitNode() {
        if (NodeID.getText().equals("Enter Node ID")) {
            validID.setText("Please enter a valid ID");
            validID.setVisible(true);
        } else {
            selectNode.getItems().add(NodeID.getText());
            validID.setVisible(false);
            submitNode.setVisible(false);
            List<String> addedInfo = new ArrayList<String>();
            addedInfo.add(NodeID.getText());
            addedInfo.add(X.getText());
            addedInfo.add(Y.getText());
            addedInfo.add(Floor.getText());
            addedInfo.add(Building.getText());
            addedInfo.add(NodeType.getText());
            addedInfo.add(LongName.getText());
            addedInfo.add(ShortName.getText());
            theNodes.add(addedInfo);
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

    public void addEdge() {
        NodeID.setText("Enter NodeID");
        selectEdge.setDisable(true);
        EdgeID.setText("Enter Edge ID");
        StartNode.setText("");
        EndNode.setText("");
        submitEdge.setVisible(true);
    }

    public void submitEdge() {
        if (EdgeID.getText().equals("Enter Edge ID")) {
            validID1.setVisible(true);
            validID1.setText("Please enter a valid ID");

        } else {
            selectEdge.getItems().add(EdgeID.getText());
            validID1.setVisible(false);
            submitEdge.setVisible(false);
            List<String> addedInfo = new ArrayList<String>();
            addedInfo.add(EdgeID.getText());
            addedInfo.add(StartNode.getText());
            addedInfo.add(EndNode.getText());
            theEdges.add(addedInfo);
            EdgeID.setText("");
            StartNode.setText("");
            EndNode.setText("");
            selectEdge.setDisable(false);
        }
    }


    @FXML
    void deleteEdge() {
        if (submitEdge.isVisible()) {
            validID1.setText("Please add edge first");
            validID1.setVisible(true);
        } else {
            selectEdge.getItems().remove(EdgeID.getText());
            EdgeID.setText("");
            StartNode.setText("");
            EndNode.setText("");
        }


    }

    @FXML
    void deleteNode() {
        if (submitNode.isVisible()) {
            validID.setText("Please add node first");
            validID.setVisible(true);
        } else {
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
