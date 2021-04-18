package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
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
import java.time.LocalDate;
import java.util.ArrayList;

public class UserRegistration extends MasterRequest {

    @FXML
    public JFXTextField nameInput;
    @FXML
    public JFXDatePicker dateOfBirth;
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
    public JFXCheckBox otherCheckbox;
    @FXML
    public JFXTextField otherInput;
    @FXML
    public JFXTextField phoneInput;

    public void submitRegistration(ActionEvent actionEvent) {
        try {
            String regexPattern = "\\d{3}-\\d{3}-\\d{4}"; //phone number pattern
            if (nameInput.getText().contains(" ") && phoneInput.getText().matches(regexPattern)) {
                LocalDate localDate = dateOfBirth.getValue();
                String date = localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth();

                ArrayList<String> reasonsForVisit = new ArrayList<String>();
                if (emergencyRoomCheckbox.isSelected()) {
                    reasonsForVisit.add("Emergency Room");
                }
                if (xrayCheckbox.isSelected()) {
                    reasonsForVisit.add("XRay");
                }
                if (mriCheckbox.isSelected()) {
                    reasonsForVisit.add("MRI");
                }
                if (eyeExamCheckbox.isSelected()) {
                    reasonsForVisit.add("Eye Exam");
                }
                if (labWorkCheckbox.isSelected()) {
                    reasonsForVisit.add("Lab Work");
                }
                if (physicalTherapyCheckbox.isSelected()) {
                    reasonsForVisit.add("Physical Therapy");
                }

                //submit
                edu.wpi.teamname.Database.UserRegistration database = new edu.wpi.teamname.Database.UserRegistration(nameInput.getText(), date, reasonsForVisit, phoneInput.getText());

                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/RegistrationConfirmation.fxml"));
                App.getPrimaryStage().getScene().setRoot(root);
            } else {
                /*
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
                */
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
