package edu.wpi.teamname.views.help;

import edu.wpi.teamname.views.LoadFXML;
import edu.wpi.teamname.views.manager.SceneManager;

public class defaultBar {
    public void closeWindow() {
        SceneManager.getInstance().getDefaultPage().getPopPop2().getChildren().clear();
        LoadFXML.setCurrentHelp("");
    }
}
