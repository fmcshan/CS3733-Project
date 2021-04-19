
package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.App;
//import edu.wpi.teamname.Database.DatabaseThread;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class MapEditor implements Initializable {

    //    @FXML
//    private Path tonysPath;
    @FXML
    private AnchorPane anchor, topElements;

    //    @FXML
//    private ComboBox<String> toCombo;
//    @FXML
//    private ComboBox<String> fromCombo;
    @FXML
    private ImageView hospitalMap;
    Node nodeBeingDragged;
    Node newNode;
    Node previousNodeDragged;

    // ArrayList<Node> listOfNodes = new ArrayList<>();
    HashMap<String, Node> nodesMap = new HashMap<>();
    ArrayList<Node> currentPath = new ArrayList<>();
    double mapWidth; //= 1000.0;
    double mapHeight;// = 680.0;
    double fileWidth; //= 5000.0;
    double fileHeight;// = 3400.0;
    double fileFxWidthRatio = mapWidth / fileWidth;
    double fileFxHeightRatio = mapHeight / fileHeight;
    Node startNode;
    Node endNode;
    boolean start = true;
    boolean once = true;
    HashMap<String, Node> map = new HashMap();
    ArrayList<Node> nodesReferenceList;
    Set<Node> nodeSet = new HashSet<>();

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {



        if (once == true) {
            nodesReferenceList = PathFindingDatabaseManager.getInstance().getNodes();
             nodeSet = new HashSet<>(nodesReferenceList);
             nodeSet.addAll(nodesReferenceList);
            once = false;
        }
        rezisingInfo();
        OnWindowSizeChanged();
        // listOfNodes = PathFindingDatabaseManager.getInstance().getNodes();

        hospitalMap.fitWidthProperty().bind(anchor.widthProperty());
        hospitalMap.fitHeightProperty().bind(anchor.heightProperty());

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

    public void updateMap() {
        topElements.getChildren().clear();
        rezisingInfo();
        displayNodes();
        displayEdges();
    }

    public void OnWindowSizeChanged() {

        anchor.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                //  drawPath(currentPath);
            }

           updateMap();
        });

        anchor.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                // drawPath(currentPath);
            }


         updateMap();
        });

    }

