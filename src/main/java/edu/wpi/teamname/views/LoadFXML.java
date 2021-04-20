package edu.wpi.teamname.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LoadFXML {

    private String currentWindow = "";

    public void loadWindow(String fileName, String windowName, VBox vbox) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindow(windowName, root, vbox);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openWindow(String windowName, Parent root, VBox vbox) {
        vbox.getChildren().clear();
        if (!windowName.equals(currentWindow)) {
            vbox.getChildren().add(root);
            currentWindow = windowName;
            return;
        }
        currentWindow = "";
    }
}
