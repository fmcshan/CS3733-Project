package edu.wpi.teamname.ServiceRequests;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.MasterServiceRequestStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Entities.ServiceRequests.ServiceRequest;
import edu.wpi.teamname.views.LoadFXML;
import edu.wpi.teamname.views.Requests;
import edu.wpi.teamname.views.Success;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Medicine Delivery Controller</h1>
 * Controller for the Medicine Delivery Request Page
 * @author Lauren Sowerbutts, Frank McShan
 */
public class MedicineDelivery {

    /**
     * Label indicating if a name has been filled in incorrectly
     */
    @FXML
    private Label failedName;

    /**
     * Text field to input name
     */
    @FXML
    private JFXTextField nameInput;

    /**
     * Label indicating if a medication name hasn't been inputted
     */
    @FXML
    private Label failedMedicationName;

    /**
     * Text field to input medication name
     */
    @FXML
    private JFXTextField medicationNameInput;

    /**
     * Label indicating if a dosage amount hasn't been inputted
     */
    @FXML
    private Label failedDosageAmount;

    /**
     * Text field to input dosage amount
     */
    @FXML
    private JFXTextField dosageAmountInput;

    /**
     * Label indicating a location hasn't been selected
     */
    @FXML
    private Label failedLocationEntry;

    /**
     * Combo Box selecting what location the delivery should be sent to
     */
    @FXML
    private JFXComboBox<String> requestLocation;

    /**
     * Success pop up page
     */
    @FXML
    private VBox successPop;

    /**
     * Instance of Requests class used to create a popup window
     */
    @FXML
    private Requests request;

    /**
     * List of Service Requests
     */
    private List<ServiceRequest> requests;

    /**
     * Constructor used to create a pop up window for GiftDelivery Request
     * @param request an instance of Requests.java
     */
    public MedicineDelivery(Requests request) {
        this.request = request;
    }

    public void initialize() {
        for (Node node : LocalStorage.getInstance().getNodes()) {
            requestLocation.getItems().add(node.getNodeID());
        }
    }

    /**
     * Retrieves success pop up page
     *
     * @return Success pop up page
     */
    public VBox getSuccessPop() {
        return successPop;
    }

    /**
     * Checks if first name and last name have been filled in correctly
     *
     * @return true if there's a space between the first and last name, and false otherwise
     */
    public boolean nameInputValid() {
        return nameInput.getText().contains(" ");
    }

    /**
     * Checks if the "Medication Name" text box has been filled
     *
     * @return true if the box was filled correctly, and false otherwise
     */
    public boolean medicationNameInputValid() {
        return !medicationNameInput.getText().isEmpty();
    }

    /**
     * Checks if the "Dosage Amount" text box has been filled
     *
     * @return true if the box was filled correctly, and false otherwise
     */
    public boolean dosageAmountInputValid() {
        return !dosageAmountInput.getText().isEmpty();
    }

    /**
     * Checks if a location has been selected correctly
     *
     * @return true if a location has been selected, and false otherwise
     */
    public boolean locationValid() {
        return requestLocation.getValue() != null;
    }

    public void addRequest(ServiceRequest request) {
        requests.add(request);
    }

    /**
     * Performs operations when the submit button is clicked
     * <ul>
     *     <li>Checks if the inputs are valid</li>
     *     <li>Creates a new gift request object</li>
     *     <li>Adds the gift request to the service request database</li>
     * </ul>
     *
     * @param event event triggering submission
     */
    public void submitRequest(ActionEvent event) {
        //Checks if all the inputs are valid
        if (!nameInputValid())
            failedName.setText("Invalid Name Entry.");
        else
            failedName.setText("");

        if (!medicationNameInputValid())
            failedMedicationName.setText("Invalid Medication Name.");
        else
            failedMedicationName.setText("");

        if (!dosageAmountInputValid())
            failedDosageAmount.setText("Invalid Name Entry.");
        else
            failedDosageAmount.setText("");

        if (!locationValid())
            failedLocationEntry.setText("Please select a location");

        if (requests == null) {
            requests = new ArrayList<ServiceRequest>();
        }

        if (nameInputValid() && medicationNameInputValid() && dosageAmountInputValid() && locationValid()) {

            ArrayList<String> items = new ArrayList<>();
            items.add(medicationNameInput.getText());

            LoadFXML.setCurrentWindow("");

            //Add this request to our list of requests
//            requests.add(new ServiceRequest(phoneInput.getText(), requestLocation.getValue(), nameInput.getText()) {
//            });
            MasterServiceRequestStorage request = new MasterServiceRequestStorage("Medicine Delivery", requestLocation.getValue(), items, dosageAmountInput.getText(), nameInput.getText(), "", "", false);
            Submit.getInstance().submitGiftDelivery(request);

            // load Success page in successPop VBox
            successPop.setPrefWidth(657.0);
            Success success = new Success(this);
            success.loadSuccess("You have successfully submitted the form. Your request will be fulfilled shortly.", successPop);
        }
    }

    /**
     * Load Request form when the button is pressed/make it disappear
     */
    public void loadRequest() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/ServiceRequestComponents/MedicineDeliveryRequest.fxml"));
        try {
            loader.setControllerFactory(type -> {
                if (type == MedicineDelivery.class)
                    return this;
                else
                    try {
                        return type.newInstance();
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            });
            Parent root = loader.load();
            LoadFXML.getInstance().openWindow("medicineDeliveryBar", root, SceneManager.getInstance().getDefaultPage().getPopPop()); //open/close request form
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
