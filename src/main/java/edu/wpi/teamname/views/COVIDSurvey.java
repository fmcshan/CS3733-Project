package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.ArrayList;

public class COVIDSurvey {
    @FXML
    public Label failedReason;
    @FXML
    public JFXCheckBox symptom1Checkbox;
    @FXML
    public JFXCheckBox symptom2Checkbox;
    @FXML
    public JFXCheckBox symptom3Checkbox;
    @FXML
    public JFXCheckBox symptom4Checkbox;
    @FXML
    public JFXCheckBox symptom5Checkbox;
    @FXML
    public JFXCheckBox noSymptomsCheckbox;
    @FXML
    public JFXCheckBox recoverCheckbox;

    public boolean aCheckboxSelected() {
        return symptom1Checkbox.isSelected() || symptom2Checkbox.isSelected() || symptom3Checkbox.isSelected() || symptom4Checkbox.isSelected() || symptom5Checkbox.isSelected() || vaccineCheckbox.isSelected() || recoverCheckbox.isSelected();
    }

    public boolean hasCovid() {
        return symptom1Checkbox.isSelected() || symptom2Checkbox.isSelected() || symptom3Checkbox.isSelected() || symptom4Checkbox.isSelected() || symptom5Checkbox.isSelected();
    }


    public void submitScreening(ActionEvent actionEvent) {
        if (!aCheckboxSelected()) {
            failedReason.setText("Select at Least One Option");
        } else {
            failedReason.setText("");
        }

        if (!hasCovid() && aCheckboxSelected()) {
            ArrayList<String> selectedOptions = new ArrayList<String>();
            if (vaccineCheckbox.isSelected()) {
                selectedOptions.add("I am fully vaccinated. ");
            }
            if (recoverCheckbox.isSelected()) {
                selectedOptions.add("I have recovered from a documented COVID-19 infection in the last three months. ");
            }

            //LoadFXML.setCurrentWindow("");
            LoadFXML.getInstance().loadWindow("UserRegistration", "userRegistration", SceneManager.getInstance().getDefaultPage().getPopPop());
//            //submit
//            edu.wpi.teamname.Database.UserRegistration formData = new edu.wpi.teamname.Database.UserRegistration(nameInput.getText(), date, reasonsForVisit, phoneInput.getText());
//            Submit.getInstance().submitUserRegistration(formData);

        } else if (aCheckboxSelected()) {
            ArrayList<String> selectedOptions = new ArrayList<String>();
            if (symptom1Checkbox.isSelected()) {
                selectedOptions.add("I have had a symptomatic COVID-19 test in the last 14 days. ");
            }
            if (symptom2Checkbox.isSelected()) {
                selectedOptions.add("I have received a positive test result for COVID-19 in the last 14 days. ");
            }
            if (symptom3Checkbox.isSelected()) {
                selectedOptions.add("I have been around someone diagnosed with COVID-19 in the last 14 days. ");
            }
            if (symptom4Checkbox.isSelected()) {
                selectedOptions.add("I am experiencing COVID-19-like symptoms. ");
            }
            if (symptom5Checkbox.isSelected()) {
                selectedOptions.add("I have traveled in the past 10 days. ");
            }
            if (vaccineCheckbox.isSelected()) {
                selectedOptions.add("I am fully vaccinated. ");
            }
            if (recoverCheckbox.isSelected()) {
                selectedOptions.add("I have recovered from a documented COVID-19 infection in the last three months. ");
            }

           // LoadFXML.setCurrentWindow("");

//            //submit
//            edu.wpi.teamname.Database.UserRegistration formData = new edu.wpi.teamname.Database.UserRegistration(nameInput.getText(), date, reasonsForVisit, phoneInput.getText());
//            Submit.getInstance().submitUserRegistration(formData);

            LoadFXML.getInstance().loadWindow("COVIDMessage", "covidMessage", SceneManager.getInstance().getDefaultPage().getPopPop());
        }

    }
}
