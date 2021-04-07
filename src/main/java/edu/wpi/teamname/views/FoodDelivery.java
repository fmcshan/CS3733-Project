package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamname.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class FoodDelivery {

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

    public void serviceRequests(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/ServiceRequests.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
