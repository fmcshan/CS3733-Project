package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.LanguageListener;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Map;

/**
 * Opens the map editor
 */
public class MapEditorButton implements LanguageListener {

    @FXML
    private JFXButton MapEditor;

    public void initialize(){
        Translator.getInstance().addLanguageListener(this);
        setLanguages();
    }

    private void setLanguages(){
        MapEditor.setText(Translator.getInstance().get("NavMenu_MapEditor"));
    }

    @Override
    public void updateLanguage() {
        setLanguages();
    }


    public void openMapEditor(ActionEvent actionEvent) {
        SceneManager.getInstance().getDefaultPage().toggleMapEditor();
    }


}
