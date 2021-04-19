package edu.wpi.teamname.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class Requests {
    @FXML
    private ComboBox<String> requestsBox;
    @FXML
    private DefaultPage defaultPage;

    public void initialize(){
        requestsBox.getItems().add("Gift Delivery");
    }

    public void selectRequest() {
        if (requestsBox.getValue().equals("Gift Delivery")){
            //open Gift Delivery in a new vbox like succPop
        }
    }
}
