package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.ServiceRequests.*;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Controller for Requests.fxml
 */
public class Requests {

    @FXML
    private VBox requestPop;
    @FXML
    private JFXButton giftDelivery, foodDelivery, laundryServices, medicineDelivery, computerService, facilitiesMaintenance, sanitationServices, patientTransportation;
    @FXML
    private VBox patientRequestBox, employeeRequestBox;

    public void initialize() {
        if (AuthenticationManager.getInstance().isAuthenticated()) {
            medicineDelivery.setVisible(true);
            computerService.setVisible(true);
            facilitiesMaintenance.setVisible(true);
            sanitationServices.setVisible(true);
            patientTransportation.setVisible(true);
        } else {
//            medicineDelivery.setVisible(false);
//            computerService.setVisible(false);
//            facilitiesMaintenance.setVisible(false);
//            sanitationServices.setVisible(false);
//            patientTransportation.setVisible(false);
            employeeRequestBox.getChildren().clear();
            patientRequestBox.setSpacing(100);
            VBox.setMargin(patientRequestBox, new Insets(150, 0, 0, 0));
        }

    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openGiftRequest() {
        requestPop.setMaxWidth(657.0);
        GiftDelivery gdr = new GiftDelivery(this);
        gdr.loadRequest();
        //LoadFXML.getInstance().loadWindow("GiftDeliveryRequest", "giftDelivery", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openFoodDelivery() {
        requestPop.setMaxWidth(657.0);
        FoodDelivery fd = new FoodDelivery(this);
        fd.loadRequest();
        //LoadFXML.getInstance().loadWindow("FoodDeliveryRequest", "foodDelivery", requestPop);
    }

//        public void openRequest() {
//        if (requestsBox.getValue().equals("Gift Delivery")){
//            requestPop.setPrefWidth(657.0);
//            GiftDelivery gdr = new GiftDelivery(this);
//            gdr.loadRequest();
//        }
//    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openMedicineDelivery() {
        requestPop.setMaxWidth(657.0);
        MedicineDelivery md = new MedicineDelivery(this);
        md.loadRequest();
        //LoadFXML.getInstance().loadWindow("MedicineDeliveryRequest", "medicineDelivery", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openLaundryServices() {
        requestPop.setMaxWidth(657.0);
        LaundryService lr = new LaundryService(this);
        lr.loadRequest();
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openComputerService() {
        requestPop.setMaxWidth(657.0);
        ComputerService cs = new ComputerService(this);
        cs.loadRequest();
        //LoadFXML.getInstance().loadWindow("ComputerService", "computerService", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openFacilitiesMaintenance() {
        requestPop.setMaxWidth(657.0);
        FacilitiesRequest fmq = new FacilitiesRequest(this);
        fmq.loadRequest();
        //LoadFXML.getInstance().loadWindow("FacilitiesRequest", "facilitiesMaintenance", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openSanitationServices() {
        requestPop.setMaxWidth(657.0);
        SanitationService ss = new SanitationService(this);
        ss.loadRequest();
        //LoadFXML.getInstance().loadWindow("SanitationService", "sanitationServices", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openPatientTransportation() {
        requestPop.setMaxWidth(657.0);
        PatientTransportation pt = new PatientTransportation(this);
        pt.loadRequest();
        //LoadFXML.getInstance().loadWindow("PatientTransportation", "patientTransportation", requestPop);
    }
}
