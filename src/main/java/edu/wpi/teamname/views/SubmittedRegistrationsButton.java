package edu.wpi.teamname.views;

import edu.wpi.teamname.bridge.Bridge;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller for SubmitRegistrationsButton.fxml
 */
public class SubmittedRegistrationsButton extends LoadFXML{

    @FXML
    private DefaultPage defaultPage;

    public void openSubmittedRegistrations() {
        Bridge.getInstance().loadRegistration();
    }
}
