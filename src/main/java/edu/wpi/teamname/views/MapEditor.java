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

import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;


public class MapEditor {

    @FXML
    private Path tonysPath;
    @FXML
    private AnchorPane anchor;
    @FXML
    private ComboBox<String> toCombo;
    @FXML
    private ComboBox<String> fromCombo;
    @FXML
    private ImageView hospitalMap;

    ArrayList<Node> listOfNodes = new ArrayList<>();
    HashMap<String, Node> nodesMap = new HashMap<>();
    ArrayList<Node> currentPath = new ArrayList<>();
    double mapWidth;
        double mapHeight;
    double fileWidth;
    double fileHeight;
    double fileFxWidthRatio;
    double fileFxHeightRatio;
    public void initialize() {

        tonysPath.getElements().clear();

        anchor.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
        });

        anchor.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
        });


        listOfNodes = PathFindingDatabaseManager.getInstance().getNodes();

        listOfNodes.forEach(n -> {
            nodesMap.put(n.getNodeID(), n);
            toCombo.getItems().add(n.getNodeID());
            fromCombo.getItems().add(n.getNodeID());
        });

        hospitalMap.fitWidthProperty().bind(anchor.widthProperty());
        hospitalMap.fitHeightProperty().bind(anchor.heightProperty());
    }
public void rezisingInfo(){
     mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
     mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
     fileWidth = hospitalMap.getImage().getWidth();
     fileHeight = hospitalMap.getImage().getHeight();
     fileFxWidthRatio = mapWidth / fileWidth;
     fileFxHeightRatio = mapHeight / fileHeight;
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
        System.out.println(fileFxWidthRatio);
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

    public void calcPath(ActionEvent actionEvent) {
        if (fromCombo.getValue() == null || !nodesMap.containsKey(fromCombo.getValue())) {
            return;
        }
        if (toCombo.getValue() == null || !nodesMap.containsKey(toCombo.getValue())) {
            return;
        }
        Node startNode = nodesMap.get(fromCombo.getValue());
        Node endNode = nodesMap.get(toCombo.getValue());
        AStar AStar = new AStar(listOfNodes, startNode, endNode);
        ArrayList<Node> path = AStar.returnPath();
        drawPath(path);
    }

    public void displayNodes () {
        ArrayList<Node> nodes = PathFindingDatabaseManager.getInstance().getNodes();
        HashMap<String, Node> map = new HashMap();
        for (Node n : nodes) {
            map.put(n.getNodeID(), n);
            System.out.println(n.getNodeType());
            Circle circle = new Circle(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio, 5);
            circle.setFill(Color.OLIVE);
            anchor.getChildren().add(circle);
            System.out.println("ADDED");
        }

        for (Node n : nodes) {

            for(Node e :n.getEdges()){
                if(map.containsKey(e.getNodeID())){
                    Line edge = LineBuilder.create().startX(n.getX()*fileFxWidthRatio).startY(n.getY()*fileFxHeightRatio).endX(e.getX()*fileFxWidthRatio).endY(e.getY()*fileFxHeightRatio).stroke(Color.BLUE).strokeWidth(3).build();
                    anchor.getChildren().add(edge);
                }
            }
        }
    }
}