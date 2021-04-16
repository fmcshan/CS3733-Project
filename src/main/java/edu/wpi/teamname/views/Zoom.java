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

    public static Point2D viewportToImage(ImageView inputMap, Point2D mapCoordinates) {
        Bounds bounds = inputMap.getBoundsInLocal();
        double xProportion = mapCoordinates.getX() / bounds.getWidth();
        double yProportion =mapCoordinates.getY() / bounds.getHeight();

        Rectangle2D viewport = inputMap.getViewport();
        return new Point2D(viewport.getMinX() + xProportion * viewport.getWidth(), viewport.getMinY() + yProportion * viewport.getHeight());
    }

    private static double calculateEnsureRange(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    private static boolean inRange(double value, double min, double max) {
        return (value>= min) && (value<= max);
    }

    private static double ensureRange(double value, double min, double max) {
        boolean isInRange = inRange(value, min, max);
        double returnVal = -1.0;

        if (isInRange){
            returnVal = calculateEnsureRange(value, min, max);
        } else {
            system.out.println("You messed up");
            returnVal = calculateEnsureRange(value, min, max);
        }

        return returnVal;
    }

    public static void shiftedImage(ImageView inputMap, Point2D changeInShift) {
        Rectangle2D theViewPort = inputMap.getViewport();

        //Extracting the image's height and width
        double imageWidth = inputMap.getImage().getWidth();
        double imageHeight = inputMap.getImage().getHeight();

        double viewportMaxWidth = imageWidth - theViewPort.getWidth();
        double viewportMaxHeight = imageHeight - theViewPort.getHeight();

        double viewportMinXCoord = theViewPort.getMinX();
        double viewportMinYCoord = theViewPort.getMinY();

        double changeInX = changeInShift.getX();
        double changeInY = changeInShift.getY();

        double viewportMinWidth = ensureRange(viewportMinXCoord - changeInX, 0, viewportMaxWidth);
        double viewportMinHeight = ensureRange(viewportMinYCoord - changeInY, 0, viewportMaxHeight);

        inputMap.setViewport(new Rectange2D());
    }

}
