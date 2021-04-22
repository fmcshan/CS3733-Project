package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.simplify.Shutdown;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.shape.Path;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MapDisplay {

    double mapWidth; //= 1000.0;
    double mapHeight;// = 680.0;
    double fileWidth; //= 5000.0;
    double fileHeight;// = 3400.0;
    double fileFxWidthRatio = mapWidth / fileWidth;
    double fileFxHeightRatio = mapHeight / fileHeight;
    static double scaledWidth = 5000;
    static double scaledHeight = 3400.0;
    static double scaledX = 0;
    static double scaledY = 0;
    ArrayList<Node> listOfNodes;
    ArrayList<Node> nodes;
    ArrayList<Node> localNodes = new ArrayList<>(); // Nodes within current parameters (IE: floor)
    ArrayList<Edge> edges;
    ArrayList<Edge> localEdges = new ArrayList<>(); // Edges within current parameters (IE: floor)
    HashMap<String, Node> nodesMap = new HashMap<>();
    HashMap<String, Edge> edgesMap = new HashMap<>();

    @FXML
    VBox popPop, adminPop, requestPop, registrationPop; // vbox to populate with different fxml such as Navigation/Requests/Login
    @FXML
    Path tonysPath; // the path displayed on the map
    @FXML
    JFXButton adminButton; // button that allows you to sign in
    @FXML
    ImageView hospitalMap;
    @FXML
    AnchorPane topElements;
    @FXML
    StackPane stackPane; // the pane the map is housed in

    /**
     * getter for popPop Vbox
     * @return
     */
    public VBox getPopPop() {
        return popPop;
    }

    public abstract void initialize();

    public void displayNodes(double _opacity) {
        resizingInfo();

        localNodes.forEach(n -> {
            Circle circle = new Circle(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio, 8);
            circle.setStrokeWidth(4);
            circle.setStroke(Color.TRANSPARENT);
            circle.setFill(Color.OLIVE);
            circle.setOpacity(_opacity);
            topElements.getChildren().add(circle);

            circle.setOnMouseEntered(e -> {
                circle.setRadius(12);
                circle.setOpacity(1);
            });

            circle.setOnMouseExited(e -> {
                circle.setRadius(8);
                circle.setOpacity(0.6);
            });
        });
    }

    public void displayEdges(double _opacity) {
        resizingInfo();
        edges.forEach(e -> {
            if (nodesMap.containsKey(e.getStartNode()) && nodesMap.containsKey(e.getEndNode())) {
                LineBuilder<?> edgeLocation = LineBuilder.create().startX(nodesMap.get(e.getStartNode()).getX() * fileFxWidthRatio).startY(nodesMap.get(e.getStartNode()).getY() * fileFxHeightRatio).endX(nodesMap.get(e.getEndNode()).getX() * fileFxWidthRatio).endY(nodesMap.get(e.getEndNode()).getY() * fileFxHeightRatio);
                Line edge = edgeLocation.stroke(Color.BLUE).strokeWidth(3).opacity(_opacity).build();
                topElements.getChildren().add(edge);

                edge.setOnMouseEntered(t -> {
                    edge.setStrokeWidth(6);
                    edge.setOpacity(1);
                });

                edge.setOnMouseExited(t -> {
                    edge.setOpacity(_opacity);
                    edge.setStrokeWidth(3);
                });
            }
        });
    }

    /**
     * stores needed resizing info for scaling the displayed nodes on the map as the window changes
     */
    public void resizingInfo() {
        mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
        //System.out.println("mapWidth: " + mapWidth);
        mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
        // System.out.println("mapHeight: " + mapHeight);
        fileWidth = hospitalMap.getImage().getWidth();
        //System.out.println("fileWidth: " + fileWidth);
        fileHeight = hospitalMap.getImage().getHeight();
        // System.out.println("fileHeight: " + fileHeight);
        fileFxWidthRatio = mapWidth / fileWidth;
        fileFxHeightRatio = mapHeight / fileHeight;
    }

    public double xCoordOnTopElement(int x) {
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

    /**
     * for the scaling the displayed nodes on the map
     *
     * @param y the y coordinate of the anchor pane, top element
     * @return the scaled y coordinate
     */
    public double yCoordOnTopElement(int y) {
        double fileWidth = 5000.0;

        double fileHeight = 3400.0;
        double widthScale = scaledWidth / fileWidth;
        double heightScale = scaledHeight / fileHeight;

        double windowWidth = hospitalMap.boundsInParentProperty().get().getWidth() / fileWidth;
        double windowHeight = hospitalMap.boundsInParentProperty().get().getHeight() / fileHeight;
        double windowSmallestScale = Math.max(Math.min(windowHeight, windowWidth), 0);
        double viewportSmallestScale = Math.max(Math.min(heightScale, widthScale), 0);
        return ((y - scaledY) / viewportSmallestScale) * windowSmallestScale;
    }

    void refreshData() {
        nodes = LocalStorage.getInstance().getNodes();
        nodes.forEach(n -> {
            if (nodeWithinSpec(n)) {
                localNodes.add(n);
            }
        });


        localNodes.forEach(n -> {
            nodesMap.put(n.getNodeID(), n);
        });

        edges = LocalStorage.getInstance().getEdges();
        edges.forEach(e -> {
            if (nodesMap.containsKey(e.getStartNode()) && nodesMap.containsKey(e.getEndNode())) {
                if (nodeWithinSpec(nodesMap.get(e.getStartNode())) && nodeWithinSpec(nodesMap.get(e.getEndNode()))) {
                    localEdges.add(e);
                }
            }
        });

        localEdges.forEach(e -> {
            edgesMap.put(e.getEdgeID(), e);
        });
    }

    public abstract void drawPath(ArrayList<Node> _listOfNodes);

    /**
     * toggles the navigation window
     */
    public void toggleNav() {
        topElements.getChildren().clear();
        tonysPath.getElements().clear();
        popPop.setPrefWidth(350.0);
        // load controller here
        Navigation navigation = new Navigation(this);
        navigation.loadNav();
        listOfNodes = navigation.getListOfNodes();
        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            resizingInfo();
            topElements.getChildren().clear();
            displayNodes(1);
            hospitalMap.fitHeightProperty().bind(stackPane.heightProperty());
        });
        if (!LoadFXML.getCurrentWindow().equals("navBar")) {
            System.out.println("made it here");
            topElements.getChildren().clear();
            return;
        }
        displayNodes(1);
    }

    /**
     * toggle the requests window
     */
    public void openRequests() {
        topElements.getChildren().clear();
        popPop.setPrefWidth(350.0);
        LoadFXML.getInstance().loadWindow("Requests", "reqBar", popPop);
    }


    /**
     * toggle the login window
     */
    public void openLogin() {
        topElements.getChildren().clear();
        popPop.setPrefWidth(350.0);
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            LoadFXML.getInstance().loadWindow("Login", "loginBar", popPop);
        } else {
            AuthenticationManager.getInstance().signOut();
        }
    }

    /**
     * toggle the check in window
     */
    public void openCheckIn() {
        topElements.getChildren().clear();
        popPop.setPrefWidth(657.0);
        LoadFXML.getInstance().loadWindow("UserRegistration", "registrationButton", popPop);
    }

    /**
     * exit the application
     */
    public void exitApplication() {
        Shutdown.getInstance().exit();
    }

//    /**
//     * displays the nodes of the map
//     */
//    public void displayNodesNav() {
//
//        resizingInfo();
//
//        for (Node n : listOfNodes) {
//            if (((n.getFloor().equals("1") || n.getFloor().equals("G") || n.getFloor().equals("")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") || n.getBuilding().equals("")))) {
//                Circle circle = new Circle(xCoordOnTopElement(n.getX()), yCoordOnTopElement(n.getY()), 8);
//                circle.setFill(Color.OLIVE);
//                topElements.getChildren().add(circle);
//            }
//        }
//    }

    public boolean nodeWithinSpec(Node n) {
        return ((n.getFloor().equals("1") || n.getFloor().equals("G") || n.getFloor().equals("")) && (n.getBuilding().equals("Tower") || n.getBuilding().equals("45 Francis") || n.getBuilding().equals("15 Francis") || n.getBuilding().equals("Parking") || n.getBuilding().equals("")));
    }
}
