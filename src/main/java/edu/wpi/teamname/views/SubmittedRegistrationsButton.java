package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.ButtonManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;

import java.util.Map;

/**
 * Controller for SubmitRegistrationsButton.fxml
 */
public class SubmittedRegistrationsButton {

    @FXML
    private JFXButton regButton;

    public JFXButton getRegButton() {
        return regButton;
    }

    public void openSubmittedRegistrations() {
        SceneManager.getInstance().getDefaultPage().toggleRegistration();
        ButtonManager.selectButton(regButton);
    }
}
