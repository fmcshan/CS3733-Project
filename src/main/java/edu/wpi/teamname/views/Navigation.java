package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Navigation {

    @FXML
    private ComboBox<String> toCombo;
    @FXML
    private ComboBox<String> fromCombo;
    @FXML
    private DefaultPage defaultPage;

    ArrayList<Node> listOfNodes = new ArrayList<>();
    HashMap<String, Node> nodesMap = new HashMap<>();
    String openWindow = "";

    /**
     *  constructor for Navigation
     * @param defaultPage controller of DefaultPage.fxml
     */
    public Navigation(DefaultPage defaultPage) {
        this.defaultPage = defaultPage;
    }

    /**
     * run on startup
     */
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

        listOfNodes = PathFindingDatabaseManager.getInstance().getNodes(); // get nodes from database

        listOfNodes.forEach(n -> {
            nodesMap.put(n.getNodeID(), n); // put the nodes in the hashmap
            toCombo.getItems().add(n.getNodeID()); // make the nodes appear in the combobox
            fromCombo.getItems().add(n.getNodeID()); // make the nodes appear in the combobox 2 electric bugaloo
        });
    }

    /**
     * load Navigation bar in the Default Page when button is pressed/ make it disappear
     */
    public void loadNav() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/Navigation.fxml"));
        try {
            loader.setControllerFactory(type -> {
                if (type == Navigation.class) {
                    return this ;
                } else {
                    try {
                        return type.newInstance();
                    } catch (RuntimeException e) {
                        throw e ;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            Parent root = loader.load();
            defaultPage.openWindowPopPop("navBar", root); //

        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        defaultPage.drawPath(path);
    }
}
