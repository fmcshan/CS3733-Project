package edu.wpi.teamname.views;

import edu.wpi.teamname.bridge.Bridge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SubmittedRequestsButton {

    @FXML
    void openSubmittedRequests(ActionEvent event) {
        Bridge.getInstance().loadRequestListener();
    }
}
