package edu.wpi.teamname.views;

import edu.wpi.teamname.bridge.Bridge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Opens the map editor
 */
public class MapEditorButton {

    public void openMapEditor(ActionEvent actionEvent) {
        Bridge.getInstance().loadMapEditor();
    }
}
