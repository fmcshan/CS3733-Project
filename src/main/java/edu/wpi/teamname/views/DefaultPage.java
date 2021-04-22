package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.App;
import edu.wpi.teamname.Authentication.AuthListener;
import edu.wpi.teamname.Authentication.AuthenticationManager;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for DefaultPage.fxml
 *
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class DefaultPage extends LoadFXML implements AuthListener {

    static double scaledWidth = 5000;
    static double scaledHeight = 3400.0;
    static double scaledX = 0;
    static double scaledY = 0;
    ArrayList<Node> currentPath = new ArrayList<>(); // used to save the current list of nodes after AStar
    ArrayList<Node> listOfNodes;
    double mapWidth; //= 1000.0;
    double mapHeight;// = 680.0;
    double fileWidth; //= 5000.0;
    double fileHeight;// = 3400.0;
    double fileFxWidthRatio = mapWidth / fileWidth;
    double fileFxHeightRatio = mapHeight / fileHeight;
    boolean start = true;
    @FXML
    private VBox popPop, adminPop, requestPop, registrationPop; // vbox to populate with different fxml such as Navigation/Requests/Login
    @FXML
    private Path tonysPath; // the path displayed on the map
    @FXML
    private ImageView hospitalMap; // the map
    @FXML
    private StackPane stackPane; // the pane the map is housed in
    @FXML
    private JFXButton adminButton; // button that allows you to sign in
    @FXML
    private AnchorPane topElements;

    public VBox getPopPop() {
        return popPop;
    }

    public VBox getAdminPop() {
        return adminPop;
    }

    public VBox getRequestPop() {
        return requestPop;
    }

    public VBox getRegistrationPop() {
        return registrationPop;
    }

    /**
     * run on startup
     */
    public void initialize() {
        SceneManager.getInstance().setDefaultPage(this);
        AuthenticationManager.getInstance().addListener(this);

        if (AuthenticationManager.getInstance().isAuthenticated()) {
            displayAuthPages();
        }

        tonysPath.getElements().clear(); // clear the path

        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> { // adjust the path and the map to the window as it changes
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            hospitalMap.fitWidthProperty().bind(stackPane.widthProperty());
            resizingInfo();
        });

        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> { // adjust the path and the map to the window as it changes
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            hospitalMap.fitHeightProperty().bind(stackPane.heightProperty());
            resizingInfo();
        });

    }

    /**
     * toggles the navigation window
     */
    public void toggleNav() {
        tonysPath.getElements().clear();
        popPop.setPrefWidth(350.0);
        // load controller here
        Navigation navigation = new Navigation(this);
        navigation.loadNav();
        listOfNodes = navigation.getListOfNodes();
        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            resizingInfo();
            topElements.getChildren().clear();
            displayNodes();
            hospitalMap.fitHeightProperty().bind(stackPane.heightProperty());
        });
        displayNodes();
    }

    /**
     * toggle the requests window
     */
    public void openRequests() {
        popPop.setPrefWidth(350.0);
        loadWindow("Requests", "reqBar", popPop);
    }


    /**
     * toggle the login window
     */
    public void openLogin() {
        popPop.setPrefWidth(350.0);
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            loadWindow("Login", "loginBar", popPop);
        } else {
            AuthenticationManager.getInstance().signOut();
        }
    }

    /**
     * toggle the check in window
     */
    public void openCheckIn() {
        popPop.setPrefWidth(657.0);
        loadWindow("UserRegistration", "registrationButton", popPop);
    }

    /**
     * exit the application
     */
    public void exitApplication() {
        Shutdown.getInstance().exit();
    }

    /**
     * for the scaling the displayed nodes on the map
     *
     * @param x the x coordinate of the anchor pane, top element
     * @return the scaled x coordinate
     */
    public double xCoordOnTopElement(int x) {
        double fileWidth = 5000.0;
        double fileHeight = 3400.0;

        double widthScale = scaledWidth / fileWidth;
        double heightScale = scaledHeight / fileHeight;

        double windowWidth = hospitalMap.boundsInParentProperty().get().getWidth() / fileWidth;
        double windowHeight = hospitalMap.boundsInParentProperty().get().getHeight() / fileHeight;
        double windowSmallestScale = Math.max(Math.min(windowHeight, windowWidth), 0);
        double viewportSmallestScale = Math.max(Math.min(heightScale, widthScale), 0);
        return ((x - scaledX) / viewportSmallestScale) * windowSmallestScale;
    }

    /**
     * for the scaling the displayed nodes on the map
     *
     * @param y the y coordinate of the anchor pane, top element
     * @return the scaled y coordinate
     */
    public double yCoordOnTopElement(int y) {
        double fileWidth = 5000.0;

        double fileHeight = 3400.0;
        double widthScale = scaledWidth / fileWidth;
        double heightScale = scaledHeight / fileHeight;

        double windowWidth = hospitalMap.boundsInParentProperty().get().getWidth() / fileWidth;
        double windowHeight = hospitalMap.boundsInParentProperty().get().getHeight() / fileHeight;
        double windowSmallestScale = Math.max(Math.min(windowHeight, windowWidth), 0);
        double viewportSmallestScale = Math.max(Math.min(heightScale, widthScale), 0);
        return ((y - scaledY) / viewportSmallestScale) * windowSmallestScale;
    }

    /**
     * stores needed resizing info for scaling the displayed nodes on the map as the window changes
     */
    public void resizingInfo() {
        mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
        //System.out.println("mapWidth: " + mapWidth);
        mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
        // System.out.println("mapHeight: " + mapHeight);
        fileWidth = hospitalMap.getImage().getWidth();
        //System.out.println("fileWidth: " + fileWidth);
        fileHeight = hospitalMap.getImage().getHeight();
        // System.out.println("fileHeight: " + fileHeight);
        fileFxWidthRatio = mapWidth / fileWidth;
        fileFxHeightRatio = mapHeight / fileHeight;
    }

    /**
     * displays the nodes of the map
     */
    public void displayNodes() {

        resizingInfo();

        for (Node n : listOfNodes) {
            if (((n.getFloor().equals("1") || n.getFloor().equals("G") || n.getFloor().equals("")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") || n.getBuilding().equals("")))) {
                Circle circle = new Circle(xCoordOnTopElement(n.getX()), yCoordOnTopElement(n.getY()), 8);
                circle.setFill(Color.OLIVE);
                topElements.getChildren().add(circle);
            }
        }
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
        loadWindow("MapEditorButton", "mapButton", adminPop);
        loadWindow("SubmittedRequestsButton", "reqButton", requestPop);
        loadWindow("SubmittedRegistrationsButton", "regButton", registrationPop);
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

    public void toggleMap() {
        this.userLogin();
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
     * close the admin registration/request window
     */
    public void closeButtonPressed() {
        popPop.getChildren().clear();
    }

    /**
     * toggle the map editor window
     */
    public void toggleMapEditor() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/MapEditorGraph.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * toggle the admin registration window
     */
    public void toggleRegistration() {
        System.out.println("made it to the function the program is fucking stupid");
        popPop.setPrefWidth(1000);
        loadWindow("RegistrationAdminView", "registrationBar", popPop);
    }

    /**
     * toggle the admin request window
     */
    public void toggleRequest() {
        popPop.setPrefWidth(1000);
        loadWindow("RequestAdminView", "requestBar", popPop);
    }

    public void closeWindows() {
        popPop.getChildren().clear();
    }
}
