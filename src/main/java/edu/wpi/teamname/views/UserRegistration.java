package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Scene;


import java.io.IOException;

public class UserRegistration extends MasterRequest {

    @FXML
    public JFXTextField nameInput;
    @FXML
    public JFXTextField birthdayInput;
    @FXML
    public JFXCheckBox emergencyRoomCheckbox;
    @FXML
    public JFXCheckBox xrayCheckbox;
    @FXML
    public JFXCheckBox mriCheckbox;
    @FXML
    public JFXCheckBox eyeExamCheckbox;
    @FXML
    public JFXCheckBox labWorkCheckbox;
    @FXML
    public JFXCheckBox physicalTherapyCheckbox;
    @FXML
    public JFXTextField emailInput;


    public void checkName() {
        if(!(nameInput.getText().contains(" "))){

        }
    }

    public void submitRegistration(ActionEvent actionEvent) {
        try {
            if(nameInput.getText().contains(" ")) {
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/RegistrationConfirmation.fxml"));
                App.getPrimaryStage().getScene().setRoot(root);
            } else {
                Stage stage = new Stage();
                stage.setTitle("Submission Error");
                TilePane tilepane = new TilePane();
                Scene scene = new Scene(tilepane, 400, 100);
                stage.setScene(scene);
                Popup popup = new Popup();
                Label label = new Label("Error! Please be sure to enter your full name.");;
                popup.getContent().add(label);
                label.setMinWidth(80);
                label.setMinHeight(80);
                stage.show();
                popup.show(stage);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
