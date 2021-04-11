package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeTableColumn;

import java.io.IOException;

public class DefaultPage {

    public void loadScene(String fileName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void pathFinding(ActionEvent actionEvent) {
    }

    public void edgesEditor(ActionEvent actionEvent) {
    }

    public void nodesEditor(ActionEvent actionEvent) { loadScene("NodesEditor");
    }

    public void serviceRequests(ActionEvent actionEvent) {
        loadScene("ServiceRequests");
    }

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }
}
