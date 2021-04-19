package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Authentication.AuthListener;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.bridge.Bridge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.apache.commons.validator.EmailValidator;

import java.io.IOException;

/**
 * Controller for Login.fxml
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class Login {

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label failedLogin;

    String openWindow = "";

    /**
     * Check if the email is valid using EmailValidator
     * @param email
     * @return true if the email is valid
     */
    public boolean isValidEmail(String email) {
        // create the EmailValidator instance
        EmailValidator validator = EmailValidator.getInstance();

        // check for valid email addresses using isValid method
        return validator.isValid(email);
    }

    /**
     * Once the login button is pressed, check if the email and password are valid
     * @param event
     */
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
            failedLogin.setText("Invalid Credentials");
            return;
        }
        Bridge.getInstance().close(); //close the window if the user is logged in
    }
}
