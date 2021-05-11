package edu.wpi.teamname.views.help;

import edu.wpi.teamname.views.LoadFXML;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;

public class defaultSignOutBar {
    public void closeWindow() {
        SceneManager.getInstance().getDefaultPage().getPopPop().getChildren().clear();
        LoadFXML.setCurrentHelp("");
        LoadFXML.setCurrentWindow("");
        SceneManager.getInstance().getDefaultPage().setHelpButton(true);
    }

    public void openCredits(ActionEvent actionEvent) {
        SceneManager.getInstance().getDefaultPage().setHelpButton(false);
        SceneManager.getInstance().getDefaultPage().getPopPop().getChildren().clear();
        LoadFXML.getInstance().loadWindow("Credits", "Credits", SceneManager.getInstance().getDefaultPage().getPopPop());
    }
}
