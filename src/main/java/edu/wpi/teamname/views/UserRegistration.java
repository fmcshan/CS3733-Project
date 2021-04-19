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

public class UserRegistration {

    @FXML
    public Label failedName;
    @FXML
    public JFXTextField nameInput;
    @FXML
    public Label failedDate;
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
    public Label failedReason;
    @FXML
    public JFXCheckBox otherCheckbox;
    @FXML
    public JFXTextField otherInput;
    @FXML
    public Label failedPhoneNumber;
    @FXML
    public JFXTextField phoneInput;

    public boolean nameInputValid() {
        return nameInput.getText().contains(" ");
    }

    public boolean dateSelected() {
        boolean ans = false;
        if (dateOfBirth.getValue() != null) {
            LocalDate date = dateOfBirth.getValue();
            ans = (date.getYear() > 0) && (date.getMonthValue() > 0 && date.getMonthValue() < 13) && (date.getDayOfMonth() > 0 && date.getDayOfMonth() < 32);
            if ((date.getMonthValue() == 2 && date.getDayOfMonth() > 28) || ((date.getMonthValue() == 4 || date.getMonthValue() == 6 || date.getMonthValue() == 9 || date.getMonthValue() == 11) && date.getDayOfMonth() > 30)) {
                ans = false;
            }
        }

        return ans;
    }

    public boolean aCheckboxSelected() {
        return emergencyRoomCheckbox.isSelected() || xrayCheckbox.isSelected() || mriCheckbox.isSelected() || eyeExamCheckbox.isSelected() || labWorkCheckbox.isSelected() || physicalTherapyCheckbox.isSelected() || otherCheckbox.isSelected();
    }

    public boolean otherCheckboxValid() {
        return !otherCheckbox.isSelected() || (otherCheckbox.isSelected() && !otherInput.getText().equals(""));
    }

    public boolean phoneNumberValid() {
        String regexPattern = "\\d{3}-\\d{3}-\\d{4}"; //phone number pattern
        return phoneInput.getText().matches(regexPattern);
    }

    public void submitRegistration(ActionEvent actionEvent) {
        try {
            if (phoneInput.getText().length() == 10 && !phoneInput.getText().contains("-")) {
                phoneInput.setText(phoneInput.getText().substring(0, 3) + "-" + phoneInput.getText().substring(3, 6) + "-" + phoneInput.getText().substring(6));
                System.out.println(phoneInput.getText());
            }
            if (!nameInputValid()) {
                failedName.setText("Invalid Name Entry");
            } else {
                failedName.setText("");
            }

            if (!dateSelected()) {
                failedDate.setText("Invalid Date Entry");
            } else {
                failedDate.setText("");
            }

            if (!aCheckboxSelected()) {
                failedReason.setText("Select a Reason");
            } else if (!otherCheckboxValid()) {
                failedReason.setText("Invalid Other Reason");
            } else {
                failedReason.setText("");
            }

            if (!phoneNumberValid()) {
                failedPhoneNumber.setText("Invalid Phone Number");
            } else {
                failedPhoneNumber.setText("");
            }

            if (nameInputValid() && dateSelected() && aCheckboxSelected() && otherCheckboxValid() && phoneNumberValid()) {
                LocalDate localDate = dateOfBirth.getValue();
                String date = localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth();
                //System.out.println(date);
                ArrayList<String> reasonsForVisit = new ArrayList<String>();
                if (emergencyRoomCheckbox.isSelected()) {
                    reasonsForVisit.add("Emergency Room");
                }
                if (xrayCheckbox.isSelected()) {
                    reasonsForVisit.add("Radiology");
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
                if (otherCheckbox.isSelected()) {
                    reasonsForVisit.add(otherInput.getText());
                }

                //submit
                edu.wpi.teamname.Database.UserRegistration database = new edu.wpi.teamname.Database.UserRegistration(nameInput.getText(), date, reasonsForVisit, phoneInput.getText());

                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/RegistrationConfirmation.fxml"));
                App.getPrimaryStage().getScene().setRoot(root);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
