package edu.wpi.teamname.views;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Zoom {

    public static void getHospitalMap(ImageView map){
        double width = map.getFitWidth(); //get the width associated with the width
        double height = map.getFitHeight(); //get the height associated with the height

        map.setPreserveRatio(false); //make sure that the image (the map) is bound to its original image dimensions (aka the aspect ratio)

        ObjectProperty<Point2D> mouseClickDown = new SimpleObjectProperty<>();

        map.setOnMousePressed(mouseEvent -> {
            Point2D pointOfMouseClick = viewportToImage(map, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            mouseClickDown.set(pointOfMouseClick);
        });

        map.setOnMouseDragged(mouseEvent -> {
            Point2D pointToDragFrom = viewportToImage(map, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            Point2D valueOfShift = pointToDragFrom.subtract(mouseClickDown.get());
            shiftedImage(map, valueOfShift);
            mouseClickDown.set(viewportToImage(map, new Point2D(mouseEvent.getX(), mouseEvent.getY())));
        });



    }
}
