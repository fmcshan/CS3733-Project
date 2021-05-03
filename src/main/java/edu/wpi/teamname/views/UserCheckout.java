package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class UserCheckout {
        static  DefaultPage defaultPage = SceneManager.getInstance().getDefaultPage();
        @FXML
        private Label registrationForm;

        @FXML
        private Text fillfieldsDesc;
        @FXML
        private JFXComboBox<String> parkingBox;
        @FXML
        private Label failedName;

        @FXML
        private Label failedDate;

        @FXML
        private Label failedReason;

        @FXML
        private Label reasonsLabel;

        @FXML
        private JFXCheckBox emergencyRoomCheckbox;

        @FXML
        private JFXCheckBox xrayCheckbox;

        @FXML
        private JFXCheckBox mriCheckbox;

        @FXML
        private JFXCheckBox eyeExamCheckbox;

        @FXML
        private JFXCheckBox physicalTherapyCheckbox;

        @FXML
        private Label failedPhoneNumber;

        @FXML
        private JFXTextField phoneInput;

        @FXML
        private JFXTextField phoneInput1;

        @FXML
        private JFXButton submitButton;

        @FXML
        private VBox successPop;

        @FXML
        void initialize(){
            ArrayList<String> listOfSpaces = new ArrayList<>();
            listOfSpaces = LocalStorage.getInstance().getReservedParkingSpaces();
            for (String s: listOfSpaces
                 ) {parkingBox.getItems().add(s);

            }
        }

        @FXML
        void userCheckout() {
            LoadFXML.setCurrentWindow("");
            defaultPage.toggleCheckIn();
            SceneManager.getInstance().getDefaultPage().closeWindows();
            if (!(parkingBox.getValue().equals(""))){
                Submit.getInstance().removeParking(parkingBox.getValue());
                System.out.println("Here");
            }
        }

    }

