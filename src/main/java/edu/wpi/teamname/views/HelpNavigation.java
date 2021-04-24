package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;

public class HelpNavigation {

    public void closeWindow() {
        SceneManager.getInstance().getDefaultPage().getPopPop2().getChildren().clear();
        LoadFXML.setCurrentHelp("");
    }
}
