package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

/**
 * Opens the map editor
 */
public class MapEditorButton {
    public void openMapEditor(ActionEvent actionEvent) {
        SceneManager.getInstance().getDefaultPage().toggleMapEditor();
    }
}
