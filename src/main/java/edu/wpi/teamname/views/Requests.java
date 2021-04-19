package edu.wpi.teamname.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class Requests {
    @FXML
    private ComboBox<String> requestsBox;

    public void initialize(){
        requestsBox.getItems().add("Gift Delivery");
    }

    public void openForm(ActionEvent actionEvent) {
        if (requestsBox.getValue().equals("Gift Delivery")){

        }
    }
}
