package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;

/**
 * Controller for SubmitRequestsButton.fxml
 */
public class SubmittedRequestsButton {
    @FXML
    private JFXButton SubmittedRequests;

    @FXML
    void openSubmittedRequests() {
        SceneManager.getInstance().getDefaultPage().toggleRequest();
    }
}