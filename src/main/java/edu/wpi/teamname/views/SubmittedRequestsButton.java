package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.LanguageListener;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller for SubmitRequestsButton.fxml
 */
public class SubmittedRequestsButton implements LanguageListener {
    @FXML
    private JFXButton SubmittedRequests;

    @FXML
    void openSubmittedRequests() {
        SceneManager.getInstance().getDefaultPage().toggleRequest();
    }

    public void initialize(){

        Translator.getInstance().addLanguageListener(this);
    }

    private void setLanguages(){
        SubmittedRequests.setText(Translator.getInstance().get("NavMenu_SubmittedRequests"));
    }

    @Override
    public void updateLanguage() {
        setLanguages();
    }
}