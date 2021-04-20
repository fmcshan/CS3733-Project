package edu.wpi.teamname.views;

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
     * @param actionEvent
     */
    public void openRequest(ActionEvent actionEvent) {
        if (requestsBox.getValue().equals("Gift Delivery")){
            System.out.println("click worked");
            requestPop.setPrefWidth(657.0);
            GiftDelivery gdr = new GiftDelivery(this);
            gdr.loadRequest();
        }
    }

    /**
     * Getter for requestPop VBox
     * @return requestPop VBox
     */
    public VBox getRequestPop() {
        return requestPop;
    }

    /**
     * Opens an FXML in the requestPop VBox
     */
    public void openWindowRequestPop(String windowName, Parent root) {
        requestPop.getChildren().clear(); //Clear requestPop VBox
        if (!windowName.equals(openWindow)) { //If the window we're trying to open is not the current window
            requestPop.getChildren().add(root); //Put the fxml in the requestPop VBox
            openWindow = windowName; //Set the current window to the specified window name
            return;
        }
        openWindow = ""; //set the current window to nothing- the window is closed
    }
}
