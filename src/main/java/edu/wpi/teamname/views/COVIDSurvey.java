package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controller for COVIDSurvey.fxml
 *
 * @author Lauren Sowerbutts, Frank McShan
 */
public class COVIDSurvey {
    @FXML
    private Label title;
    @FXML
    private Label desc;
    @FXML
    private Label check1;
    @FXML
    private Label check2;
    @FXML
    private Label check3;
    @FXML
    private Label check4;
    @FXML
    private Label check5;
    @FXML
    private Label checkInst;
    @FXML
    private JFXCheckBox symptom1Checkbox;
    @FXML
    private JFXCheckBox symptom2Checkbox;
    @FXML
    private JFXCheckBox symptom3Checkbox;
    @FXML
    private JFXCheckBox symptom4Checkbox;
    @FXML
    private JFXButton submitButton;
    @FXML
    private VBox symptomsPop;

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
        CloseContact closeContact = new CloseContact(this);
        closeContact.loadCloseContact();
    }
}
