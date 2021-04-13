package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamname.App;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;


public class MapEditor {
    @FXML
    JFXComboBox<String> toCombo;
    @FXML
    JFXComboBox<String> fromCombo;

    public void initialize() {
        PathFindingDatabaseManager.getInstance().getNodes().forEach(n -> {
            toCombo.getItems().add(n.getLongName());
            fromCombo.getItems().add(n.getLongName());
        });
    }

    public void returnHome(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/DefaultPage.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }
}
