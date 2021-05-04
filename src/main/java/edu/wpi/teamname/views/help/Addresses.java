package edu.wpi.teamname.views.help;

import com.google.maps.GeoApiContext;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.AutocompletePrediction;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Addresses{
private static final Addresses ourInstance = new Addresses();
    PlaceAutocompleteRequest.SessionToken token;
    GeoApiContext context;
    private JFXTextField addressInput;
    private JFXComboBox destination;
    public Addresses(){}

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//        primaryStage.setTitle("VBox Experiment 1");
//
//        destination = new JFXComboBox();
//        addressInput = new JFXTextField();
//        VBox vbox = new VBox(addressInput, destination);
//
//        Scene scene = new Scene(vbox, 2000, 1000);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    public static void main(String[] args) {
        Application.launch(args);
    }



    public static synchronized Addresses getInstance(){return ourInstance;}


     public List <String> getAddresses(String lookup){
        AutocompletePrediction[] autocompletePredictions =
                PlacesApi.placeAutocomplete(context,lookup, token).awaitIgnoreError();
         List<String>  results = new ArrayList<>();
         for (int i = 0; i < autocompletePredictions.length; i++) {
             results.add(autocompletePredictions[i].description);
         }
         return results;
    }




}
