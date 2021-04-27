package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;

/**
 * Controller for SubmitRegistrationsButton.fxml
 */
public class SubmittedRegistrationsButton {

    @FXML
    private JFXButton Registrations;

    public void openSubmittedRegistrations() {
        SceneManager.getInstance().getDefaultPage().toggleRegistration();
    }
}
