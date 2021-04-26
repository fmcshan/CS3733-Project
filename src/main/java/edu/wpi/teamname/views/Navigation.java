package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teamname.Algo.Algorithms.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.Pathfinding.NavigationHelper;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.views.manager.LevelChangeListener;
import edu.wpi.teamname.views.manager.LevelManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
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
    private JFXCheckBox handicapMode;
    @FXML
    private ComboBox<String> fromCombo; // start location drop down
    @FXML
    private Label textDirections;
    @FXML
    private MapDisplay mapDisplay; // MapDisplay.fxml controller


    ArrayList<Node> listOfNodes = new ArrayList<>(); // create a list of nodes
    HashMap<String, Node> nodesMap = new HashMap<>();
    ArrayList<String> listOfNodeNames = new ArrayList<>();
    ArrayList<Node> nodeNameNodes = new ArrayList<>();
    ArrayList<Node> listOfHandiNodes = new ArrayList<Node>();
    ArrayList<String> listOfHandiNodeNames = new ArrayList<String>();

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
        listOfNodes.forEach(n -> {
            if (n.getNodeType().equals("HALL")) {
                return;
            }
            if (!n.getNodeType().equals("STAI")){
                listOfHandiNodes.add(n);
                listOfHandiNodeNames.add(n.getLongName());
            }
            nodesMap.put(n.getNodeID(), n); // put the nodes in the hashmap
            listOfNodeNames.add(n.getLongName());
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
        if (fromCombo.getValue() == null) { // if combobox is null or the key does not exist
            return;
        }
        if (toCombo.getValue() == null) { // if combobox is null or the key does not exist
            return;
        }
        Node startNode = nodeNameNodes.get(listOfNodeNames.indexOf(fromCombo.getValue())); // get starting location
        Node endNode = nodeNameNodes.get(listOfNodeNames.indexOf(toCombo.getValue())); // get ending location
        System.out.println(startNode.getLongName());
        System.out.println(endNode.getLongName());
        AStar AStar = new AStar(listOfNodes, startNode, endNode);
        if (handicapMode.isSelected()){
            AStar = new AStar(listOfHandiNodes, startNode, endNode);
        }
        ArrayList<Node> path = AStar.getPath(); // list the nodes found using AStar to create a path
        String currentFloor = LevelManager.getInstance().getFloor();
        mapDisplay.drawPath(AStar.getFloorNodes(currentFloor)); // draw the path on the map
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
        System.out.println("done" + result);
        setTextDirections(result);
        //SceneManager.getInstance().getDefaultPage().toggleButtons(unusedFloors);
    }

    void setTextDirections(String directions){
        textDirections.setText(directions);
    }

    @Override
    public void levelChanged(int _level) {
        calcPath();
        //refreshNodes();
    }


    @FXML
    void updateToFrom(ActionEvent event) {
        if (handicapMode.isSelected()) {
            toCombo.getItems().clear();
            fromCombo.getItems().clear();
            for (String n : listOfHandiNodeNames
            ) {
                toCombo.getItems().add(n);
                fromCombo.getItems().add(n);
            }
        } else {
            toCombo.getItems().clear();
            fromCombo.getItems().clear();
            for (String n : listOfNodeNames
            ) {
                toCombo.getItems().add(n);
                fromCombo.getItems().add(n);
            }
        }

    }
}