//
//package edu.wpi.teamname.views;
//
//import com.jfoenix.controls.JFXComboBox;
//import edu.wpi.teamname.Algo.AStar;
//import edu.wpi.teamname.Algo.Node;
//import edu.wpi.teamname.App;
//import edu.wpi.teamname.Database.PathFindingDatabaseManager;
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.control.ListCell;
//import javafx.scene.control.ListView;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.*;
//import javafx.util.Callback;
//import javafx.util.StringConverter;
//
//import javax.swing.*;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Observable;
//
//
//public class MapEditor {
//
//    @FXML
//    private Path tonysPath;
//    @FXML
//    private AnchorPane anchor;
//    @FXML
//    private JFXComboBox<String> toCombo;
//    @FXML
//    private JFXComboBox<String> fromCombo;
//    @FXML
//    private ImageView hospitalMap;
//   // @FXML
//   // private AnchorPane topElements;
//  //  @FXML
//  //  private StackPane stackPane;
//
//    ArrayList<Node> listOfNodes = new ArrayList<>();
//    HashMap<String, Node> nodesMap = new HashMap<>();
//    ArrayList<Node> currentPath = new ArrayList<>();
//
//    double mapWidth;
//    double mapHeight;
//    double fileWidth;
//    double fileHeight;
//    double fileFxWidthRatio;
//    double fileFxHeightRatio;
//
//@FXML
//    public void initialize() {
//
//
//        tonysPath.getElements().clear();
//
////        for( int i=0;i<anchor.getChildren().size(); i++){ //javafx.scene.Node n: anchor.getChildren()){
////
////            if(anchor.getChildren().get(i) instanceof Circle){
////                anchor.getChildren().remove(1);
////            }
////
////    }
//
//        anchor.widthProperty().addListener((obs, oldVal, newVal) -> {
//            if (currentPath.size() >= 0) {
//                drawPath(currentPath);
//            }
//        });
//
//        anchor.heightProperty().addListener((obs, oldVal, newVal) -> {
//            if (currentPath.size() >= 0) {
//                drawPath(currentPath);
//            }
//        });
//
//        listOfNodes = PathFindingDatabaseManager.getInstance().getNodes();
//
//        listOfNodes.forEach(n -> {
//            nodesMap.put(n.getNodeID(), n);
//            toCombo.getItems().add(n.getNodeID());
//            fromCombo.getItems().add(n.getNodeID());
//        });
//
//        hospitalMap.fitWidthProperty().bind(anchor.widthProperty());
//        hospitalMap.fitHeightProperty().bind(anchor.heightProperty());
////    hospitalMap.fitWidthProperty().bind(anchor.widthProperty());
////   hospitalMap.fitHeightProperty().bind(anchor.heightProperty());
//
//        setResizing();
//        displayNodes();
//
////        mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
////        mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
////        fileWidth = hospitalMap.getImage().getWidth();
////        fileHeight = hospitalMap.getImage().getHeight();
////        fileFxWidthRatio = mapWidth / fileWidth;
////        fileFxHeightRatio = mapHeight / fileHeight;
//
//
//    }
//
//    public void setResizing(){
//
//
////        mapWidth = stackPane.boundsInParentProperty().get().getWidth();
////        mapHeight = stackPane.boundsInParentProperty().get().getHeight();
//        mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
//        mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
//        fileWidth = hospitalMap.getImage().getWidth();
//        fileHeight = hospitalMap.getImage().getHeight();
//        fileFxWidthRatio = mapWidth / fileWidth;
//        fileFxHeightRatio = mapHeight / fileHeight;
//
//    }
//    public void drawPath(ArrayList<Node> listOfNodes) {
//        if (listOfNodes.size() < 1) {
//            return;
//        }
//        currentPath = listOfNodes;
//        tonysPath.getElements().clear();
//       setResizing();
//        Node firstNode = listOfNodes.get(0);
////        MoveTo start = new MoveTo(firstNode.getX() * fileFxWidthRatio, firstNode.getY() * fileFxHeightRatio);
//        MoveTo start = new MoveTo(firstNode.getX(), firstNode.getY());
//
//        Collection<LineTo> collection = new ArrayList<>();
//        tonysPath.getElements().add(start);
//        System.out.println(fileFxWidthRatio);
//        listOfNodes.forEach(n -> {
////            tonysPath.getElements().add(new LineTo(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio)
//
//            tonysPath.getElements().add(new LineTo(n.getX(), n.getY()));
//        });
//        Path path = new Path(start, new LineTo(firstNode.getX() * fileFxWidthRatio, firstNode.getY() * fileFxHeightRatio));
//        path.setFill(Color.TOMATO);
//        path.setStrokeWidth(4);
//        displayNodes ();
//    }
//
//    public void returnHome(ActionEvent actionEvent) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/DefaultPage.fxml"));
//            App.getPrimaryStage().getScene().setRoot(root);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public void exitApplication(ActionEvent actionEvent) {
//        Platform.exit();
//    }
//
//    public void calcPath(ActionEvent actionEvent) {
//        if (fromCombo.getValue() == null || !nodesMap.containsKey(fromCombo.getValue())) {
//            return;
//        }
//        if (toCombo.getValue() == null || !nodesMap.containsKey(toCombo.getValue())) {
//            return;
//        }
//        Node startNode = nodesMap.get(fromCombo.getValue());
//        Node endNode = nodesMap.get(toCombo.getValue());
//        Circle startCircle = new Circle(startNode.getX() * fileFxWidthRatio,startNode.getY()* fileFxHeightRatio,10);
//        startCircle.setFill(Color.RED);
//        Circle endCircle = new Circle(endNode.getX() * fileFxWidthRatio,endNode.getY()* fileFxHeightRatio,10);
//        endCircle.setFill(Color.BLUE);
//       // anchor.getChildren().add(startCircle);
//       // anchor.getChildren().add(endCircle);
//        AStar AStar = new AStar(listOfNodes, startNode, endNode);
//        ArrayList<Node> path = AStar.returnPath();
//        drawPath(path);
//    }
//
//    public void displayNodes (){
//   ArrayList<Node> nodes = PathFindingDatabaseManager.getInstance().getNodes();
//   for ( Node n : nodes){
//       System.out.println(n.getNodeType());
//      Circle circle=  new Circle(n.getX() * fileFxWidthRatio, n.getY()* fileFxHeightRatio, 5);
//            circle.setFill(Color.GREEN);
//       anchor.getChildren().add(circle);
//       System.out.println("ADDED");}
//
////        Circle returnCircle = new Circle(x, y, r);
////        returnCircle.setFill(color);
//    }
//    public void update(){
//
//    }
//}
//
package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.App;
//import edu.wpi.teamname.Database.DatabaseThread;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class MapEditor {

    @FXML
    private Path tonysPath;
    @FXML
    private AnchorPane anchor,topElements;

    @FXML
    private ComboBox<String> toCombo;
    @FXML
    private ComboBox<String> fromCombo;
    @FXML
    private ImageView hospitalMap;

    ArrayList<Node> listOfNodes = new ArrayList<>();
    HashMap<String, Node> nodesMap = new HashMap<>();
    ArrayList<Node> currentPath = new ArrayList<>();
    double mapWidth= 1000.0;
    double mapHeight = 680.0;
    double fileWidth =5000.0;
    double fileHeight = 3400.0;
    double fileFxWidthRatio= mapWidth / fileWidth;
    double fileFxHeightRatio= mapHeight / fileHeight;
    Node startNode;
    Node endNode;
    boolean start= true;

    @FXML
    public void initialize() {

        tonysPath.getElements().clear();
        OnWindowSizeChanged();
        listOfNodes = PathFindingDatabaseManager.getInstance().getNodes();

        listOfNodes.forEach(n -> {
            nodesMap.put(n.getNodeID(), n);
            toCombo.getItems().add(n.getNodeID());
            fromCombo.getItems().add(n.getNodeID());
        });

        hospitalMap.fitWidthProperty().bind(anchor.widthProperty());
        hospitalMap.fitHeightProperty().bind(anchor.heightProperty());
       // rezisingInfo();
        if( start){
        displayNodes();
        start =false;}
        else{
            topElements.getChildren().clear();
            rezisingInfo();
            displayNodes();
        }
    }
