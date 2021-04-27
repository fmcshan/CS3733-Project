package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.views.manager.LanguageListener;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class COVIDSurvey implements LanguageListener {
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

    public void initialize(){
        Translator.getInstance().addLanguageListener(this);
        setLanguages();
    }

    private void setLanguages(){
        title.setText(Translator.getInstance().get("Covid_title"));
        desc.setText(Translator.getInstance().get("Covid_desc"));
        check1.setText(Translator.getInstance().get("Covid_check1"));
        check2.setText(Translator.getInstance().get("Covid_check2"));
        check3.setText(Translator.getInstance().get("Covid_check3"));
        check4.setText(Translator.getInstance().get("Covid_check4"));
        check5.setText(Translator.getInstance().get("Covid_check5"));
        checkInst.setText(Translator.getInstance().get("Covid_checkInst"));
        symptom1Checkbox.setText(Translator.getInstance().get("Covid_symptom1Checkbox"));
        symptom2Checkbox.setText(Translator.getInstance().get("Covid_symptom2Checkbox"));
        symptom3Checkbox.setText(Translator.getInstance().get("Covid_symptom3Checkbox"));
        symptom4Checkbox.setText(Translator.getInstance().get("Covid_symptom4Checkbox"));
        submitButton.setText(Translator.getInstance().get("Covid_submitButton"));
    }

    @Override
    public void updateLanguage() {
        setLanguages();
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
