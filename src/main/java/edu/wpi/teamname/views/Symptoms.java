package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.LanguageListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Symptoms implements LanguageListener {
    @FXML private COVIDSurvey covidSurvey;
    @FXML private Label title;
    @FXML private Label symptomsInclude;
    @FXML private Label s1;
    @FXML private Label s2;
    @FXML private Label s3;
    @FXML private Label s4;
    @FXML private Label s5;
    @FXML private Label s6;
    @FXML private Label s7;
    @FXML private Label s8;
    @FXML private Label s9;
    @FXML private Label s10;
    @FXML private Label s11;
    @FXML private JFXButton OKButton;


    public Symptoms(COVIDSurvey covidSurvey) {
        this.covidSurvey = covidSurvey;
    }

    public void initialize(){
        Translator.getInstance().addLanguageListener(this);
        setLanguages();
    }

    private void setLanguages(){
        title.setText(Translator.getInstance().get("Symptoms_title"));
        symptomsInclude.setText(Translator.getInstance().get("Symptoms_symptomsInclude"));
        s1.setText(Translator.getInstance().get("Symptoms_s1"));
        s2.setText(Translator.getInstance().get("Symptoms_s2"));
        s3.setText(Translator.getInstance().get("Symptoms_s3"));
        s4.setText(Translator.getInstance().get("Symptoms_s4"));
        s5.setText(Translator.getInstance().get("Symptoms_s5"));
        s6.setText(Translator.getInstance().get("Symptoms_s6"));
        s7.setText(Translator.getInstance().get("Symptoms_s7"));
        s8.setText(Translator.getInstance().get("Symptoms_s8"));
        s9.setText(Translator.getInstance().get("Symptoms_s9"));
        s10.setText(Translator.getInstance().get("Symptoms_s10"));
        s11.setText(Translator.getInstance().get("Symptoms_s11"));
        OKButton.setText(Translator.getInstance().get("Symptoms_OKButton"));
    }

    @Override
    public void updateLanguage() {
        setLanguages();
    }

    /**
     * load symptoms popup when help button is pressed/ make it disappear
     */
    public void loadSymptoms() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/Symptoms.fxml")); // used to load fxml in it's own controller
        try {
            loader.setControllerFactory(type -> {
                if (type == Symptoms.class) {
                    return this ;
                } else {
                    try {
                        return type.newInstance();
                    } catch (RuntimeException e) {
                        throw e ;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            Parent root = loader.load();
            LoadFXML.getInstance().openWindow("symptomsBar", root, covidSurvey.getSymptomsPop()); // open/close navigation bar
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void exitView() {
        LoadFXML.setCurrentWindow("");
        covidSurvey.getSymptomsPop().getChildren().clear(); // clear the symptomsPop vbox
    }
}
