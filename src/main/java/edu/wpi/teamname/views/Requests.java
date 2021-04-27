package edu.wpi.teamname.views;

import edu.wpi.teamname.ServiceRequests.*;
import edu.wpi.teamname.views.manager.SceneManager;
import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.LanguageListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private Text desc;
    @FXML
    private JFXButton GiftDelivery;
    @FXML
    private JFXButton FoodDelivery;
    @FXML
    private JFXButton MedicineDelivery;
    @FXML
    private JFXButton LaundryServices;
    @FXML
    private JFXButton ComputerService;
    @FXML
    private JFXButton FacilitiesMaintenance;
    @FXML
    private JFXButton SanitationServices;
    @FXML
    private JFXButton PatientTransportation;
    @FXML
    private VBox requestPop;

    String openWindow = ""; //variable for currently open window


    private void setLanguages(){
        header.setText(Translator.getInstance().get("Requests_header"));
        desc.setText(Translator.getInstance().get("Requests_desc"));
        GiftDelivery.setText(Translator.getInstance().get("Requests_GiftDelivery"));
        FoodDelivery.setText(Translator.getInstance().get("Requests_FoodDelivery"));
        MedicineDelivery.setText(Translator.getInstance().get("Requests_MedicineDelivery"));
        LaundryServices.setText(Translator.getInstance().get("Requests_LaundryServices"));
        ComputerService.setText(Translator.getInstance().get("Requests_ComputerService"));
        FacilitiesMaintenance.setText(Translator.getInstance().get("Requests_FacilitiesMaintenance"));
        SanitationServices.setText(Translator.getInstance().get("Requests_SanitationServices"));
        PatientTransportation.setText(Translator.getInstance().get("Requests_PatientTransportation"));
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
        //setLanguages();
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
        LaundryService lr = new LaundryService(this);
        lr.loadRequest();
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openComputerService() {
        requestPop.setPrefWidth(657.0);
        ComputerService cs = new ComputerService(this);
        cs.loadRequest();
        //LoadFXML.getInstance().loadWindow("ComputerService", "computerService", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openFacilitiesMaintenance() {
        requestPop.setPrefWidth(657.0);
        FacilitiesRequest fmq = new FacilitiesRequest(this);
        fmq.loadRequest();
        //LoadFXML.getInstance().loadWindow("FacilitiesRequest", "facilitiesMaintenance", requestPop);
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openSanitationServices() {
        requestPop.setPrefWidth(657.0);
        SanitationService ss = new SanitationService(this);
        ss.loadRequest();
        //LoadFXML.getInstance().loadWindow("SanitationService", "sanitationServices", requestPop);
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
