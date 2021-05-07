package edu.wpi.teamname.views.help;

import edu.wpi.teamname.views.LoadFXML;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;

public class defaultBar {

    public void openAbout(ActionEvent actionEvent) {
        SceneManager.getInstance().getDefaultPage().setHelpButton(false);
        SceneManager.getInstance().getDefaultPage().getPopPop().getChildren().clear();
        LoadFXML.getInstance().loadWindow("AboutPage", "aboutPage", SceneManager.getInstance().getDefaultPage().getPopPop());
    }

    public void closeWindow() {
        SceneManager.getInstance().getDefaultPage().getPopPop().getChildren().clear();
        LoadFXML.setCurrentHelp("");
        LoadFXML.setCurrentWindow("");
        SceneManager.getInstance().getDefaultPage().setHelpButton(true);
    }
}
