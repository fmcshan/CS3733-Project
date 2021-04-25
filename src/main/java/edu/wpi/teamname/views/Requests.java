package edu.wpi.teamname.views;

import edu.wpi.teamname.ServiceRequests.GiftDelivery;
import edu.wpi.teamname.ServiceRequests.LaundryRequest;
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
       // requestsBox.getItems().add("Gift Delivery"); //ADD BACK IN ITERATION 3
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openGiftRequest() {
//        if (requestsBox.getValue().equals("Gift Delivery")){ //ADD BACK IN ITERATION 3
//            System.out.println("click worked");
//            requestPop.setPrefWidth(657.0);
//            GiftDelivery gdr = new GiftDelivery(this);
//            gdr.loadRequest();
//        }
        requestPop.setPrefWidth(657.0);
        GiftDelivery gdr = new GiftDelivery(this);
        gdr.loadRequest();
        //LoadFXML.getInstance().loadWindow("GiftDeliveryRequest", "giftDelivery", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openFoodDelivery() {
        requestPop.setPrefWidth(657.0);
        LoadFXML.getInstance().loadWindow("FoodDeliveryRequest", "foodDelivery", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openMedicineDelivery() {
        requestPop.setPrefWidth(657.0);
        LoadFXML.getInstance().loadWindow("MedicineDeliveryRequest", "medicineDelivery", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openLaundryServices() {
        requestPop.setPrefWidth(657.0);
        LaundryRequest lr = new LaundryRequest(this);
        lr.loadRequest();
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openComputerService() {
        requestPop.setPrefWidth(657.0);
//        ComputerServices cs = new ComputerServices(this);
//        cs.loadRequest();
        //LoadFXML.getInstance().loadWindow("ComputerService", "computerService", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openFacilitiesMaintenance() {
        requestPop.setPrefWidth(657.0);
        LoadFXML.getInstance().loadWindow("FacilitiesMaintenanceRequest", "facilitiesMaintenance", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openSanitationServices() {
        requestPop.setPrefWidth(657.0);
        LoadFXML.getInstance().loadWindow("SanitationServices", "sanitationServices", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openPatientTransportation() {
        requestPop.setPrefWidth(657.0);
        LoadFXML.getInstance().loadWindow("PatientTransportation", "patientTransportation", requestPop);
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
