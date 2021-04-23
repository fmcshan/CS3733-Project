package edu.wpi.teamname.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Symptoms {
    @FXML
    private COVIDSurvey covidSurvey;
    private VBox pop;

    public Symptoms(COVIDSurvey covidSurvey) {
        this.covidSurvey = covidSurvey;
    }

    /**
     * load symptoms popup when help button is pressed/ make it disappear
     */
    public void loadSymptoms(VBox pop) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/Symptoms.fxml"));
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
            LoadFXML.getInstance().openWindow("symptomsBar", root, pop); // open/close symptoms bar
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void exitView(ActionEvent actionEvent) {
        pop.getChildren().clear(); // clear the symptomsPop vbox
    }
}
