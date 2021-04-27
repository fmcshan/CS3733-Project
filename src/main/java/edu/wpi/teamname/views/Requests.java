package edu.wpi.teamname.views;

import edu.wpi.teamname.ServiceRequests.*;
import edu.wpi.teamname.views.manager.SceneManager;
import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.LanguageListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Controller for Requests.fxml
 */
public class Requests implements LanguageListener {
    @FXML
    private Label header;

    @FXML
    private Text description;

    @FXML
    private Label typeLabel;

    @FXML
    private ComboBox<String> requestsBox;
    @FXML
    private JFXButton openButton;

    @FXML
    private VBox requestPop;

    String openWindow = ""; //variable for currently open window


    private void setLanguages(){
        header.setText(Translator.getInstance().get("Requests_header"));
        openButton.setText(Translator.getInstance().get("Requests_openButton"));
        requestsBox.setPromptText(Translator.getInstance().get("Requests_requestsBox"));
        typeLabel.setText(Translator.getInstance().get("Requests_typeLabel"));
        description.setText(Translator.getInstance().get("Requests_description"));
    }

    @Override
    public void updateLanguage() {
        setLanguages();
    }

    /**
     * Method that runs when this page is initialized
     */
    public void initialize(){
        Translator.getInstance().addLanguageListener(this);
        setLanguages();
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
