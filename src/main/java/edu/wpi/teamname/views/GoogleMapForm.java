package edu.wpi.teamname.views;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.maps.DirectionsApi;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsStep;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.json.*;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
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


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

    @FXML
    private JFXComboBox<String> streetEnding;

    public void start(Stage stage) throws Exception{


    }

    public void initialize() {
        travelMode.getItems().add("driving");
        travelMode.getItems().add("bicycling");
        travelMode.getItems().add("walking");
        streetEnding.getItems().add("St");
        streetEnding.getItems().add("Dr");
        streetEnding.getItems().add("Rd");
        streetEnding.getItems().add("St");
        streetEnding.getItems().add("Ave");
        streetEnding.getItems().add("Blvd");
        streetEnding.getItems().add("Cir");
        streetEnding.getItems().add("Ln");
    }

    @FXML
    void submit() throws URISyntaxException, IOException, InterruptedException, ApiException {
        Stage stage = new Stage();

       // String URL = "https://www.google.com/maps/dir/?api=1&origin=" + numInput.getText() + "+" + streetInput.getText() +
            //    "+" + streetEnding.getValue() + "+" + townInput.getText() + "+" + stateInput.getText() + "&destination=75+Francis+St+Boston+MA&key=" ;
       // URI link = new URI(URL);
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCsYyTEAMqOBjOpG84HSCXDUJ7iQsNF1bA")
                .build();
        DirectionsResult results =  DirectionsApi.getDirections(context, "24 Delaney Drive, Walpole MA 02081", "75 Francis Street, Boston MA").await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
       // System.out.println(gson.toJson(results.routes[0].legs));
        DirectionsLeg[] feet = results.routes[0].legs;
        for (DirectionsLeg foot: feet) {
            for (DirectionsStep step: foot.steps) {

               String newStep = cleanTags(step.htmlInstructions);
                System.out.println(newStep);


            }
            System.out.println("start location " + foot.startLocation);
            System.out.println("end location " + foot.endLocation);
            System.out.println("duration " + foot.duration);


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

    }
    public String cleanTags(String s){


        s = s.replaceAll("<[^>]*>", "");
        return s;
    }

}