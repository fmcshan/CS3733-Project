package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.Algo.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.AuthListener;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import edu.wpi.teamname.bridge.Bridge;
import edu.wpi.teamname.bridge.CloseListener;
import edu.wpi.teamname.simplify.Shutdown;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller for DefaultPage.fxml
 *
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class DefaultPage implements AuthListener, CloseListener {

    @FXML
    private VBox popPop; // vbox to populate with different fxml such as Navigation/Requests/Login
    @FXML
    private VBox adminPop; // vbox to populate Map Editor button
    @FXML
    private VBox requestPop; // vbox to populate Submitted Requests button
    @FXML
    private Path tonysPath; // this is Tony's path
    @FXML
    private AnchorPane anchor, topElements; //
    @FXML
    private ImageView hospitalMap;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXButton adminButton;
    @FXML
    private AnchorPane pathAnchor;

    double mapWidth; //= 1000.0;
    double mapHeight; // = 680.0;
    double fileWidth;//= 5000.0;
    double fileHeight; // = 3400.0;
    double fileFxWidthRatio = mapWidth / fileWidth;
    double fileFxHeightRatio = mapHeight / fileHeight;
    boolean start = true;
    static double scaledWidth = 5000;
    static double scaledHeight = 3400.0;
    Node startNode;
    Node endNode;
    static double scaledX = 0;
    static double scaledY = 0;

    String openWindow = "";
    ArrayList<Node> currentPath = new ArrayList<>();
    ArrayList<Node> listOfNodes = PathFindingDatabaseManager.getInstance().getNodes(); // create a list of nodes
    HashMap<String, Node> nodesMap = new HashMap<>(); //

    public void initialize() {
        AuthenticationManager.getInstance().addListener(this);
        Bridge.getInstance().addCloseListener(this);

        tonysPath.getElements().clear();

        double fileWidth = 5000.0;
        double fileHeight = 3400.0;

        double windowWidth = hospitalMap.boundsInParentProperty().get().getWidth() / fileWidth;
        double windowHeight = hospitalMap.boundsInParentProperty().get().getHeight() / fileHeight;
        double windowSmallestScale = Math.max(Math.min(windowHeight, windowWidth), 0);

        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            hospitalMap.fitWidthProperty().bind(stackPane.widthProperty());
            mapWidth = hospitalMap.boundsInParentProperty().get().getWidth() / windowSmallestScale;
            topElements.getChildren().clear();
            displayNodes();
//            mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
        });

        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            hospitalMap.fitHeightProperty().bind(stackPane.heightProperty());
            mapHeight = hospitalMap.boundsInParentProperty().get().getHeight()/ windowSmallestScale;
            topElements.getChildren().clear();
            displayNodes();
