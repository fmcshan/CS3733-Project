package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class MapEditor {
    @FXML
    JFXComboBox<String> toCombo;
    @FXML
    JFXComboBox<String> fromCombo;

    public void initialize() {
        PathFindingDatabaseManager.getNodes().forEach(n -> {
            toCombo.getItems().add(n.getLongName());
            fromCombo.getItems().add(n.getLongName());
        });
    }
}
