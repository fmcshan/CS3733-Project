package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller for SubmitRequestsButton.fxml
 */
public class SubmittedRequestsButton {

    @FXML
    void openSubmittedRequests() {
        SceneManager.getInstance().getDefaultPage().toggleRequest();
    }
}
