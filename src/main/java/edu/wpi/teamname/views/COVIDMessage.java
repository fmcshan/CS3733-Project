package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class COVIDMessage {

    @FXML private Label title;
    @FXML private Label successText;

    public static boolean covid = false;

    public void openPathToEmergency() {
        covid = true;
        SceneManager.getInstance().getDefaultPage().toggleNav();
    }
}
