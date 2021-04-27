package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.LanguageListener;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller for SubmitRegistrationsButton.fxml
 */
public class SubmittedRegistrationsButton implements LanguageListener {

    @FXML
    private JFXButton Registrations;

    public void openSubmittedRegistrations() {
        SceneManager.getInstance().getDefaultPage().toggleRegistration();
    }
    public void initialize(){

        Translator.getInstance().addLanguageListener(this);
    }

    private void setLanguages(){
        Registrations.setText(Translator.getInstance().get("NavMenu_Registrations"));
    }

    @Override
    public void updateLanguage() {
        setLanguages();
    }
}
