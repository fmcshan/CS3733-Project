package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.NavManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;

public class COVIDMessage {

    public static boolean covid = false;

    public void openPathToEmergency() {
        covid = true;
        SceneManager.getInstance().getDefaultPage().toggleNav();
    }
}
