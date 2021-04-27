package edu.wpi.teamname.views;

import edu.wpi.teamname.ServiceRequests.*;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller for Success.fxml
 * @author Lauren Sowerbutts
 */
public class Success {

    @FXML public Label successText;
    @FXML private Label title;

    private UserRegistration userRegistration;

    private GiftDelivery giftDelivery;

    private LaundryService laundryService;

    private ComputerService computerService;

    private FacilitiesRequest facilitiesRequest;

    private MedicineDelivery medicineDelivery;

    private FoodDelivery foodDelivery;

    private SanitationService sanitationService;

    private PatientTransportation patientTransportation;


    private VBox pop;

    public Success(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
    }

    public Success(GiftDelivery giftDelivery) {
        this.giftDelivery = giftDelivery;
    }

    public Success(LaundryService laundryService) {
        this.laundryService = laundryService;
    }

    public Success(ComputerService computerService) {
        this.computerService = computerService;
    }

    public Success(PatientTransportation patientTransportation) {
        this.patientTransportation = patientTransportation;
    }

    public Success(FacilitiesRequest facilitiesRequest) {
        this.facilitiesRequest = facilitiesRequest;
    }

    public Success(MedicineDelivery medicineDelivery) {
        this.medicineDelivery = medicineDelivery;
    }

    public Success(SanitationService sanitationService) {
        this.sanitationService = sanitationService;
    }

    public Success(FoodDelivery foodDelivery) {
        this.foodDelivery = foodDelivery;
    }

    /**
     * load success bar in the Default Page when button is pressed/ make it disappear
     */
    public void loadSuccess(String message, VBox pop) {
        this.pop = pop;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/Success.fxml"));
        try {
            loader.setControllerFactory(type -> {
                if (type == Success.class) {
                    return this ;
                } else {
                    try {
                        return type.newInstance();
                    } catch (RuntimeException e) {
                        throw e ;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            Parent root = loader.load();
            successText.setText(message);
            LoadFXML.getInstance().openWindow("successBar", root, pop); // open/close success bar
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When close button is pressed close the success page and the form
     */
    public void closeSuccess() {
        pop.getChildren().clear(); // clear the successPop vbox
        SceneManager.getInstance().getDefaultPage().closeWindows();
    }
}
