package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.commons.validator.EmailValidator;

public class Login {

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label failedLogin;

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
    }
}
