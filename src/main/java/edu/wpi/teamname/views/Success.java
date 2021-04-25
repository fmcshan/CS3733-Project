package edu.wpi.teamname.views;

import edu.wpi.teamname.ServiceRequests.ComputerServices;
import edu.wpi.teamname.ServiceRequests.FacilitiesMaintenanceRequest;
import edu.wpi.teamname.ServiceRequests.GiftDelivery;
import edu.wpi.teamname.ServiceRequests.LaundryRequest;
import edu.wpi.teamname.ServiceRequests.MedicineDelivery;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.junit.runner.Computer;

import java.io.IOException;

/**
 * Controller for Success.fxml
 * @author Lauren Sowerbutts
 */
public class Success {

    @FXML
    public Label successText;
    @FXML
    private UserRegistration userRegistration;

    private GiftDelivery giftDelivery;

    private LaundryRequest laundryRequest;

    @FXML
    private ComputerServices computerServices;
    private FacilitiesMaintenanceRequest facilitiesMaintenanceRequest;

    private MedicineDelivery medicineDelivery;

    private VBox pop;

    public Success(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
    }

    public Success(GiftDelivery giftDelivery) {
        this.giftDelivery = giftDelivery;
    }

    public Success(LaundryRequest laundryRequest) {
        this.laundryRequest = laundryRequest;
    }

    public Success(ComputerServices computerServices) {
        this.computerServices = computerServices;
    }

    public Success(FacilitiesMaintenanceRequest facilitiesMaintenanceRequest) {
        this.facilitiesMaintenanceRequest = facilitiesMaintenanceRequest;
    }

    public Success(MedicineDelivery medicineDelivery) {
        this.medicineDelivery = medicineDelivery;
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
