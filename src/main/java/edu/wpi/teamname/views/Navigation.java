package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.Algorithms.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.NodeComparator;
import edu.wpi.teamname.Algo.Pathfinding.NavigationHelper;
import edu.wpi.teamname.Algo.Pathfinding.NodeSortComparator;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.views.manager.LevelChangeListener;
import edu.wpi.teamname.views.manager.LevelManager;
import edu.wpi.teamname.views.manager.SceneManager;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Controller for Navigation.fxml
 *
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class Navigation implements LevelChangeListener {

    @FXML
    private ComboBox<String> toCombo; // destination drop down
    @FXML
    private ComboBox<String> fromCombo; // start location drop down
    @FXML
    private Label textDirections;
    @FXML
    private Button cancelNavigation;
    @FXML
    private MapDisplay mapDisplay; // MapDisplay.fxml controller


    ArrayList<Node> listOfNodes = new ArrayList<>(); // create a list of nodes
    HashMap<String, Node> nodesMap = new HashMap<>();
    ArrayList<String> listOfNodeNames = new ArrayList<>();
    ArrayList<Node> nodeNameNodes = new ArrayList<>();
    AStar residentAStar;
    boolean pathCanceled;

    public ArrayList<Node> getListOfNodes() {
        return listOfNodes;
    }

    /**
     * constructor for Navigation
     *
     * @param mapDisplay controller of MapDisplay.fxml
     */
    public Navigation(MapDisplay mapDisplay) {
        this.mapDisplay = mapDisplay;
    }

    /**
     * run on startup
     */
    public void initialize() {
        if (COVIDMessage.covid) {
            AutoCompleteComboBoxListener listener = new AutoCompleteComboBoxListener(toCombo);
            listener.setValue("Emergency Department Entrance[1]");
            COVIDMessage.covid = false;
        }

        LevelManager.getInstance().addListener(this);
        refreshNodes();

        new AutoCompleteComboBoxListener<>(fromCombo);
        new AutoCompleteComboBoxListener<>(toCombo);

    }

    private void refreshNodes() {
        listOfNodeNames.clear();
        nodeNameNodes.clear();
        listOfNodes = LocalStorage.getInstance().getNodes(); // get nodes from database
        nodesMap.clear();
        toCombo.getItems().clear();
        fromCombo.getItems().clear();
        Collections.sort(listOfNodes, new NodeSortComparator());
        listOfNodes.forEach(n -> {
            if (n.getNodeType().equals("HALL")) {
                return;
            }
            nodesMap.put(n.getNodeID(), n); // put the nodes in the hashmap
            listOfNodeNames.add(n.getLongName() + "[" + n.getFloor() + "]");
            //Collections.sort(listOfNodeNames);
            nodeNameNodes.add(n);
            /*if (n.getFloor().equals(LevelManager.getInstance().getFloor())) {

            }*/
        });
        listOfNodeNames.forEach(n -> {
            toCombo.getItems().add(n); // make the nodes appear in the combobox
            fromCombo.getItems().add(n); // make the nodes appear in the combobox 2 electric bugaloo
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
                    return this;
                } else {
                    try {
                        return type.newInstance();
                    } catch (RuntimeException e) {
                        throw e;
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
        if (fromCombo.getValue() == null || !listOfNodeNames.contains(fromCombo.getValue())) { // if combobox is null or the key does not exist
            return;
        }
        if (toCombo.getValue() == null || !listOfNodeNames.contains(toCombo.getValue())) { // if combobox is null or the key does not exist
            return;
        }
        pathCanceled = false;
        Node startNode = nodeNameNodes.get(listOfNodeNames.indexOf(fromCombo.getValue())); // get starting location
        Node endNode = nodeNameNodes.get(listOfNodeNames.indexOf(toCombo.getValue())); // get ending location
        AStar AStar = new AStar(listOfNodes, startNode, endNode); // perform AStar
        residentAStar = AStar;
        ArrayList<Node> path = AStar.getPath(); // list the nodes found using AStar to create a path
        String currentFloor = LevelManager.getInstance().getFloor();
        for (ArrayList<Node> floorPath : residentAStar.getFloorPaths(currentFloor)) {
            mapDisplay.drawPath(floorPath); // draw the path on the map
        }
        System.out.println("number of paths: " + residentAStar.getFloorPaths(currentFloor).size());

        ArrayList<String> allFloors = new ArrayList<>();
        allFloors.add("L2");
        allFloors.add("L1");
        allFloors.add("G");
        allFloors.add("1");
        allFloors.add("2");
        allFloors.add("3");
        ArrayList<String> relevantFloors = AStar.getRelevantFloors();
        ArrayList<String> unusedFloors = new ArrayList<>();
        for (String floor : allFloors) {
            if (!relevantFloors.contains(floor))
                unusedFloors.add(floor);
        }
        NavigationHelper nav = new NavigationHelper(AStar);
        String result = "";
        for (String textDirection : nav.getTextDirections()) {
            //System.out.println(textDirection);
            result = result + textDirection + "\n";
        }
        //System.out.println("done");
        //System.out.println("done" + result);
        setTextDirections(result);
        LevelManager.getInstance().setFloor(startNode.getFloor());
        SceneManager.getInstance().getDefaultPage().disableButtons(unusedFloors);
    }

    void setTextDirections(String directions){
        textDirections.setText(directions);
    }
    void clearDirections(){textDirections.setText("");}

    @Override
    public void levelChanged(int _level) {
        if (pathCanceled) {
            mapDisplay.clearMap();
        }
        else {
            String currentFloor = LevelManager.getInstance().getFloor();
            for (ArrayList<Node> floorPath : residentAStar.getFloorPaths(currentFloor)) {
                mapDisplay.drawPath(floorPath); // draw the path on the map
            }
        }
        //refreshNodes();
    }

    public void cancelNavigation(ActionEvent actionEvent) {
        ArrayList<String> allFloors = new ArrayList<>();
        allFloors.add("L2");
        allFloors.add("L1");
        allFloors.add("G");
        allFloors.add("1");
        allFloors.add("2");
        allFloors.add("3");
        refreshNodes();
        SceneManager.getInstance().getDefaultPage().getTonysPath().getElements().clear();
        SceneManager.getInstance().getDefaultPage().currentPath.clear();
        clearDirections();
        pathCanceled = true;
        SceneManager.getInstance().getDefaultPage().enableButtons(allFloors);
    }
}