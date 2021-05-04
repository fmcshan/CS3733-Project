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
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    private JFXButton floor3Bttn, floor2Bttn, floor1Bttn, groundBttn, L1Bttn, GBttn, L2Bttn;


    @Override
    public VBox getPopPop() {
        return popPop;
    }

    @FXML
    private JFXButton checkButton;
    @FXML
    private VBox popPop, adminPop, requestPop, registrationPop, helpPop, chatBox, closedChatBox; // vbox to populate with different fxml such as Navigation/Requests/Login
    @FXML
    private Path tonysPath; // the path displayed on the map
    @FXML
    private ImageView hospitalMap; // the map
    @FXML
    private JFXButton adminButton; // button that allows you to sign in
    @FXML
    AnchorPane topElements, chatBot; // anchor pane where displayed nodes reside
    @FXML
    private JFXTextField enteredMessage;
    @FXML
    private ScrollPane chatScrollPane;
    @FXML
    private JFXButton helpButton;
    @FXML
    private JFXButton chatButton;
    @FXML
    private JFXButton testingButton;
    @FXML
    private AnchorPane closedChatBot;

    /**
     * run on startup
     */
    public void initialize() {
        SceneManager.getInstance().setDefaultPage(this);
        Font test = Font.loadFont(getClass().getResourceAsStream("/edu/wpi/teamname/images/Nunito-SemiBold.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/edu/wpi/teamname/images/Nunito-Regular.ttf"), 24);
        Font.loadFont(getClass().getResourceAsStream("/edu/wpi/teamname/images/Nunito-Bold.ttf"), 24);
        //System.out.println(test.getFamily());
        hideAddNodePopup();
        LevelManager.getInstance().setFloor(3);
        AuthenticationManager.getInstance().addListener(this);
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
        LoadFXML.setCurrentHelp("");
        helpButton.setVisible(true);
        if (navigation != null) {
            navigation.cancelNavigation();
        }
        scaledX = 0;
        scaledY = 0;
        scaledWidth = 5000;
        scaledHeight = 3400.0;
        clearMap();
        popPop.getChildren().clear();
        popPop2.getChildren().clear();
        currentPath.clear();
        listOfNode.clear();
        startAndEnd.clear();
        startNode = null;
        endNode = null;
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

    public void initGoogleForm() {
        System.out.println("called");
        zooM.zoomAndPan();
    }

    @FXML
    private void openChatBot() {
        closedChatBox.getChildren().clear();
        closedChatBox.setPickOnBounds(false);
        if (!opened) {
            chatBot.setVisible(true);
            chatBot.setPickOnBounds(true);
            opened = true;
            MaterialDesignIconView messageIcon = new MaterialDesignIconView(MaterialDesignIcon.CHEVRON_DOWN);
            messageIcon.setFill(Color.WHITE);
            messageIcon.setGlyphSize(40);
            chatButton.setGraphic(messageIcon);
            LoadFXML.setCurrentWindow("chatBot");
            return;
        }
        chatBot.setVisible(false);
        chatBot.setPickOnBounds(false);
        opened = false;

        MaterialDesignIconView messageIcon = new MaterialDesignIconView(MaterialDesignIcon.MESSAGE_REPLY);
        messageIcon.setFill(Color.WHITE);
        messageIcon.setGlyphSize(40);
        chatButton.setGraphic(messageIcon);
        LoadFXML.setCurrentWindow("");
    }

    @FXML
    void sendMessage() { //317fb8
        String message = enteredMessage.getText();
        if (message.isEmpty()) {
            return;
        }
        Text sentMessage = new Text();
        sentMessage.setStyle("-fx-font-size: 16; -fx-font-family: 'Nunito'");
        sentMessage.setFill(Color.WHITE);
        //System.out.println(message.length());
        if (message.length() >= 30) {
            sentMessage.setWrappingWidth(255);
        }

        HBox sentBox = new HBox(sentMessage);
        sentBox.setStyle("-fx-background-color: #317fb8; " + "-fx-background-radius: 20 20 0 20;" +
                "-fx-min-width: 50; -fx-padding: 10 10 10 10");
        sentBox.setAlignment(Pos.BOTTOM_LEFT);

        AnchorPane sentPane = new AnchorPane(sentBox);
        AnchorPane.setRightAnchor(sentBox, 0.0);
        sentPane.setMaxWidth(275);
        sentPane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        VBox.setMargin(sentPane, new Insets(0, 0, 10, 50));

        sentMessage.setText(message);
        chatBox.getChildren().add(sentPane);
        enteredMessage.clear();

        chatScrollPane.setFitToHeight(false);

        chatScrollPane.setVvalue(1);

        ChatBot.getInstance().sendMessage(message);
    }

    public void receiveMessage(String _msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Text sentMessage = new Text();
                sentMessage.setStyle("-fx-font-size: 16; -fx-font-family: 'Nunito';");
                HBox sentBox = new HBox(sentMessage);
                sentMessage.setText(_msg);

                if (_msg.length() >= 30) {
                    sentMessage.setWrappingWidth(255);
                }

                System.out.println(LoadFXML.getCurrentWindow());
                //if chat bot is not open, only show response message and write a reply
                if (!(LoadFXML.getCurrentWindow().equals("chatBot"))) {
                    Text sentMessage2 = new Text();
                    sentMessage2.setStyle("-fx-font-size: 16; -fx-font-family: 'Nunito';");
                    HBox sentBox2 = new HBox(sentMessage2);
                    sentMessage2.setText(_msg);

                    if (_msg.length() >= 30) {
                        sentMessage2.setWrappingWidth(255);
                    }
                    closedChatBox.setVisible(true);
                    closedChatBox.setPickOnBounds(true);
                    sentBox2.setStyle("-fx-background-color: #eeeeee; " + "-fx-background-radius: 20 20 0 20;" +
                            "-fx-min-width: 50; -fx-padding: 10 10 10 10;");
                    sentBox2.setAlignment(Pos.BOTTOM_LEFT);
                    closedChatBox.getChildren().add(sentBox2);

                    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2), sentBox2);
                    translateTransition.setFromX(10);
                    translateTransition.setToX(0);

                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), sentBox2);
                    fadeIn.setFromValue(0.4);
                    fadeIn.setToValue(1.0);

                    ParallelTransition pt = new ParallelTransition(translateTransition, fadeIn);
                    pt.play();

                    PauseTransition pause = new PauseTransition(Duration.seconds(7));
                    pause.setOnFinished(event -> {
                                FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), sentBox2);
                                fadeOut.setFromValue(1.0);
                                fadeOut.setToValue(0);
                                fadeOut.play();
                                fadeOut.setOnFinished(s -> {
                                    closedChatBox.getChildren().remove(sentBox2);
                                });
                            }
                    );
                    pause.play();

                }  // if chat bot is open
                sentBox.setStyle("-fx-background-color: #eeeeee; " + "-fx-background-radius: 20 20 20 0;" +
                        "-fx-min-width: 50; -fx-padding: 10 10 10 10");
                sentBox.setAlignment(Pos.BOTTOM_LEFT);

                AnchorPane openChat = new AnchorPane(sentBox);
                openChat.setMaxWidth(275);
                VBox.setMargin(openChat, new Insets(0, 0, 10, -50));

                sentMessage.setText(_msg);
                chatBox.getChildren().add(openChat);
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

    public void toggleCheckIn() {
        if (checkButton.getText().equals("Check-In")) {
            checkButton.setText("Check-Out");
        } else {
            checkButton.setText("Check-In");
        }
    }

    @FXML
    public void openCheckIn() {
        popPop.setPrefWidth(657);
        clearMap(); // Clear map
        popPop.setPrefWidth(657.0); // Set preferable width to 657
        if (checkButton.getText().equals("Check-In")) {
            LoadFXML.getInstance().loadWindow("COVIDSurvey", "surveyBar", popPop); // Load registration window
        } else {
            LoadFXML.getInstance().loadWindow("UserCheckout", "surveyBar", popPop); // Load registration window
        }

    }

    public void setHelpButton(boolean value) {
        helpButton.setVisible(value);
    }

    public void clearPathAnimation() {
        for (int i = 0; i < onTopOfTopElements.getChildren().size(); i++) {
            if (onTopOfTopElements.getChildren().get(i) instanceof Polygon) {
                onTopOfTopElements.getChildren().remove(i);
                return;
            }
        }
    }
}




