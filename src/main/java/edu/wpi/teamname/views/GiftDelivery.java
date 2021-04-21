package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.GiftDeliveryStorage;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Entities.ServiceRequests.GiftRequest;
import edu.wpi.teamname.Entities.ServiceRequests.ServiceRequest;
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
 * <h1>Gift Delivery Request Controller</h1>
 * Controller for the Gift Delivery Request Page
 * @author Emmanuel Ola, Frank McShan
 */
public class GiftDelivery {

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
     * Label indicating if a gift hasn't been selected
     */
    @FXML
    private Label failedGiftSelection;

    /**
     * Combo Box selecting Teddy Bear for delivery
     */
    @FXML
    private JFXCheckBox teddyBearBox;

    /**
     * Combo Box selecting chocolates for delivery
     */
    @FXML
    private JFXCheckBox chocolateBox;

    /**
     * Combo Box selecting gift basket for delivery
     */
    @FXML
    private JFXCheckBox giftBasketBox;

    /**
     * Combo Box selecting some other gift for delivery
     */
    @FXML
    private JFXCheckBox otherCheckbox;

    /**
     * Text Field to specify what gift should be delivered
     */
    @FXML
    private JFXTextField otherInput;

    /**
     * Label indicating the phone number text field has been filled in incorrectly
     */
    @FXML
    private Label failedPhoneNumber;

    /**
     * Label indicating the person to fulfill the request has been filled in incorrectly
     */
    @FXML
    private Label failedPerson;

    /**
     * Text Field to enter phone number
     */
    @FXML
    private JFXTextField phoneInput;

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
     * Combo Box selecting what person the delivery is assigned to
     */
    @FXML
    private JFXComboBox<String> assignTo;

    /**
     * Success pop up page
     */
    @FXML
    private VBox giftPop;

    /**
     * Instance of Requests class used to create a popup window
     */
    @FXML
    private edu.wpi.teamname.views.Requests request;

    /**
     * List of Service Requests
     */
    private List<ServiceRequest> requests;

    /**
     * Constructor used to create a pop up window for GiftDelivery Request
     * @param request an instance of Requests.java
     */
    public GiftDelivery(Requests request) {
        this.request = request;
    }

    public void initialize() {
        for (Node node : LocalStorage.getInstance().getNodes()) {
            requestLocation.getItems().add(node.getNodeID());
        }
    }

    /**
     * Retrieves sucess pop up page
     *
     * @return Success pop up page
     */
    public VBox getGiftPop() {
        return giftPop;
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
     * Checks if a checkbox has been selected
     *
     * @return true if any checkbox has been selected, and false otherwise
     */
    public boolean checkBoxSelected() {
        return teddyBearBox.isSelected() || chocolateBox.isSelected() || giftBasketBox.isSelected() || otherCheckbox.isSelected();
    }

    /**
     * Checks if the "Other" text box for gift delivery options has been filled correctly
     *
     * @return true if the box was filled correctly, and false otherwise
     */
    public boolean otherInputValid() {
        return !otherCheckbox.isSelected() || (otherCheckbox.isSelected() && !otherInput.getText().isEmpty());
    }

    /**
     * Checks if the phone number text field has been filled in correctly
     *
     * @return true if the number matches the "XXX-XXX-XXXX" format
     */
    public boolean phoneNumberValid() {
        return phoneInput.getText().matches("\\d{3}-\\d{3}-\\d{4}");
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
        if (phoneInput.getText().length() == 10 && !phoneInput.getText().contains("-")) {
            phoneInput.setText(phoneInput.getText().substring(0, 3) + "-" + phoneInput.getText().substring(3, 6) + "-" + phoneInput.getText().substring(6));
        }

        //Checks if all the inputs are valid
        if (!nameInputValid())
            failedName.setText("Invalid Name Entry.");
        else
            failedName.setText("");




        if (!checkBoxSelected())
            failedGiftSelection.setText("Please select a gift to be delivered.");
        else if (!otherInputValid())
            failedGiftSelection.setText("Please ensure you have selected the \"Other\" box and have correctly filled in the text field.");
        else
            failedGiftSelection.setText("");

        if (!phoneNumberValid())
            failedPhoneNumber.setText("Invalid Phone Number");
        else
            failedPhoneNumber.setText("");

        if (!locationValid())
            failedLocationEntry.setText("Please select a location");

        if (requests == null) {
            requests = new ArrayList<ServiceRequest>();
        }

        if (nameInputValid() && checkBoxSelected() && otherInputValid() && phoneNumberValid()) {
            //Adds all the selected gifts to an arraylist
            ArrayList<String> giftSelected = new ArrayList<>();
            if (teddyBearBox.isSelected())
                giftSelected.add("Teddy Bear");
            if (chocolateBox.isSelected())
                giftSelected.add("Chocolate");
            if (giftBasketBox.isSelected())
                giftSelected.add("Gift Basket");
            if (otherCheckbox.isSelected())
                giftSelected.add(otherInput.getText());

            LoadFXML.setCurrentWindow("");

            //Add this request to our list of requests
            requests.add(new GiftRequest(phoneInput.getText(), requestLocation.getValue(), nameInput.getText()));
            GiftDeliveryStorage request = new GiftDeliveryStorage("Gift Delivery", requestLocation.getValue(), giftSelected, nameInput.getText(), phoneInput.getText(), "", false);
            Submit.getInstance().submitGiftDelivery(request);

            // load Success page in successPop VBox
            giftPop.setPrefWidth(657.0);
            Success success = new Success(this);
            success.loadSuccess("You have successfully submitted the form. Your request will be fulfilled shortly.", giftPop);
        }
    }

    /**
     * Load Request form when the button is pressed/make it disappear
     */
    public void loadRequest() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/GiftDeliveryRequest.fxml"));
        try {
            loader.setControllerFactory(type -> {
                if (type == GiftDelivery.class)
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
            request.openWindowRequestPop("requestForm", root); //open/close request form
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
