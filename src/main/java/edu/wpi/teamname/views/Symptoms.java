package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class Symptoms {
    @FXML private COVIDSurvey covidSurvey;

    public Symptoms(COVIDSurvey covidSurvey) {
        this.covidSurvey = covidSurvey;
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
