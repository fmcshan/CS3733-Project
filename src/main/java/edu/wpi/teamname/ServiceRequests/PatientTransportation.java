package edu.wpi.teamname.ServiceRequests;

import com.jfoenix.controls.JFXCheckBox;
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
import java.util.HashMap;
import java.util.List;

/**
 * <h1>Gift Delivery Request Controller</h1>
 * Controller for the Gift Delivery Request Page
 * @author Lauren Sowerbutts, Frank McShan
 */
public class PatientTransportation {

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
     * Text field to input reason
     */
    @FXML
    private JFXTextField reasonInput;

    /**
     * Label indicating if a current location is not selected
     */
    @FXML
    private Label failedCurrentLocation;

    /**
     * Checkbox selecting yes for immediate medical assistance
     */
    @FXML
    private JFXCheckBox yesCheckbox;

    /**
     * Combo Box selecting current location
     */
    @FXML
    private JFXComboBox currentLocation;

    /**
     * Combo Box selecting destination
     */
    @FXML
    private JFXComboBox destination;

    /**
     * Label indicating if a destination location is not selected
     */
    @FXML
    private Label failedDestination;

    /**
     * Label indicating if a reason is not entered
     */
    @FXML
    private Label failedReason;

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
    public PatientTransportation(Requests request) {
        this.request = request;
    }

    public void initialize() {
        for (Node node : LocalStorage.getInstance().getNodes()) {
            currentLocation.getItems().add(node.getNodeID());
            destination.getItems().add(node.getNodeID());
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
     * Checks if the "Other" text box for gift delivery options has been filled correctly
     *
     * @return true if the box was filled correctly, and false otherwise
     */
    public boolean otherInputValid() {
        return !yesCheckbox.isSelected() || (yesCheckbox.isSelected() && !reasonInput.getText().isEmpty());
    }

    /**
     * Checks if a current location has been selected correctly
     *
     * @return true if a location has been selected, and false otherwise
     */
    public boolean currentLocationValid() {
        return currentLocation.getValue() != null;
    }

    /**
     * Checks if a destination location has been selected correctly
     *
     * @return true if a location has been selected, and false otherwise
     */
    public boolean destinationLocationValid() {
        return destination.getValue() != null;
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

        if (!yesCheckbox.isSelected() && reasonInput.getText() != null)
            failedReason.setText("Please ensure you have selected the \"Yes\" box and have correctly filled in the text field.");
        else
            failedReason.setText("");

        if (!currentLocationValid())
            failedCurrentLocation.setText("Please select a current location");

        if (!destinationLocationValid())
            failedDestination.setText("Please select a destination");

        if (requests == null) {
            requests = new ArrayList<ServiceRequest>();
        }

        if (nameInputValid() && currentLocationValid() && destinationLocationValid()) {
            //Adds all the selected gifts to an arraylist
            ArrayList<String> reason = new ArrayList<>();
            if (yesCheckbox.isSelected())
                reason.add(reasonInput.getText());

            LoadFXML.setCurrentWindow("");

            //Add this request to our list of requests
            //requests.add(new GiftRequest(phoneInput.getText(), requestLocation.getValue(), nameInput.getText()));
            MasterServiceRequestStorage request = new MasterServiceRequestStorage("Patient Transportation", currentLocation.getValue().toString() + " to " + destination.getValue().toString(), reason, nameInput.getText(), "", "", false);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/Service Request Components/PatientTransportation.fxml"));
        try {
            loader.setControllerFactory(type -> {
                if (type == PatientTransportation.class)
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
            LoadFXML.getInstance().openWindow("patientTransportationBar", root, SceneManager.getInstance().getDefaultPage().getPopPop()); //open/close request form
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
