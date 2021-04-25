package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.Algorithms.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller for Navigation.fxml
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class Navigation {
    @FXML
    private ComboBox<String> toCombo; // destination drop down
    @FXML
    private ComboBox<String> fromCombo; // start location drop down
    @FXML
    private MapDisplay mapDisplay; // MapDisplay.fxml controller

    ArrayList<Node> listOfNodes = new ArrayList<>(); // create a list of nodes
    HashMap<String, Node> nodesMap = new HashMap<>();

    public ArrayList<Node> getListOfNodes(){
        return listOfNodes;
    }

    /**
     *  constructor for Navigation
     * @param mapDisplay controller of MapDisplay.fxml
     */
    public Navigation(MapDisplay mapDisplay) {
        this.mapDisplay = mapDisplay;
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

        listOfNodes = LocalStorage.getInstance().getNodes(); // get nodes from database
//        listOfNodes = LocalStorage.getInstance().getNodes();

        listOfNodes.forEach(n -> {
            if (((n.getFloor().equals("1") || n.getFloor().equals("G") ||n.getFloor().equals("")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") || n.getBuilding().equals("") ))) {
                nodesMap.put(n.getNodeID(), n); // put the nodes in the hashmap
                toCombo.getItems().add(n.getNodeID()); // make the nodes appear in the combobox
                fromCombo.getItems().add(n.getNodeID()); // make the nodes appear in the combobox 2 electric bugaloo
            }
        });
    }

    /**
     * load Navigation bar in the Default Page when button is pressed/ make it disappear
     */
    public void loadNav() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/Navigation.fxml")); // used to load fxml in it's own controller
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
            LoadFXML.getInstance().openWindow("navBar", root, mapDisplay.getPopPop()); // open/close navigation bar
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When both comboboxes are filled calculate a path using AStar
     */
    public void calcPath() {
        if (fromCombo.getValue() == null || !nodesMap.containsKey(fromCombo.getValue())) { // if combobox is null or the key does not exist
            return;
        }
        if (toCombo.getValue() == null || !nodesMap.containsKey(toCombo.getValue())) { // if combobox is null or the key does not exist
            return;
        }
        Node startNode = nodesMap.get(fromCombo.getValue()); // get starting location
        Node endNode = nodesMap.get(toCombo.getValue()); // get ending location
        System.out.println(startNode.getNodeID());
        System.out.println(endNode.getNodeID());
        System.out.println(listOfNodes);
        System.out.println(listOfNodes.get(0).getEdges());
        AStar AStar = new AStar(listOfNodes, startNode, endNode); // perform AStar
        ArrayList<Node> path = AStar.getPath(); // list the nodes found using AStar to create a path
        mapDisplay.drawPath(path); // draw the path on the map
    }

    public boolean toComboisEmpty() {
        return toCombo.getValue() == null || !nodesMap.containsKey(toCombo.getValue());
    }

    public boolean fromComboisEmpty() {
        return fromCombo.getValue() == null || !nodesMap.containsKey(fromCombo.getValue());
    }
}
