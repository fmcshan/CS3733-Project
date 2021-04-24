package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class ZoomAndPan {
    MapDisplay page;
    double scaledWidth = 5000;
    double scaledHeight = 3400.0;
    double scaledX;
    double scaledY;
    ArrayList<Node> _listOfNodes;

    public ZoomAndPan(MapDisplay page){
        this.page = page;
        //scaledX = page.scaledY;
        //scaledY = page.scaledY;
        _listOfNodes = page.listOfNodes;
    }

    public void zoomAndPan() {
        //ImageView hospitalMap = page.hospitalMap;
        //double width = page.mapWidth;
        //double height = page.mapHeight;
        //AnchorPane inputTopElements = page.onTopOfTopElements;

        // hMap = hospitalMap;
        //get the height associated with the height
        page.hospitalMap.setPreserveRatio(true); //make sure that the image (the hospitalMap) is bound to its original image dimensions (aka the aspect ratio)
        reset(page.hospitalMap, page.mapWidth, page.mapHeight);
        double fileWidth = page.hospitalMap.getImage().getWidth();
        double fileHeight = page.hospitalMap.getImage().getHeight();

        SimpleObjectProperty<Point2D> mouseClickDown = new SimpleObjectProperty<>();

        page.onTopOfTopElements.setOnMousePressed(mouseEvent -> {
            Point2D pointOfMouseClick = viewportToImageView(page.hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            mouseClickDown.set(pointOfMouseClick);
        });

        page.onTopOfTopElements.setOnMouseDragged(mouseEvent -> {
            Point2D pointToDragFrom = viewportToImageView(page.hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            Point2D valueOfShift = pointToDragFrom.subtract(mouseClickDown.get());
            shiftedImage(page.hospitalMap, valueOfShift, page.onTopOfTopElements);
            mouseClickDown.set(viewportToImageView(page.hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY())));
        });

        page.onTopOfTopElements.setOnScroll(mouseEvent -> {
            double getDifference = -mouseEvent.getDeltaY();
            System.out.println("getDifference: " + getDifference);
            Rectangle2D viewportOfImage = page.hospitalMap.getViewport();

            double scaleDifference = Math.pow(1.01, getDifference);
            System.out.println("scaleDifference: " + scaleDifference);
            double minPixels = 10;


            double lowestBoundaryWidth = minPixels / viewportOfImage.getWidth();
            double lowestBoundaryHeight = minPixels / viewportOfImage.getHeight();
            double minimumZoomScale = Math.min(lowestBoundaryWidth, lowestBoundaryHeight);

            double highestBoundaryWidth = page.mapWidth / viewportOfImage.getWidth();
            double highestBoundaryHeight = page.mapHeight / viewportOfImage.getHeight();
            double maximumZoomScale = Math.min(highestBoundaryWidth, highestBoundaryHeight);

            double boundariesOfViewPort = ensureRange(scaleDifference, minimumZoomScale, maximumZoomScale);
            System.out.println("boundariesOfViewPort: " + boundariesOfViewPort);

            Point2D mouseCursorLocationOnMap = viewportToImageView(page.hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY()));

            page.scaledWidth = viewportOfImage.getWidth() * boundariesOfViewPort;
            page.scaledHeight = viewportOfImage.getHeight() * boundariesOfViewPort;

            double minXValueOfMouseClick = mouseCursorLocationOnMap.getX() - ((mouseCursorLocationOnMap.getX() - viewportOfImage.getMinX()) * boundariesOfViewPort);
            double minYValueOfMouseClick = mouseCursorLocationOnMap.getY() - ((mouseCursorLocationOnMap.getY() - viewportOfImage.getMinY()) * boundariesOfViewPort);

            double widthDifferenceBetweenScaledAndNormal = page.mapWidth - page.scaledWidth;
            double heightDifferenceBetweenScaledAndNormal = page.mapHeight - page.scaledHeight;

            double scaledMinWidth = ensureRange(minXValueOfMouseClick, 0, widthDifferenceBetweenScaledAndNormal);
            double scaledMinHeight = ensureRange(minYValueOfMouseClick, 0, heightDifferenceBetweenScaledAndNormal);
            page.scaledX = scaledMinWidth;
            page.scaledY = scaledMinHeight;

            Rectangle2D newViewPort = new Rectangle2D(scaledMinWidth, scaledMinHeight, page.scaledWidth, page.scaledHeight);

//            double widthRatio = width / fileWidth;
//            double heightRatio = height / fileHeight;

            page.hospitalMap.setViewport(newViewPort);

            page.clearMap();
            System.out.println("scroll listener");
            page.displayNodes(.8);
//            page.drawPath(_listOfNodes);
        });


        page.onTopOfTopElements.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                reset(page.hospitalMap, page.mapWidth, page.mapHeight);
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

    public void shiftedImage(ImageView inputMap, Point2D changeInShift, AnchorPane topElements) {
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
        page.scaledX = viewportMinWidth;
        page.scaledY = viewportMinHeight;

        inputMap.setViewport(new Rectangle2D(viewportMinWidth, viewportMinHeight, theViewPort.getWidth(), theViewPort.getHeight()));

        page.scaledWidth = theViewPort.getWidth();
        page.scaledHeight = theViewPort.getHeight();

        page.clearMap();
        System.out.println("pan listener");
        page.displayNodes(.8);
//        page.drawPath(_listOfNodes);
    }
}
