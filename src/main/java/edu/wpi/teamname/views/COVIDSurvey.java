package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;


public class COVIDSurvey {
    @FXML
    public JFXCheckBox symptom1Checkbox;
    @FXML
    public JFXCheckBox symptom2Checkbox;
    @FXML
    public JFXCheckBox symptom3Checkbox;
    @FXML
    public JFXCheckBox symptom4Checkbox;
    @FXML
    public VBox symptomsPop;

    public VBox getSymptomsPop() {
        return symptomsPop;
    }

    public boolean hasCovid() {
        return symptom1Checkbox.isSelected() || symptom2Checkbox.isSelected() || symptom3Checkbox.isSelected() || symptom4Checkbox.isSelected();
    }

    public void submitScreening() {
        if (!hasCovid()) {
            LoadFXML.getInstance().loadWindow("UserRegistration", "userRegistration", SceneManager.getInstance().getDefaultPage().getPopPop());
        } else {
            LoadFXML.getInstance().loadWindow("COVIDMessage", "covidMessage", SceneManager.getInstance().getDefaultPage().getPopPop());
        }
    }

    public void openSymptoms() {
        Symptoms symptoms = new Symptoms(this);
        symptoms.loadSymptoms();
    }

    public void openCloseContact(ActionEvent actionEvent) {
       //LoadFXML.getInstance().loadWindow("CloseContact", "closeContact", symptomsPop);
    }
}
