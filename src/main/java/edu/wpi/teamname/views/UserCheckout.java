package edu.wpi.teamname.views;

import com.jfoenix.controls.*;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.views.manager.ButtonManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.HashMap;


public class UserCheckout {
        static  DefaultPage defaultPage = SceneManager.getInstance().getDefaultPage();

        @FXML
        private JFXComboBox<String> parkingBox;
        @FXML
        private Label failedParking;
        @FXML
        private Label failedRating;
        @FXML
        private JFXSlider experienceSlider;
        @FXML
        private JFXTextField additionalComments;
        @FXML
        private JFXButton submitButton;
        @FXML
        private VBox successPop;
        ArrayList<Node> listOfNodes = new ArrayList<>();
        HashMap<String, Node> mapNodes = new HashMap<>();
        HashMap<String, Node> mapNodes2 = new HashMap<>();
        @FXML
        void initialize(){
           refreshSpaces();
            //System.out.println("ONCE");
        }

        void refreshSpaces(){

            listOfNodes = LocalStorage.getInstance().getNodes();
            for (Node n: listOfNodes
            ) {
                mapNodes.put(n.getNodeID(), n);
                mapNodes2.put(n.getLongName(), n);
            }
            ArrayList<String> listOfSpaces = new ArrayList<>();
            listOfSpaces = LocalStorage.getInstance().getReservedParkingSpaces();
            for (String s: listOfSpaces
            ) {
                parkingBox.getItems().add(mapNodes.get(s).getLongName());
            }
        }


        @FXML
        void userCheckout() {
            LoadFXML.setCurrentWindow("");
            defaultPage.toggleCheckIn();
            SceneManager.getInstance().getDefaultPage().closeWindows();
            if (!(parkingBox.getValue() == null)){
                Submit.getInstance().removeParking(mapNodes2.get(parkingBox.getValue()).getNodeID());
                Submit.getInstance().removeParking(parkingBox.getValue());
                ButtonManager.remove_class();
            }
        }

    }

