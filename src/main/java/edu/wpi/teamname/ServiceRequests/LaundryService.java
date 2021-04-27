package edu.wpi.teamname.ServiceRequests;

import com.jfoenix.controls.JFXButton;
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
 * <h1>Laundry Request Controller</h1>
 * Controller for the Laundry Service Request Page
 * @author Lauren Sowerbutts, Frank McShan
 */

public class LaundryService implements LanguageListener {

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
     * Label indicating if a load type hasn't been selected
     */
    @FXML
    private Label failedLoadType;

    /**
     * Check Box selecting colors for load type
     */
    @FXML
    private JFXCheckBox colorsBox;

    /**
     * Check Box selecting whites for load type
     */
    @FXML
    private JFXCheckBox whitesBox;

    /**
     * Check Box selecting some other load type
     */
    @FXML
    private JFXCheckBox otherCheckbox;

    /**
     * Text Field to specify what load type
     */
    @FXML
    private JFXTextField otherInput;

    /**
     * Label indicating if a wash temp hasn't been selected
     */
    @FXML
    private Label failedWashTemp;

    /**
     * Check Box selecting cold wash temperature
     */
    @FXML
    private JFXCheckBox coldBox;

    /**
     * Check Box selecting warm wash temperature
     */
    @FXML
    private JFXCheckBox warmBox;

    /**
     * Check Box selecting hot wash temperature
     */
    @FXML
    private JFXCheckBox hotBox;

    /**
     * Label indicating if phone number wasn't entered
     */
    @FXML
    private Label failedPhoneNumber;

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
     * Success pop up page
     */
    @FXML
    private VBox successPop;

    /**
     * Instance of Requests class used to create a popup window
     */
    @FXML
    private edu.wpi.teamname.views.Requests request;

    @FXML
    private Label title;

    @FXML
    private Text desc;

    @FXML
    private Label askName;

    @FXML
    private Label askType;

    @FXML
    private Label askTemperature;

    @FXML
    private Label askPhone;

    @FXML
    private Label askLocation;

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
    public LaundryService(Requests request) {
        this.request = request;
    }

    private void setLanguages(){
        title.setText(Translator.getInstance().get("LaundryServices_title"));
        desc.setText(Translator.getInstance().get("LaundryServices_desc"));
        askName.setText(Translator.getInstance().get("LaundryServices_askName"));
        nameInput.setPromptText(Translator.getInstance().get("LaundryServices_nameInput"));
        askType.setText(Translator.getInstance().get("LaundryServices_askType"));
        colorsBox.setText(Translator.getInstance().get("LaundryServices_colorsBox"));
        whitesBox.setText(Translator.getInstance().get("LaundryServices_whitesBox"));
        otherCheckbox.setText(Translator.getInstance().get("LaundryServices_otherCheckbox"));
        askTemperature.setText(Translator.getInstance().get("LaundryServices_askTemperature"));
        coldBox.setText(Translator.getInstance().get("LaundryServices_coldBox"));
        warmBox.setText(Translator.getInstance().get("LaundryServices_warmBox"));
        hotBox.setText(Translator.getInstance().get("LaundryServices_hotBox"));
        askPhone.setText(Translator.getInstance().get("LaundryServices_askPhone"));
        phoneInput.setPromptText(Translator.getInstance().get("LaundryServices_phoneInput"));
        askLocation.setText(Translator.getInstance().get("LaundryServices_askLocation"));
        requestLocation.setPromptText(Translator.getInstance().get("LaundryServices_requestLocation"));
        submitButton.setText(Translator.getInstance().get("LaundryServices_submitButton"));
    }

    @Override
    public void updateLanguage() {
        setLanguages();
    }

