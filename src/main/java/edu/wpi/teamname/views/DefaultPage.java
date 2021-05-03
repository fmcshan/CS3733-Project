package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.Authentication.AuthListener;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.bot.ChatBot;
import edu.wpi.teamname.views.manager.LevelManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Controller for DefaultPage.fxml
 *
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class DefaultPage extends MapDisplay implements AuthListener {

    // used to save the current list of nodes after AStar

    boolean opened = false;
    @FXML
    private JFXButton floor3Bttn, floor2Bttn, floor1Bttn, groundBttn, L1Bttn, L2Bttn;
    @FXML
    private VBox popPop, adminPop, requestPop, registrationPop, helpPop, chatBox; // vbox to populate with different fxml such as Navigation/Requests/Login
    @FXML
    private Path tonysPath; // the path displayed on the map
    @FXML
    private ImageView hospitalMap; // the map
    @FXML
    private JFXButton adminButton; // button that allows you to sign in
    @FXML
    private AnchorPane topElements, chatBot; // anchor pane where displayed nodes reside
    @FXML
    private JFXTextField enteredMessage;
    @FXML
    private ScrollPane chatScrollPane;
    @FXML
    private JFXButton helpButton;
    @FXML
    private JFXButton chatButton;

    /**
     * run on startup
     */
    public void initialize() {
        SceneManager.getInstance().setDefaultPage(this);
       Font.loadFont(getClass().getResourceAsStream("/edu/wpi/teamname/images/proximanova-regular.ttf"), 16);
        Font test = Font.loadFont(getClass().getResourceAsStream("/edu/wpi/teamname/images/Graphik-Regular_1.ttf"), 16);
        System.out.println(test.getFamily());
        hideAddNodePopup();
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
            if (!LoadFXML.getCurrentWindow().equals("navBar")) {
                currentPath = new ArrayList();
            }
            topElements.getChildren().clear();
            resizingInfo();
            zooM.zoomAndPan();
        });

        anchor.widthProperty().addListener((obs, oldVal, newVal) -> { // adjust the path and the map to the window as it changes
            if (currentPath.size() > 0 && LoadFXML.getCurrentWindow().equals("navBar")) {
                drawPath(currentPath);
            }
            if (!LoadFXML.getCurrentWindow().equals("navBar")) {
                currentPath = new ArrayList();
            }

            topElements.getChildren().clear();
            resizingInfo();
            zooM.zoomAndPan();
        });

        refreshData();
        zooM.zoomAndPan();
    }

    private void displayAuthPages() {
        if (AuthenticationManager.getInstance().isAdmin()) {
        LoadFXML.getInstance().loadWindow("MapEditorButton", "mapButton", adminPop);
        LoadFXML.getInstance().loadWindow("SubmittedRequestsButton", "reqButton", requestPop);
        LoadFXML.getInstance().loadWindow("SubmittedRegistrationsButton", "regButton", registrationPop);
        LoadFXML.getInstance().loadWindow("EmployeeTableButton", "employeeButton", employeePop);
        MaterialDesignIconView signOut = new MaterialDesignIconView(MaterialDesignIcon.EXIT_TO_APP);
        signOut.setFill(Paint.valueOf("#c3c3c3"));
        signOut.setGlyphSize(52);
        adminButton.setGraphic(signOut);
        return;
        }
        LoadFXML.getInstance().loadWindow("SubmittedRequestsButton", "reqButton", requestPop);
        LoadFXML.getInstance().loadWindow("SubmittedRegistrationsButton", "regButton", registrationPop);
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
        helpButton.setVisible(true);
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
        helpButton.setVisible(true);
        LoadFXML.setCurrentWindow("");
    }

    /**
     * toggle the map editor window
     */
    public void toggleMapEditor() {
        helpButton.setVisible(true);
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
        helpButton.setVisible(false);
        clearMap();
        popPop.setPrefWidth(1000);
        LoadFXML.getInstance().loadWindow("RegistrationAdminView", "checkAdminBar", popPop);
    }

    /**
     * toggle the admin request window
     */
    public void toggleRequest() {
        helpButton.setVisible(false);
        clearMap();
        popPop.setPrefWidth(1000);
        LoadFXML.getInstance().loadWindow("RequestAdmin", "reqAdminBar", popPop);
    }

    /**
     * toggle the admin request window
     */
    public void toggleEmployee() {
        helpButton.setVisible(false);
        clearMap();
        popPop.setPrefWidth(1000);
        LoadFXML.getInstance().loadWindow("EmployeeTable", "employeeBar", popPop);
    }

    @FXML
    private void openHelp() {
        if (!AuthenticationManager.getInstance().isAuthenticated() && LoadFXML.getCurrentWindow().equals("")) {
            popPop.setPrefWidth(340);
            LoadFXML.getInstance().loadHelp("defaultBar", "help_defaultBar", popPop);
            return;
        }
        if (LoadFXML.getCurrentWindow().equals("mapEditorBar")) {
            popPop.setPrefWidth(340);
            LoadFXML.getInstance().loadHelp("mapEditorBar", "help_mapBar", popPop);
            return;
        }
        if (AuthenticationManager.getInstance().isAuthenticated() && LoadFXML.getCurrentWindow().equals("")) {
            LoadFXML.getInstance().loadHelp("defaultSignOutBar", "help_defaultSignOutBar", popPop);
            return;
        }
        LoadFXML.getInstance().loadHelp(LoadFXML.getCurrentWindow(), "help_" + LoadFXML.getCurrentWindow(), popPop2);
    }

    @FXML
    private void openChatBot() {
        if (!opened) {
            chatBot.setVisible(true);
            chatBot.setPickOnBounds(true);
            opened = true;
            MaterialDesignIconView messageIcon = new MaterialDesignIconView(MaterialDesignIcon.CHEVRON_DOWN);
            messageIcon.setFill(Color.WHITE);
            messageIcon.setGlyphSize(40);
            chatButton.setGraphic(messageIcon);
            return;
        }
        chatBot.setVisible(false);
        chatBot.setPickOnBounds(false);
        opened = false;

        MaterialDesignIconView messageIcon = new MaterialDesignIconView(MaterialDesignIcon.MESSAGE_REPLY);
        messageIcon.setFill(Color.WHITE);
        messageIcon.setGlyphSize(40);
        chatButton.setGraphic(messageIcon);
    }

    @FXML
    void sendMessage() {
        String message = enteredMessage.getText();
        if (message.isEmpty()) {
            return;
        }
        Label sentMessage = new Label();
        sentMessage.setStyle("-fx-font-family: 'Segoe UI Semibold'; -fx-font-size: 16; -fx-text-fill: white; -fx-background-color: #317fb8; " +
                "-fx-background-radius: 20 20 0 20; -fx-border-radius: 20 20 0 20; -fx-border-width: 1.5; -fx-wrap-text: true; -fx-min-height: 50; " +
                "-fx-min-width: 50; -fx-padding: 10 10 10 15");

        VBox sentVBox = new VBox(sentMessage);
        sentVBox.setMaxWidth(275);
        sentVBox.setAlignment(Pos.BOTTOM_RIGHT);
        VBox.setMargin(sentVBox, new Insets(0, -70, 10, 0));

        sentMessage.setText(message);
        chatBox.getChildren().add(sentVBox);
        enteredMessage.clear();

        chatScrollPane.setFitToHeight(false);
        chatScrollPane.setVvalue(1);

        ChatBot.getInstance().sendMessage(message);
    }

    public void receiveMessage(String _msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label sentMessage = new Label();
                sentMessage.setStyle("-fx-font-size: 16; -fx-text-fill: black; -fx-background-color: #eeeeee; " +
                        "-fx-background-radius: 20 20 20 0; -fx-border-radius: 20 20 20 0; -fx-border-width: 1.5; -fx-wrap-text: true; -fx-min-height: 50; " +
                        "-fx-min-width: 50; -fx-padding: 10 10 10 15");

                VBox sentVBox = new VBox(sentMessage);
                sentVBox.setMaxWidth(275);
                sentVBox.setAlignment(Pos.BOTTOM_LEFT);
                VBox.setMargin(sentVBox, new Insets(0, 0, 10, -70));

                sentMessage.setText(_msg);
                chatBox.getChildren().add(sentVBox);
                enteredMessage.clear();

                chatScrollPane.setFitToHeight(false);
                chatScrollPane.setVvalue(1);
            }
        });
    }

    void disableButtons(ArrayList<String> floors) {
        if (floors.contains("L2"))
            L2Bttn.setDisable(true);
        if (floors.contains("L1"))
            L1Bttn.setDisable(true);
        if (floors.contains("G"))
            groundBttn.setDisable(true);
        if (floors.contains("1"))
            floor1Bttn.setDisable(true);
        if (floors.contains("2"))
            floor2Bttn.setDisable(true);
        if (floors.contains("3"))
            floor3Bttn.setDisable(true);
    }

    void enableButtons(ArrayList<String> floors) {
        if (floors.contains("L2"))
            L2Bttn.setDisable(false);
        if (floors.contains("L1"))
            L1Bttn.setDisable(false);
        if (floors.contains("G"))
            groundBttn.setDisable(false);
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

    public void setHelpButton(boolean value) {
        helpButton.setVisible(value);
    }

}