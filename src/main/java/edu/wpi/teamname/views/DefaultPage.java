package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.App;
import edu.wpi.teamname.Authentication.AuthListener;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.simplify.Shutdown;
import edu.wpi.teamname.views.manager.LevelManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Controller for DefaultPage.fxml
 *
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class DefaultPage extends MapDisplay implements AuthListener {

    // used to save the current list of nodes after AStar

    @FXML
    private JFXButton floor3Bttn, floor2Bttn, floor1Bttn, GBttn, L1Bttn, L2Bttn;

    @Override
    public VBox getPopPop() {
        return popPop;
    }

    @FXML
    private JFXButton CheckIn;
    @FXML
    private VBox popPop, adminPop, requestPop, registrationPop, helpPop; // vbox to populate with different fxml such as Navigation/Requests/Login
    @FXML
    private Path tonysPath; // the path displayed on the map
    @FXML
    private ImageView hospitalMap; // the map
    @FXML
    private JFXButton adminButton; // button that allows you to sign in

//    public AnchorPane getTopElements() {
//        return topElements;
//    }

//    @FXML
//    private AnchorPane topElements; // anchor pane where displayed nodes reside


    /**
     * run on startup
     */
    public void initialize() {
        hideAddNodePopup();
        SceneManager.getInstance().setDefaultPage(this);
        LevelManager.getInstance().setFloor(3);
        AuthenticationManager.getInstance().addListener(this);
        LoadFXML.setCurrentHelp("");
        LoadFXML.setCurrentWindow("");

        if (AuthenticationManager.getInstance().isAuthenticated()) {
            displayAuthPages();
        }

        tonysPath.getElements().clear(); // clear the path
        LoadFXML.setCurrentWindow(""); // set the open window to nothing

        anchor.heightProperty().addListener((obs, oldVal, newVal) -> { // adjust the path and the map to the window as it changes
            if (currentPath.size() > 0 && LoadFXML.getCurrentWindow().equals("navBar")) {
                drawPath(currentPath);
            }
            if(!LoadFXML.getCurrentWindow().equals("navBar")){
                currentPath= new ArrayList();
            }
            topElements.getChildren().clear();
            resizingInfo();
            zooM.zoomAndPan();
        });

        anchor.widthProperty().addListener((obs, oldVal, newVal) -> { // adjust the path and the map to the window as it changes
            if (currentPath.size() > 0 && LoadFXML.getCurrentWindow().equals("navBar")) {
                drawPath(currentPath);
            }
            if(!LoadFXML.getCurrentWindow().equals("navBar")){
                currentPath= new ArrayList();
            }

            topElements.getChildren().clear();
            resizingInfo();
            zooM.zoomAndPan();
        });

        refreshData();
        zooM.zoomAndPan();
    }

    private void displayAuthPages() {
        LoadFXML.getInstance().loadWindow("MapEditorButton", "mapButton", adminPop);
        LoadFXML.getInstance().loadWindow("SubmittedRequestsButton", "reqButton", requestPop);
        LoadFXML.getInstance().loadWindow("SubmittedRegistrationsButton", "regButton", registrationPop);
        LoadFXML.getInstance().loadWindow("EmployeeTableButton", "employeeButton", employeePop);
        MaterialDesignIconView signOut = new MaterialDesignIconView(MaterialDesignIcon.EXIT_TO_APP);
        signOut.setFill(Paint.valueOf("#c3c3c3"));
        signOut.setGlyphSize(52);
        adminButton.setGraphic(signOut);
    }

    /**
     * for displaying the new buttons after user logins
     */
    @Override
    public void userLogin() {
        displayAuthPages();
    }

    /**
     * getting rid of the buttons after user sign outs
     */
    @Override
    public void userLogout() {
        adminPop.getChildren().clear();
        requestPop.getChildren().clear();
        registrationPop.getChildren().clear();
        employeePop.getChildren().clear();
        MaterialDesignIconView signOut = new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT_BOX_OUTLINE);
        signOut.setFill(Paint.valueOf("#c3c3c3"));
        signOut.setGlyphSize(52);
        adminButton.setGraphic(signOut);
        popPop.getChildren().clear();
    }

    /**
     * toggle the map editor window
     */
    public void toggleMapEditor() {
        scaledX = 0;
        scaledY = 0;
        scaledWidth = 5000;
        scaledHeight = 3400.0;
        clearMap();
        popPop.getChildren().clear();
        popPop2.getChildren().clear();
        zooM.zoomAndPan();
        if (LoadFXML.getCurrentWindow().equals("mapEditorBar")) {
            topElements.getChildren().clear();
            LoadFXML.setCurrentWindow("");
            zooM.zoomAndPan();
            return;
        }

        initMapEditor();

        LoadFXML.setCurrentWindow("mapEditorBar");
    }

    /**
     * toggle the admin registration window
     */
    public void toggleRegistration() {
        clearMap();
        popPop.setPrefWidth(1000);
        LoadFXML.getInstance().loadWindow("RegistrationAdminView", "checkAdminBar", popPop);
    }

    /**
     * toggle the admin request window
     */
    public void toggleRequest() {
        clearMap();
        popPop.setPrefWidth(1000);
        LoadFXML.getInstance().loadWindow("RequestAdmin", "reqAdminBar", popPop);
    }

    /**
     * toggle the admin request window
     */
    public void toggleEmployee() {
        clearMap();
        popPop.setPrefWidth(1000);
        LoadFXML.getInstance().loadWindow("EmployeeTable", "employeeBar", popPop);
    }

    public void toggleGoogleMaps() {
        scaledX = 0;
        scaledY = 0;
        scaledWidth = 5000;
        scaledHeight = 3400.0;
        clearMap();
        popPop.setPrefWidth(400);
        popPop.getChildren().clear();
        LoadFXML.getInstance().loadWindow("GoogleMapForm", "googleMapBar", popPop);
    }

    public void toggleGoogleMapsHome() {
        clearMap();
        popPop.setPrefWidth(400);
        popPop.getChildren().clear();
        LoadFXML.getInstance().loadWindow("GoogleMapHome", "googleMapHomeBar", popPop);
        zooM.zoomAndPan();
    }
    @FXML
    private void openHelp() {
        if (LoadFXML.getCurrentWindow().equals("")) {
            popPop.setPrefWidth(340);
            LoadFXML.getInstance().loadHelp("defaultBar", "help_defaultBar", popPop);
            return;
        }
        if (LoadFXML.getCurrentWindow().equals("mapEditorBar")) {
            popPop.setPrefWidth(340);
            LoadFXML.getInstance().loadHelp("mapEditorBar", "help_mapBar", popPop);
            return;
        }
        LoadFXML.getInstance().loadHelp(LoadFXML.getCurrentWindow(), "help_" + LoadFXML.getCurrentWindow(), popPop2);
    }
    public void initGoogleForm(){
        System.out.println("called");
        zooM.zoomAndPan();
    }
    
    void disableButtons(ArrayList<String> floors){
        if (floors.contains("L2"))
            L2Bttn.setDisable(true);
        if (floors.contains("L1"))
            L1Bttn.setDisable(true);
        if (floors.contains("G"))
            GBttn.setDisable(true);
        if (floors.contains("1"))
            floor1Bttn.setDisable(true);
        if (floors.contains("2"))
            floor2Bttn.setDisable(true);
        if (floors.contains("3"))
            floor3Bttn.setDisable(true);
    }

    void enableButtons(ArrayList<String> floors){
        if (floors.contains("L2"))
            L2Bttn.setDisable(false);
        if (floors.contains("L1"))
            L1Bttn.setDisable(false);
        if (floors.contains("G"))
            GBttn.setDisable(false);
        if (floors.contains("1"))
            floor1Bttn.setDisable(false);
        if (floors.contains("2"))
            floor2Bttn.setDisable(false);
        if (floors.contains("3"))
            floor3Bttn.setDisable(false);
    }

    public void closeWindows() {
        popPop.getChildren().clear();
    }

    public void setHospitalMap(Image _image) {
        hospitalMap.setImage(_image);
    }

    public void toggleCheckIn(){
        if(CheckIn.getText().equals("Check-In")){
            CheckIn.setText("Check-Out");
        } else{
            CheckIn.setText("Check-In");
        }
    }
    @FXML
    public void openCheckIn() {
        popPop.setPrefWidth(657);
        clearMap(); // Clear map
        popPop.setPrefWidth(657.0); // Set preferable width to 657
        if(CheckIn.getText().equals("Check-In")){
            LoadFXML.getInstance().loadWindow("COVIDSurvey", "surveyBar", popPop); // Load registration window
        } else {
            LoadFXML.getInstance().loadWindow("UserCheckout", "surveyBar", popPop); // Load registration window
        }

    }

}