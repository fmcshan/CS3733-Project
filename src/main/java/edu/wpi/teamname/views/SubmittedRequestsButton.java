package edu.wpi.teamname.views;

import edu.wpi.teamname.bridge.Bridge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller for SubmitRequestsButton.fxml
 */
public class SubmittedRequestsButton {

    @FXML
    void openSubmittedRequests() {
        Bridge.getInstance().loadRequestListener();
    }
}
