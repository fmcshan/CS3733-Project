package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.ButtonManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Opens the map editor
 */
public class MapEditorButton {

    @FXML
    private JFXButton mapEditorButton;

    public JFXButton getMapEditorButton() {
        return mapEditorButton;
    }

    public void openMapEditor() {
        SceneManager.getInstance().getDefaultPage().toggleMapEditor();
        ButtonManager.selectButton(mapEditorButton, "nav-btn-selected", ButtonManager.buttons);
        if (LoadFXML.getCurrentWindow().equals("")) {
            ButtonManager.remove_class();
        }
    }
}
