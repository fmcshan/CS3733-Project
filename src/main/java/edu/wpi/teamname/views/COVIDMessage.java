package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.NavManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;

public class COVIDMessage {

    public void openPathToEmergency(ActionEvent actionEvent) {
        NavManager.getInstance().getNavigationPage().getToCombo().setValue("FDEPT00401");
        SceneManager.getInstance().getDefaultPage().toggleNav();

    }
}
