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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class GoogleMapHome {

    @FXML
    private Label title;

    @FXML
    private Text description;

    @FXML
    private Label fromLabel;

    @FXML
    private JFXTextField numInput;

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
    private JFXComboBox<String> selectParking;

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


    String chosenPark = "";

    @FXML
    private JFXButton printButton;

    public void start(Stage stage) throws Exception {


    }
    @FXML
    public void initialize() {
         selectParking.getItems().add("75 Francis Street, Boston MA");
        selectParking.getItems().add("15 New Whitney St, Boston MA");
//        travelMode.getItems().add("driving");e
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
        numInput.setDisable(true);
        directionSpace.setVisible(false);
        errorMes.setVisible(false);
        addressFill.setDisable(true);
        context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDsCE050FgQ8Q0VnfBP5XymPyTlWLht_88")
                .build();
        token = new PlaceAutocompleteRequest.SessionToken();
    }

    @FXML
    void submit() throws URISyntaxException, IOException, InterruptedException, ApiException, PrinterException {
        Stage stage = new Stage();
        Duration durationFran = new Duration();
        Duration durationWhit = new Duration();
        // String URL = "https://www.google.com/maps/dir/?api=1&origin=" + numInput.getText() + "+" + streetInput.getText() +
        //    "+" + streetEnding.getValue() + "+" + townInput.getText() + "+" + stateInput.getText() + "&destination=75+Francis+St+Boston+MA&key=" ;
        // URI link = new URI(URL);
//        String origin = numInput.getText() + " " + streetInput.getText() + " " + streetEnding.getValue()
//                + ", " + townInput.getText() + " " + stateInput.getText();
        String origin = addressFill.getValue();
        DirectionsResult results =  DirectionsApi.getDirections(context, "75 Francis Street, Boston MA", origin).await();
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
        DirectionsResult results2 = DirectionsApi.getDirections(context, "15 New Whitney St, Boston MA", origin).await();
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
        if (selectParking.getValue().equals("75 Francis Street, Boston MA")) {
            System.out.println("Francis");
            for (DirectionsLeg foot : feet) {
                for (DirectionsStep step : foot.steps) {

                    String newStep = cleanTags(step.htmlInstructions);
                    System.out.println(newStep);
                    lowDir = lowDir + newStep + "\n";
                }

            }
            chosenPark = "75 Francis Street, Boston MA";
        } else {
            System.out.println("Whitney");
            for (DirectionsLeg foot : feet2) {
                for (DirectionsStep step : foot.steps) {

                    String newStep = cleanTags(step.htmlInstructions);
                    System.out.println(newStep);
                    lowDir = lowDir + newStep + "\n";
                }
                String durationFWhit = foot.duration.toString();
            }
            chosenPark = "15 New Whitney St, Boston MA";
        }
        lowDir = lowDir.replaceAll("&nbsp;","");
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
    void enableSearch(){
        numInput.setDisable(false);
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
        return s;
    }

    public void lookUp(){
        addressFill.setDisable(false);
        String input = numInput.getText();
        // System.out.println(input);
        List<String> results = getAddresses(input) ;
        results.forEach(n->{
            addressFill.getItems().add(n);
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

}