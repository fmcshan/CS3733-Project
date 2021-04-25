package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

/**
 * Controller for Requests.fxml
 */
public class Requests {
    @FXML
    private ComboBox<String> requestsBox;
    @FXML
    private VBox requestPop; //VBox to display the Request page

    String openWindow = ""; //variable for currently open window

    /**
     * Method that runs when this page is initialized
     */
    public void initialize(){
        requestsBox.getItems().add("Gift Delivery");
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openRequest() {
        if (requestsBox.getValue().equals("Gift Delivery")){
            requestPop.setPrefWidth(657.0);
            GiftDelivery gdr = new GiftDelivery(this);
            gdr.loadRequest();
        }
    }

    /**
     * Opens an FXML in the requestPop VBox
     */
    public void openWindowRequestPop(String windowName, Parent root) {
        requestPop.getChildren().clear(); //Clear requestPop VBox
        SceneManager.getInstance().getDefaultPage().getPopPop2().getChildren().clear();
        if (!windowName.equals(openWindow)) { //If the window we're trying to open is not the current window
            requestPop.getChildren().add(root); //Put the fxml in the requestPop VBox
            openWindow = windowName; //Set the current window to the specified window name
            return;
        }
        openWindow = ""; //set the current window to nothing- the window is closed
    }
}
