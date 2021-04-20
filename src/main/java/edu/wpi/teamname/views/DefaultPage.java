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
public class DefaultPage implements AuthListener, CloseListener {

    @FXML
    private VBox popPop; // vbox to populate with different fxml such as Navigation/Requests/Login
    @FXML
    private VBox adminPop; // vbox to populate Map Editor button
    @FXML
    private VBox requestPop; // vbox to populate Submitted Requests button
    @FXML
    private Path tonysPath; // this is Tony's path
    @FXML
    private AnchorPane anchor; //
    @FXML
    private ImageView hospitalMap;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXButton adminButton;

    String openWindow = "";
    ArrayList<Node> currentPath = new ArrayList<>();

    public void initialize() {
        AuthenticationManager.getInstance().addListener(this);
        Bridge.getInstance().addCloseListener(this);

        tonysPath.getElements().clear();

        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
        });

        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
        });

        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            hospitalMap.fitWidthProperty().bind(stackPane.widthProperty());
        });

        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            hospitalMap.fitHeightProperty().bind(stackPane.heightProperty());
        });
    }

    public void loadWindowPopPop(String fileName, String windowName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindowPopPop(windowName, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadWindowAdminPop(String fileName, String windowName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindowAdminPop(windowName, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadWindowRequestPop(String fileName, String windowName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindowRequestPop(windowName, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openWindowPopPop(String windowName, Parent root) {
        popPop.getChildren().clear();
        if (!windowName.equals(openWindow)) {
            popPop.getChildren().add(root);
            openWindow = windowName;
            return;
        }
        openWindow = "";
    }

    public void openWindowAdminPop(String windowName, Parent root) {
        adminPop.getChildren().clear();
        if (!windowName.equals(openWindow)) {
            adminPop.getChildren().add(root);
            openWindow = windowName;
            return;
        }
        openWindow = "";
    }

    public void openWindowRequestPop(String windowName, Parent root) {
        requestPop.getChildren().clear();
        if (!windowName.equals(openWindow)) {
            requestPop.getChildren().add(root);
            openWindow = windowName;
            return;
        }
        openWindow = "";
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
        loadWindowPopPop("Requests", "reqBar");
    }

    public void openLogin(ActionEvent actionEvent) {
        popPop.setPrefWidth(350.0);
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            loadWindowPopPop("Login", "loginBar");
        } else {
            AuthenticationManager.getInstance().signOut();
        }
    }

    public void openCheckIn(ActionEvent actionEvent) {
        popPop.setPrefWidth(657.0);
        loadWindowPopPop("UserRegistration", "registrationButton");
        //RegistrationAdminView admin = new RegistrationAdminView();
        //loadWindowPopPop("RegistrationAdminView", "registrationButton");
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
        loadWindowAdminPop("MapEditorButton", "mapButton");
        loadWindowRequestPop("SubmittedRequests", "reqButton");
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
