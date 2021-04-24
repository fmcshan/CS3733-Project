package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.App;
import edu.wpi.teamname.Authentication.AuthListener;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.simplify.Shutdown;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller for DefaultPage.fxml
 *
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class DefaultPage extends MapDisplay implements AuthListener {

    ArrayList<Node> currentPath = new ArrayList<>(); // used to save the current list of nodes after AStar
    //ZoomAndPan zoooooooM;

    /**
     * run on startup
     */
    public void initialize() {
        hideAddNodePopup();
        SceneManager.getInstance().setDefaultPage(this);
        AuthenticationManager.getInstance().addListener(this);

        if (AuthenticationManager.getInstance().isAuthenticated()) {
            displayAuthPages();
        }

        tonysPath.getElements().clear(); // clear the path
        LoadFXML.setCurrentWindow(""); // set the open window to nothing

        double fileWidth = 5000.0;
        double fileHeight = 3400.0;

        double windowWidth = hospitalMap.boundsInParentProperty().get().getWidth() / fileWidth;
        double windowHeight = hospitalMap.boundsInParentProperty().get().getHeight() / fileHeight;
        double windowSmallestScale = Math.max(Math.min(windowHeight, windowWidth), 0);

        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> { // adjust the path and the map to the window as it changes
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            hospitalMap.fitWidthProperty().bind(stackPane.widthProperty());
            mapWidth = hospitalMap.boundsInParentProperty().get().getWidth() / windowSmallestScale;
            topElements.getChildren().clear();
            resizingInfo();
            zooM.zoomAndPan();
        });

        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> { // adjust the path and the map to the window as it changes
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            hospitalMap.fitHeightProperty().bind(stackPane.heightProperty());
            mapHeight = hospitalMap.boundsInParentProperty().get().getHeight()/ windowSmallestScale;
            topElements.getChildren().clear();
            resizingInfo();
            zooM.zoomAndPan();
        });

        mapWidth = hospitalMap.boundsInParentProperty().get().getWidth() / windowSmallestScale;
        System.out.println("mapWidth: " + mapWidth);

        mapHeight = hospitalMap.boundsInParentProperty().get().getHeight()/ windowSmallestScale;
        System.out.println("mapHeight: " + mapHeight);

        refreshData();
        zooM.zoomAndPan();
    }

    public boolean nodeWithinSpec(Node n) {
        return ((n.getFloor().equals("1") || n.getFloor().equals("G") || n.getFloor().equals("")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") || n.getBuilding().equals("")));
    }

    /**
     * for updating and displaying the map
     *
     * @param _listOfNodes a path of nodes
     */
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

    private void displayAuthPages() {
        LoadFXML.getInstance().loadWindow("MapEditorButton", "mapButton", adminPop);
        LoadFXML.getInstance().loadWindow("SubmittedRequestsButton", "reqButton", requestPop);
        LoadFXML.getInstance().loadWindow("SubmittedRegistrationsButton", "regButton", registrationPop);
        MaterialDesignIconView signOut = new MaterialDesignIconView(MaterialDesignIcon.EXIT_TO_APP);
        signOut.setFill(Paint.valueOf("#c3c3c3"));
        signOut.setGlyphSize(52);
        adminButton.setGraphic(signOut);
    }

    /**
     * for displaying the new buttons after user logins
     */
    @Override
    public void userLogin() {
        displayAuthPages();
    }

    /**
     * getting rid of the buttons after user sign outs
     */
    @Override
    public void userLogout() {
        adminPop.getChildren().clear();
        requestPop.getChildren().clear();
        registrationPop.getChildren().clear();
        MaterialDesignIconView signOut = new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT_BOX_OUTLINE);
        signOut.setFill(Paint.valueOf("#c3c3c3"));
        signOut.setGlyphSize(52);
        adminButton.setGraphic(signOut);
        popPop.getChildren().clear();
    }

    /**
     * toggle the map editor window
     */
    public void toggleMapEditor() {
        clearMap();
        popPop.getChildren().clear();
        zooM.zoomAndPan();
        if (LoadFXML.getCurrentWindow().equals("mapEditorBar")) {
            topElements.getChildren().clear();
            LoadFXML.setCurrentWindow("");
            zooM.zoomAndPan();
            return;
        }

        initMapEditor();
        zooM.zoomAndPan();
        LoadFXML.setCurrentWindow("mapEditorBar");
    }

    /**
     * toggle the admin registration window
     */
    public void toggleRegistration() {
        clearMap();
        popPop.setPrefWidth(1000);
        LoadFXML.getInstance().loadWindow("RegistrationAdminView", "registrationBar", popPop);
    }

    /**
     * toggle the admin request window
     */
    public void toggleRequest() {
        clearMap();
        popPop.setPrefWidth(1000);
        LoadFXML.getInstance().loadWindow("RequestAdminView", "requestBar", popPop);
    }

    public void closeWindows() {
        popPop.getChildren().clear();
    }
}
