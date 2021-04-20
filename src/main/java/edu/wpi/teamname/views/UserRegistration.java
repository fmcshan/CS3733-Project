package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controller for Navigation.fxml
 * @author Frank McShan, Lauren Sowerbutts
 */
public class UserRegistration {

    @FXML
    private Label failedName;
    @FXML
    private JFXTextField nameInput;
    @FXML
    private JFXDatePicker dateOfBirth;
    @FXML
    private Label failedDate;
    @FXML
    private JFXCheckBox emergencyRoomCheckbox;
    @FXML
    private JFXCheckBox xrayCheckbox;
    @FXML
    private JFXCheckBox mriCheckbox;
    @FXML
    private JFXCheckBox eyeExamCheckbox;
    @FXML
    private JFXCheckBox labWorkCheckbox;
    @FXML
    private JFXCheckBox physicalTherapyCheckbox;
    @FXML
    private JFXCheckBox otherCheckbox;
    @FXML
    private Label failedReason;
    @FXML
    private JFXTextField otherInput;
    @FXML
    private JFXTextField phoneInput;
    @FXML
    private Label failedPhoneNumber;
    @FXML
    private VBox successPop; // this Vbox will be used to display the success page

    /**
     * getter for successPop Vbox
     * @return the successPop VBox
     */
    public VBox getSuccessPop() {
        return successPop;
    }

    /**
     * Check if name input contains a space for first and last name
     * @return true if there is a space
     */
    public boolean nameInputValid() {
        return nameInput.getText().contains(" ");
    }

    /**
     * Check if their is a valid date selected
     * @return true if there is a valid value in the DatePicker
     */
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

    /**
     * Check is there is a checkbox selected
     * @return true if there is a checkbox selected
     */
    public boolean aCheckboxSelected() {
        return emergencyRoomCheckbox.isSelected() || xrayCheckbox.isSelected() || mriCheckbox.isSelected() || eyeExamCheckbox.isSelected() || labWorkCheckbox.isSelected() || physicalTherapyCheckbox.isSelected() || otherCheckbox.isSelected();
    }

    /**
     * If the "Other" checkbox was selected, check if there was an input in the text field
     * @return true if there is an input in the text field
     */
    public boolean otherCheckboxValid() {
        return !otherCheckbox.isSelected() || (otherCheckbox.isSelected() && !otherInput.getText().equals(""));
    }

    /**
     * Check if the phone number entered is valid
     * @return true if the phone number is valid
     */
    public boolean phoneNumberValid() {
        String regexPattern = "\\d{3}-\\d{3}-\\d{4}"; //phone number pattern
        return phoneInput.getText().matches(regexPattern);
    }

    /**
     * If the submit button is pressed, check if inputs are valid and display Success page
     */
    public void submitRegistration() {
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

            LoadFXML.setCurrentWindow("");

            //submit
            edu.wpi.teamname.Database.UserRegistration database = new edu.wpi.teamname.Database.UserRegistration(nameInput.getText(), date, reasonsForVisit, phoneInput.getText());

            // load Success page in successPop VBox
            successPop.setPrefWidth(657.0);
            Success success = new Success(this);
            success.loadSuccess();
        }
    }
}
