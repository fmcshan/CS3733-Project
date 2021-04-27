package edu.wpi.teamname.ServiceRequests;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    @FXML
    private Text title;
    @FXML
    private Text desc;
    @FXML
    private Label askName;
    @FXML
    private Label askLocation;
    @FXML
    private Label askDestination;
    @FXML
    private Label askAssistance;
    @FXML
    private JFXButton submitButton;

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
        ArrayList<String> listOfNodeNames = new ArrayList<>();
        HashMap<String, Node> nodesMap = new HashMap<>();
        for (Node node : LocalStorage.getInstance().getNodes()) {
            nodesMap.put(node.getNodeID(), node); // put the nodes in the hashmap
            listOfNodeNames.add(node.getLongName());
            Collections.sort(listOfNodeNames);
        }  listOfNodeNames.forEach(n -> {
            currentLocation.getItems().add(n); // make the nodes appear in the combobox
            destination.getItems().add(n);
        });
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
            failedName.setText("Invalid Name Entry");
        else
            failedName.setText("");

        if (reasonInput.getText() == null)
            failedReason.setText("Invalid Reason Entry");
        else
            failedReason.setText("");

        if (!currentLocationValid())
            failedCurrentLocation.setText("Invalid Current Location Selection");

        if (!destinationLocationValid())
            failedDestination.setText("Invalid Destination Location Selection");

        if (requests == null) {
            requests = new ArrayList<ServiceRequest>();
        }

        if (nameInputValid() && currentLocationValid() && destinationLocationValid() && reasonInput.getText() != null) {
            //Adds all the selected gifts to an arraylist
            ArrayList<String> reason = new ArrayList<>();
            reason.add(reasonInput.getText());

            LoadFXML.setCurrentWindow("");

            //Add this request to our list of requests
            //requests.add(new GiftRequest(phoneInput.getText(), requestLocation.getValue(), nameInput.getText()));
            MasterServiceRequestStorage request = new MasterServiceRequestStorage("Patient Transportation", currentLocation.getValue().toString(), reason, destination.getValue().toString(), nameInput.getText(), "", "", false);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/ServiceRequestComponents/PatientTransportation.fxml"));
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

    public void backToRequests(ActionEvent actionEvent) {
        LoadFXML.getInstance().loadWindow("Requests2", "reqBar", SceneManager.getInstance().getDefaultPage().getPopPop());
    }
}
