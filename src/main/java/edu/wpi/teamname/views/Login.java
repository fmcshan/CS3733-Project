package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    /**
     * Once the login button is pressed, check if the email and password are valid
     */
    @FXML
    void login() {
        String email = emailField.getText();

        if (!isValidEmail(email)) {
            failedLogin.setText("failed login");
            return;
        } else {
            failedLogin.setText("");
        }

        AuthenticationManager.getInstance().loginWithEmailAndPassword(emailField.getText(), passwordField.getText());
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            failedLogin.setText("failed login");
            return;
        }
        SceneManager.getInstance().getDefaultPage().closeWindows();
    }


}