//        Path path = new Path(start, new LineTo(firstNode.getX() * fileFxWidthRatio, firstNode.getY() * fileFxHeightRatio));
//        path.setFill(Color.TOMATO);
//        path.setStrokeWidth(4);


    public void returnHome(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/DefaultPage.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void displayNodes() {

        //System.out.println("got here");
        rezisingInfo();
        map.clear();
        ArrayList<Node> nodesList = new ArrayList<>(nodeSet);
        for (Node n : nodesList) {

            map.put(n.getNodeID(), n);
            //   System.out.println(n.getNodeType());
            Circle circle = new Circle(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio, 8);
            //System.out.println(fileFxWidthRatio);
           // System.out.println(fileFxHeightRatio);
            circle = (Circle) clickNode(circle, n);
            circle.setFill(Color.OLIVE);
            topElements.getChildren().add(circle);
            //   System.out.println("ADDED");
        }


        if (startNode != null) {
            Circle startCircle = new Circle(startNode.getX() * fileFxWidthRatio, startNode.getY() * fileFxHeightRatio, 8);
            startCircle.setFill(Color.MAGENTA);
            topElements.getChildren().add(startCircle);
        }
        if (endNode != null) {
            Circle endCircle = new Circle(endNode.getX() * fileFxWidthRatio, endNode.getY() * fileFxHeightRatio, 8);
            endCircle.setFill(Color.MAROON);
            topElements.getChildren().add(endCircle);
        }
    }

    public void displayEdges() {
        if (newNode != null) {
            //nodesReferenceList.add(0, newNode);
            nodeSet.add(newNode);
            map.put(newNode.getNodeID(),newNode);
        }
        ArrayList<Node> nodesList = new ArrayList<>(nodeSet);
        for (Node n : nodesList) {

            for (Node e : n.getEdges()) {
                if (map.containsKey(e.getNodeID()) && map.containsKey(n.getNodeID())) {
                    Line edge = LineBuilder.create().startX(n.getX() * fileFxWidthRatio).startY(n.getY() * fileFxHeightRatio).endX(map.get(e.getNodeID()).getX() * fileFxWidthRatio).endY(map.get(e.getNodeID()).getY() * fileFxHeightRatio).stroke(Color.BLUE).strokeWidth(3).build();
                    topElements.getChildren().add(edge);
                }
            }
        }
//        if (newNode != null&&nodeBeingDragged.getNodeID().equals(newNode.getNodeID())) {
//     nodesReferenceList.remove(0);
//            }
    }

    int newNodeINdex;



    protected javafx.scene.Node clickNode(javafx.scene.Node circle, Node node) {

        circle.setOnMouseClicked(
                t -> {
                    circle.setStyle("-fx-cursor: hand");
                });

        circle.setOnMouseDragged(t -> {
            Circle newCircle = (Circle) topElements.getChildren().get(topElements.getChildren().indexOf(circle));
            newCircle.setFill(Color.DARKORCHID);
            newCircle.setCenterX(t.getX());
            newCircle.setCenterY(t.getY());
//            System.out.print("new x "+ t.getX()/fileFxWidthRatio + "\t");
//            System.out.println("new y "+ t.getY()/fileFxHeightRatio);
//            System.out.print("old x " + node.getX() +"\t");
//            System.out.println("new Y "+ node.getY());
            nodeBeingDragged = node;

            if (nodesReferenceList.contains(node)|| nodeSet.contains(node))
            {//nodesReferenceList.remove(node);
            nodeSet.remove(node);}

            newNode = new Node(node.getNodeID(), (int) (newCircle.getCenterX() / fileFxWidthRatio), (int) (newCircle.getCenterY() / fileFxHeightRatio), node.getFloor(), node.getBuilding(),
                    node.getNodeType(), node.getLongName(), node.getShortName());
            newNode.setEdges(node.getEdges());
//            if (!nodesReferenceList.isEmpty()) { //&& nodes.contains(node)
//                newNodeINdex = nodesReferenceList.indexOf(node);
//               // nodesReferenceList.remove(newNodeINdex);
//            }
            for (int i = 0; i < topElements.getChildren().size(); i++) {
                if (topElements.getChildren().get(i) instanceof Line) {
                    topElements.getChildren().remove(i);

                }
            }
            //displayNodes();
            displayEdges();
        });
        if (newNode != null) {
            //nodes.remove(node);
            System.out.println("got into if");
          //  nodesReferenceList.set(newNodeINdex, newNode);
            nodeSet.add(newNode);
          //displayEdges();
            //updateMap();

            // List<Node> nodesToBeUpdated= getNodesWithThisNodeAsEdge(nodesReferenceList, nodeBeingDragged);
//            for (Node e:nodesToBeUpdated){
//            System.out.println(e.getLongName());};
            // changeEdges(nodesToBeUpdated,nodeBeingDragged,newNode);
        }
        //updateMap();
        circle.setStyle("-fx-cursor: hand");
        return circle;
    }


    public List<Node> getNodesWithThisNodeAsEdge(List<Node> nodes, Node connectedNode) {
        return nodes.stream()
                .filter(n -> n.getEdges().contains(connectedNode))
                .collect(Collectors.toList());
    }

    private void changeEdges(List<Node> nodes, Node currentConnectedNode, Node newConnectedNode) {
        List<Node> newNodesList = nodesReferenceList;
        for (Node n : nodes) {
            ArrayList<Node> previousEdges = n.getEdges();
            for (int i = 0; i < previousEdges.size(); i++) {
                if (previousEdges.get(i).getNodeID().equals(currentConnectedNode)) {
                    previousEdges.set(i, newConnectedNode);
                }

            }
            nodesReferenceList.forEach(l -> {
                if (l.getNodeID().equals(n.getNodeID())) {
                    l.setEdges(previousEdges);
                }
            });
        }
//        for(Node n: nodes){
//
//
//        }
//
//            nodesReferenceList.forEach(l -> l.setEdges())
//            nodes.set(newNodeINdex,newNode);
//        }


    }
}

