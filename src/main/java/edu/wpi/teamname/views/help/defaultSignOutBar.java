package edu.wpi.teamname.views.help;

import edu.wpi.teamname.views.LoadFXML;
import edu.wpi.teamname.views.manager.SceneManager;

public class defaultSignOutBar {
    public void closeWindow() {
        SceneManager.getInstance().getDefaultPage().getPopPop().getChildren().clear();
        LoadFXML.setCurrentHelp("");
    }
}
