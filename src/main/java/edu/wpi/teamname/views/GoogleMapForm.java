package edu.wpi.teamname.views;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.maps.DirectionsApi;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.*;
import com.google.maps.*;
import com.google.maps.GeoApiContext;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.json.*;
import com.google.maps.errors.ApiException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.slf4j.Marker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleMapForm {

    @FXML
    private Label title;

    @FXML
    private Text description;

    @FXML
    private Label fromLabel;

    @FXML
    private JFXTextField numInput;

    @FXML
    private JFXTextField emailInput;

    @FXML
    private Label fromLabel1;

    @FXML
    private JFXTextField streetInput;

    @FXML
    private Label fromLabel2;

    @FXML
    private JFXTextField townInput;

    @FXML
    private Label fromLabel11;

    @FXML
    private JFXTextField stateInput;

    @FXML
    private Label toLabel;

    @FXML
    private JFXComboBox<String> addressFill;

    @FXML
    private JFXButton submitButton;

    @FXML
    private JFXTextArea directionSpace;

    String allDirFran = "";
    String allDirWhit = "";
    String lowDir = "";

    @FXML
    private JFXComboBox<String> streetEnding;
    PlaceAutocompleteRequest.SessionToken token;
    GeoApiContext context;

    @FXML
    private Text errorMes;
    @FXML
    private ImageView imageBox;
    //DefaultPage;

    String chosenPark = "";

    @FXML
    private JFXButton printButton;
  static  DefaultPage defaultPage =SceneManager.getInstance().getDefaultPage();
   static ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();

    public void start(Stage stage) throws Exception {}
//    public GoogleMapForm(MapDisplay mapDisplay){
//
//        this.mapDisplay =mapDisplay;
//    }

 @FXML
    public void initialize() {
//        travelMode.getItems().add("driving");
//        travelMode.getItems().add("bicycling");
//        travelMode.getItems().add("walking");
//        streetEnding.getItems().add("St");
//        streetEnding.getItems().add("Dr");
//        streetEnding.getItems().add("Rd");
//        streetEnding.getItems().add("St");
//        streetEnding.getItems().add("Ave");
//        streetEnding.getItems().add("Blvd");
//        streetEnding.getItems().add("Cir");
//        streetEnding.getItems().add("Ln");
     directionSpace.setVisible(false);
     errorMes.setVisible(false);
     addressFill.setDisable(true);
        context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDsCE050FgQ8Q0VnfBP5XymPyTlWLht_88")
                .build();
        token = new PlaceAutocompleteRequest.SessionToken();
//        mapDisplay = new MapDisplay();
     displayParkingSpots();
     defaultPage.initGoogleForm();

    }

    @FXML
    void submit() throws URISyntaxException, IOException, InterruptedException, ApiException, PrinterException {
        directionSpace.setText("");
        lowDir = "";
        allDirFran = "";
        allDirWhit = "";
        Stage stage = new Stage();
         Duration durationFran = new Duration();
        Duration durationWhit = new Duration();
       // String URL = "https://www.google.com/maps/dir/?api=1&origin=" + numInput.getText() + "+" + streetInput.getText() +
            //    "+" + streetEnding.getValue() + "+" + townInput.getText() + "+" + stateInput.getText() + "&destination=75+Francis+St+Boston+MA&key=" ;
       // URI link = new URI(URL);
//        String origin = numInput.getText() + " " + streetInput.getText() + " " + streetEnding.getValue()
//                + ", " + townInput.getText() + " " + stateInput.getText();
        String origin = addressFill.getValue();
        DirectionsResult results =  DirectionsApi.getDirections(context, origin, "75 Francis Street, Boston MA").await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        DirectionsLeg[] feet = results.routes[0].legs;

        for (DirectionsLeg foot : feet) {
//            for (DirectionsStep step : foot.steps) {
//
//                String newStep = cleanTags(step.htmlInstructions);
//                System.out.println(newStep);
//                allDirFran = allDirFran + newStep + "\n";
//
//            }
            durationFran = foot.duration;
        }
        DirectionsResult results2 = DirectionsApi.getDirections(context, origin, "15 New Whitney St, Boston MA").await();
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        DirectionsLeg[] feet2 = results2.routes[0].legs;

        for (DirectionsLeg foot : feet2) {
//            for (DirectionsStep step : foot.steps) {
//
//                String newStep = cleanTags(step.htmlInstructions);
//                System.out.println(newStep);
//                allDirWhit = allDirWhit + newStep + "\n";
//
//            }
             durationWhit = foot.duration;
        }
        if (durationFran.inSeconds < durationWhit.inSeconds) {
            System.out.println("Francis shorter");
            for (DirectionsLeg foot : feet) {
                for (DirectionsStep step : foot.steps) {

                    String newStep = cleanTags(step.htmlInstructions);
                    System.out.println(step.htmlInstructions);
                    lowDir = lowDir + newStep + "\n";

                }

            }
            chosenPark = "75 Francis Street, Boston MA";
        } else {
            System.out.println("Whitney shorter");
            for (DirectionsLeg foot : feet2) {
                for (DirectionsStep step : foot.steps) {

                    String newStep = cleanTags(step.htmlInstructions);
                    System.out.println(step.htmlInstructions);
                    lowDir = lowDir + "\n" + newStep;

                }
                String durationFWhit = foot.duration.toString();
            }
            chosenPark = "15 New Whitney St, Boston MA";
        }
        directionSpace.setVisible(true);
        directionSpace.setText(lowDir);
        Size size = new Size(500,400);
        StaticMapsRequest request = StaticMapsApi.newRequest(context, size);
        request.center(origin);
        request.scale(2);
        request.format(StaticMapsRequest.ImageFormat.png32);
        request.maptype(StaticMapsRequest.StaticMapType.terrain);
        List<LatLng> decodedPath = results.routes[0].overviewPolyline.decodePath();
        StaticMapsRequest.Path path = new StaticMapsRequest.Path();
        //path.color("blue");
        decodedPath.forEach(p -> {
           path.addPoint(p);
        });
        //path.addPoint(origin);
       // path.addPoint(chosenPark);
        //path.fillcolor("red");
        request.path(path);
       // request.
        StaticMapsRequest.Markers depMarker = new StaticMapsRequest.Markers();
        StaticMapsRequest.Markers destMarker = new StaticMapsRequest.Markers();
        destMarker.color("green");

        depMarker.addLocation(new LatLng(results.routes[0].legs[0].startLocation.lat,results.routes[0].legs[0].startLocation.lng));
        destMarker.addLocation(new LatLng(results.routes[0].legs[0].endLocation.lat,results.routes[0].legs[0].endLocation.lng));
       // request.markers( new StaticMapsRequest.Markers().addLocation(new LatLng(results.routes[0].legs[0].startLocation.lat,results.routes[0].legs[0].startLocation.lng)));
        request.markers(depMarker);
        request.markers(destMarker);

        ByteArrayInputStream bais = new ByteArrayInputStream(request.await().imageData);
        Image img = new Image(bais);
        imageBox.setImage(img);



    }
    @FXML
    void arrived(){
        defaultPage.getPopPop().setPrefWidth(657);
        defaultPage.clearMap(); // Clear map
        defaultPage.getPopPop().setPrefWidth(657.0); // Set preferable width to 657
        LoadFXML.getInstance().loadWindow("COVIDSurvey", "surveyBar", defaultPage.getPopPop()); // Load registration window
    }
    @FXML
    void print() throws PrinterException {
        if (lowDir.equals("")) {
            errorMes.setVisible(true);
        } else {
            errorMes.setVisible(false);
            JTextArea YourTextArea = new JTextArea();
            YourTextArea.setLineWrap(true);
            YourTextArea.append(lowDir);
            YourTextArea.print();
        }
    }




    //aDesktop.browse(link);
//        WebView view = new WebView();
//        WebEngine engine = view.getEngine();
//        engine.load(URL);
//        VBox box = new VBox();
//        box.getChildren().addAll(view);
//        Scene scene = new Scene(box, 1000,500);
//        stage.setScene(scene);
//        stage.show();


    public String cleanTags(String s) {

        s = s.replaceAll("<[^>]*>", "");
        s = s.replaceAll("Destination", "\nDestination");
        lowDir = lowDir.replaceAll("&nbsp;","");
        return s;
    }

    public void lookUp(){
        addressFill.setDisable(false);
        String input = numInput.getText();
       // System.out.println(input);
        List<String> results = getAddresses(input) ;
        results.forEach(n->{
            if(addressFill.getItems().contains(n)){
                return;}
            addressFill.getItems().add(n);
            if( addressFill.getItems().size()>3){
                addressFill.getItems().remove(0);
            }


        });
    }


    public List <String> getAddresses(String lookup){
        AutocompletePrediction[] autocompletePredictions = PlacesApi.placeAutocomplete(context,lookup, token).awaitIgnoreError();
        List<String>  results = new ArrayList<>();
        if(autocompletePredictions!=null){
            //System.out.println("not null");
        for (int i = 0; i < autocompletePredictions.length; i++) {
            results.add(autocompletePredictions[i].description);
        }}
        return results;
    }


     public static void displayParkingSpots(){
         System.out.println(LoadFXML.getCurrentWindow());
        nodes.forEach(n ->{
            if (n.getNodeType().equals("PARK")){
                Circle circle = new Circle(defaultPage.xCoordOnTopElement(n.getX()), defaultPage.yCoordOnTopElement(n.getY()), 8); // New node/cicle
                circle.setStrokeWidth(4);
                circle.setFill(Color.valueOf("145c0a")); // Set node color to olive
               defaultPage.topElements.getChildren().add(circle);
             //   System.out.println(defaultPage.xCoordOnTopElement(n.getX()));
            }
        });
     }
}