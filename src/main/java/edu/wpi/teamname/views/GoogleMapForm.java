package edu.wpi.teamname.views;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.maps.DirectionsApi;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.*;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsStep;
import com.google.maps.GeoApiContext;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.json.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodingResult;
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


import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
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
    private JFXComboBox<String> travelMode;

    @FXML
    private JFXButton submitButton;

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
    private JFXButton printButton;

    public void start(Stage stage) throws Exception {


    }
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
        context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCZVPvXk5oKKZvJKEEe6uaBmA8FuzzgbJg")
                .build();
        token = new PlaceAutocompleteRequest.SessionToken();
    }

    @FXML
    void submit() throws URISyntaxException, IOException, InterruptedException, ApiException, PrinterException {
        Stage stage = new Stage();
        String durationFran = "";
        String durationWhit = "";

       // String URL = "https://www.google.com/maps/dir/?api=1&origin=" + numInput.getText() + "+" + streetInput.getText() +
            //    "+" + streetEnding.getValue() + "+" + townInput.getText() + "+" + stateInput.getText() + "&destination=75+Francis+St+Boston+MA&key=" ;
       // URI link = new URI(URL);
        String origin = numInput.getText() + " " + streetInput.getText() + " " + streetEnding.getValue()
                + ", " + townInput.getText() + " " + stateInput.getText();
        DirectionsResult results =  DirectionsApi.getDirections(context, "24 Delaney Drive, Walpole MA 02081", "75 Francis Street, Boston MA").await();
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
            durationFran = foot.duration.toString();
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
             durationWhit = foot.duration.toString();
        }
        durationFran = durationFran.replaceAll(" mins", "");
        durationWhit = durationWhit.replaceAll(" mins", "");
        System.out.println("duration Francis: " + durationFran + " durantion Whitney " + durationWhit);
        if (Integer.parseInt(durationFran) < Integer.parseInt(durationWhit)) {
            System.out.println("Francis shorter");
            for (DirectionsLeg foot : feet) {
                for (DirectionsStep step : foot.steps) {

                    String newStep = cleanTags(step.htmlInstructions);
                    System.out.println(newStep);
                    lowDir = lowDir + newStep + "\n";

                }

            }
        } else {
            System.out.println("Whitney shorter");
            for (DirectionsLeg foot : feet2) {
                for (DirectionsStep step : foot.steps) {

                    String newStep = cleanTags(step.htmlInstructions);
                    System.out.println(newStep);
                    lowDir = lowDir + newStep + "\n";

                }
                String durationFWhit = foot.duration.toString();
            }
        }
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
        String input = numInput.getText();
        System.out.println(input);
        List<String> results = getAddresses(input) ;
        results.forEach(n->{
            travelMode.getItems().add(n);
        });
    }


    public List <String> getAddresses(String lookup){
        AutocompletePrediction[] autocompletePredictions = PlacesApi.placeAutocomplete(context,lookup, token).awaitIgnoreError();
        List<String>  results = new ArrayList<>();
        if(autocompletePredictions!=null){
            System.out.println("not null");
        for (int i = 0; i < autocompletePredictions.length; i++) {
            results.add(autocompletePredictions[i].description);
        }}
        return results;
    }

}