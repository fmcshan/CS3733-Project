package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class ZoomAndPan {
    MapDisplay page;
    ArrayList<Node> _listOfNodes;
    double windowWidth;
    double windowHeight;
    double windowSmallestScale;
    boolean dragged = false;

    public ZoomAndPan(MapDisplay page) {
        this.page = page;
        _listOfNodes = page.listOfNodes;
    }

    public void zoomAndPan() {
        updateVars();
        page.hospitalMap.setPreserveRatio(true); //make sure that the image (the hospitalMap) is bound to its original image dimensions (aka the aspect ratio)
        reset(page.hospitalMap, page.mapWidth, page.mapHeight);

        SimpleObjectProperty<Point2D> mouseClickDown = new SimpleObjectProperty<>();

        page.onTopOfTopElements.setOnMousePressed(mouseEvent -> {
            Point2D pointOfMouseClick = viewportToImageView(page.hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            mouseClickDown.set(pointOfMouseClick);
        });

        page.onTopOfTopElements.setOnMouseDragged(mouseEvent -> {
            dragged = true;
            if (mouseEvent.getTarget() instanceof Circle) {
                return;
            }

            Point2D pointToDragFrom = viewportToImageView(page.hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            Point2D valueOfShift = pointToDragFrom.subtract(mouseClickDown.get());
            shiftedImage(page.hospitalMap, valueOfShift, page.onTopOfTopElements);
            mouseClickDown.set(viewportToImageView(page.hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY())));
        });
        page.onTopOfTopElements.setOnScroll(mouseEvent -> {
            updateVars();
            double getDifference = -mouseEvent.getDeltaY();
            Rectangle2D viewportOfImage = page.hospitalMap.getViewport();

            double scaleDifference = Math.pow(1.01, getDifference);
            double minPixels = 10;

            double viewportWidth = viewportOfImage.getWidth();
            double viewportHeight = viewportOfImage.getHeight();

            double minimumZoomScale = Math.min(minPixels / viewportWidth, minPixels / viewportHeight);

            double maximumZoomScale = Math.min(page.mapWidth / viewportWidth, page.mapHeight / viewportHeight);

            if (maximumZoomScale > 15 && getDifference < 0) {
                return;
            }

            double boundariesOfViewPort = ensureRange(scaleDifference, minimumZoomScale, maximumZoomScale);

            Point2D mouseCursorLocationOnMap = viewportToImageView(page.hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY()));

            page.scaledWidth = viewportWidth * boundariesOfViewPort;
            page.scaledHeight = viewportHeight * boundariesOfViewPort;

            double mouseCursorX = mouseCursorLocationOnMap.getX();
            double mouseCursorY = mouseCursorLocationOnMap.getY();

            double minXValueOfMouseClick = mouseCursorX - ((mouseCursorX - viewportOfImage.getMinX()) * boundariesOfViewPort);
            double minYValueOfMouseClick = mouseCursorY - ((mouseCursorY - viewportOfImage.getMinY()) * boundariesOfViewPort);

            double scaledMinWidth = ensureRange(minXValueOfMouseClick, 0, page.mapWidth - page.scaledWidth);
            double scaledMinHeight = ensureRange(minYValueOfMouseClick, 0, page.mapHeight - page.scaledHeight);
            page.setScaledX(scaledMinWidth);
            page.setScaledY(scaledMinHeight);
            Rectangle2D newViewPort = new Rectangle2D(page.scaledX, page.scaledY, page.scaledWidth, page.scaledHeight);
            render();
            if (!LoadFXML.getCurrentWindow().equals("navBar")){
                page.currentPath= new ArrayList();
            }
            page.hospitalMap.setViewport(newViewPort);


        });

        page.onTopOfTopElements.setOnMouseReleased(e -> {
            page.processClick(e, dragged);
            dragged = false;
        });
    }

    private void render() {
        if (LoadFXML.getCurrentWindow().equals("mapEditorBar")) {
            page.renderMap();}
        if (LoadFXML.getCurrentWindow().equals("navBar")){
            page.onTopOfTopElements.getChildren().clear();
            page.topElements.getChildren().clear(); // Clear top elements
            page.tonysPath.getElements().clear(); // Clear Tony's path
            page.hidePopups();
            page.drawPath(page.currentPath);
            page.displayHotspots(0.8);

        }
    }

    private void updateVars() {
        windowWidth = page.hospitalMap.boundsInParentProperty().get().getWidth() / page.fileWidth;
        windowHeight = page.hospitalMap.boundsInParentProperty().get().getHeight() / page.fileHeight;
        windowSmallestScale = ensureRange(windowHeight, 0, windowWidth);

        page.hospitalMap.fitWidthProperty().bind(page.anchor.widthProperty());
        page.mapWidth = page.hospitalMap.boundsInParentProperty().get().getWidth() / windowSmallestScale;
        page.mapHeight = page.hospitalMap.boundsInParentProperty().get().getHeight() / windowSmallestScale;
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
        render();
    }
}
