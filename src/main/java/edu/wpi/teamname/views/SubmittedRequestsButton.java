package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.ButtonManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;

/**
 * Controller for SubmitRequestsButton.fxml
 */
public class SubmittedRequestsButton {

    @FXML
    private JFXButton submittedReqButton;

    public JFXButton getSubmittedReqButton() {
        return submittedReqButton;
    }

    @FXML
    void openSubmittedRequests() {
        SceneManager.getInstance().getDefaultPage().toggleRequest();
        ButtonManager.selectButton(submittedReqButton, "nav-btn-selected", ButtonManager.buttons);
        if (LoadFXML.getCurrentWindow().equals("")) {
            ButtonManager.remove_class();
        }
    }
}