package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.views.manager.LanguageListener;
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
public class Login implements LanguageListener {



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


    public void initialize(){
        Translator.getInstance().addLanguageListener(this);
        setLanguages();
    }

    private void setLanguages(){
        loginButton.setText(Translator.getInstance().get("Login_Button"));
        loginDescription.setText(Translator.getInstance().get("Login_loginDescription"));
        loginLabel.setText(Translator.getInstance().get("Login_loginLabel"));
        passwordField.setPromptText(Translator.getInstance().get("Login_passwordField"));
        emailField.setPromptText(Translator.getInstance().get("Login_emailField"));
    }

    @Override
    public void updateLanguage() {
        setLanguages();
    }


    public void openMapEditor(ActionEvent actionEvent) {
        SceneManager.getInstance().getDefaultPage().toggleMapEditor();
    }



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
            failedLogin.setText(Translator.getInstance().get("Login_failedLogin"));
            return;
        } else {
            failedLogin.setText("");
        }

        AuthenticationManager.getInstance().loginWithEmailAndPassword(emailField.getText(), passwordField.getText());
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            failedLogin.setText(Translator.getInstance().get("Login_failedLogin"));
            return;
        }
        SceneManager.getInstance().getDefaultPage().closeWindows();
    }


}
