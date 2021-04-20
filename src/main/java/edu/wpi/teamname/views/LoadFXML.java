package edu.wpi.teamname.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LoadFXML {

    private static String currentWindow;

    /**
     * setter for currentWindow
     * @param windowName // pass in the string that modifies currentWindow
     */
    public static void setCurrentWindow(String windowName) {
        currentWindow = windowName;
    }

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