    public void initialize() {
        ArrayList<String> listOfNodeNames = new ArrayList<>();
        HashMap<String, Node> nodesMap = new HashMap<>();
        Translator.getInstance().addLanguageListener(this);
        setLanguages();
        for (Node node : LocalStorage.getInstance().getNodes()) {
            nodesMap.put(node.getNodeID(), node); // put the nodes in the hashmap
            listOfNodeNames.add(node.getLongName());
            Collections.sort(listOfNodeNames);
        }  listOfNodeNames.forEach(n -> {
            requestLocation.getItems().add(n); // make the nodes appear in the combobox
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
     * Checks if a checkbox has been selected for load type
     *
     * @return true if any checkbox has been selected, and false otherwise
     */
    public boolean checkBoxLoadSelected() {
        return colorsBox.isSelected() || whitesBox.isSelected() || otherCheckbox.isSelected();
    }

    public boolean oneLoadSelected() {
        return (colorsBox.isSelected() && !whitesBox.isSelected() && !otherCheckbox.isSelected()) || (!colorsBox.isSelected() && whitesBox.isSelected() && !otherCheckbox.isSelected()) || (!colorsBox.isSelected() && !whitesBox.isSelected() && otherCheckbox.isSelected());
    }


    /**
     * Checks if a checkbox has been selected for wash temperature
     *
     * @return true if any checkbox has been selected, and false otherwise
     */
    public boolean checkBoxTempSelected() {
        return coldBox.isSelected() || warmBox.isSelected() || hotBox.isSelected();
    }

    public boolean oneTempSelected() {
        return (coldBox.isSelected() || !warmBox.isSelected() || !hotBox.isSelected()) || (!coldBox.isSelected() || warmBox.isSelected() || !hotBox.isSelected()) || (!coldBox.isSelected() || !warmBox.isSelected() || hotBox.isSelected());
    }

    /**
     * Checks if the "Other" text box for load type options has been filled correctly
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


        if (!checkBoxLoadSelected())
            failedLoadType.setText("Please select a load type.");
        else if (!oneLoadSelected())
            failedLoadType.setText("Invalid Selection");
        else if (!otherInputValid())
            failedLoadType.setText("Please ensure you have selected the \"Other\" box and have correctly filled in the text field.");
        else
            failedLoadType.setText("");

        if (!checkBoxTempSelected())
            failedWashTemp.setText("Please select a wash temperature.");
        else if (!oneTempSelected())
            failedWashTemp.setText("Invalid Selection");
        else
            failedWashTemp.setText("");


        if (!phoneNumberValid())
            failedPhoneNumber.setText("Invalid Phone Number");
        else
            failedPhoneNumber.setText("");

        if (!locationValid())
            failedLocationEntry.setText("Please select a location");

        if (requests == null) {
            requests = new ArrayList<ServiceRequest>();
        }

        if (nameInputValid() && checkBoxLoadSelected() && checkBoxTempSelected() && otherInputValid() && phoneNumberValid() && oneLoadSelected() && oneTempSelected()) {
            //Adds all the selected gifts to an arraylist
            ArrayList<String> laundryTypeSelected = new ArrayList<>();
            if (colorsBox.isSelected())
                laundryTypeSelected.add("Colors");
            if (whitesBox.isSelected())
                laundryTypeSelected.add("Whites");
            if (otherCheckbox.isSelected())
                laundryTypeSelected.add(otherInput.getText());
            String washTemp = "";
            if (coldBox.isSelected())
                washTemp += "Cold";
            if (warmBox.isSelected())
                washTemp += "Warm";
            if (hotBox.isSelected())
                washTemp += "Hot";

            LoadFXML.setCurrentWindow("");

            //Add this request to our list of requests
//            requests.add(new ServiceRequest(phoneInput.getText(), requestLocation.getValue(), nameInput.getText()) {
//            });
            MasterServiceRequestStorage request = new MasterServiceRequestStorage("Laundry Service", requestLocation.getValue(), laundryTypeSelected, washTemp, nameInput.getText(), phoneInput.getText(), "", false);
            Submit.getInstance().submitGiftDelivery(request);

            // load Success page in successPop VBox
            successPop.setPrefWidth(657.0);
            Success success = new Success(this);
            success.loadSuccess(Translator.getInstance().get("Requests_success"), successPop);
        }
    }

    /**
     * Load Request form when the button is pressed/make it disappear
     */
    public void loadRequest() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/ServiceRequestComponents/LaundryRequest.fxml"));
        try {
            loader.setControllerFactory(type -> {
                if (type == LaundryService.class)
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
            LoadFXML.getInstance().openWindow("laundryBar", root, SceneManager.getInstance().getDefaultPage().getPopPop()); //open/close request form
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
