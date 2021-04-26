package edu.wpi.teamname.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;


public class RegistrationAdminViewNew {
    private double x, y;

    public void initialize() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/CheckInTableCells.fxml"));
            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
//            root.setOnMouseDragged(event -> {
//             //   primaryStage.setX(event.getScreenX() - x);
//
//            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
