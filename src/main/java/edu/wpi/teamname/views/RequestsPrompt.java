package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.ButtonManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;

public class RequestsPrompt {

    public void openSupportChat(ActionEvent actionEvent) {
        SceneManager.getInstance().getDefaultPage().getPopPop().getChildren().clear();
        SceneManager.getInstance().getDefaultPage().openChatBot();
        ButtonManager.remove_class();
        LoadFXML.setCurrentWindow("");
    }

    public void openServiceRequests(ActionEvent actionEvent) {
        SceneManager.getInstance().getDefaultPage().getPopPop().getChildren().clear();
        LoadFXML.getInstance().loadWindow("Requests", "Requests", SceneManager.getInstance().getDefaultPage().getPopPop());
        LoadFXML.setCurrentHelp("reqBar");
        LoadFXML.setCurrentWindow("reqBar");
        SceneManager.getInstance().getDefaultPage().setHelpButton(true);
    }
}
