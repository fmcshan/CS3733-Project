package edu.wpi.teamname.views;

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
        requestsBox.getItems().add("Gift Delivery");
    }

    /**
     * OnAction command for clicking the "Open Request Form" button
     */
    public void openRequest() {
        if (requestsBox.getValue().equals("Gift Delivery")){
            System.out.println("click worked");
            requestPop.setPrefWidth(657.0);
            GiftDelivery gdr = new GiftDelivery(this);
            gdr.loadRequest();
        }
    }

    /**
     * Opens an FXML in the requestPop VBox
     */
    public void openWindowRequestPop(String windowName, Parent root) {
        requestPop.getChildren().clear(); //Clear requestPop VBox
        if (!windowName.equals(openWindow)) { //If the window we're trying to open is not the current window
            requestPop.getChildren().add(root); //Put the fxml in the requestPop VBox
            openWindow = windowName; //Set the current window to the specified window name
            return;
        }
        openWindow = ""; //set the current window to nothing- the window is closed
    }
}
