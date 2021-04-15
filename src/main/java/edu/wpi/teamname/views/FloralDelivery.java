package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FloralDelivery extends MasterRequest {

    @FXML
    JFXComboBox<String> comboBox;
    public void initialize() {
        comboBox.getItems().add("Roses");
        comboBox.getItems().add("Tulips");
        comboBox.getItems().add("Daisies");
        comboBox.getItems().add("Lilies");
    }
}
