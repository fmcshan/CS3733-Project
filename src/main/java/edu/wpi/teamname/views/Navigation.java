package edu.wpi.teamname.views;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.Algo.Algorithms.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.Pathfinding.NavigationHelper;
import edu.wpi.teamname.Algo.Pathfinding.NodeSortComparator;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.views.manager.LevelChangeListener;
import edu.wpi.teamname.views.manager.LevelManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static javafx.scene.effect.BlurType.GAUSSIAN;

/**
 * Controller for Navigation.fxml
 *
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class Navigation implements LevelChangeListener {

    ArrayList<Node> listOfNodes = new ArrayList<>(); // create a list of nodes
    HashMap<String, Node> nodesMap = new HashMap<>();
    ArrayList<String> listOfNodeNames = new ArrayList<>();
    ArrayList<Node> nodeNameNodes = new ArrayList<>();
    AStar residentAStar;
    boolean pathCanceled;
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
    @FXML
    private VBox navBox;
    @FXML
    private ScrollPane scrollBar;


    /**
     * constructor for Navigation
     *
     * @param mapDisplay controller of MapDisplay.fxml
     */
    public Navigation(MapDisplay mapDisplay) {
        this.mapDisplay = mapDisplay;
    }

    public ArrayList<Node> getListOfNodes() {
        return listOfNodes;
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

        scrollBar.setFitToHeight(true);
    }

    private HBox generateNavElem(String _direction) {
        String directionText = _direction.toLowerCase();
        HBox directionGuiWrapper = new HBox();
        directionGuiWrapper.setStyle("-fx-background-color: #fafafa; -fx-background-radius: 10px; -fx-margin: 0 0 0 0;");
        DropShadow shadow = new DropShadow();
        shadow.setBlurType(GAUSSIAN);
        shadow.setSpread(0.33);
        shadow.setColor(Color.valueOf("#ebebeb"));
        directionGuiWrapper.setEffect(shadow);
        directionGuiWrapper.setMaxWidth(300);

        VBox navIconWrapper = new VBox();
        navIconWrapper.setStyle("-fx-background-color: #37d461; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 4 0 0 4;");
        navIconWrapper.setPrefSize(64, 64);
        navIconWrapper.setMinSize(64, 64);
        MaterialDesignIconView navigationIcon;
        if (directionText.contains("straight") || directionText.contains("head")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ARROW_UP_BOLD);
        } else if (directionText.contains("left")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ARROW_LEFT_BOLD);
        } else if (directionText.contains("right")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ARROW_RIGHT_BOLD);
        } else if (directionText.contains("stair")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.STAIRS);
        } else if (directionText.contains("elevator")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ARROW_UP_BOLD_CIRCLE_OUTLINE);
        } else if (directionText.contains("escalator")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ESCALATOR);
        } else if (directionText.contains("destination")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.MAP_MARKER);
        } else {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ARROW_UP_BOLD);
        }

        navigationIcon.setFill(Paint.valueOf("#ffffff"));
        navigationIcon.setGlyphSize(56);
        navIconWrapper.getChildren().add(navigationIcon);

        VBox spacer = new VBox();
        spacer.setPrefSize(10, 1);
        spacer.setMinSize(10, 1);

        VBox navLabelWrapper = new VBox();
        Text navigationLabel = new Text(_direction);
        navigationLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-padding: 10 0 0 10;");
        navigationLabel.setWrappingWidth(200);
        navLabelWrapper.getChildren().add(navigationLabel);
        navigationLabel.prefWidth(200);
        navigationLabel.prefHeight(60);

        directionGuiWrapper.getChildren().add(navIconWrapper);
        directionGuiWrapper.getChildren().add(spacer);
        directionGuiWrapper.getChildren().add(navLabelWrapper);
        return directionGuiWrapper;
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
        navBox.getChildren().clear();
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
        nav.getTextDirections().forEach(t -> {
            navBox.getChildren().add(generateNavElem(t));
            VBox spacer = new VBox();
            spacer.setPrefSize(1, 10);
            spacer.setMinSize(1, 10);
            navBox.getChildren().add(spacer);
        });
        LevelManager.getInstance().setFloor(startNode.getFloor());
        //SceneManager.getInstance().getDefaultPage().disableButtons(unusedFloors);
    }

    void clearDirections() {
        navBox.getChildren().clear();
    }

    @Override
    public void levelChanged(int _level) {
        if (pathCanceled) {
            mapDisplay.clearMap();
        } else {
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
        //SceneManager.getInstance().getDefaultPage().getTonysPath().getElements().clear();
        SceneManager.getInstance().getDefaultPage().currentPath.clear();
        clearDirections();
        pathCanceled = true;
        //SceneManager.getInstance().getDefaultPage().enableButtons(allFloors);
    }
}