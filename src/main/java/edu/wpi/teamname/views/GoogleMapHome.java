package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GoogleMapHome {

    @FXML
    private Label title;

    @FXML
    private Text description;

    @FXML
    private Label fromLabel;

    @FXML
    private JFXTextField numInput;

    @FXML
    private Label fromLabel1;

    @FXML
    private Label fromLabel12;

    @FXML
    private JFXTextField streetInput;

    @FXML
    private JFXComboBox<String> streetEnding;

    @FXML
    private Label fromLabel2;

    @FXML
    private JFXTextField townInput;

    @FXML
    private Label fromLabel11;

    @FXML
    private JFXTextField stateInput;

    @FXML
    private Label toLabel;

    @FXML
    private JFXComboBox<String> travelMode;

    @FXML
    private JFXButton submitButton;



    public void initialize() {
        travelMode.getItems().add("driving");
        travelMode.getItems().add("bicycling");
        travelMode.getItems().add("walking");
        streetEnding.getItems().add("St");
        streetEnding.getItems().add("Dr");
        streetEnding.getItems().add("Rd");
        streetEnding.getItems().add("St");
        streetEnding.getItems().add("Ave");
        streetEnding.getItems().add("Blvd");
        streetEnding.getItems().add("Cir");
        streetEnding.getItems().add("Ln");
    }

    @FXML
    void submit() throws URISyntaxException, IOException {
        Desktop aDesktop = Desktop.getDesktop();
        String URL = "https://www.google.com/maps/dir/?api=1&origin=75+Francis+St+Boston+MA&destination=" + numInput.getText() + "+" + streetInput.getText() +
"+" + streetEnding.getValue() + "+" + townInput.getText() + "+" + stateInput.getText() + "&travelmode=" + travelMode.getValue().toString();
        URI link = new URI(URL);
        aDesktop.browse(link);

    }
}

//String URL = "https://www.google.com/maps/dir/?api=1&origin=" + numInput.getText() + "+" + streetInput.getText() +
//"+" + streetEnding.getValue() + "+" + townInput.getText() + "+" + stateInput.getText() + "&destination=75+Francis+St+Boston+MA&travelmode=" + travelMode.getValue().toString();