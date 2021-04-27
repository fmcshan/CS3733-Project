package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Opens the map editor
 */
public class MapEditorButton {

    @FXML
    private JFXButton MapEditor;

    public void openMapEditor() {
        SceneManager.getInstance().getDefaultPage().toggleMapEditor();
    }
}
