package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.util.ArrayList;
import java.util.HashMap;

public class Navigation {

    @FXML
    private ComboBox<String> toCombo;
    @FXML
    private ComboBox<String> fromCombo;

    ArrayList<Node> listOfNodes = new ArrayList<>();
    HashMap<String, Node> nodesMap = new HashMap<>();

    public void initialize() {

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

        listOfNodes = PathFindingDatabaseManager.getInstance().getNodes();

        listOfNodes.forEach(n -> {
            nodesMap.put(n.getNodeID(), n);
            toCombo.getItems().add(n.getNodeID());
            fromCombo.getItems().add(n.getNodeID());
        });
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
    }
}
