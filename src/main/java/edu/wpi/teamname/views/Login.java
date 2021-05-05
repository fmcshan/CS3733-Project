package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.commons.validator.EmailValidator;

/**
 * Controller for Login.fxml
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class Login {



    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXPasswordField passwordField;

    /**
     * Once the login button is pressed, check if the email and password are valid
     * @param event
     */
    @FXML
    private Label failedLogin;
    @FXML
    private Label loginLabel;
    @FXML
    private Text loginDescription;
    @FXML
    private JFXButton loginButton;

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

    public void colorLoginButton() {
        if (!emailField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            loginButton.setStyle("-fx-background-color: #0067b1; -fx-text-fill: white; -fx-background-radius: 8");
        }
        else {
            loginButton.setStyle("-fx-background-color: white; -fx-border-color: #c3c3c3; -fx-border-radius: 8; -fx-text-fill: #c3c3c3");
        }
    }

    /**
     * Once the login button is pressed, check if the email and password are valid
     */
    @FXML
    void login() {
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
        SceneManager.getInstance().getDefaultPage().closeWindows();
        LoadFXML.setCurrentWindow("");
    }


}
