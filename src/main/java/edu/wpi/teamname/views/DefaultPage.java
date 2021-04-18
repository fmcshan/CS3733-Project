package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.wpi.teamname.App;
import edu.wpi.teamname.Authentication.AuthListener;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class DefaultPage implements AuthListener {

    @FXML
    private VBox popPop;

    @FXML
    private VBox adminPop;

    @FXML
    private JFXButton navButton;

    @FXML
    private JFXButton reqButton;

    @FXML
    private JFXButton exitApplication;

    @FXML
    private FontAwesomeIconView adminButton;

    String openWindow = "";

    public void initialize() {
        AuthenticationManager.getInstance().addListener(this);
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

    public void toggleNav(ActionEvent actionEvent) {
        loadWindowPopPop("Navigation", "navBar");
    }

    public void openRequests(ActionEvent actionEvent) {
        loadWindowPopPop("Requests", "reqBar");
    }

    public void openLogin(ActionEvent actionEvent) {
        loadWindowPopPop("Login", "loginBar");
    }

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void userLogin() {
        loadWindowAdminPop("MapEditorButton", "mapButton");
    }
}
