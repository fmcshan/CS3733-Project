package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.wpi.teamname.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class DefaultPage {

    @FXML
    private VBox popPop;

    @FXML
    private JFXButton navButton;

    @FXML
    private JFXButton reqButton;

    @FXML
    private JFXButton exitApplication;

    @FXML
    private FontAwesomeIconView adminButton;

    String openWindow = "";

    public void loadWindow(String fileName, String windowName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindow(windowName, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openWindow(String windowName, Parent root) {
        popPop.getChildren().clear();
        if (!windowName.equals(openWindow)) {
            popPop.getChildren().add(root);
            openWindow = windowName;
            return;
        }
        openWindow = "";
    }

    public void toggleNav(ActionEvent actionEvent) {
        loadWindow("Navigation", "navBar");
    }

    public void openRequests(ActionEvent actionEvent) {
        loadWindow("Requests", "reqBar");
    }

    public void openLogin(ActionEvent actionEvent) {
        loadWindow("Login", "loginBar");
    }

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void registerUser(ActionEvent actionEvent) {
        //loadScene("UserRegistration");
    }
}
