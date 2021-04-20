package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.App;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Navigation implements Initializable {
    @FXML
    private Path tonysPath;
    @FXML
    private AnchorPane anchor, topElements;
    @FXML
    private ComboBox<String> toCombo;
    @FXML
    private ComboBox<String> fromCombo;
    @FXML
    private ImageView hospitalMap;
    @FXML
    private StackPane stackPaneA;

    ArrayList<Node> listOfNodes = new ArrayList<>();
    HashMap<String, Node> nodesMap = new HashMap<>();
    ArrayList<Node> currentPath = new ArrayList<>();
    double mapWidth; //= 1000.0;
    double mapHeight; // = 680.0;
    double fileWidth; //= 5000.0;
    double fileHeight; // = 3400.0;
    double fileFxWidthRatio = mapWidth / fileWidth;
    double fileFxHeightRatio = mapHeight / fileHeight;
    Node startNode;
    Node endNode;
    boolean start = true;
    static double scaledWidth = 5000;
    static double scaledHeight = 3400.0;

    static double scaledX = 0;
    static double scaledY = 0;

    ZoomPan zoomPan = new ZoomPan();

    public Navigation() {
    }

    public ImageView getHospitalMap() {
        return this.hospitalMap;
    }


    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tonysPath.getElements().clear();
        OnWindowSizeChanged();
        listOfNodes = PathFindingDatabaseManager.getInstance().getNodes();

        listOfNodes.forEach(n -> {
            nodesMap.put(n.getNodeID(), n);
            toCombo.getItems().add(n.getNodeID());
            fromCombo.getItems().add(n.getNodeID());
        });

        hospitalMap.fitWidthProperty().bind(anchor.widthProperty());
        hospitalMap.fitHeightProperty().bind(anchor.heightProperty());
        mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
        mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();

        resizingInfo();
        if (start) {
            System.out.println("GOT HERE");
            displayNodes();
            start = false;
        } else {
            topElements.getChildren().clear();
            resizingInfo();
            displayNodes();
        }

        zoomAndPan(hospitalMap, topElements, mapWidth, mapHeight);
    }

    public void resizingInfo() {
        mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
        // System.out.println("mapWidth: "+ mapWidth);
        mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
        //System.out.println("mapHeight: "+mapHeight);
        fileWidth = hospitalMap.getImage().getWidth();
        // System.out.println("fileWidth: "+ fileWidth);
        fileHeight = hospitalMap.getImage().getHeight();
        // System.out.println("fileHeight: "+ fileHeight);
        fileFxWidthRatio = mapWidth / fileWidth;
        fileFxHeightRatio = mapHeight / fileHeight;
    }

    public void OnWindowSizeChanged() {

        anchor.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            topElements.getChildren().clear();
            resizingInfo();
            displayNodes();

        });

        anchor.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            topElements.getChildren().clear();
            resizingInfo();
            displayNodes();
        });

    }

    public void drawPath(ArrayList<Node> _listOfNodes) {
        if (_listOfNodes.size() < 1) {
            return;
        }
        resizingInfo();
        //displayNodes ();
        currentPath = _listOfNodes;
        tonysPath.getElements().clear();

        Node firstNode = _listOfNodes.get(0);

        MoveTo start = new MoveTo(xCoordOnTopElement(firstNode.getX()), yCoordOnTopElement(firstNode.getY()));
        tonysPath.getElements().add(start);
        _listOfNodes.forEach(n -> {
            tonysPath.getElements().add(new LineTo(xCoordOnTopElement(n.getX()), yCoordOnTopElement(n.getY())));
        });
    }

    public void returnHome(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/DefaultPage.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void getAndDrawPathWithCombos() {
        if (fromCombo.getValue() == null || !nodesMap.containsKey(fromCombo.getValue())) {
            return;
        }
        if (toCombo.getValue() == null || !nodesMap.containsKey(toCombo.getValue())) {
            return;
        }
        assignStartAndEndNodes(nodesMap.get(fromCombo.getValue()));
        assignStartAndEndNodes(nodesMap.get(toCombo.getValue()));
    }

    public void assignStartAndEndNodes(Node node) {
        if (startNode == null) {
            startNode = node;
            if (endNode != null) {
                calcAndDrawPath();
            }
            return;
        }
        if (startNode != null && endNode == null) {
            endNode = node;

            calcAndDrawPath();

            startNode = null;
            endNode = null;

        }
    }

    public void calcAndDrawPath() {


        AStar AStar = new AStar(listOfNodes, startNode, endNode);
        ArrayList<Node> path = AStar.returnPath();
        drawPath(path);
    }

    HashMap<String, Node> map = new HashMap();
    ArrayList<Node> nodes = PathFindingDatabaseManager.getInstance().getNodes();

    public void displayNodes() {


        //rezisingInfo();
        for (Node n : nodes) {
            map.put(n.getNodeID(), n);

            double weightedNodeX;
            double weightedNodeY;
            //  System.out.println("got here");
            weightedNodeX = xCoordOnTopElement(n.getX());
            weightedNodeY = yCoordOnTopElement(n.getY());
            Circle circle = new Circle(weightedNodeX, weightedNodeY, 8);

            circle = (Circle) clickNode(circle, n);
            circle.setFill(Color.OLIVE);
            topElements.getChildren().add(circle);
        }


        if (startNode != null) {
            Circle startCircle = new Circle(startNode.getX() * fileFxWidthRatio, startNode.getY() * fileFxHeightRatio, 8);
            startCircle.setFill(Color.MAGENTA);
            topElements.getChildren().add(startCircle);
        }
        if (endNode != null) {
            Circle endCircle = new Circle(endNode.getX() * fileFxWidthRatio, endNode.getY() * fileFxHeightRatio, 8);
            endCircle.setFill(Color.MAROON);
            topElements.getChildren().add(endCircle);
        }
    }

    protected javafx.scene.Node clickNode(javafx.scene.Node circle, Node node) {
        circle.setOnMouseClicked(
                t -> {
                    circle.setStyle("-fx-cursor: hand");
                    assignStartAndEndNodes((node));

                });
        circle.setStyle("-fx-cursor: hand");
        return circle;
    }

    public double xCoordOnTopElement(int x) {
        double mapWidth = 1000.0;
        double mapHeight = 680.0;
        double fileWidth = 5000.0;
        double fileHeight = 3400.0;

        double widthScale = scaledWidth / fileWidth;
        double heightScale = scaledHeight / fileHeight;
        double windowWidth = hospitalMap.boundsInParentProperty().get().getWidth() / fileWidth;
        double windowHeight = hospitalMap.boundsInParentProperty().get().getHeight() / fileHeight;
        double windowSmallestScale = Math.max(Math.min(windowHeight, windowWidth), 0);
        double viewportSmallestScale = Math.max(Math.min(heightScale, widthScale), 0);
        return ((x - scaledX) / viewportSmallestScale) * windowSmallestScale;
    }

    public double yCoordOnTopElement(int y) {
        double mapWidth = 1000.0;
        double mapHeight = 680.0;
        double fileWidth = 5000.0;
        double fileHeight = 3400.0;
        double widthScale = scaledWidth / fileWidth;
        double heightScale = scaledHeight / fileHeight;

        double windowWidth = hospitalMap.boundsInParentProperty().get().getWidth() / fileWidth;
        double windowHeight = hospitalMap.boundsInParentProperty().get().getHeight() / fileHeight;
        double windowSmallestScale = Math.max(Math.min(windowHeight, windowWidth), 0);
        double viewportSmallestScale = Math.max(Math.min(heightScale, widthScale), 0);
        System.out.println("y divided by :" + viewportSmallestScale);
        System.out.println("multiplied by: " + windowSmallestScale);
        return ((y - scaledY) / viewportSmallestScale) * windowSmallestScale;
    }


    public void zoomAndPan(ImageView hospitalMap, AnchorPane inputTopElements, double width, double height) {


        // hMap = hospitalMap;
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
            shiftedImage(hospitalMap, valueOfShift, inputTopElements);
            mouseClickDown.set(viewportToImageView(hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY())));
        });

        inputTopElements.setOnScroll(mouseEvent -> {
            double getDifference = -mouseEvent.getDeltaY();
            System.out.println("getDifference: " + getDifference);
            Rectangle2D viewportOfImage = hospitalMap.getViewport();

            double scaleDifference = Math.pow(1.01, getDifference);
            System.out.println("scaleDifference: " + scaleDifference);
            double minPixels = 10;


            double lowestBoundaryWidth = minPixels / viewportOfImage.getWidth();
            double lowestBoundaryHeight = minPixels / viewportOfImage.getHeight();
            double minimumZoomScale = Math.min(lowestBoundaryWidth, lowestBoundaryHeight);

            double highestBoundaryWidth = width / viewportOfImage.getWidth();
            double highestBoundaryHeight = height / viewportOfImage.getHeight();
            double maximumZoomScale = Math.min(highestBoundaryWidth, highestBoundaryHeight);

            double boundariesOfViewPort = ensureRange(scaleDifference, minimumZoomScale, maximumZoomScale);
            System.out.println("boundariesOfViewPort: " + boundariesOfViewPort);

            Point2D mouseCursorLocationOnMap = viewportToImageView(hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY()));

            scaledWidth = viewportOfImage.getWidth() * boundariesOfViewPort;
            scaledHeight = viewportOfImage.getHeight() * boundariesOfViewPort;

            double minXValueOfMouseClick = mouseCursorLocationOnMap.getX() - ((mouseCursorLocationOnMap.getX() - viewportOfImage.getMinX()) * boundariesOfViewPort);
            double minYValueOfMouseClick = mouseCursorLocationOnMap.getY() - ((mouseCursorLocationOnMap.getY() - viewportOfImage.getMinY()) * boundariesOfViewPort);

            double widthDifferenceBetweenScaledAndNormal = width - scaledWidth;
            double heightDifferenceBetweenScaledAndNormal = height - scaledHeight;

            double scaledMinWidth = ensureRange(minXValueOfMouseClick, 0, widthDifferenceBetweenScaledAndNormal);
            double scaledMinHeight = ensureRange(minYValueOfMouseClick, 0, heightDifferenceBetweenScaledAndNormal);
            scaledX = scaledMinWidth;
            scaledY = scaledMinHeight;

            Rectangle2D newViewPort = new Rectangle2D(scaledMinWidth, scaledMinHeight, scaledWidth, scaledHeight);

            double widthRatio = width / fileWidth;
            double heightRatio = height / fileHeight;

            hospitalMap.setViewport(newViewPort);
            topElements.getChildren().clear();

            displayNodes();
            drawPath(currentPath);
        });


        inputTopElements.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
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
        scaledX = viewportMinWidth;
        scaledY = viewportMinHeight;

        inputMap.setViewport(new Rectangle2D(viewportMinWidth, viewportMinHeight, theViewPort.getWidth(), theViewPort.getHeight()));

        scaledWidth = theViewPort.getWidth();
        scaledHeight = theViewPort.getHeight();
        topElements.getChildren().clear();
        displayNodes();
    }
}

