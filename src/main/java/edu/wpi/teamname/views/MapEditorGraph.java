package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class MapEditorGraph {

    double mapWidth; //= 1000.0;
    double mapHeight;// = 680.0;
    double fileWidth; //= 5000.0;
    double fileHeight;// = 3400.0;
    double fileFxWidthRatio = mapWidth / fileWidth;
    double fileFxHeightRatio = mapHeight / fileHeight;
    ArrayList<Node> nodes;
    ArrayList<Node> localNodes = new ArrayList<Node>(); // Nodes within current parameters (IE: floor)
    ArrayList<Edge> edges;
    ArrayList<Edge> localEdges = new ArrayList<Edge>(); // Edges within current parameters (IE: floor)
    HashMap<String, Node> nodesMap = new HashMap<>();
    HashMap<String, Edge> edgesMap = new HashMap<>();

    private boolean addNodeMenuOpen = false;

    @FXML
    private AnchorPane anchor;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView hospitalMap;
    @FXML
    private AnchorPane topElements;
    @FXML
    private JFXTextField nodeFile;
    @FXML
    private JFXTextField edgeFile;
    @FXML
    private Label validID;
    @FXML
    private JFXComboBox<String> selectNode;
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
    private JFXTextField LongName;
    @FXML
    private JFXTextField ShortName;
    @FXML
    private Label validID1;
    @FXML
    private JFXComboBox<String> selectEdge;
    @FXML
    private JFXComboBox<String> startNode;
    @FXML
    private JFXComboBox<String> endNode;
    @FXML
    private JFXButton submitEdge;
    @FXML
    private JFXButton submitNode;
    @FXML
    private JFXButton submitEdge1;
    @FXML
    private AnchorPane addNodeField;

    public void initialize() {
        refreshData();
        addNodeField.setPickOnBounds(false);
        addNodeField.setVisible(false);

        stackPane.onMouseClickedProperty().set((EventHandler<MouseEvent>) this::openAddNodePopup);

        anchor.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if(t.getCode()== KeyCode.ESCAPE)
                {hideAddNodePopup();}
            }
        });
    }

    private void refreshData() {
        nodes = LocalStorage.getInstance().getNodes();
        nodes.forEach(n -> {
            if (nodeWithinSpec(n)) {
                localNodes.add(n);
            }
        });

        selectNode.getItems().clear();
        startNode.getItems().clear();
        endNode.getItems().clear();

        localNodes.forEach(n -> {
            nodesMap.put(n.getNodeID(), n);
            selectNode.getItems().add(n.getNodeID());
            startNode.getItems().add(n.getNodeID());
            endNode.getItems().add(n.getNodeID());
        });

        edges = LocalStorage.getInstance().getEdges();
        edges.forEach(e -> {
            if (nodesMap.containsKey(e.getStartNode()) && nodesMap.containsKey(e.getEndNode())) {
                if (nodeWithinSpec(nodesMap.get(e.getStartNode())) && nodeWithinSpec(nodesMap.get(e.getEndNode()))) {
                    localEdges.add(e);
                }
            }
        });

        localEdges.forEach(e -> {
            edgesMap.put(e.getEdgeID(), e);
            selectEdge.getItems().add(e.getEdgeID());
        });

        displayEdges(0.8);
        displayNodes(0.6);
    }

    public void resizingInfo() {
        mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
        mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
        fileWidth = hospitalMap.getImage().getWidth();
        fileHeight = hospitalMap.getImage().getHeight();
        fileFxWidthRatio = mapWidth / fileWidth;
        fileFxHeightRatio = mapHeight / fileHeight;
    }

    private void openAddNodePopup(MouseEvent t) {
        addNodeField.setPickOnBounds(true);
        addNodeField.setVisible(true);

        if (t.getY() < anchor.getHeight()/2) {
            addNodeField.setLayoutY(t.getY() + 20);
        } else {
            addNodeField.setLayoutY(t.getY() - addNodeField.getHeight() - 20);
        }

        if (t.getX() < anchor.getWidth()/2) {
            addNodeField.setLayoutX(t.getX() + (2 * addNodeField.getWidth()) - 45 - (0.5 * addNodeField.getWidth()));
        } else {
            addNodeField.setLayoutX(t.getX() + addNodeField.getWidth() - 45 + (0.5 * addNodeField.getWidth()));
        }
    }

    private void hideAddNodePopup() {
        addNodeField.setPickOnBounds(false);
        addNodeField.setVisible(false);
    }

    public boolean nodeWithinSpec(Node n) {
        return ((n.getFloor().equals("1") || n.getFloor().equals("G") || n.getFloor().equals("")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") || n.getBuilding().equals("")));
    }

    public void displayNodes(double _opacity) {
        resizingInfo();

        localNodes.forEach(n -> {
            Circle circle = new Circle(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio, 8);
            circle.setStrokeWidth(4);
            circle.setStroke(Color.TRANSPARENT);
            circle.setFill(Color.OLIVE);
            circle.setOpacity(_opacity);
            topElements.getChildren().add(circle);

            circle.setOnMouseEntered(e -> {
                circle.setRadius(12);
                circle.setOpacity(1);
            });

            circle.setOnMouseExited(e -> {
                circle.setRadius(8);
                circle.setOpacity(0.6);
            });
        });
    }

    public void displayEdges(double _opacity) {
        resizingInfo();
        edges.forEach(e -> {
            if (nodesMap.containsKey(e.getStartNode()) && nodesMap.containsKey(e.getEndNode())) {
                LineBuilder<?> edgeLocation = LineBuilder.create().startX(nodesMap.get(e.getStartNode()).getX() * fileFxWidthRatio).startY(nodesMap.get(e.getStartNode()).getY() * fileFxHeightRatio).endX(nodesMap.get(e.getEndNode()).getX() * fileFxWidthRatio).endY(nodesMap.get(e.getEndNode()).getY() * fileFxHeightRatio);
                Line edge = edgeLocation.stroke(Color.BLUE).strokeWidth(3).opacity(_opacity).build();
                topElements.getChildren().add(edge);

                edge.setOnMouseEntered(t -> {
                    edge.setStrokeWidth(6);
                    edge.setOpacity(1);
                });

                edge.setOnMouseExited(t -> {
                    edge.setOpacity(_opacity);
                    edge.setStrokeWidth(3);
                });
            }
        });
    }

    public void displaySelectedNode(Node n) {
        Circle circle = new Circle(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio, 11);
        topElements.getChildren().add(circle);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.OLIVE);
    }

    public void displaySelectedEdge(Edge e) {
        System.out.println(e.getStartNode());
        System.out.println(e.getEndNode());
        System.out.println(nodesMap.get(e.getStartNode()));
        System.out.println(nodesMap.get(e.getEndNode()));
        LineBuilder<?> edgeLocation = LineBuilder.create().startX(nodesMap.get(e.getStartNode()).getX() * fileFxWidthRatio).startY(nodesMap.get(e.getStartNode()).getY() * fileFxHeightRatio).endX(nodesMap.get(e.getEndNode()).getX() * fileFxWidthRatio).endY(nodesMap.get(e.getEndNode()).getY() * fileFxHeightRatio);
        Line edge = edgeLocation.stroke(Color.BLUE).strokeWidth(3).opacity(1).build();
        topElements.getChildren().add(edge);
    }


    @FXML
    void addEdge(ActionEvent event) {

    }

    @FXML
    void addNode(ActionEvent event) {

    }

    @FXML
    void changeEdgeEvent(ActionEvent event) {

    }

    @FXML
    void changeNodeEvent(ActionEvent event) {

    }

    @FXML
    void deleteEdge(ActionEvent event) {

    }

    @FXML
    void deleteNode(ActionEvent event) {

    }

    @FXML
    void exitApplication(ActionEvent event) {

    }

    @FXML
    void fillFieldsEdge(ActionEvent event) {
        Edge e = edgesMap.get(selectEdge.getValue());
        startNode.setValue(e.getStartNode());
        endNode.setValue(e.getEndNode());
        topElements.getChildren().clear();

        displayNodes(0.6);
        displayEdges(0.2);
        displaySelectedEdge(e);
    }

    @FXML
    void fillFieldsNode(ActionEvent event) {
        Node n = nodesMap.get(selectNode.getValue());
        NodeID.setText(n.getNodeID());
        X.setText(String.valueOf(n.getX()));
        Y.setText(String.valueOf(n.getY()));
        Floor.setText(n.getFloor());
        Building.setText(n.getBuilding());
        NodeType.setText(n.getNodeType());
        LongName.setText(n.getLongName());
        ShortName.setText(n.getShortName());
        topElements.getChildren().clear();

        displayNodes(0.6);
        displayEdges(0.2);
        displaySelectedNode(n);
    }

    @FXML
    void loadFileEdge(ActionEvent event) {

    }

    @FXML
    void loadFileNode(ActionEvent event) {

    }

    @FXML
    void returnHome(ActionEvent event) {

    }

    @FXML
    void saveEdges(ActionEvent event) {

    }

    @FXML
    void saveNodes(ActionEvent event) {

    }

    @FXML
    void submitEdge(ActionEvent event) {

    }

    @FXML
    void submitEditedEdge(ActionEvent event) {

    }

    @FXML
    void submitEditedNode(ActionEvent event) {

    }

    @FXML
    void submitNode(ActionEvent event) {

    }

}
