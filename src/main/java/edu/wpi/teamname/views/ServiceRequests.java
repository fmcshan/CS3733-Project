package edu.wpi.teamname.views;

import edu.wpi.teamname.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ServiceRequests {
    public void floralDelivery(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/FloralDelivery.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void foodDelivery(ActionEvent actionEvent) {
    }

    public void medicineDelivery(ActionEvent actionEvent) {
    }

    public void giftDelivery(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/GiftDelivery.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sanitationServices(ActionEvent actionEvent) {
    }

    public void laundryServices(ActionEvent actionEvent) {
    }

    public void patientTransportation(ActionEvent actionEvent) {
    }

    public void facilitiesMaintenance(ActionEvent actionEvent) {
    }

    public void computerService(ActionEvent actionEvent) {
    }
}
