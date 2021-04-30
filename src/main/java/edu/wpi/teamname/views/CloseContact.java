package edu.wpi.teamname.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class CloseContact {
    @FXML private COVIDSurvey covidSurvey;
    @FXML private Label title;
    @FXML private Label desc;
    @FXML private Label b1;
    @FXML private Label b2;
    @FXML private Label b3;
    @FXML private Label b4;

    public CloseContact(COVIDSurvey covidSurvey) {
        this.covidSurvey = covidSurvey;
    }

    /**
     * load symptoms popup when help button is pressed/ make it disappear
     */
    public void loadCloseContact() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/CloseContact.fxml")); // used to load fxml in it's own controller
        try {
            loader.setControllerFactory(type -> {
                if (type == CloseContact.class) {
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
            LoadFXML.getInstance().openWindow("closeContactBar", root, covidSurvey.getSymptomsPop()); // open/close navigation bar
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void exitView() {
        LoadFXML.setCurrentWindow("");
        covidSurvey.getSymptomsPop().getChildren().clear(); // clear the symptomsPop vbox
    }
}
