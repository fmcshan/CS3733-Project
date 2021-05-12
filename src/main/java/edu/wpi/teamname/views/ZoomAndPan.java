package edu.wpi.teamname.views;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
    boolean dragged = false;
    double w1 = 5000;
    double h1 = 3400;
    double w2 = 1427;
    double h2 = 970;

    public ZoomAndPan(MapDisplay page) {
        this.page = page;
    }

    public void zoomAndPan(boolean reset) {
        page.hospitalMap.setPreserveRatio(true); //make sure that the image (the hospitalMap) is bound to its original image dimensions (aka the aspect ratio)
        if (reset) {
            reset(page.hospitalMap, page.fileWidth, page.fileHeight);
        } else {
            if (page.hospitalMap.getViewport().getWidth() == 0) {
                page.hospitalMap.setViewport(new Rectangle2D(page.hospitalMap.getViewport().getMinX(), page.hospitalMap.getViewport().getMinY(), page.fileWidth, page.fileHeight));
            } else {
                page.hospitalMap.setFitWidth(page.anchor.getWidth() - 375);
                page.hospitalMap.setFitHeight(page.anchor.getHeight());
            }
        }

        SimpleObjectProperty<Point2D> mouseClickDown = new SimpleObjectProperty<>();

        page.onTopOfTopElements.setOnMousePressed(mouseEvent -> {
            Point2D pointOfMouseClick = viewportToImageView(page.hospitalMap, mouseEvent.getX(), mouseEvent.getY());
            mouseClickDown.set(pointOfMouseClick);
        });

        page.onTopOfTopElements.setOnMouseDragged(mouseEvent -> {
            dragged = true;
            if (mouseEvent.getTarget() instanceof Circle) {
                return;
            }

            Point2D pointToDragFrom = viewportToImageView(page.hospitalMap, mouseEvent.getX(), mouseEvent.getY());
            Point2D valueOfShift = pointToDragFrom.subtract(mouseClickDown.get());
            shiftedImage(page.hospitalMap, valueOfShift);
            mouseClickDown.set(viewportToImageView(page.hospitalMap, mouseEvent.getX(), mouseEvent.getY()));
        });
        page.onTopOfTopElements.setOnScroll(mouseEvent -> {
            double mouseDeltaY = mouseEvent.getDeltaY();
            Rectangle2D viewportOfImage = page.hospitalMap.getViewport();

            double viewportWidth = viewportOfImage.getWidth();
            double viewportHeight = viewportOfImage.getHeight();

            if (viewportWidth < 1000 && mouseDeltaY > 0) { // prevent from zooming in too much
                return;
            }
            if (viewportWidth > 5000 && mouseDeltaY < 0) { // prevent from zooming out too much
                return;
            }

            double boundariesOfViewPort;
            if (mouseDeltaY > 0) { // <0 for zoom out, >0 for zoom in
                boundariesOfViewPort = 1 / 1.1;
            } else {
                boundariesOfViewPort = 1.1;
            }

            Point2D mouseCursorLocationOnMap = viewportToImageView(page.hospitalMap, mouseEvent.getX(), mouseEvent.getY());

            page.scaledWidth = viewportWidth * boundariesOfViewPort;
            page.scaledHeight = viewportHeight * boundariesOfViewPort;

            double mouseCursorX = mouseCursorLocationOnMap.getX();
            double mouseCursorY = mouseCursorLocationOnMap.getY();

            page.scaledX = mouseCursorX - ((mouseCursorX - viewportOfImage.getMinX()) * boundariesOfViewPort);
            page.scaledY = mouseCursorY - ((mouseCursorY - viewportOfImage.getMinY()) * boundariesOfViewPort);
            Rectangle2D newViewPort = new Rectangle2D(page.scaledX, page.scaledY, page.scaledWidth, page.scaledHeight);
            render();
            if (!LoadFXML.getCurrentWindow().equals("navBar")) {
                page.currentPath = new ArrayList();
            }
            page.hospitalMap.setViewport(newViewPort);


        });

        page.onTopOfTopElements.setOnMouseReleased(e -> {
            page.processClick(e, dragged);
            dragged = false;
        });
    }

    private void render() {
        if (page.getRevisionHistoryMode() && LoadFXML.getCurrentWindow().equals("revisionHistory")) {
//            System.out.println("called");
            page.updateAndDisplay();
        }
        if (LoadFXML.getCurrentWindow().equals("mapEditorBar") || !page.getRevisionHistoryMode() && LoadFXML.getCurrentWindow().equals("revisionHistory")) {
            page.renderMap();
//            System.out.println("rascal");
        }
        if (LoadFXML.getCurrentWindow().equals("navBar")) {
            page.onTopOfTopElements.getChildren().clear();
            page.topElements.getChildren().clear(); // Clear top elements
            page.tonysPath.getElements().clear(); // Clear Tony's path
            page.hidePopups();
            page.drawPath(page.currentPath, false);
            page.displayHotspots(0.8);
        }
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

    public void shiftedImage(ImageView inputMap, Point2D changeInShift) {
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

    public void setViewPort(double x, double y, double width, double height) {
        page.hospitalMap.setViewport(new Rectangle2D(x, y, width, height));
    }
}