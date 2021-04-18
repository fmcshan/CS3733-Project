package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.App;
import edu.wpi.teamname.Database.DatabaseThread;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


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

//        Callback<ListView<Node>, ListCell<Node>> cellFactory = new Callback<ListView<Node>, ListCell<Node>>() {
//
//            @Override
//            public ListCell<Node> call(ListView<Node> l) {
//                return new ListCell<Node>() {
//
//                    @Override
//                    protected void updateItem(Node item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (item == null || empty) {
//                            setGraphic(null);
//                        } else {
//                            setText(item.getLongName());
//                        }
//                    }
//                };
//            }
//        };
//
//        fromCombo.setConverter(new StringConverter<String>() {
//            @Override
//            public String toString(Node node) {
//                if (node == null){
//                    return null;
//                } else {
//                    return node.getLongName();
//                }
//            }
//
//            @Override
//            public Node fromString(String string) {
//                return null;
//            }
//        });
//
//        fromCombo.setButtonCell(cellFactory.call(null));
//        fromCombo.setCellFactory(cellFactory);

        listOfNodes = LocalStorage.getInstance().getNodes();

        listOfNodes.forEach(n -> {
            nodesMap.put(n.getNodeID(), n);
            toCombo.getItems().add(n.getNodeID());
            fromCombo.getItems().add(n.getNodeID());
        });

        hospitalMap.fitWidthProperty().bind(anchor.widthProperty());
        hospitalMap.fitHeightProperty().bind(anchor.heightProperty());
    }

    public void drawPath(ArrayList<Node> _listOfNodes) {
        if (_listOfNodes.size() < 1) {
            return;
        }
        currentPath = _listOfNodes;
        tonysPath.getElements().clear();
        double mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
        double mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
        double fileWidth = hospitalMap.getImage().getWidth();
        double fileHeight = hospitalMap.getImage().getHeight();
        double fileFxWidthRatio = mapWidth / fileWidth;
        double fileFxHeightRatio = mapHeight / fileHeight;
        Node firstNode = _listOfNodes.get(0);
        MoveTo start = new MoveTo(firstNode.getX() * fileFxWidthRatio, firstNode.getY() * fileFxHeightRatio);
        tonysPath.getElements().add(start);
        System.out.println(fileFxWidthRatio);
        _listOfNodes.forEach(n -> {
            tonysPath.getElements().add(new LineTo(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio));
        });
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
}