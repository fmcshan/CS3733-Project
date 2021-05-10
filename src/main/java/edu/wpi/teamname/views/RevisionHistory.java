package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.views.manager.Event;
import edu.wpi.teamname.views.manager.SceneManager;
import edu.wpi.teamname.views.manager.Snapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static javafx.scene.effect.BlurType.GAUSSIAN;

/**
 * Controller for Navigation.fxml
 *
 * @author Justin Luce
 */
public class RevisionHistory {
    static DefaultPage defaultPage = SceneManager.getInstance().getDefaultPage();
    @FXML
    private ScrollPane scrollBar;

    @FXML
    private JFXButton restoreButton;

    @FXML
    private JFXButton cancelButton;
    @FXML
    private VBox navBox;

    @FXML
    private MaterialDesignIconView exitOut;

    ArrayList<Snapshot> snapshots = LocalStorage.getInstance().getSnapshots();

    HashMap<HBox, Event> eventMap = new HashMap<>();
    HashMap<HBox, Snapshot> snapMap = new HashMap<>();

    public RevisionHistory() {

    }

    public void initialize() {
        restoreButton.setVisible(false);
        cancelButton.setVisible(false);
        addEventsAndSnapshots();
    }


    public void closeWindows() {
        defaultPage.getPopPop().getChildren().clear();
        LoadFXML.setCurrentWindow("mapEditorBar");
        defaultPage.rebootMapEditor();
        defaultPage.renderMap();

    }

    @FXML
    public void cancel() {
        cancelButton.setVisible(false);
        restoreButton.setVisible(false);
        defaultPage.setRevisionHistoryMode(false);
        defaultPage.renderMap();


    }
    @FXML
    public void restore() {
        cancelButton.setVisible(false);
        restoreButton.setVisible(false);
    }

    public void addEventsAndSnapshots() {
        ArrayList<Event> events = new ArrayList<>();
        events = LocalStorage.getInstance().getEvents();
        ArrayList<Snapshot> snapshots = new ArrayList<>();
        snapshots = LocalStorage.getInstance().getSnapshots();
        ArrayList<Event> finalEvents = events;
        snapshots.forEach(s -> {
            if (!(s.getNodes().isEmpty())) {
                navBox.getChildren().add(generateElemSnap(s));
                VBox spacer = new VBox();
                spacer.setPrefSize(1, 10);
                spacer.setMinSize(1, 10);
                navBox.getChildren().add(spacer);
                finalEvents.forEach(e -> {
                    if (e.getSnapshot().equals(s.getId())) {
                        navBox.getChildren().add(generateElem(e));
                        VBox spacer2 = new VBox();
                        spacer2.setPrefSize(1, 10);
                        spacer2.setMinSize(1, 10);
                        navBox.getChildren().add(spacer2);
                    }
                });
            }
        });
    }

    public HBox generateElem(Event event) {
        String _event = event.getEvent();
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
        navigationIcon = new MaterialDesignIconView();
        if (_event.equals("add_node")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.PLUS_CIRCLE);
        } else if (_event.equals("remove_node")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.MINUS_CIRCLE);
        } else if (_event.equals("add_edge")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.PLUS_CIRCLE);
        } else if (_event.equals("remove_edge")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.MINUS_CIRCLE);
        }

        navigationIcon.setFill(Paint.valueOf("#ffffff"));
        navigationIcon.setGlyphSize(56);
        navIconWrapper.getChildren().add(navigationIcon);

        VBox spacer = new VBox();
        spacer.setPrefSize(10, 1);
        spacer.setMinSize(10, 1);

        VBox navLabelWrapper = new VBox();
        String type = "";
        if (_event.equals("remove_node")){
            type = "Remove Node " + event.getNode().getLongName();
        }
        if (_event.equals("remove_edge")){
            type = "Remove Edge " + event.getEdge().getEdgeID();
        }
        if (_event.equals("add_node")){
            type = "Add Node " + event.getNode().getLongName();
        }
        if (_event.equals("add_edge")){
            type = "Add Edge "+ event.getEdge().getEdgeID();
        }
        Text navigationLabel = new Text(type);
        navigationLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-padding: 10 0 0 10;");
        navigationLabel.setWrappingWidth(200);
        navLabelWrapper.getChildren().add(navigationLabel);
        navigationLabel.prefWidth(200);
        navigationLabel.prefHeight(60);


//        Text navigationLabel2 = new Text(event.getDate());
//        navigationLabel2.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-padding: 10 0 0 10;");
//        navigationLabel2.setWrappingWidth(200);
//        navLabelWrapper.getChildren().add(navigationLabel2);
//        navigationLabel2.prefWidth(200);
//        navigationLabel2.prefHeight(60);

        directionGuiWrapper.getChildren().add(navIconWrapper);
        directionGuiWrapper.getChildren().add(spacer);
        directionGuiWrapper.getChildren().add(navLabelWrapper);
        directionGuiWrapper.setOnMouseClicked(a -> {
            cancelButton.setVisible(true);
            restoreButton.setVisible(true);
            ArrayList<Node> nodes = new ArrayList<>();
            ArrayList<Edge> edges = new ArrayList<>();
            for (Snapshot s : snapshots) {
                if (s.getId().equals(event.getSnapshot())){
                    s.doEvent(event);
                    nodes = s.getNodes();
                    edges = s.getEdges();
                }
            }
            System.out.println(nodes.get(nodes.size()-1).getLongName());
            defaultPage.clearMap();
            defaultPage.displayNodesAndEdgesPreveiw(nodes, edges);
        });

        return directionGuiWrapper;
    }

    public HBox generateElemSnap(Snapshot _snap) {
        HBox directionGuiWrapper = new HBox();
        directionGuiWrapper.setStyle("-fx-background-color: #fafafa; -fx-background-radius: 10px; -fx-margin: 0 0 0 0;");
        snapMap.put(directionGuiWrapper, _snap);
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
        navigationIcon = new MaterialDesignIconView();
        navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.UPLOAD);


        navigationIcon.setFill(Paint.valueOf("#ffffff"));
        navigationIcon.setGlyphSize(56);
        navIconWrapper.getChildren().add(navigationIcon);

        VBox spacer = new VBox();
        spacer.setPrefSize(10, 1);
        spacer.setMinSize(10, 1);

        VBox navLabelWrapper = new VBox();
        Text navigationLabel = new Text("File loaded");
        navigationLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-padding: 10 0 0 10;");
        navigationLabel.setWrappingWidth(200);
        navLabelWrapper.getChildren().add(navigationLabel);
        navigationLabel.prefWidth(200);
        navigationLabel.prefHeight(60);

        directionGuiWrapper.getChildren().add(navIconWrapper);
        directionGuiWrapper.getChildren().add(spacer);
        directionGuiWrapper.getChildren().add(navLabelWrapper);
        directionGuiWrapper.setOnMouseClicked(a -> {
            cancelButton.setVisible(true);
            restoreButton.setVisible(true);
            ArrayList<Node> nodes = _snap.getNodes();
            ArrayList<Edge> edges = _snap.getEdges();
            defaultPage.clearMap();
            defaultPage.displayNodesAndEdgesPreveiw(nodes, edges);
        });
        return directionGuiWrapper;
    }
}