public void rezisingInfo(){
     mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
    System.out.println("mapWidth: "+ mapWidth);
     mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
    System.out.println("mapHeight: "+mapHeight);
     fileWidth = hospitalMap.getImage().getWidth();
    System.out.println("fileWidth: "+ fileWidth);
     fileHeight = hospitalMap.getImage().getHeight();
    System.out.println("fileHeight: "+ fileHeight);
     fileFxWidthRatio = mapWidth / fileWidth;
     fileFxHeightRatio = mapHeight / fileHeight;
}
public void OnWindowSizeChanged(){

    anchor.widthProperty().addListener((obs, oldVal, newVal) -> {
        if (currentPath.size() > 0) {
            drawPath(currentPath);
        }
        topElements.getChildren().clear();
        rezisingInfo();
        displayNodes();
    });

    anchor.heightProperty().addListener((obs, oldVal, newVal) -> {
        if (currentPath.size() > 0) {
            drawPath(currentPath);
        }
        topElements.getChildren().clear();
        rezisingInfo();
        displayNodes();
    });

}
    public void drawPath(ArrayList<Node> _listOfNodes) {
        if (_listOfNodes.size() < 1) {
            return;
        }
        rezisingInfo();
       displayNodes ();
        currentPath = _listOfNodes;
        tonysPath.getElements().clear();

        Node firstNode = _listOfNodes.get(0);

        MoveTo start = new MoveTo(firstNode.getX() * fileFxWidthRatio, firstNode.getY() * fileFxHeightRatio);
//        Collection<LineTo> collection = new ArrayList<>();
        tonysPath.getElements().add(start);
       // System.out.println(fileFxWidthRatio);
        _listOfNodes.forEach(n -> {
            tonysPath.getElements().add(new LineTo(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio));
        });
//        Path path = new Path(start, new LineTo(firstNode.getX() * fileFxWidthRatio, firstNode.getY() * fileFxHeightRatio));
//        path.setFill(Color.TOMATO);
//        path.setStrokeWidth(4);
    }

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
public void getAndDrawPathWithCombos(){
    if (fromCombo.getValue() == null || !nodesMap.containsKey(fromCombo.getValue())) {
        return;
    }
    if (toCombo.getValue() == null || !nodesMap.containsKey(toCombo.getValue())) {
        return;}
    assignStartAndEndNodes(nodesMap.get(fromCombo.getValue()));
    assignStartAndEndNodes(nodesMap.get(toCombo.getValue()));
    //calcAndDrawPath(nodesMap.get(fromCombo.getValue()),nodesMap.get(toCombo.getValue()));
}
    public void assignStartAndEndNodes(Node node){
        if(startNode == null ){
            startNode = node;
            if(endNode!=null){
                calcAndDrawPath();
            }
            return;
        }
        if(startNode != null && endNode ==null){
            endNode = node;

            calcAndDrawPath();

            startNode=null;
            endNode= null;

        }


    }
    public void calcAndDrawPath() {


        AStar AStar = new AStar(listOfNodes, startNode, endNode);
        System.out.println("startNode: "+startNode.getLongName() + " nodeID: "+ startNode.getNodeID());
        System.out.println("endNode :"+ endNode.getLongName()+ " nodeID: "+ endNode.getNodeID());
        ArrayList<Node> path = AStar.returnPath();
        drawPath(path);
    }

    public void displayNodes () {
        ArrayList<Node> nodes = PathFindingDatabaseManager.getInstance().getNodes();
        HashMap<String, Node> map = new HashMap();
        System.out.println("got here");
        //rezisingInfo();
        for (Node n : nodes) {
            map.put(n.getNodeID(), n);
         //   System.out.println(n.getNodeType());
            Circle circle = new Circle(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio, 8);
            System.out.println(fileFxWidthRatio);
            System.out.println(fileFxHeightRatio);
            circle = (Circle) clickNode(circle,n);
            circle.setFill(Color.OLIVE);
            topElements.getChildren().add(circle);
         //   System.out.println("ADDED");
        }

        for (Node n : nodes) {

            for (Node e : n.getEdges()) {
                if (map.containsKey(e.getNodeID())) {
                    Line edge = LineBuilder.create().startX(n.getX() * fileFxWidthRatio).startY(n.getY() * fileFxHeightRatio).endX(e.getX() * fileFxWidthRatio).endY(e.getY() * fileFxHeightRatio).stroke(Color.BLUE).strokeWidth(3).build();
                    topElements.getChildren().add(edge);
                }
            }
        }
        if( startNode !=null){
        Circle startCircle = new Circle(startNode.getX() * fileFxWidthRatio, startNode.getY() * fileFxHeightRatio, 8);
        startCircle.setFill(Color.MAGENTA);
        topElements.getChildren().add(startCircle);}
        if(endNode !=null){
            Circle endCircle = new Circle(endNode.getX() * fileFxWidthRatio, endNode.getY() * fileFxHeightRatio, 8);
            endCircle.setFill(Color.MAROON);
            topElements.getChildren().add(endCircle);
        }
    }

    protected javafx.scene.Node clickNode(javafx.scene.Node circle, Node node) {
        circle.setOnMouseClicked(
                t -> {
                    circle.setStyle("-fx-cursor: hand");
                    assignStartAndEndNodes((node));

                });
        circle.setStyle("-fx-cursor: hand");
        return circle;
    }
}