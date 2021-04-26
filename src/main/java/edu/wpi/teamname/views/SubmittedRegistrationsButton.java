package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller for SubmitRegistrationsButton.fxml
 */
public class SubmittedRegistrationsButton {

    public void openSubmittedRegistrations() {
        SceneManager.getInstance().getDefaultPage().toggleRegistration();
    }
}
