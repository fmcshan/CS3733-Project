package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LoadFXML {

    private static String currentWindow = "";
    private static String currentHelp = "";

    private LoadFXML() {
    }

    public static synchronized LoadFXML getInstance() { return instance; }

    private static final LoadFXML instance = new LoadFXML();

    /**
     * setter for currentWindow
     * @param windowName // pass in the string that modifies currentWindow
     */
    public static void setCurrentWindow(String windowName) {
        currentWindow = windowName;
    }

    public static String getCurrentWindow() {
        return currentWindow;
    }

    public static void setCurrentHelp(String currentHelp) {
        LoadFXML.currentHelp = currentHelp;
    }

    /**
     * loads and opens an fxml inside another fxml
     * @param fileName the fxml file you want to open
     * @param windowName the variable the program will check to see if the window is open or closed
     * @param vbox the vbox the fxml will be open in
     */
    public void loadWindow(String fileName, String windowName, VBox vbox) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindow(windowName, root, vbox);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * checks if the window is currently open or closed, and then opens the window
     * @param windowName the variable the function will check to see if the window is open or closed
     * @param root the loaded fxml
     * @param vbox the vbox the fxml will be open in
     */
    public static void openWindow(String windowName, Parent root, VBox vbox) {
        vbox.getChildren().clear();
        SceneManager.getInstance().getDefaultPage().getPopPop2().getChildren().clear();
        if (!windowName.equals(currentWindow)) {
            vbox.getChildren().add(root);
            SceneManager.getInstance().getDefaultPage().getPopPop2().getChildren().clear();
            currentWindow = windowName;
            return;
        }
        currentWindow = "";
    }

    public void loadHelp(String fileName, String windowName, VBox vbox) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/Haaaalp/" + fileName + ".fxml"));
            openHelp(windowName, root, vbox);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void openHelp(String windowName, Parent root, VBox vbox) {
        vbox.getChildren().clear();
        if (!windowName.equals(currentHelp)) {
            vbox.getChildren().add(root);
            currentHelp = windowName;
            return;
        }
        currentHelp = "";
    }

}
