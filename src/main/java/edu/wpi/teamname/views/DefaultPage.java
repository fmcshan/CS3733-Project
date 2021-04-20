package edu.wpi.teamname.views;

import com.google.rpc.context.AttributeContext;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.Algo.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.AuthListener;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.bridge.Bridge;
import edu.wpi.teamname.bridge.CloseListener;
import edu.wpi.teamname.simplify.Shutdown;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for DefaultPage.fxml
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class DefaultPage extends LoadFXML implements AuthListener, CloseListener {

    @FXML
    private VBox popPop; // vbox to populate with different fxml such as Navigation/Requests/Login
    @FXML
    private VBox adminPop; // vbox to populate Map Editor button
    @FXML
    private VBox requestPop; // vbox to populate Submitted Requests button
    @FXML
    private Path tonysPath; // the path displayed on the map
    @FXML
    private ImageView hospitalMap; // the map
    @FXML
    private StackPane stackPane; // the pane the map is housed in
    @FXML
    private JFXButton adminButton; // button that allows you to sign in

    static String currentWindow = ""; // string that tracks what window is open in popPop Vbox
    ArrayList<Node> currentPath = new ArrayList<>(); // used to save the current list of nodes after AStar

    public VBox getPopPop() {
        return popPop;
    }

    /**
     * setter for openWindow
     * @param windowName // pass in the string that modifys openWindow
     */
    public static void setOpenWindow(String windowName) {
        currentWindow = windowName;
    }

    /**
     * run on startup
     */
    public void initialize() {
        AuthenticationManager.getInstance().addListener(this);
        Bridge.getInstance().addCloseListener(this);

        tonysPath.getElements().clear(); // clear the path

        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> { // adjust the path and the map to the window as it changes
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            hospitalMap.fitWidthProperty().bind(stackPane.widthProperty());
        });

        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> { // adjust the path and the map to the window as it changes
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            hospitalMap.fitHeightProperty().bind(stackPane.heightProperty());
        });
    }

    public void toggleNav(ActionEvent actionEvent) {
        tonysPath.getElements().clear();
        popPop.setPrefWidth(350.0);
        // load controller here
        Navigation navigation = new Navigation(this);

        navigation.loadNav();
    }

    public void openRequests(ActionEvent actionEvent) {
        popPop.setPrefWidth(350.0);
        loadWindow("Requests", "reqBar", popPop);
    }

    public void openLogin(ActionEvent actionEvent) {
        popPop.setPrefWidth(350.0);
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            loadWindow("Login", "loginBar", popPop);
        } else {
            AuthenticationManager.getInstance().signOut();
        }
    }

    public void openCheckIn(ActionEvent actionEvent) {
        popPop.setPrefWidth(657.0);
        loadWindow("UserRegistration", "registrationButton", popPop);
    }

    public void exitApplication(ActionEvent actionEvent) {
        Shutdown.getInstance().exit();
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

    @Override
    public void userLogin() {
        loadWindow("MapEditorButton", "mapButton", adminPop);
        loadWindow("SubmittedRequests", "reqButton", requestPop);
        MaterialDesignIconView signOut = new MaterialDesignIconView(MaterialDesignIcon.EXIT_TO_APP);
        signOut.setFill(Paint.valueOf("#c3c3c3"));
        signOut.setGlyphSize(52);
        adminButton.setGraphic(signOut);
    }

    @Override
    public void userLogout() {
        adminPop.getChildren().clear();
        requestPop.getChildren().clear();
        MaterialDesignIconView signOut = new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT_BOX_OUTLINE);
        signOut.setFill(Paint.valueOf("#c3c3c3"));
        signOut.setGlyphSize(52);
        adminButton.setGraphic(signOut);
    }

    @Override
    public void closeButtonPressed() {
        popPop.getChildren().clear();
    }
}
