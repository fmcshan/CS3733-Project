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
import edu.wpi.teamname.views.manager.LanguageListener;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller for DefaultPage.fxml
 *
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class DefaultPage extends MapDisplay implements AuthListener {

    ArrayList<Node> currentPath = new ArrayList<>(); // used to save the current list of nodes after AStar
    ArrayList<Node> listOfNodes;
    double mapWidth; //= 1000.0;
    double mapHeight;// = 680.0;
    double fileWidth; //= 5000.0;
    double fileHeight;// = 3400.0;
    double fileFxWidthRatio = mapWidth / fileWidth;
    double fileFxHeightRatio = mapHeight / fileHeight;
    @FXML
    private VBox popPop, adminPop, requestPop, registrationPop; // vbox to populate with different fxml such as Navigation/Requests/Login
    @FXML
    private Path tonysPath; // the path displayed on the map
    @FXML
    private ImageView hospitalMap; // the map
    @FXML
    private StackPane stackPane; // the pane the map is housed in
    @FXML
    private JFXButton adminButton; // button that allows you to sign in
    @FXML
    private AnchorPane topElements; // anchor pane where displayed nodes reside
    @FXML
    private JFXComboBox<String> languageBox; //selects language you want
    @FXML
    private JFXButton Navigation;
    @FXML
    private JFXButton CheckIn;
    @FXML
    private JFXButton Requests;


    public String languageHelper(String getText){ //simplifies the language in helper 2, gets the value for given language
        return Translator.getInstance().languageHashmap.get(Translator.getInstance().getCurrentLanguage()).get(getText);
    }

    public void languageHelper2(){ //can call this for each language
        Navigation.setText(languageHelper("Navigation"));
        CheckIn.setText(languageHelper("CheckIn"));
        Requests.setText(languageHelper("Requests"));
    }


    public void languageSwitch() { //picks a language and checks current language
        if(languageBox.getValue().equals("Spanish")){
            Translator.getInstance().setCurrentLanguage("language_spanish");
            languageHelper2();
        }
        if(languageBox.getValue().equals("English")){
            Translator.getInstance().setCurrentLanguage("language_english");
            languageHelper2();

        }


    }

    public void setLanguages(){ //call this in intialize, sets the values in language hashmap and words hashmap
        Translator.getInstance().languageHashmap.put("language_english", Translator.getInstance().language_english); //add english hashmap
        Translator.getInstance().language_english.put("Navigation", "Navigation");
        Translator.getInstance().language_english.put("CheckIn", "Check-In");
        Translator.getInstance().language_english.put("Requests", "Requests");
        Translator.getInstance().languageHashmap.put("language_spanish", Translator.getInstance().language_spanish); //add spanish hashmap
        Translator.getInstance().language_spanish.put("Navigation", "Navegacion");
        Translator.getInstance().language_spanish.put("CheckIn", "Registrarse");
        Translator.getInstance().language_spanish.put("Requests", "Peticiones");

    }


    /**
     * getter for popPop Vbox
     *
     * @return
     */
    public VBox getPopPop() {
        return popPop;
    }

    /**
     * run on startup
     */
    public void initialize() {
        setLanguages();
        hideAddNodePopup();
        SceneManager.getInstance().setDefaultPage(this);
        AuthenticationManager.getInstance().addListener(this);
        languageBox.getItems().add("English");
        languageBox.getItems().add("Spanish");

        if (AuthenticationManager.getInstance().isAuthenticated()) {
            displayAuthPages();
        }

        tonysPath.getElements().clear(); // clear the path
        LoadFXML.setCurrentWindow(""); // set the open window to nothing

        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> { // adjust the path and the map to the window as it changes
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            hospitalMap.fitWidthProperty().bind(stackPane.widthProperty());
            resizingInfo();
        });

        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> { // adjust the path and the map to the window as it changes
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            hospitalMap.fitHeightProperty().bind(stackPane.heightProperty());
            resizingInfo();
        });

        refreshData();
    }

    public boolean nodeWithinSpec(Node n) {
        return ((n.getFloor().equals("1") || n.getFloor().equals("G") || n.getFloor().equals("")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") || n.getBuilding().equals("")));
    }

    /**
     * for updating and displaying the map
     *
     * @param _listOfNodes a path of nodes
     */
    public void drawPath(ArrayList<Node> _listOfNodes) {
        if (_listOfNodes.size() < 1) {
            return;
        }
        currentPath = _listOfNodes;
        tonysPath.getElements().clear();
        double mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
        double mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
        double fileWidth = hospitalMap.getImage().getWidth();
        double fileHeight = hospitalMap.getImage().getHeight();
        double fileFxWidthRatio = mapWidth / fileWidth;
        double fileFxHeightRatio = mapHeight / fileHeight;
        Node firstNode = _listOfNodes.get(0);
        MoveTo start = new MoveTo(firstNode.getX() * fileFxWidthRatio, firstNode.getY() * fileFxHeightRatio);
        tonysPath.getElements().add(start);
        System.out.println(fileFxWidthRatio);
        _listOfNodes.forEach(n -> {
            tonysPath.getElements().add(new LineTo(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio));
        });
    }

    private void displayAuthPages() {
        LoadFXML.getInstance().loadWindow("MapEditorButton", "mapButton", adminPop);
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
    }

    /**
     * getting rid of the buttons after user sign outs
     */
    @Override
    public void userLogout() {
        adminPop.getChildren().clear();
        requestPop.getChildren().clear();
        registrationPop.getChildren().clear();
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
        topElements.getChildren().clear();
        popPop.getChildren().clear();
        if (LoadFXML.getCurrentWindow().equals("mapEditorBar")) {
            topElements.getChildren().clear();
            LoadFXML.setCurrentWindow("");
            return;
        }
        initMapEditor();
        LoadFXML.setCurrentWindow("mapEditorBar");
    }

    /**
     * toggle the admin registration window
     */
    public void toggleRegistration() {
        topElements.getChildren().clear();
        popPop.setPrefWidth(1000);
        LoadFXML.getInstance().loadWindow("RegistrationAdminView", "registrationBar", popPop);
    }

    /**
     * toggle the admin request window
     */
    public void toggleRequest() {
        topElements.getChildren().clear();
        popPop.setPrefWidth(1000);
        LoadFXML.getInstance().loadWindow("RequestAdminView", "requestBar", popPop);
    }

    public void closeWindows() {
        popPop.getChildren().clear();
    }


}
