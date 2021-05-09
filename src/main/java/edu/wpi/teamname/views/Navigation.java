package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.Algo.Algorithms.*;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Algo.Pathfinding.NavigationHelper;
import edu.wpi.teamname.Algo.Pathfinding.NodeSortComparator;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.views.manager.ButtonManager;
import edu.wpi.teamname.views.manager.LevelChangeListener;
import edu.wpi.teamname.views.manager.LevelManager;
import edu.wpi.teamname.views.manager.SceneManager;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.lang.reflect.Array;
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
    SearchContext searchAlgorithm;
    boolean pathCanceled = false;
    @FXML
    private ComboBox<String> toCombo, algoCombo; // destination drop down
    @FXML
    private ComboBox<String> fromCombo; // start location drop down
    @FXML
    private Label textDirections;
    @FXML
    private Button cancelNavigation;
    @FXML
    private MapDisplay mapDisplay; // MapDisplay.fxml controller
    @FXML
    private VBox navBox, algoBox;
    @FXML
    private ScrollPane scrollBar;
    @FXML
    public JFXButton handicapButton;
    @FXML
    private Label directions;
    @FXML
    private JFXButton mapsButton;
    @FXML
    private JFXButton mapsButtonHome;
    boolean handicap = false;


    ArrayList<Node> path = new ArrayList<>();
    ArrayList<String> allFloors = new ArrayList<>();

    public void setToCombo(Node node) {
        AutoCompleteComboBoxListener box = new AutoCompleteComboBoxListener(toCombo);
        box.setValue(node.getLongName() + "[" + node.getFloor() + "]");
    }

    public void setFromCombo(Node node) {
        AutoCompleteComboBoxListener box = new AutoCompleteComboBoxListener(fromCombo);
        box.setValue(node.getLongName() + "[" + node.getFloor() + "]");
    }

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

        if (AuthenticationManager.getInstance().isAuthenticated()) {
            algoBox.setVisible(true);
            algoCombo.setStyle("-fx-font-size: 24");
        } else {
            algoBox.setVisible(false);
            algoCombo.setStyle("-fx-font-size: .1");
        }

        if (COVIDMessage.covid) {
            AutoCompleteComboBoxListener listener = new AutoCompleteComboBoxListener(toCombo);
            listener.setValue("Emergency Department Entrance[1]");
            COVIDMessage.covid = false;
        }

        LevelManager.getInstance().addListener(this);

        algoCombo.getItems().add("AStar");
        algoCombo.getItems().add("Best First Search");
        algoCombo.getItems().add("Breadth-First Search");
        algoCombo.getItems().add("Depth-First Search");
        algoCombo.getItems().add("Djikstra's Algorithm");

        refreshNodes();

        new AutoCompleteComboBoxListener<>(fromCombo);
        new AutoCompleteComboBoxListener<>(toCombo);

        scrollBar.setFitToHeight(true);
        SceneManager.getInstance().getDefaultPage().getStartNode();
        SceneManager.getInstance().getDefaultPage().getEndNode();
        AStar aStar = new AStar();
        searchAlgorithm = new SearchContext(aStar);
    }

    public HBox generateNavElem(String _direction) {
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
        navIconWrapper.setStyle("-fx-background-color: #317fb8; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 4 0 0 4;");
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

    private void refreshNodes(boolean clear) {
        listOfNodeNames.clear();
        nodeNameNodes.clear();
        listOfNodes = LocalStorage.getInstance().getNodes(); // get nodes from database
        nodesMap.clear();
        if (clear) {
            toCombo.getItems().clear();
            fromCombo.getItems().clear();
        }
        Collections.sort(listOfNodes, new NodeSortComparator());
        listOfNodes.forEach(n -> {
            if (n.getNodeType().equals("HALL")) {
                return;
            }
            nodesMap.put(n.getNodeID(), n); // put the nodes in the hashmap
            listOfNodeNames.add(n.getLongName() + "[" + n.getFloor() + "]");
            //Collections.sort(listOfNodeNames);
            nodeNameNodes.add(n);

        });
        listOfNodeNames.forEach(n -> {
            toCombo.getItems().add(n); // make the nodes appear in the combobox
            fromCombo.getItems().add(n); // make the nodes appear in the combobox 2 electric bugaloo
        });
    }

    private void refreshNodes() {
        refreshNodes(true);
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
        //searchAlgorithm = new SearchContext(new AStar());
        if (fromCombo.getValue() == null || !listOfNodeNames.contains(fromCombo.getValue())) { // if combobox is null or the key does not exist
            return;
        }
        SceneManager.getInstance().getDefaultPage().setStartNode(nodeNameNodes.get(listOfNodeNames.indexOf(fromCombo.getValue()))); // get starting location
        LevelManager.getInstance().setFloor(nodeNameNodes.get(listOfNodeNames.indexOf(fromCombo.getValue())).getFloor()); // switch to the floor that the selected node is on
        selectButtonBasedOnFloor();
        SceneManager.getInstance().getDefaultPage().addStartAndEnd(SceneManager.getInstance().getDefaultPage().getStartNode());
        SceneManager.getInstance().getDefaultPage().displayNodes(SceneManager.getInstance().getDefaultPage().getStartAndEnd(), .8, false);
        if (toCombo.getValue() == null || !listOfNodeNames.contains(toCombo.getValue())) { // if combobox is null or the key does not exist
            return;
        }
        navBox.getChildren().clear();
        SceneManager.getInstance().getDefaultPage().clearStartAndEnd();
        allFloors.add("L2");
        allFloors.add("L1");
        allFloors.add("G");
        allFloors.add("1");
        allFloors.add("2");
        allFloors.add("3");
        SceneManager.getInstance().getDefaultPage().enableButtons(allFloors);
        pathCanceled = false;

        SceneManager.getInstance().getDefaultPage().setEndNode(nodeNameNodes.get(listOfNodeNames.indexOf(toCombo.getValue()))); // get ending location
        SceneManager.getInstance().getDefaultPage().addStartAndEnd(SceneManager.getInstance().getDefaultPage().getEndNode());
        if (handicap )
            searchAlgorithm.setContext(new AStar(listOfNodes, SceneManager.getInstance().getDefaultPage().getStartNode(), SceneManager.getInstance().getDefaultPage().getEndNode(), handicap));
        else if(algoCombo.getValue() == null)
            searchAlgorithm.setContext(new AStar(listOfNodes, SceneManager.getInstance().getDefaultPage().getStartNode(), SceneManager.getInstance().getDefaultPage().getEndNode(), handicap));
        else if(algoCombo.getValue().equals("AStar"))
            searchAlgorithm.setContext(new AStar(listOfNodes, SceneManager.getInstance().getDefaultPage().getStartNode(), SceneManager.getInstance().getDefaultPage().getEndNode(), handicap));
        //System.out.println(handicap);
        searchAlgorithm.loadNodes(listOfNodes, SceneManager.getInstance().getDefaultPage().getStartNode(), SceneManager.getInstance().getDefaultPage().getEndNode());
        //System.out.println(SceneManager.getInstance().getDefaultPage().getStartNode().getNodeID());
        //System.out.println(SceneManager.getInstance().getDefaultPage().getEndNode().getNodeID());
        ArrayList<Node> path = searchAlgorithm.getPath(); // list the nodes found using AStar to create a path
        System.out.println(searchAlgorithm.getAlgorithm());
        //System.out.println(path);
        ArrayList<String> relevantFloors = searchAlgorithm.getRelevantFloors();
        ArrayList<String> unusedFloors = new ArrayList<>();
        for (String floor : allFloors) {
            if (!relevantFloors.contains(floor))
                unusedFloors.add(floor);
        }
        NavigationHelper nav = new NavigationHelper(searchAlgorithm);
        nav.getTextDirections().forEach(t -> {
            navBox.getChildren().add(generateNavElem(t));
            VBox spacer = new VBox();
            spacer.setPrefSize(1, 10);
            spacer.setMinSize(1, 10);
            navBox.getChildren().add(spacer);
        });
        LevelManager.getInstance().setFloor(SceneManager.getInstance().getDefaultPage().getStartNode().getFloor());
        SceneManager.getInstance().getDefaultPage().disableButtons(unusedFloors);
        SceneManager.getInstance().getDefaultPage().displayNodes(path, .8, false);
    }

    void clearDirections() {
        navBox.getChildren().clear();
    }
    @FXML
    void googleMaps() {
        SceneManager.getInstance().getDefaultPage().getPopPop().setPrefWidth(440);
        SceneManager.getInstance().getDefaultPage().toggleGoogleMaps();
    }

    @FXML
    void googleMapsHome() {
        SceneManager.getInstance().getDefaultPage().getPopPop().setPrefWidth(440);
        SceneManager.getInstance().getDefaultPage().toggleGoogleMapsHome();
    }


    @Override
    public void levelChanged(int _level) {
        if (!pathCanceled) {
            String currentFloor = LevelManager.getInstance().getFloor();
            if (searchAlgorithm == null) {
                System.out.println("hello");
                return;
            }
            if (toCombo.getValue() == null || fromCombo.getValue() == null) {
                return;
            }
            if(!(searchAlgorithm.getPath().size() == 0))
                mapDisplay.drawPath(searchAlgorithm.getFloorPaths(currentFloor), true);
            SceneManager.getInstance().getDefaultPage().displayNodes(path, .8, false);
        }
    }

    private void selectButtonBasedOnFloor() {
        switch(nodeNameNodes.get(listOfNodeNames.indexOf(fromCombo.getValue())).getFloor()) {
            case "L2":
                ButtonManager.selectButton(SceneManager.getInstance().getDefaultPage().L2Bttn, "floor-btn-selected", ButtonManager.floors);
                break;
            case "L1":
                ButtonManager.selectButton(SceneManager.getInstance().getDefaultPage().L1Bttn, "floor-btn-selected", ButtonManager.floors);
                break;
            case "G":
                ButtonManager.selectButton(SceneManager.getInstance().getDefaultPage().groundBttn, "floor-btn-selected", ButtonManager.floors);
                break;
            case "1":
                ButtonManager.selectButton(SceneManager.getInstance().getDefaultPage().floor1Bttn, "floor-btn-selected", ButtonManager.floors);
                break;
            case "2":
                ButtonManager.selectButton(SceneManager.getInstance().getDefaultPage().floor2Bttn, "floor-btn-selected", ButtonManager.floors);
                break;
            case "3":
                ButtonManager.selectButton(SceneManager.getInstance().getDefaultPage().floor3Bttn, "floor-btn-selected", ButtonManager.floors);
                break;
        }
    }

    public void cancelNavigation() {
        allFloors.clear();
        allFloors.add("L2");
        allFloors.add("L1");
        allFloors.add("G");
        allFloors.add("1");
        allFloors.add("2");
        allFloors.add("3");
        refreshNodes();
        SceneManager.getInstance().getDefaultPage().clearStartAndEnd();
        SceneManager.getInstance().getDefaultPage().listOfNode.clear();
        SceneManager.getInstance().getDefaultPage().clearMap();
        clearDirections();
        pathCanceled = true;
        SceneManager.getInstance().getDefaultPage().enableButtons(allFloors);
        SceneManager.getInstance().getDefaultPage().setStartNode(null);
        SceneManager.getInstance().getDefaultPage().setEndNode(null);
        SceneManager.getInstance().getDefaultPage().displayHotspots(.8);
    }

    @FXML
    void toggleHandicap(ActionEvent event) {
        if (!handicap) {
            handicapButton.setStyle("-fx-background-color: #dedede; " + "-fx-border-color:  #c3c3c3; " + "-fx-border-radius: 8px; " + "-fx-background-radius: 8px");
        } else {
            handicapButton.setStyle("-fx-background-color: #ffffff; " + "-fx-border-color:  #c3c3c3; " + "-fx-border-radius: 8px; " + "-fx-background-radius: 8px");
        }
        handicap ^= true;
        String fromSelected = fromCombo.getValue();
        String toSelected = toCombo.getValue();
        fromCombo.getItems().clear();
        toCombo.getItems().clear();
        refreshNodes(false);
        fromCombo.setValue(fromSelected);
        toCombo.setValue(toSelected);
        calcPath();
    }

    public void changeSearch() {
        switch (algoCombo.getValue()){
            case "AStar":
                searchAlgorithm.setContext(new AStar());
                calcPath();
                break;
            case "Best First Search":
                searchAlgorithm.setContext(new BestFirstSearch());
                calcPath();
                break;
            case "Breadth-First Search":
                searchAlgorithm.setContext(new BFS());
                calcPath();
                break;
            case "Depth-First Search":
                searchAlgorithm.setContext(new DFS());
                calcPath();
                break;
            case "Djikstra's Algorithm":
                searchAlgorithm.setContext(new Djikstra());
                calcPath();
                break;
        }
    }
}