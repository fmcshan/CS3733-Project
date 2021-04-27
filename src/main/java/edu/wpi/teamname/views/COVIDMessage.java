package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.LanguageListener;
import edu.wpi.teamname.views.manager.NavManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class COVIDMessage implements LanguageListener {

    @FXML private Label title;
    @FXML private Label successText;

    public void initialize(){
        Translator.getInstance().addLanguageListener(this);
        setLanguages();
    }

    private void setLanguages(){
        title.setText(Translator.getInstance().get("CovidMessage_title"));
        successText.setText(Translator.getInstance().get("CovidMessage_successText"));
    }

    @Override
    public void updateLanguage() {
        setLanguages();
    }
    public static boolean covid = false;

    public void openPathToEmergency() {
        covid = true;
        SceneManager.getInstance().getDefaultPage().toggleNav();
    }
}
