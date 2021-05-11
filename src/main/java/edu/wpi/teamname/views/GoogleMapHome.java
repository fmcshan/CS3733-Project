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
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.effect.BlurType.GAUSSIAN;

public class GoogleMapHome {

    @FXML
    private JFXComboBox<String> addressFill;
    @FXML
    private Text errorMes;
    @FXML
    private ImageView imageBox;
    @FXML
    private VBox navBox;

    String allDirFran = "";
    String allDirWhit = "";
    String lowDir = "";
    PlaceAutocompleteRequest.SessionToken token;
    GeoApiContext context;
    String chosenPark = "";

    static DefaultPage defaultPage = SceneManager.getInstance().getDefaultPage();
    static ArrayList<Node> nodes = LocalStorage.getInstance().getNodes();
    private HashMap<String, String> items = new HashMap<>();

    @FXML
    public void initialize() {
        errorMes.setVisible(false);
        context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDsCE050FgQ8Q0VnfBP5XymPyTlWLht_88")
                .build();
        token = new PlaceAutocompleteRequest.SessionToken();
        defaultPage.initGoogleForm();
        addressFill.setEditable(true);

    }

    @FXML
    void submit() throws URISyntaxException, IOException, InterruptedException, ApiException, PrinterException {

        if ((items.containsKey(addressFill.getSelectionModel().getSelectedItem()))) {
            lowDir = "";
            allDirFran = "";
            allDirWhit = "";
            Duration durationFran = new Duration();
            Duration durationWhit = new Duration();
            String origin = addressFill.getValue();
            DirectionsResult results = DirectionsApi.getDirections(context, "75 Francis Street, Boston MA", origin).await();
            DirectionsLeg[] feet = results.routes[0].legs;

            for (DirectionsLeg foot : feet) {
                durationFran = foot.duration;
            }
            DirectionsResult results2 = DirectionsApi.getDirections(context, "15 New Whitney St, Boston MA", origin).await();
            DirectionsLeg[] feet2 = results2.routes[0].legs;

            for (DirectionsLeg foot : feet2) {
                durationWhit = foot.duration;
            }

            if (durationFran.inSeconds < durationWhit.inSeconds) {
                System.out.println("Francis shorter");
                for (DirectionsLeg foot : feet) {
                    for (DirectionsStep step : foot.steps) {
                        String newStep = cleanTags(step.htmlInstructions);
                        navBox.getChildren().add(generateNavElem(cleanTags(newStep)));
                        lowDir = lowDir + newStep + "\n";
                        VBox spacer = new VBox();
                        spacer.setPrefSize(1, 10);
                        spacer.setMinSize(1, 10);
                        navBox.getChildren().add(spacer);
                    }
                }
                chosenPark = "75 Francis Street, Boston MA";
            } else {
                System.out.println("Whitney shorter");
                for (DirectionsLeg foot : feet2) {
                    for (DirectionsStep step : foot.steps) {
                        String newStep = cleanTags(step.htmlInstructions);
                        System.out.println("hi 2");
                        newStep.replace("&nbsp;", " ");
                        navBox.getChildren().add(generateNavElem(cleanTags(newStep)));
                        lowDir = lowDir + "\n" + newStep;
                        VBox spacer = new VBox();
                        spacer.setPrefSize(1, 10);
                        spacer.setMinSize(1, 10);
                        navBox.getChildren().add(spacer);
                    }
                }
                chosenPark = "15 New Whitney St, Boston MA";
            }

            Size size = new Size(500, 400);
            StaticMapsRequest request = StaticMapsApi.newRequest(context, size);
            request.center(origin);
            request.scale(2);
            request.format(StaticMapsRequest.ImageFormat.png32);
            request.maptype(StaticMapsRequest.StaticMapType.terrain);
            List<LatLng> decodedPath = results.routes[0].overviewPolyline.decodePath();
            StaticMapsRequest.Path path = new StaticMapsRequest.Path();
            decodedPath.forEach(p -> {
                path.addPoint(p);
            });

            request.path(path);
            StaticMapsRequest.Markers depMarker = new StaticMapsRequest.Markers();
            StaticMapsRequest.Markers destMarker = new StaticMapsRequest.Markers();
            destMarker.color("green");

            depMarker.addLocation(new LatLng(results.routes[0].legs[0].startLocation.lat, results.routes[0].legs[0].startLocation.lng));
            destMarker.addLocation(new LatLng(results.routes[0].legs[0].endLocation.lat, results.routes[0].legs[0].endLocation.lng));
            request.markers(depMarker);
            request.markers(destMarker);

            ByteArrayInputStream bais = new ByteArrayInputStream(request.await().imageData);
            Image img = new Image(bais);
            imageBox.setImage(img);
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

    public String cleanTags(String s) {
        s = s.replaceAll("<[^>]*>", "");
        s = s.replaceAll("Destination", "\nDestination");
        s = s.replace("&nbsp;", " ");
        return s;
    }

    public void lookUp() {
        String input = addressFill.getEditor().getText();
        List<String> results = getAddresses(input);
        results.forEach(n -> {
            if (addressFill.getItems().contains(n)) {
                return;
            }
            addressFill.getItems().add(n);
            items.put(n, n);
            addressFill.show();

            if (addressFill.getItems().size() > 3) {
                addressFill.getItems().remove(0);
            }
        });
    }


    public List<String> getAddresses(String lookup) {
        AutocompletePrediction[] autocompletePredictions = PlacesApi.placeAutocomplete(context, lookup, token).awaitIgnoreError();
        List<String> results = new ArrayList<>();
        if (autocompletePredictions != null) {
            for (int i = 0; i < autocompletePredictions.length; i++) {
                results.add(autocompletePredictions[i].description);
            }
        }
        return results;
    }

    public HBox generateNavElem(String _direction) {
        String directionText = _direction.toLowerCase();
        HBox directionGuiWrapper = new HBox();
        directionGuiWrapper.setStyle("-fx-background-color: #fafafa; -fx-background-radius: 10px; -fx-margin: 0 0 0 0;");
        DropShadow shadow = new DropShadow();
        shadow.setBlurType(GAUSSIAN);
        shadow.setSpread(0.33);
        shadow.setColor(Color.valueOf("#ebebeb"));
        directionGuiWrapper.setEffect(shadow);
        directionGuiWrapper.setMaxWidth(445);

        VBox navIconWrapper = new VBox();
        navIconWrapper.setStyle("-fx-background-color: #317fb8; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 4 0 0 4;");
        navIconWrapper.setPrefSize(64, 64);
        navIconWrapper.setMinSize(64, 64);
        MaterialDesignIconView navigationIcon;
        if (directionText.contains("straight") || directionText.contains("head")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ARROW_UP_BOLD);
        } else if (directionText.contains("left")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ARROW_LEFT_BOLD);
        } else if (directionText.contains("right")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ARROW_RIGHT_BOLD);
        } else if (directionText.contains("stair")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.STAIRS);
        } else if (directionText.contains("elevator")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ARROW_UP_BOLD_CIRCLE_OUTLINE);
        } else if (directionText.contains("escalator")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ESCALATOR);
        } else if (directionText.contains("destination")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.MAP_MARKER);
        } else if (directionText.contains("ferry") && directionText.contains("take")) {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.FERRY);
        } else {
            navigationIcon = new MaterialDesignIconView(MaterialDesignIcon.ARROW_UP_BOLD);
        }

        navigationIcon.setFill(Paint.valueOf("#ffffff"));
        navigationIcon.setGlyphSize(56);
        navIconWrapper.getChildren().add(navigationIcon);

        VBox spacer = new VBox();
        spacer.setPrefSize(10, 1);
        spacer.setMinSize(10, 1);

        VBox navLabelWrapper = new VBox();
        Text navigationLabel = new Text(_direction);
        navigationLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-padding: 10 0 0 10;");
        navigationLabel.setWrappingWidth(400);
        navLabelWrapper.getChildren().add(navigationLabel);
        navigationLabel.prefWidth(200);
        navigationLabel.prefHeight(60);

        directionGuiWrapper.getChildren().add(navIconWrapper);
        directionGuiWrapper.getChildren().add(spacer);
        directionGuiWrapper.getChildren().add(navLabelWrapper);
        return directionGuiWrapper;
    }
}