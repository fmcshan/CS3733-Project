package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.Node;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

public class ZoomAndPan {
    public int panVel = 0;
    public int zoomVel = 0;
    MapDisplay page;
    ArrayList<Node> _listOfNodes;
    double windowWidth;
    double windowHeight;
    double windowSmallestScale;
    boolean dragged = false;
    boolean velDecay = false;
    private double currentMouseX;
    private double currentMouseY;

    public ZoomAndPan(MapDisplay page) {
        this.page = page;
        _listOfNodes = page.listOfNodes;
    }

    private static void reset(ImageView map, double width, double height) {
        Rectangle2D newViewPort = new Rectangle2D(0, 0, width, height);
        map.setViewport(newViewPort);
    }

    public static Point2D viewportToImageView(ImageView inputMap, double Xcoord, double Ycoord) {
        Bounds bounds = inputMap.getBoundsInLocal();

        Rectangle2D viewport = inputMap.getViewport();
        return new Point2D(viewport.getMinX() + (Xcoord / bounds.getWidth()) * viewport.getWidth(), viewport.getMinY() + (Ycoord / bounds.getHeight()) * viewport.getHeight());
    }

    private static double ensureRange(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    public void zoomAndPan() {
        updateVars();
        page.hospitalMap.setPreserveRatio(true); //make sure that the image (the hospitalMap) is bound to its original image dimensions (aka the aspect ratio)
        reset(page.hospitalMap, page.mapWidth, page.mapHeight);

        SimpleObjectProperty<Point2D> mouseClickDown = new SimpleObjectProperty<>();

        page.onTopOfTopElements.setOnMousePressed(mouseEvent -> {
            Point2D pointOfMouseClick = viewportToImageView(page.hospitalMap, mouseEvent.getX(), mouseEvent.getY());
            mouseClickDown.set(pointOfMouseClick);
        });

        page.onTopOfTopElements.setOnMouseMoved(e -> {
            currentMouseX = e.getX();
            currentMouseY = e.getY();
        });

        page.onTopOfTopElements.setOnMouseDragged(mouseEvent -> {
            dragged = true;
            if (mouseEvent.getTarget() instanceof Circle) {
                return;
            }

            Point2D pointToDragFrom = viewportToImageView(page.hospitalMap, mouseEvent.getX(), mouseEvent.getY());
            Point2D valueOfShift = pointToDragFrom.subtract(mouseClickDown.get());
            shiftedImage(page.hospitalMap, valueOfShift, page.onTopOfTopElements);
            mouseClickDown.set(viewportToImageView(page.hospitalMap, mouseEvent.getX(), mouseEvent.getY()));
        });
        page.onTopOfTopElements.setOnScroll(mouseEvent -> {
            updateVars();
            double mouseDeltaY = mouseEvent.getDeltaY();

            if (mouseDeltaY > 0 && zoomVel < 500) { // <0 for zoom out, >0 for zoom in
                zoomVel += 100;
            } else if (zoomVel > -500){
                zoomVel -= 100;
            }
        });

        page.onTopOfTopElements.setOnMouseReleased(e -> {
            page.processClick(e, dragged);
            dragged = false;
        });

        if (!velDecay) {
            Timeline tick = TimelineBuilder.create()
                    .keyFrames(
                            new KeyFrame(
                                    new Duration(16.7),
                                    (EventHandler<ActionEvent>) t -> {
                                        if (panVel > 0) {
                                            panVel -= 4;
                                        } else if (panVel < 0) {
                                            panVel += 4;
                                        }

                                        if (zoomVel > 0) {
                                            zoomVel -= 4;
                                        } else if (zoomVel < 0) {
                                            zoomVel += 4;
                                        }

                                        if (zoomVel != 0) {
                                            double lclVel = ((double) zoomVel / 500);
                                            double viewVel = Math.pow((lclVel/6), 2) * (-zoomVel/Math.abs(zoomVel));
                                            updateViewport(1 + viewVel);
                                        }
                                    }
                            )
                    )
                    .cycleCount(Timeline.INDEFINITE)
                    .build();
            tick.play();
        }
        velDecay = true;
    }

    private void updateViewport(double _boundariesOfViewPort) {
        Rectangle2D viewportOfImage = page.hospitalMap.getViewport();

        double viewportWidth = viewportOfImage.getWidth();
        double viewportHeight = viewportOfImage.getHeight();

        if (viewportWidth < 700 && zoomVel > 0) { // prevent from zooming in too much
            zoomVel = 0;
            return;
        }
        if (viewportWidth > 5000 && zoomVel < 0) { // prevent from zooming out too much
            zoomVel = 0;
            return;
        }

        Point2D mouseCursorLocationOnMap = viewportToImageView(page.hospitalMap, currentMouseX, currentMouseY);

        page.scaledWidth = viewportWidth * _boundariesOfViewPort;
        page.scaledHeight = viewportHeight * _boundariesOfViewPort;

        double mouseCursorX = mouseCursorLocationOnMap.getX();
        double mouseCursorY = mouseCursorLocationOnMap.getY();

        page.scaledX = mouseCursorX - ((mouseCursorX - viewportOfImage.getMinX()) * _boundariesOfViewPort);
        page.scaledY = mouseCursorY - ((mouseCursorY - viewportOfImage.getMinY()) * _boundariesOfViewPort);
        Rectangle2D newViewPort = new Rectangle2D(page.scaledX, page.scaledY, page.scaledWidth, page.scaledHeight);
        render();
        if (!LoadFXML.getCurrentWindow().equals("navBar")) {
            page.currentPath = new ArrayList();
        }
        page.hospitalMap.setViewport(newViewPort);
    }

    private void render() {
        if (LoadFXML.getCurrentWindow().equals("mapEditorBar")) {
            page.renderMap();
        }
        if (LoadFXML.getCurrentWindow().equals("navBar")) {
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

    public void shiftedImage(ImageView inputMap, Point2D changeInShift, AnchorPane topElements) {
        Rectangle2D theViewPort = inputMap.getViewport();

        double viewPortWidth = theViewPort.getWidth();
        double viewPortHeight = theViewPort.getHeight();

        page.scaledX = theViewPort.getMinX() - changeInShift.getX();
        page.scaledY = theViewPort.getMinY() - changeInShift.getY();

        inputMap.setViewport(new Rectangle2D(page.scaledX, page.scaledY, viewPortWidth, viewPortHeight));

        page.scaledWidth = viewPortWidth;
        page.scaledHeight = viewPortHeight;
        render();
    }
}
