package edu.wpi.teamname.views;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Zoom {

    public static void getHospitalMap(ImageView map){
        double width = map.getFitWidth(); //get the width associated with the width
        double height = map.getFitHeight(); //get the height associated with the height

        map.setPreserveRatio(false); //make sure that the image (the map) is bound to its original image dimensions (aka the aspect ratio)

        ObjectProperty<Point2D> mouseClickDown = new SimpleObjectProperty<>();

    }
}
