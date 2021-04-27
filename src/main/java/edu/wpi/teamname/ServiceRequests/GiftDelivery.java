package edu.wpi.teamname.ServiceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.MasterServiceRequestStorage;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Entities.ServiceRequests.GiftRequest;
import edu.wpi.teamname.Entities.ServiceRequests.ServiceRequest;
import edu.wpi.teamname.views.LoadFXML;
import edu.wpi.teamname.views.Requests;
import edu.wpi.teamname.views.Success;
import edu.wpi.teamname.views.Translator;
import edu.wpi.teamname.views.manager.LanguageListener;
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
 * @author Emmanuel Ola, Frank McShan
 */
public class GiftDelivery implements LanguageListener {

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
     * Checkbox selecting Teddy Bear for delivery
     */
    @FXML
    private JFXCheckBox teddyBearBox;

    /**
     * Checkbox selecting flowers for delivery
     */
    @FXML
    private JFXCheckBox flowerCheckbox;

    /**
     * Combo Box selecting Teddy Bear for delivery
     */
    @FXML
    private JFXComboBox flowerType;

    /**
     * Checkbox selecting chocolates for delivery
     */
    @FXML
    private JFXCheckBox chocolateBox;

    /**
     * Checkbox selecting balloons for delivery
     */
    @FXML
    private JFXCheckBox balloonsBox;

    /**
     * Checkbox selecting gift basket for delivery
     */
    @FXML
    private JFXCheckBox giftBasketBox;

    /**
     * Checkbox selecting some other gift for delivery
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

    @FXML
    private Label title;

    @FXML
    private Text desc;
    @FXML
    private Label askName;
    @FXML
    private Label askGifts;
    @FXML
    private Label askNumber;
    @FXML
    private Label askLocation;
    @FXML
    private JFXButton submitButton;
    @FXML
    private VBox successPop;


    private void setLanguages(){
        title.setText(Translator.getInstance().get("GiftDelivers_title"));
        desc.setText(Translator.getInstance().get("GiftDelivers_desc"));
        askName.setText(Translator.getInstance().get("GiftDelivers_askName"));
        nameInput.setPromptText(Translator.getInstance().get("GiftDelivers_nameInput"));
        askGifts.setText(Translator.getInstance().get("GiftDelivers_askGifts"));
        teddyBearBox.setText(Translator.getInstance().get("GiftDelivers_teddyBearBox"));
        flowerCheckbox.setText(Translator.getInstance().get("GiftDelivers_flowerCheckbox"));
        flowerType.setPromptText(Translator.getInstance().get("GiftDelivers_flowerType"));
        chocolateBox.setText(Translator.getInstance().get("GiftDelivers_chocolateBox"));
        balloonsBox.setText(Translator.getInstance().get("GiftDelivers_balloonsBox"));
        giftBasketBox.setText(Translator.getInstance().get("GiftDelivers_giftBasketBox"));
        otherCheckbox.setText(Translator.getInstance().get("GiftDelivers_otherCheckbox"));
        askNumber.setText(Translator.getInstance().get("GiftDelivers_askNumber"));
        phoneInput.setPromptText(Translator.getInstance().get("GiftDelivers_phoneInput"));
        askLocation.setText(Translator.getInstance().get("GiftDelivers_askLocation"));
        requestLocation.setPromptText(Translator.getInstance().get("GiftDelivers_requestLocation"));
        submitButton.setText(Translator.getInstance().get("GiftDelivers_submitButton"));
    }

    @Override
    public void updateLanguage() {
        setLanguages();
    }

    /**
     * Constructor used to create a pop up window for GiftDelivery Request
     * @param request an instance of Requests.java
     */
    public GiftDelivery(Requests request) {
        this.request = request;
    }

    public void initialize() {
        Translator.getInstance().addLanguageListener(this);
        setLanguages();
        ArrayList<String> listOfNodeNames = new ArrayList<>();
        HashMap<String, Node> nodesMap = new HashMap<>();
        for (Node node : LocalStorage.getInstance().getNodes()) {
            nodesMap.put(node.getNodeID(), node); // put the nodes in the hashmap
            listOfNodeNames.add(node.getLongName());
            Collections.sort(listOfNodeNames);
        }  listOfNodeNames.forEach(n -> {
            requestLocation.getItems().add(n); // make the nodes appear in the combobox
        });
        ArrayList<String> flowers = new ArrayList<>();
        flowers.add("Roses");
        flowers.add("Daisies");
        flowers.add("Tulips");
        flowerType.getItems().addAll(flowers);
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
        return teddyBearBox.isSelected() || flowerCheckbox.isSelected() || chocolateBox.isSelected() || balloonsBox.isSelected() || giftBasketBox.isSelected() || otherCheckbox.isSelected();
    }

    /**
     * Checks if the "Other" text box for gift delivery options has been filled correctly
     *
     * @return true if the box was filled correctly, and false otherwise
     */
    public boolean otherInputValid() {
        return !otherCheckbox.isSelected() || (otherCheckbox.isSelected() && !otherInput.getText().isEmpty());
    }

    public boolean flowerSelectionValid() {
        return flowerType.getValue() != null;
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
        else if (flowerCheckbox.isSelected() && !flowerSelectionValid())
            failedGiftSelection.setText("Please select a flower type.");
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
            if (flowerCheckbox.isSelected())
                giftSelected.add(flowerType.getValue().toString());
            if (chocolateBox.isSelected())
                giftSelected.add("Chocolates");
            if (balloonsBox.isSelected())
                giftSelected.add("Balloons");
            if (giftBasketBox.isSelected())
                giftSelected.add("Gift Basket");
            if (otherCheckbox.isSelected())
                giftSelected.add(otherInput.getText());

            LoadFXML.setCurrentWindow("");

            //Add this request to our list of requests
            //requests.add(new GiftRequest(phoneInput.getText(), requestLocation.getValue(), nameInput.getText()));
            MasterServiceRequestStorage request = new MasterServiceRequestStorage("Gift Delivery", requestLocation.getValue(), giftSelected, nameInput.getText(), phoneInput.getText(), "", false);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/Service Request Components/GiftDeliveryRequest.fxml"));
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
            LoadFXML.getInstance().openWindow("giftDeliveryBar", root, SceneManager.getInstance().getDefaultPage().getPopPop());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