//            mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
        });

        mapWidth = hospitalMap.boundsInParentProperty().get().getWidth() / windowSmallestScale;
        //System.out.println("mapWidth: " + mapWidth);

        mapHeight = hospitalMap.boundsInParentProperty().get().getHeight()/ windowSmallestScale;
        //System.out.println("mapHeight: " + mapHeight);

        //OnWindowSizeChanged();

        //resizingInfo();
        if (start) {
            displayNodes();
//            System.out.println("Before Zoom and Pan On Start");
//            zoomAndPan(hospitalMap, topElements, mapWidth, mapHeight);
            start = false;
        } else {
            topElements.getChildren().clear();
            //resizingInfo();
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
            displayNodes();
//            System.out.println("Before Zoom and Pan After Start");
//            zoomAndPan(hospitalMap, topElements, mapWidth, mapHeight);
        }


        zoomAndPan(hospitalMap, topElements, mapWidth, mapHeight);
    }

    public void loadWindowPopPop(String fileName, String windowName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindowPopPop(windowName, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadWindowAdminPop(String fileName, String windowName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindowAdminPop(windowName, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadWindowRequestPop(String fileName, String windowName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindowRequestPop(windowName, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openWindowPopPop(String windowName, Parent root) {
        popPop.getChildren().clear();
        if (!windowName.equals(openWindow)) {
            popPop.getChildren().add(root);
            openWindow = windowName;
            return;
        }
        openWindow = "";
    }

    public void openWindowAdminPop(String windowName, Parent root) {
        adminPop.getChildren().clear();
        if (!windowName.equals(openWindow)) {
            adminPop.getChildren().add(root);
            openWindow = windowName;
            return;
        }
        openWindow = "";
    }

    public void openWindowRequestPop(String windowName, Parent root) {
        requestPop.getChildren().clear();
        if (!windowName.equals(openWindow)) {
            requestPop.getChildren().add(root);
            openWindow = windowName;
            return;
        }
        openWindow = "";
    }

    public void toggleNav(ActionEvent actionEvent) {
        tonysPath.getElements().clear();
        popPop.setPrefWidth(350.0);
        // load controller here
        Navigation navigation = new Navigation(this);

        navigation.loadNav();
    }

    public void openRequests(ActionEvent actionEvent) {
        popPop.setPrefWidth(350.0);
        loadWindowPopPop("Requests", "reqBar");
    }

    public void openLogin(ActionEvent actionEvent) {
        popPop.setPrefWidth(350.0);
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            loadWindowPopPop("Login", "loginBar");
        } else {
            AuthenticationManager.getInstance().signOut();
        }
    }

    public void openCheckIn(ActionEvent actionEvent) {
        popPop.setPrefWidth(657.0);
        loadWindowPopPop("UserRegistration", "registrationButton");
    }

    public void exitApplication(ActionEvent actionEvent) {
        Shutdown.getInstance().exit();
    }

    public void drawPath(ArrayList<Node> _listOfNodes) {
        if (_listOfNodes.size() < 1) {
            return;
        }
        //resizingInfo();
        //displayNodes ();
        currentPath = _listOfNodes;
        tonysPath.getElements().clear();

        Node firstNode = _listOfNodes.get(0);

        MoveTo start = new MoveTo(xCoordOnTopElement(firstNode.getX()), yCoordOnTopElement(firstNode.getY()));
//        Collection<LineTo> collection = new ArrayList<>();
        tonysPath.getElements().add(start);
        // System.out.println(fileFxWidthRatio);
        _listOfNodes.forEach(n -> {
            tonysPath.getElements().add(new LineTo(xCoordOnTopElement(n.getX()), yCoordOnTopElement(n.getY())));
        });
//        Path path = new Path(start, new LineTo(firstNode.getX() * fileFxWidthRatio, firstNode.getY() * fileFxHeightRatio));
//        path.setFill(Color.TOMATO);
//        path.setStrokeWidth(4);
    }

    @Override
    public void userLogin() {
        loadWindowAdminPop("MapEditorButton", "mapButton");
        loadWindowRequestPop("SubmittedRequests", "reqButton");
        MaterialDesignIconView signOut = new MaterialDesignIconView(MaterialDesignIcon.EXIT_TO_APP);
        signOut.setFill(Paint.valueOf("#c3c3c3"));
        signOut.setGlyphSize(52);
        adminButton.setGraphic(signOut);
    }

    @Override
    public void userLogout() {
        adminPop.getChildren().clear();
        requestPop.getChildren().clear();
        MaterialDesignIconView signOut = new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT_BOX_OUTLINE);
        signOut.setFill(Paint.valueOf("#c3c3c3"));
        signOut.setGlyphSize(52);
        adminButton.setGraphic(signOut);
    }

    @Override
    public void closeButtonPressed() {
        popPop.getChildren().clear();
    }

    public void resizingInfo() {
        //mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
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

    HashMap<String, Node> map = new HashMap();

    public void displayNodes() {


        //rezisingInfo();
        for (Node n : listOfNodes) {
            map.put(n.getNodeID(), n);
            nodesMap.put(n.getNodeID(), n);

            double weightedNodeX;
            double weightedNodeY;
            weightedNodeX = xCoordOnTopElement(n.getX());
            weightedNodeY = yCoordOnTopElement(n.getY());
            Circle circle = new Circle(weightedNodeX, weightedNodeY, 8);

            // System.out.println(fileFxWidthRatio);
            //  System.out.println(fileFxHeightRatio);
            circle = (Circle) clickNode(circle, n);
            circle.setFill(Color.OLIVE);
            topElements.getChildren().add(circle);
            //   System.out.println("ADDED");
        }
        if (startNode != null && startNode.getNodeID().length() >= 2) {
            Circle startCircle = new Circle(xCoordOnTopElement(startNode.getX()), yCoordOnTopElement(startNode.getY()), 8);
            startCircle.setFill(Color.MAGENTA);
            topElements.getChildren().add(startCircle);
        }
        if (endNode != null && endNode.getNodeID().length() >= 2) {
            Circle endCircle = new Circle(xCoordOnTopElement(endNode.getX()), yCoordOnTopElement(endNode.getY()), 8);
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

//        double fileWidth = ZoomPan.gethMap().getImage().getWidth();
//        double   fileHeight = ZoomPan.gethMap().getImage().getHeight();
        //  System.out.println(fileWidth);
        //double getScreenX = mouseEvent.getSceneX();//Math.pow(1.01, -mouseScrollVal);
        double widthScale = scaledWidth / fileWidth;
        double heightScale = scaledHeight / fileHeight;
//        double windowWidth = hospitalMap.getFitWidth() / fileWidth;
//        double windowHeight = hospitalMap.getFitHeight() / fileHeight;
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
        //  double fileWidth = ZoomPan.gethMap().getImage().getWidth();
        //   double   fileHeight = ZoomPan.gethMap().getImage().getHeight();
        double fileHeight = 3400.0;
        double widthScale = scaledWidth / fileWidth;
        double heightScale = scaledHeight / fileHeight;

        double windowWidth = hospitalMap.boundsInParentProperty().get().getWidth() / fileWidth;
        double windowHeight = hospitalMap.boundsInParentProperty().get().getHeight() / fileHeight;
        double windowSmallestScale = Math.max(Math.min(windowHeight, windowWidth), 0);
        double viewportSmallestScale = Math.max(Math.min(heightScale, widthScale), 0);
//        System.out.println("y divided by :" + viewportSmallestScale);
//        System.out.println("multiplied by: " + windowSmallestScale);
        return ((y - scaledY) / viewportSmallestScale) * windowSmallestScale;
    }


    public void zoomAndPan(ImageView hospitalMap, AnchorPane inputTopElements, double width, double height) {

        // hMap = hospitalMap;
        //get the height associated with the height
        hospitalMap.setPreserveRatio(true); //make sure that the image (the hospitalMap) is bound to its original image dimensions (aka the aspect ratio)
        //System.out.println("width" + width);
        //System.out.println("height" + height);
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

        inputTopElements.setOnScroll(mouseEvent -> {
            double getDifference = -mouseEvent.getDeltaY();
            //System.out.println("getDifference: " + getDifference);
            Rectangle2D viewportOfImage = hospitalMap.getViewport();

            double scaleDifference = Math.pow(1.01, getDifference);
            //System.out.println("scaleDifference: " + scaleDifference);
            double minPixels = 10;
            //viewportOfImageWidth = viewportOfImage.getWidth();
            //viewportOfImageHeight = viewportOfImage.getHeight();
//            System.out.println("viewportOfImageWidth: " + viewportOfImageWidth);
//            System.out.println("viewportOfImageHeight: " + viewportOfImageHeight);


            double lowestBoundaryWidth = minPixels / viewportOfImage.getWidth();
            double lowestBoundaryHeight = minPixels / viewportOfImage.getHeight();
            double minimumZoomScale = Math.min(lowestBoundaryWidth, lowestBoundaryHeight);

            double highestBoundaryWidth = width / viewportOfImage.getWidth();
            double highestBoundaryHeight = height / viewportOfImage.getHeight();
            double maximumZoomScale = Math.min(highestBoundaryWidth, highestBoundaryHeight);

            double boundariesOfViewPort = ensureRange(scaleDifference, minimumZoomScale, maximumZoomScale);
            //System.out.println("boundariesOfViewPort: " + boundariesOfViewPort);

            Point2D mouseCursorLocationOnMap = viewportToImageView(hospitalMap, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            //System.out.println("mouseCursorLocationOnMap" + mouseCursorLocationOnMap);
            //System.out.println("scaleDifference" + scaleDifference);

            scaledWidth = viewportOfImage.getWidth() * boundariesOfViewPort;
            scaledHeight = viewportOfImage.getHeight() * boundariesOfViewPort;
            // scaledWidth = s

            double minXValueOfMouseClick = mouseCursorLocationOnMap.getX() - ((mouseCursorLocationOnMap.getX() - viewportOfImage.getMinX()) * boundariesOfViewPort);
            double minYValueOfMouseClick = mouseCursorLocationOnMap.getY() - ((mouseCursorLocationOnMap.getY() - viewportOfImage.getMinY()) * boundariesOfViewPort);
//            System.out.println("minXValueOfMouseClick" + minXValueOfMouseClick);
//            System.out.println("minYValueOfMouseClick" + minYValueOfMouseClick);

            double widthDifferenceBetweenScaledAndNormal = width - scaledWidth;
            double heightDifferenceBetweenScaledAndNormal = height - scaledHeight;

            double scaledMinWidth = ensureRange(minXValueOfMouseClick, 0, widthDifferenceBetweenScaledAndNormal);
            double scaledMinHeight = ensureRange(minYValueOfMouseClick, 0, heightDifferenceBetweenScaledAndNormal);
            scaledX = scaledMinWidth;
            scaledY = scaledMinHeight;

//
            Rectangle2D newViewPort = new Rectangle2D(scaledX, scaledY, scaledWidth, scaledHeight);

            double widthRatio = width / fileWidth;
            double heightRatio = height / fileHeight;

            hospitalMap.setViewport(newViewPort);
            topElements.getChildren().clear();
//            displayNodes(inputTopElements, hospitalMap, scaledMinWidth, scaledMinHeight, scaledWidth, scaledHeight);
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

    public void shiftedImage(ImageView inputMap, Point2D changeInShift, AnchorPane inputTopElements) {
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
        // displayNodes(topElements, inputMap, viewportMinWidth, viewportMinHeight, theViewPort.getWidth(), theViewPort.getHeight());
        displayNodes();
        drawPath(currentPath);
    }

    /**
     * When both comboboxes are filled calculate a path using AStar
     */
    public void calcPath() {
//
//        Navigation navigation = new Navigation(this);
//
//        if (navigation.getFromCombo() == null || !nodesMap.containsKey(navigation.getFromCombo())) { // if combobox is null or the key does not exist
//            return;
//        }
//        if (navigation.getToCombo() == null || !nodesMap.containsKey(navigation.getToCombo())) { // if combobox is null or the key does not exist
//            return;
//        }
//        Node startNode = nodesMap.get(navigation.getFromCombo()); // get starting location
//        Node endNode = nodesMap.get(navigation.getToCombo()); // get ending location
//        AStar AStar = new AStar(listOfNodes, startNode, endNode); // perform AStar
//        ArrayList<Node> path = AStar.returnPath(); // list the nodes found using AStar to create a path
//        drawPath(path); // draw the path on the map

        AStar AStar = new AStar(listOfNodes, startNode, endNode);
        ArrayList<Node> path = AStar.returnPath();
        drawPath(path);
        System.out.println("AStar startNode: "+ startNode.getLongName() + " , nodeID: "+ startNode.getNodeID());
        System.out.println("AStar endNode :"+ endNode.getLongName()+ " , nodeID: "+ endNode.getNodeID());
    }

    public void getAndDrawPathWithCombos(String fromCombo, String toCombo) {
        if (fromCombo == null || !nodesMap.containsKey(fromCombo)){
            return;
        }
        if (toCombo == null || !nodesMap.containsKey(toCombo)){
            return;
        }
        assignStartAndEndNodes(nodesMap.get(fromCombo));
        assignStartAndEndNodes(nodesMap.get(toCombo));
        //calcAndDrawPath(nodesMap.get(fromCombo.getValue()),nodesMap.get(toCombo.getValue()));
    }

    public void assignStartAndEndNodes(Node node) {
        if (startNode == null) {
            startNode = nodesMap.get(node.getNodeID());
            System.out.println("Start Node: " + node.getLongName());
            if (endNode != null) {
                calcPath();
                startNode=null;
                endNode= null;
            }
            return;
        }
        if (startNode != null && endNode == null) {
            endNode = nodesMap.get(node.getNodeID());
            System.out.println("Close Node: " + node.getLongName());

            calcPath();

            startNode = null;
            endNode = null;

        }
    }
}
