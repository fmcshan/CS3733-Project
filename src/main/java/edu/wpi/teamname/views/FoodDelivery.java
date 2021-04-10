package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FoodDelivery extends MasterRequest {

    JFXComboBox<Label> jfxCombo = new JFXComboBox<Label>();

    @FXML
    JFXComboBox<String> comboBox;
    public void initialize() {
        comboBox.getItems().add("Hotdog");
        comboBox.getItems().add("Hamburger");
        comboBox.getItems().add("Turkeyburger");
        comboBox.getItems().add("Cheeseburger");
        comboBox.getItems().add("Beyond Burger");
        comboBox.getItems().add("Impossible Burger");
    }
}
