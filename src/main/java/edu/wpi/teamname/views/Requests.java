package edu.wpi.teamname.views;

import edu.wpi.teamname.ServiceRequests.*;
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
        FoodDelivery fd = new FoodDelivery(this);
        fd.loadRequest();
        //LoadFXML.getInstance().loadWindow("FoodDeliveryRequest", "foodDelivery", requestPop);
    }

        public void openRequest() {
        if (requestsBox.getValue().equals("Gift Delivery")){
            requestPop.setPrefWidth(657.0);
            GiftDelivery gdr = new GiftDelivery(this);
            gdr.loadRequest();
        }
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openMedicineDelivery() {
        requestPop.setPrefWidth(657.0);
        MedicineDelivery md = new MedicineDelivery(this);
        md.loadRequest();
        //LoadFXML.getInstance().loadWindow("MedicineDeliveryRequest", "medicineDelivery", requestPop);
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
        ComputerServices cs = new ComputerServices(this);
        cs.loadRequest();
        //LoadFXML.getInstance().loadWindow("ComputerService", "computerService", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openFacilitiesMaintenance() {
        requestPop.setPrefWidth(657.0);
        FacilitiesMaintenanceRequest fmq = new FacilitiesMaintenanceRequest(this);
        fmq.loadRequest();
        //LoadFXML.getInstance().loadWindow("FacilitiesMaintenanceRequest", "facilitiesMaintenance", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openSanitationServices() {
        requestPop.setPrefWidth(657.0);
        SanitationServices ss = new SanitationServices(this);
        ss.loadRequest();
        //LoadFXML.getInstance().loadWindow("SanitationServices", "sanitationServices", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openPatientTransportation() {
        requestPop.setPrefWidth(657.0);
        PatientTransportation pt = new PatientTransportation(this);
        pt.loadRequest();
        //LoadFXML.getInstance().loadWindow("PatientTransportation", "patientTransportation", requestPop);
    }
}
