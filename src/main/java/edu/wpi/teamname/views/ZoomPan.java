package edu.wpi.teamname.views;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.*;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.apache.derby.iapi.services.io.FormatableHashtable;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class ZoomPan {
    private static double viewportOfImageWidth;
    private static double viewportOfImageHeight;

    public static double getViewportOfImageWidth(){
        return viewportOfImageWidth;
    }

    public static double getViewportOfImageHeight(){
        return viewportOfImageHeight;
    }

    public static void getHospitalMap(ImageView hospitalMap, AnchorPane inputTopElements, double width, double height){
        //get the height associated with the height
        hospitalMap.setPreserveRatio(true); //make sure that the image (the hospitalMap) is bound to its original image dimensions (aka the aspect ratio)
        reset(hospitalMap, width, height);
        double fileWidth = hospitalMap.getImage().getWidth();
        double fileHeight = hospitalMap.getImage().getHeight();

        ObjectProperty<Point2D> mouseClickDown = new SimpleObjectProperty<>();

        inputTopElements.setOnMousePressed(mouseEvent -> {
            Point2D pointOfMouseClick = viewportToImageView(hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            mouseClickDown.set(pointOfMouseClick);
        });

        inputTopElements.setOnMouseDragged(mouseEvent -> {
            Point2D pointToDragFrom = viewportToImageView(hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            Point2D valueOfShift = pointToDragFrom.subtract(mouseClickDown.get());
            //System.out.println("pointToDragFrom" + pointToDragFrom);
            //System.out.println("valueOfShift" + valueOfShift);
            shiftedImage(hospitalMap, valueOfShift, inputTopElements);
            mouseClickDown.set(viewportToImageView(hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY())));
        });

        inputTopElements.setOnScroll(mouseEvent ->  {
            double getDifference = -mouseEvent.getDeltaY();
            System.out.println("getDifference: " + getDifference);
            Rectangle2D viewportOfImage = hospitalMap.getViewport();

            double scaleDifference = Math.pow(1.01, getDifference);
            System.out.println("scaleDifference: " + scaleDifference);
            double minPixels = 10;
            //viewportOfImageWidth = viewportOfImage.getWidth();
            //viewportOfImageHeight = viewportOfImage.getHeight();
            System.out.println("viewportOfImageWidth: " + viewportOfImageWidth);
            System.out.println("viewportOfImageHeight: " + viewportOfImageHeight);


            double lowestBoundaryWidth = minPixels / viewportOfImage.getWidth();
            double lowestBoundaryHeight = minPixels / viewportOfImage.getHeight();
            double minimumZoomScale = Math.min(lowestBoundaryWidth, lowestBoundaryHeight);

            double highestBoundaryWidth = width / viewportOfImage.getWidth();
            double highestBoundaryHeight = height / viewportOfImage.getHeight();
            double maximumZoomScale = Math.min(highestBoundaryWidth, highestBoundaryHeight);

            double boundariesOfViewPort = ensureRange(scaleDifference, minimumZoomScale, maximumZoomScale);
            System.out.println("boundariesOfViewPort: " + boundariesOfViewPort);

            Point2D mouseCursorLocationOnMap = viewportToImageView(hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            //System.out.println("mouseCursorLocationOnMap" + mouseCursorLocationOnMap);
            //System.out.println("scaleDifference" + scaleDifference);

            double scaledWidth = viewportOfImage.getWidth() * boundariesOfViewPort;
            double scaledHeight = viewportOfImage.getHeight() * boundariesOfViewPort;

            double minXValueOfMouseClick = mouseCursorLocationOnMap.getX() - ((mouseCursorLocationOnMap.getX() - viewportOfImage.getMinX()) * boundariesOfViewPort);
            double minYValueOfMouseClick = mouseCursorLocationOnMap.getY() - ((mouseCursorLocationOnMap.getY() - viewportOfImage.getMinY()) * boundariesOfViewPort);
            System.out.println("minXValueOfMouseClick" + minXValueOfMouseClick);
            System.out.println("minYValueOfMouseClick" + minYValueOfMouseClick);

            double widthDifferenceBetweenScaledAndNormal = width - scaledWidth;
            double heightDifferenceBetweenScaledAndNormal = height - scaledHeight;

            double scaledMinWidth = ensureRange(minXValueOfMouseClick, 0, widthDifferenceBetweenScaledAndNormal);
            double scaledMinHeight = ensureRange(minYValueOfMouseClick, 0, heightDifferenceBetweenScaledAndNormal);

//
            Rectangle2D newViewPort = new Rectangle2D(scaledMinWidth, scaledMinHeight, scaledWidth, scaledHeight);

            double widthRatio = width / fileWidth;
            double heightRatio = height / fileHeight;

            hospitalMap.setViewport(newViewPort);
            inputTopElements.getChildren().clear();
            displayNodes(inputTopElements, hospitalMap, scaledMinWidth, scaledMinHeight, scaledWidth, scaledHeight);
        });


        inputTopElements.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                reset(hospitalMap, width, height);
            }
        });
    }

    private static void reset(ImageView map, double width, double height) {
        Rectangle2D newViewPort = new Rectangle2D(0, 0, width, height);
        map.setViewport(newViewPort);
    }

    public static Point2D viewportToImageView(ImageView inputMap, Point2D mapCoordinates) {
        Bounds bounds = inputMap.getBoundsInLocal();
        double xProportion = mapCoordinates.getX() / bounds.getWidth();
        double yProportion = mapCoordinates.getY() / bounds.getHeight();

        Rectangle2D viewport = inputMap.getViewport();
        return new Point2D(viewport.getMinX() + xProportion * viewport.getWidth(), viewport.getMinY() + yProportion * viewport.getHeight());
    }

    private static double ensureRange(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    public static void shiftedImage(ImageView inputMap, Point2D changeInShift, AnchorPane topElements) {
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

        inputMap.setViewport(new Rectangle2D(viewportMinWidth, viewportMinHeight, theViewPort.getWidth(), theViewPort.getHeight()));

        topElements.getChildren().clear();
        displayNodes(topElements, inputMap, viewportMinWidth, viewportMinHeight, theViewPort.getWidth(), theViewPort.getHeight());

    }

    //HashMap<String, Node> map = new HashMap();
    static ArrayList<Node> nodes = PathFindingDatabaseManager.getInstance().getNodes();
    public static void displayNodes(AnchorPane topElements, ImageView hospitalMap, double scaledX, double scaledY, double scaledWidth, double scaledHeight) {

        System.out.println("got here");
        //rezisingInfo();
        for (Node n : nodes) {
            //map.put(n.getNodeID(), n);
            //   System.out.println(n.getNodeType());
            double mapWidth = 1000.0;
            double mapHeight = 680.0;
            double fileWidth = 5000.0;
            double fileHeight = 3400.0;
            //double getScreenX = mouseEvent.getSceneX();//Math.pow(1.01, -mouseScrollVal);
            double widthScale = scaledWidth / fileWidth;
            double heightScale = scaledHeight / fileHeight;
            double windowWidth = hospitalMap.getFitWidth() / fileWidth;
            double windowHeight = hospitalMap.getFitHeight() / fileHeight;
            double windowSmallestScale = Math.max(Math.min(windowHeight, windowWidth), 0);
            double viewportSmallestScale = Math.max(Math.min(heightScale, widthScale), 0);
            double compareWidthAndHeight = Math.min(hospitalMap.getFitWidth() * (fileHeight/fileWidth), hospitalMap.getFitHeight());
            System.out.println("windowSmallestScale: " + windowSmallestScale);
            System.out.println("viewportSmallestScale: " + viewportSmallestScale);

            System.out.println("scaledWidth: " + scaledWidth);
            System.out.println("scaledHeight: " + scaledHeight);

            System.out.println("windowWidth: " + windowWidth);
            System.out.println("windowHeight: " + windowHeight);

            System.out.println("hospitalMap.getFitWidth(): " + hospitalMap.getFitWidth());
            System.out.println("hospitalMap.getFitHeight(): " + hospitalMap.getFitHeight());
            //double scale = Math.pow(1.01, mouseScrollVal);

            double fileRatio = fileHeight / fileWidth;

            double nodeX = n.getX();
            double nodeY = n.getY();

            System.out.println("nodeX: " + nodeX);
            System.out.println("nodeY: " + nodeY);

            //System.out.println("scale: " + scale);
            System.out.println("scaledX: " + scaledX);
            System.out.println("scaledY: " + scaledY);

            double widthRatio = mapWidth / fileWidth;
            double heightRatio = mapHeight / fileHeight;
            System.out.println("widthRatio: " + widthRatio);


            double weightedNodeX;
            double weightedNodeY;

            weightedNodeX = ((n.getX() - scaledX) / viewportSmallestScale) * windowSmallestScale;
            weightedNodeY = ((n.getY() - scaledY) / viewportSmallestScale) * windowSmallestScale;

            System.out.println("weightedNodeX: " + weightedNodeX);
            System.out.println("weightedNodeY: " + weightedNodeY);

            double lowerXBound = 0;
            double upperXBound = compareWidthAndHeight * (fileWidth/fileHeight);

            double lowerYBound = 0;
            double upperYBound = compareWidthAndHeight;

            System.out.println("lowerXBound: " + lowerXBound);
            System.out.println("upperXBound: " + upperXBound);

            System.out.println("lowerYBound: " + lowerYBound);
            System.out.println("upperYBound: " + upperYBound);

            if ((lowerXBound <= weightedNodeX && weightedNodeX <= upperXBound) && (lowerYBound <= weightedNodeY && weightedNodeY <= upperYBound)) {
            Circle circle = new Circle(weightedNodeX, weightedNodeY, 8);
            circle.setFill(Color.OLIVE);
            topElements.getChildren().add(circle);
            }
        }
    }

}