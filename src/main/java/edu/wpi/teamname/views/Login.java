package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.apache.commons.validator.EmailValidator;

import java.io.IOException;

public class Login implements AuthListener {

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label failedLogin;

    @FXML
    private JFXButton Login;

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
        adminPop.getChildren().clear();
        if (!windowName.equals(openWindow)) {
            adminPop.getChildren().add(root);
            openWindow = windowName;
            return;
        }
        openWindow = "";
    }

    public boolean isValidEmail(String email) {
        // create the EmailValidator instance
        EmailValidator validator = EmailValidator.getInstance();

        // check for valid email addresses using isValid method
        return validator.isValid(email);
    }

    @FXML
    void login(ActionEvent event) {
        String email = emailField.getText();

        if (!isValidEmail(email)) {
            failedLogin.setText("Invalid Credentials");
            return;
        } else {
            failedLogin.setText("");
        }

        AuthenticationManager.getInstance().loginWithEmailAndPassword(emailField.getText(), passwordField.getText());
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            failedLogin.setText("Incorrect Password");
        }
        else {
            loadWindow("Map Editor", "mapButton");
        }
    }
}
