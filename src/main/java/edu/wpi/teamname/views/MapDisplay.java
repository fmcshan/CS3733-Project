package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import edu.wpi.teamname.Database.CSVOperator;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.simplify.Shutdown;
import edu.wpi.teamname.views.manager.LevelChangeListener;
import edu.wpi.teamname.views.manager.LevelManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MapDisplay implements LevelChangeListener {

    @FXML
    public Navigation navigation;
    double scaledWidth = 5000;
    double scaledHeight = 3400.0;
    double scaledX = 0;
    ArrayList<ArrayList<Node>> currentPath = new ArrayList<>();
    ArrayList<Node> listOfNode = new ArrayList<>();
    double scaledY = 0;
    double mapWidth; //= 1000.0;
    double mapHeight;// = 680.0;
    double fileWidth; //= 5000.0;
    double fileHeight;// = 3400.0;
    double fileFxWidthRatio = mapWidth / fileWidth;
    double fileFxHeightRatio = mapHeight / fileHeight;
    ArrayList<Node> listOfNodes;
    ArrayList<Node> nodes;
    ArrayList<Node> localNodes = new ArrayList<>(); // Nodes within current parameters (IE: floor)
    ArrayList<Edge> edges;
    ArrayList<Edge> localEdges = new ArrayList<>(); // Edges within current parameters (IE: floor)
    ArrayList<Node> startAndEnd = new ArrayList<>();
    HashMap<String, Node> nodesMap = new HashMap<>();
    HashMap<String, Node> localNodesMap = new HashMap<>();
    HashMap<String, Edge> edgesMap = new HashMap<>();
    HashMap<Circle, Node> renderedNodeMap = new HashMap<>();
    HashMap<Line, Edge> renderedEdgeMap = new HashMap<>();
    HashMap<Node, ArrayList<Edge>> edgesBetweenFloors = new HashMap<>();
    boolean nodeBeingDragged = false;
    Circle renderedAddNode;
    int addNodeX;
    int addNodeY;
    Circle dragStart;
    Circle dragEnd;
    Line renderedEdgePreview;
    Node addEdgeStart;
    Node addEdgeEnd;
    Node selectedNode;
    Edge selectedEdge;
    Circle draggedCircle;
    Node draggedNode;
    Node startNode;
    Node endNode;
    HashMap<String, ArrayList<Text>> nodeToTextMap = new HashMap<>();
    HashMap<Text, Edge> textToEdgeMap = new HashMap<>();
    ArrayList<Text> allText = new ArrayList<>();
    HashMap<Text, Node> textToNodeMap = new HashMap<>();
    HashMap<String, Node> portalNodeMap = new HashMap<>();
    HashMap<Node, Circle> edgeCircles = new HashMap<>();
    HashMap<Node, ArrayList<String>> textLevels = new HashMap<>();
    boolean start = false;
    boolean end = false;
    Node sNode;
    Node fNode;
    @FXML
    VBox popPop, popPop2, adminPop, requestPop, registrationPop, employeePop; // vbox to populate with different fxml such as Navigation/Requests/Login
    @FXML
    Path tonysPath; // the path displayed on the map
    @FXML
    JFXButton adminButton; // button that allows you to sign in
    @FXML
    ImageView hospitalMap;
    @FXML
    AnchorPane topElements;
    @FXML
    AnchorPane onTopOfTopElements;
    @FXML
    StackPane stackPane; // the pane the map is housed in
    @FXML
    VBox addNodeField;
    @FXML
    VBox addEdgeField;
    @FXML
    AnchorPane pathAnchor;
    @FXML
    AnchorPane anchor;
    @FXML
    JFXTextField edgeIdPreview;
    ZoomAndPan zooM;
    @FXML
    private JFXButton floor3Bttn, floor2Bttn, floor1Bttn, L1Bttn, L2Bttn, groundBttn;
    @FXML
    private JFXButton edge_3;
    @FXML
    private JFXButton edge_2;
    @FXML
    private JFXButton edge_1;
    @FXML
    private JFXButton edge_g;
    @FXML
    private JFXButton edge_l1;
    @FXML
    private JFXButton edge_l2;
    @FXML
    private JFXTextField nodeId;
    @FXML
    private JFXTextField nodeBuilding;
    @FXML
    private JFXTextField nodeType;
    @FXML
    private JFXTextField nodeShortName;
    @FXML
    private JFXTextField nodeLongName;
    @FXML
    private Label addEdgeWarning;
    @FXML
    private VBox editNode; // Edit node menu
    @FXML
    private VBox edgeBetweenFloors;
    @FXML
    private JFXTextField editNodeBuilding;
    @FXML
    private JFXTextField editNodeType;
    @FXML
    private JFXTextField editNodeShortName;
    @FXML
    private JFXTextField editNodeLongName;
    @FXML
    private VBox deleteEdge;
    @FXML
    private JFXTextField deleteEdgeId;
    @FXML
    private VBox rightClick;

    public MapDisplay() {
        zooM = new ZoomAndPan(this);

    }

    public Path getTonysPath() {
        return tonysPath;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public void addStartAndEnd(Node addedNode) {
        startAndEnd.add(addedNode);
    }

    public void clearStartAndEnd() {
        startAndEnd.clear();
    }

    public ArrayList<Node> getStartAndEnd() {
        return startAndEnd;
    }

    /**
     * getter for popPop Vbox
     *
     * @return popPop
     */
    public VBox getPopPop() {
        return popPop;
    }

    public VBox getPopPop2() {
        return popPop2;
    }

    /**
     * Display localNodes on the map
     *
     * @param _nodes   List of nodes to display
     * @param _opacity Node opacity
     */
    public void displayNodes(ArrayList<Node> _nodes, double _opacity, boolean showHall) {
        resizingInfo(); // Set resizing info

        ArrayList<Node> nodes = _nodes;

        if (startNode != null && endNode != null) {
            nodes.clear();
            if (LevelManager.getInstance().getFloor().equals(startNode.getFloor())) {
                nodes.add(startNode);
            }
            if (LevelManager.getInstance().getFloor().equals(endNode.getFloor())) {
                nodes.add(endNode);
            }
        }

        currentPath.forEach(n -> {
            n.forEach(h -> {
                listOfNode.add(h);
                nodes.add(h);
            });
        });

        nodes.forEach(n -> { // For each node in localNodes

            if (n.getNodeType().equals("HALL") && !showHall) {
                return;
            }

            if (n.equals(draggedNode)) {
                return;
            }
            if (onScreen(n)) {
                Tooltip tooltip = new Tooltip(n.getLongName());
                Circle circle = new Circle(xCoordOnTopElement(n.getX()), yCoordOnTopElement(n.getY()), 8); // New node/cicle
                circle.setStrokeWidth(4); // Set the stroke with to 4
                circle.setStroke(Color.TRANSPARENT);
                circle.setFill(Color.OLIVE); // Set node color to olive
                circle.setOpacity(_opacity); // Set node opacity (input param)

                renderedNodeMap.put(circle, n); // Link the rendered circle to the node in renderedNodeMap
                onTopOfTopElements.getChildren().add(circle); // Render the node

                if (n.equals(startNode) || n.equals(endNode)) {
                    circle.setFill(Color.RED);
                    circle.setRadius(10);
                }

                if (listOfNode.contains(n) && !n.equals(startNode) && !n.equals(endNode)) {
                    circle.setFill(Color.RED);
                    circle.setRadius(6);
                }

                circle.setOnMouseEntered(e -> { // Show a hover effect
                    if (listOfNode.contains(n) && !n.equals(startNode) && !n.equals(endNode)) {
                        circle.setRadius(8);
                    } else {
                        circle.setRadius(12); // Increase radius
                    }
                    circle.setOpacity(0.6);
                });
                circle.setOnMouseExited(e -> { // Hide hover effect
                    if (listOfNode.contains(n) && !n.equals(startNode) && !n.equals(endNode)) {
                        circle.setRadius(6);
                    } else {
                        circle.setRadius(8); // Reset/set radius
                    }
                    circle.setOpacity(0.8); // Reset/set opacity
                    tooltip.hide();
                });
                circle.setOnMouseMoved(
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                // +15 moves the tooltip 15 pixels below the mouse cursor to avoid flicker
                                tooltip.show(circle, event.getScreenX() - 15, event.getScreenY() + 20);
                            }
                        });

                if (!LoadFXML.getCurrentWindow().equals("mapEditorBar")) {
                    return; // Don't process drags outside of the map editor.
                }

                circle.setOnMouseDragged(e -> {
                    nodeBeingDragged = true;
                    draggedCircle = (Circle) e.getTarget();
                    draggedCircle.setCenterX(e.getX());
                    draggedCircle.setCenterY(e.getY());
                    draggedNode = renderedNodeMap.get(draggedCircle);
                    refreshDraggedEdges();
                });

                circle.setOnMouseReleased(e -> {
                    if (!nodeBeingDragged) {
                        return;
                    }
                    nodeBeingDragged = false;
                    Submit.getInstance().editNode(new Node(
                            draggedNode.getNodeID(),
                            (int) actualX(draggedCircle.getCenterX()),
                            (int) actualY(draggedCircle.getCenterY()),
                            draggedNode.getFloor(),
                            draggedNode.getBuilding(),
                            draggedNode.getNodeType(),
                            draggedNode.getLongName(),
                            draggedNode.getShortName()
                    ));
                    draggedCircle = null;
                    draggedNode = null;
                    refreshData();
                    renderMap();
                });
            }
        });
    }

    /**
     * To prevent breaking changes
     *
     * @param _opacity Node opacity
     */
    public void displayNodes(double _opacity) {
        displayNodes(localNodes, _opacity, true);
    }

    public void displayHotspots(double _opacity) {
        ArrayList<Node> toDisplay = (ArrayList<Node>) localNodes.clone();
        displayNodes(toDisplay, _opacity, false);
    }

    /**
     * Display localEdges on the map
     *
     * @param _opacity Edge opacity
     */
    public void displayEdges(double _opacity) {
        resizingInfo(); // Set sizing info
        portalNodeMap.forEach((Id, n) -> {
            if (localNodesMap.containsKey(Id)) {
                Circle circle = edgeCircles.get(n);
                circle.setCenterX(xCoordOnTopElement(n.getX()));
                circle.setCenterY(yCoordOnTopElement(n.getY()));
                circle.setRadius(15);
                circle.setFill(Color.YELLOW);
                onTopOfTopElements.getChildren().add(circle);

//                if (textLevels != null) {
//                    final double[] rotation = {1}; // in radians
//                    AtomicReference<Double> x = new AtomicReference<>((double) 0);
//                    AtomicReference<Double> y = new AtomicReference<>((double) 0);
//                    AtomicInteger ic = new AtomicInteger();
//                    Font font = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16);
//
//                    if (nodeToTextMap.containsKey(Id)) {
//
//
//                        nodeToTextMap.get(n.getNodeID()).forEach(genuineText -> {
//                            x.set(xCoordOnTopElement((int) (n.getX())) + Math.cos(rotation[0]) * hospitalMap.boundsInParentProperty().get().getWidth() * 0.0125);
//                            y.set(yCoordOnTopElement((int) (n.getY())) + Math.sin(rotation[0]) * hospitalMap.boundsInParentProperty().get().getWidth() * 0.0125);
//                            genuineText.setX(x.get());
//                            genuineText.setY(y.get());
//                            genuineText.setFill(Color.RED);
//                            genuineText.setRotate(rotation[0]);
//                            genuineText.setFont(font);
//                            onTopOfTopElements.getChildren().add(genuineText);
//                            rotation[0] += 1;
//                        });
//                    }
//                }
            }
        });
        localEdges.forEach(e -> { // For edge in localEdges
            if (localNodesMap.containsKey(e.getStartNode()) && localNodesMap.containsKey(e.getEndNode())) { // If nodes exist
                if (onScreen(localNodesMap.get(e.getStartNode())) || onScreen(localNodesMap.get(e.getEndNode()))) { //draw the edge if one of the ends is on screen.
                    double startX = xCoordOnTopElement(localNodesMap.get(e.getStartNode()).getX());
                    double startY = yCoordOnTopElement(localNodesMap.get(e.getStartNode()).getY());
                    double endX = xCoordOnTopElement(localNodesMap.get(e.getEndNode()).getX());
                    double endY = yCoordOnTopElement(localNodesMap.get(e.getEndNode()).getY());

                    if (draggedNode != null && e.getStartNode().equals(draggedNode.getNodeID())) {
                        startX = draggedCircle.getCenterX();
                        startY = draggedCircle.getCenterY();
                    }
                    if (draggedNode != null && e.getEndNode().equals(draggedNode.getNodeID())) {
                        endX = draggedCircle.getCenterX();
                        endY = draggedCircle.getCenterY();
                    }
                    // Create edge
                    LineBuilder<?> edgeLocation = LineBuilder.create().startX(startX).startY(startY).endX(endX).endY(endY);
                    Line edge = edgeLocation.stroke(Color.BLUE).strokeWidth(3).opacity(_opacity).build(); // Style edge
                    renderedEdgeMap.put(edge, e);
                    onTopOfTopElements.getChildren().add(edge); // Render edge

                    edge.setOnMouseEntered(t -> { // Show a hover effect
                        edge.setStrokeWidth(6); // Increase width
                        edge.setOpacity(1); // Increase opacity
                    });

                    edge.setOnMouseExited(t -> { // Hide hover effect
                        edge.setOpacity(_opacity); // Reset/set opacity
                        edge.setStrokeWidth(3); // Reset/set stroke width
                    });
                }
            }
        });
    }

    /**
     * Initialize the map editor/display
     */
    public void initMapEditor() {
        LevelManager.getInstance().addListener(this);
        popPop.setPickOnBounds(false); // Set popPop to disregard clicks
        popPop2.setPickOnBounds(false); // Set popPop to disregard clicks
        displayEdges(.6); // Render edges at 0.6 opacity
        displayNodes(.8); // Render nodes at 0.8 opacity

        //   onTopOfTopElements.addEventHandler(MouseEvent.MOUSE_CLICKED, this::processClick); // Handled in zoom/pan now
        onTopOfTopElements.addEventHandler(MouseEvent.MOUSE_MOVED, this::processMovement); // Process mouse movement events

        addEscListeners(addNodeField, addEdgeField, editNode, deleteEdge, rightClick, anchor);
        zooM.zoomAndPan();
    }

    private void addEscListener(javafx.scene.Node _toAdd) {
        _toAdd.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() { // Exit popups on "Esc" key
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    hidePopups(); // Hide all popups
                }
            }
        });
    }

    private void addEscListeners(javafx.scene.Node... _toAdd) {
        for (javafx.scene.Node node : _toAdd) {
            addEscListener(node);
        }
    }

    public void hidePopups() {
        hideAddNodePopup();
        hideAddEdgePopup();
        hideEditNodePopup();
        hideDeleteEdgePopup();
        hideRightClickMenu();
    }

    /**
     * Process mouse movement (IE: display edge preview)
     *
     * @param t Mouse event
     */
    private void processMovement(MouseEvent t) {
        if (dragStart != null && dragEnd != null) {
            return;
        }
        if (dragStart != null) {
            if (renderedEdgePreview != null) {
                topElements.getChildren().remove(renderedEdgePreview);
            }
            LineBuilder<?> edgeLocation = LineBuilder.create().startX(dragStart.getCenterX()).startY(dragStart.getCenterY()).endX(t.getX()).endY(t.getY());
            this.renderedEdgePreview = edgeLocation.stroke(Color.TOMATO).strokeWidth(3).opacity(1).build();
            this.renderedEdgePreview.setPickOnBounds(false);
            topElements.getChildren().add(renderedEdgePreview);
        }
    }

    /**
     * stores needed resizing info for scaling the displayed nodes on the map as the window changes
     */
    public void resizingInfo() {
        mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
        mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
        fileWidth = hospitalMap.getImage().getWidth();
        fileHeight = hospitalMap.getImage().getHeight();
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
     * Converts a scaled X coordinate to the actual coordinate
     *
     * @param _x Scaled x coordinate
     * @return The actual (non-scaled) x coordinate
     */
    public double actualX(double _x) {
        double fileWidth = 5000.0;
        double fileHeight = 3400.0;

        double widthScale = scaledWidth / fileWidth;
        double heightScale = scaledHeight / fileHeight;

        double windowWidth = hospitalMap.boundsInParentProperty().get().getWidth() / fileWidth;
        double windowHeight = hospitalMap.boundsInParentProperty().get().getHeight() / fileHeight;
        double windowSmallestScale = Math.max(Math.min(windowHeight, windowWidth), 0);
        double viewportSmallestScale = Math.max(Math.min(heightScale, widthScale), 0);
        return (((_x / windowSmallestScale) * viewportSmallestScale) + scaledX);
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

    /**
     * Converts a scaled Y coordinate to the actual coordinate
     *
     * @param _y Scaled Y coordinate
     * @return The actual (non-scaled) x coordinate
     */
    public double actualY(double _y) {
        double fileWidth = 5000.0;
        double fileHeight = 3400.0;

        double widthScale = scaledWidth / fileWidth;
        double heightScale = scaledHeight / fileHeight;

        double windowWidth = hospitalMap.boundsInParentProperty().get().getWidth() / fileWidth;
        double windowHeight = hospitalMap.boundsInParentProperty().get().getHeight() / fileHeight;
        double windowSmallestScale = Math.max(Math.min(windowHeight, windowWidth), 0);
        double viewportSmallestScale = Math.max(Math.min(heightScale, widthScale), 0);
        return (((_y / windowSmallestScale) * viewportSmallestScale) + scaledY);
    }

    private void refreshDraggedEdges() {
        renderedEdgeMap.forEach((l, e) -> {
            String draggedNodeId = draggedNode.getNodeID();
            if (e.getStartNode().equals(draggedNodeId)) {
                l.setStartX(draggedCircle.getCenterX());
                l.setStartY(draggedCircle.getCenterY());
            }

            if (e.getEndNode().equals(draggedNodeId)) {
                l.setEndX(draggedCircle.getCenterX());
                l.setEndY(draggedCircle.getCenterY());
            }
        });
    }

    /**
     * Pull from LocalStorage and refresh data
     */
    void refreshData() {
        nodeToTextMap = new HashMap<>();
        allText = new ArrayList<>();
        nodes = LocalStorage.getInstance().getNodes(); // Get nodes from LocalStorage
        localNodes = new ArrayList<>(); // Reset localNodes
        nodesMap.clear();
        nodes.forEach(n -> { // For node in nodes
            if (nodeWithinSpec(n)) { // If node is within spec (IE: building/floor)
                localNodes.add(n); // Add to local nodes
            }
            nodesMap.put(n.getNodeID(), n);
        });

        localNodesMap.clear(); // CLear the node map
        localNodes.forEach(n -> { // For node in localNodes
            localNodesMap.put(n.getNodeID(), n); // Add node to localNodesMap
        });

        edges = LocalStorage.getInstance().getEdges(); // Get edges from LocalStorage
        localEdges = new ArrayList<>(); // Reset localEdges
        edges.forEach(e -> { // For edge in edges
            if (localNodesMap.containsKey(e.getStartNode()) && localNodesMap.containsKey(e.getEndNode())) { // If nodes exist in nodes map
                /* We don't have to check if the start and end nodes
                are within spec as all nodes in localNodesMap are in spec. */
                localEdges.add(e); // Add edge to local edges
            }

            if (nodesMap.containsKey(e.getStartNode()) && nodesMap.containsKey(e.getEndNode())) {
                Node startNode = nodesMap.get(e.getStartNode());
                Node endNode = nodesMap.get(e.getEndNode());
                if (!startNode.getFloor().equals(endNode.getFloor())) {
                    if (edgesBetweenFloors.containsKey(startNode)) {
                        edgesBetweenFloors.get(startNode).add(e);
                    } else {
                        ArrayList<Edge> edgeArray = new ArrayList<>();
                        edgeArray.add(e);
                        edgesBetweenFloors.put(startNode, edgeArray);
                    }
                }
            }
        });
        edgesMap.clear(); // Clear edge map
        localEdges.forEach(e -> { // For edge in localEdges
            edgesMap.put(e.getEdgeID(), e); // Add edge to edgesMap
        });
    }

    /**
     * Refresh/display map
     */
    public void renderMap() {
        clearMap(); // Clear map
        displayEdges(.6); // Display edges at 0.6 opacity
        displayNodes(.8); // Display nodes at 0.8 opacity
        dragStart = null; // Reset dragStart (IE: user clicks away)
        dragEnd = null; // Reset dragEnd (IE: user clicks away)
    }

    /**
     * Process click events. Handles showing/hiding the add node
     * and add edge popups, as well as add edge tracking vars.
     *
     * @param t Mouse Event
     */
    public void processClick(MouseEvent t, boolean dragged) {
        if (dragged) {
            return;
        }
        if (LoadFXML.getCurrentWindow().equals("navBar")) {
            if (t.getTarget() instanceof Circle) {
                if (endNode != null) {
                    navigation.cancelNavigation();
                    return;
                }
                if (startNode == null) {
                    ((Circle) t.getTarget()).setFill(Color.RED);
                    ((Circle) t.getTarget()).setRadius(12);
                    startNode = renderedNodeMap.get((Circle) t.getTarget()); // Get potential start node for pathfinding
                    navigation.setFromCombo(startNode);
                } else {
                    ((Circle) t.getTarget()).setFill(Color.RED);
                    endNode = renderedNodeMap.get((Circle) t.getTarget()); // Get potential end node for pathfinding
                    ((Circle) t.getTarget()).setRadius(12);
                    navigation.setToCombo(endNode);
                }
                return;
            }
        }
        if (!LoadFXML.getCurrentWindow().equals("mapEditorBar")) {
            System.out.println("i got in here though");
            return; // Don't process clicks outside of the map editor.
        }
        if (t.getButton() == MouseButton.SECONDARY) {
            processRightClick(t);
            return;
        } else if (t.getButton() != MouseButton.PRIMARY) {
            return;
        }

        if (t.getTarget() instanceof Circle) { // If a circle object is clicked

//            if (!start && (renderedNodeMap.get((Circle) t.getTarget()).getNodeType().equals("STAI") || renderedNodeMap.get((Circle) t.getTarget()).getNodeType().equals("ELEV"))) {
//                start = true;
//                sNode = renderedNodeMap.get(((Circle) t.getTarget()));
//                System.out.println("start " + start);
//            } else if (!end && (renderedNodeMap.get((Circle) t.getTarget()).getNodeType().equals("STAI") || renderedNodeMap.get((Circle) t.getTarget()).getNodeType().equals("ELEV"))) {
//                fNode = renderedNodeMap.get(((Circle) t.getTarget()));
//                end = true;
//                System.out.println("end " + end);
//            }
//            if (start && end) {
//                Submit.getInstance().addEdge(new Edge(sNode.getNodeID() + "_" + fNode.getNodeID(), sNode.getNodeID(), fNode.getNodeID()));
//                System.out.println("edge submitted");
//                refreshData();
//                renderMap();
//                start = false;
//                end = false;
//                fNode = null;
//                sNode = null;
//                floor1Bttn.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));
//                floor2Bttn.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));
//                floor3Bttn.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(0))));
//                L1Bttn.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));
//                L2Bttn.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(0))));
//                groundBttn.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));
//                return;
//            }
            if (dragStart == null) { // If dragStart isn't null (IE: If the user has started to create an edge)
                hidePopups(); // Hide all popups
                dragStart = (Circle) t.getTarget(); // Set selected circle as dragStart (new edge start)
            } else if (dragEnd == null) { // Else if dragEnd isn't null (IE: If the user is partway through creating an edge)
                dragEnd = (Circle) t.getTarget(); // Set selected circle as dragEnd (new edge end)

                // Build line between dragStart and dragEnd (potential new edge)
                LineBuilder<?> edgeLocation = LineBuilder.create().startX(dragStart.getCenterX()).startY(dragStart.getCenterY()).endX(dragEnd.getCenterX()).endY(dragEnd.getCenterY());
                topElements.getChildren().remove(renderedEdgePreview); // Remove previously displayed edge preview
                this.renderedEdgePreview = edgeLocation.stroke(Color.RED).strokeWidth(3).opacity(1).build(); // Set potential edge styling
                topElements.getChildren().add(renderedEdgePreview); // Display potential edge

                // Edge popup
                addEdgeStart = renderedNodeMap.get(dragStart); // Get potential edge start node
                addEdgeEnd = renderedNodeMap.get(dragEnd); // Get potential edge end node
                showEdgePopup(renderedNodeMap.get(dragStart), renderedNodeMap.get(dragEnd), dragEnd); // Show edge confirmation window

                dragStart = null; // Reset dragStart
                dragEnd = null; // Reset dragEnd
            }
            return;
        }

        hidePopups(); // Hide all popups

        dragStart = null; // Reset dragStart (IE: user clicks away)
        dragEnd = null; // Reset dragEnd (IE: user clicks away)

        addNodeX = (int) actualX(t.getX()); // Set the potential new node X coords
        addNodeY = (int) actualY(t.getY()); // Set the potential new node Y coords

        addNodeField.setPickOnBounds(true); // Enable clicking on the add node popup
        addNodeField.setVisible(true); // Show the add node popup

        // Relative to mouse
        if (t.getY() < onTopOfTopElements.getHeight() / 2) { // If mouse is in bottom half of screen
            addNodeField.setTranslateY(t.getY() + 20); // Show above
        } else { // Else (if mouse is in top half of screen)
            addNodeField.setTranslateY(t.getY() - addNodeField.getHeight() - 20); // Show below
        }

        // Relative to mouse
        if (onTopOfTopElements.getWidth() * 0.2 > t.getX()) { // If mouse is in the left 1/5th of screen
            addNodeField.setTranslateX(t.getX()); // Show popup to the right
        } else if (onTopOfTopElements.getWidth() * 0.8 > t.getX()) { // Else if it's in the middle
            addNodeField.setTranslateX(t.getX() - (0.5 * addNodeField.getWidth())); // Show popup in the center
        } else { // Else (if mouse is in the right 1/5th)
            addNodeField.setTranslateX(t.getX() - addNodeField.getWidth()); // Show popup to the left
        }

        if (renderedAddNode != null) { // If an old node is rendered
            onTopOfTopElements.getChildren().remove(renderedAddNode); // Hide old node
        }
        renderedAddNode = new Circle(t.getX(), t.getY(), 8); // Build potential new node
        renderedAddNode.setFill(Color.TOMATO); // Set color
        renderedAddNode.setOpacity(0.9); // Set opacity
        onTopOfTopElements.getChildren().add(renderedAddNode); // Display potential new node
    }

    /**
     * @param n the node to be compared
     * @return true if the node is inside the viewport
     */
    public boolean onScreen(Node n) {
        if (hospitalMap.getViewport().getMinX() < n.getX() && n.getX() < hospitalMap.getViewport().getMaxX()) { // compare X coords
            if (hospitalMap.getViewport().getMinY() < n.getY() && n.getY() < hospitalMap.getViewport().getMaxY()) { //compare Y coords
                return true;
            }
        }
        return false;
    }

    /**
     * Process right clicks
     *
     * @param t Mouse Event
     */
    private void processRightClick(MouseEvent t) {
        if (t.getTarget() instanceof Text) {
            Edge toRemove = textToEdgeMap.get(t.getTarget());
            System.out.println("Gotcha");
            Submit.getInstance().removeEdge(toRemove);
            refreshData();
            renderMap();
            return;
        }
        if (t.getTarget() instanceof Circle) { // If the user clicks a circle/node
            Node toEdit = renderedNodeMap.get(t.getTarget()); // Get the node
            showEditNodePopup(toEdit, t); // Show edit node popup
            selectedNode = toEdit; // Update selectedNode
        } else if (t.getTarget() instanceof Line) { // Else if the user clicks a line/edge
            Edge toDelete = renderedEdgeMap.get(t.getTarget()); // Get the edge
            showRemoveEdgePopup(toDelete, t); // Show the remove edge popup
            selectedEdge = toDelete; // Updated selected edge
        } else {
            showRightClickMenu(t); // Show the right click menu
        }
    }

    private void disableButtons(JFXButton... _toDisable) {
        for (JFXButton btn : _toDisable) {
            btn.setDisable(true);
        }
    }

    /**
     * Hide the edit node popup
     */
    private void hideEditNodePopup() {
        editNode.setVisible(false); // Hide the popup
        editNode.setPickOnBounds(false); // Set clickable to false
        edgeBetweenFloors.setVisible(false); // Hide the popup
        edgeBetweenFloors.setPickOnBounds(false); // Set clickable to false
        editNodeBuilding.setText(""); // Reset the node building field
        editNodeType.setText(""); // Reset the node type field
        editNodeShortName.setText(""); // Reset the node short name field
        editNodeLongName.setText(""); // Reset the node long name field
        selectedNode = null; // Reset the selected node
        disableButtons(edge_1, edge_2, edge_3, edge_g, edge_l1, edge_l2); // Disable edge buttons
    }

    /**
     * Show the edit node popup
     *
     * @param _toEdit The node to edit
     * @param t       Mouse Event
     */
    private void showEditNodePopup(Node _toEdit, MouseEvent t) {
        hidePopups(); // Hide all popups

        editNodeBuilding.setText(_toEdit.getBuilding()); // Set the building field
        editNodeType.setText(_toEdit.getNodeType()); // Set the type field
        editNodeShortName.setText(_toEdit.getShortName()); // Set the short name field
        editNodeLongName.setText(_toEdit.getLongName()); // Set the long name field

        // TODO enable edge buttons
        if (edgesBetweenFloors.get(_toEdit) != null) {
            edgesBetweenFloors.get(_toEdit).forEach(e -> {
                switch (nodesMap.get(e.getEndNode()).getFloor()) {
                    case "3":
                        edge_3.setDisable(false);
                        break;
                    case "2":
                        edge_2.setDisable(false);
                        break;
                    case "1":
                        edge_1.setDisable(false);
                        break;
                    case "g":
                        edge_g.setDisable(false);
                        break;
                    case "L1":
                        edge_l1.setDisable(false);
                        break;
                    case "L2":
                        edge_l2.setDisable(false);
                        break;

                }
            });
        }

        editNode.setVisible(true); // Set visible to true
        edgeBetweenFloors.setVisible(true); // Set visible to true
        editNode.setPickOnBounds(true); // Set clickable to true
        edgeBetweenFloors.setPickOnBounds(true); // Set clickable to true
        // Relative to mouse
        if (t.getY() < onTopOfTopElements.getHeight() / 2) { // If mouse is in bottom half of screen
            editNode.setTranslateY(t.getY() + 20); // Show above
            edgeBetweenFloors.setTranslateY(t.getY() + 20); // Show above
        } else { // Else (if mouse is in top half of screen)
            editNode.setTranslateY(t.getY() - editNode.getHeight() - 20); // Show below
            edgeBetweenFloors.setTranslateY(t.getY() - editNode.getHeight() - 20); // Show below
        }

        // Relative to mouse
        if (onTopOfTopElements.getWidth() * 0.2 > t.getX()) { // If mouse is in the left 1/5th of screen
            editNode.setTranslateX(t.getX()); // Show popup to the right
            edgeBetweenFloors.setTranslateX(t.getX() + editNode.getWidth() + 6); // Show popup to the right
        } else if (onTopOfTopElements.getWidth() * 0.8 > t.getX()) { // Else if it's in the middle
            editNode.setTranslateX(t.getX() - (0.5 * editNode.getWidth())); // Show popup in the center
            edgeBetweenFloors.setTranslateX(t.getX() + (editNode.getWidth() / 2) + 6); // Show popup to the right
        } else { // Else (if mouse is in the right 1/5th)
            editNode.setTranslateX(t.getX() - editNode.getWidth()); // Show popup to the left
            edgeBetweenFloors.setTranslateX(t.getX() - editNode.getWidth() - edgeBetweenFloors.getWidth() - 6); // Show popup to the left
        }
    }

    /**
     * Show the remove edge popup
     *
     * @param _toDelete Edge to delete
     * @param t         Mouse Event
     */
    private void showRemoveEdgePopup(Edge _toDelete, MouseEvent t) {
        hidePopups(); // Hide all popups

        deleteEdgeId.setText(_toDelete.getEdgeID()); // Set the edgeId field/preview

        deleteEdge.setVisible(true); // Show the remove edge popup
        deleteEdge.setPickOnBounds(true); // Set clickable to true
        // Relative to mouse
        if (t.getY() < onTopOfTopElements.getHeight() / 2) { // If mouse is in bottom half of screen
            deleteEdge.setTranslateY(t.getY() + 20); // Show above
        } else { // Else (if mouse is in top half of screen)
            deleteEdge.setTranslateY(t.getY() - deleteEdge.getHeight() - 20); // Show below
        }

        // Relative to mouse
        if (onTopOfTopElements.getWidth() * 0.2 > t.getX()) { // If mouse is in the left 1/5th of screen
            deleteEdge.setTranslateX(t.getX()); // Show popup to the right
        } else if (onTopOfTopElements.getWidth() * 0.8 > t.getX()) { // Else if it's in the middle
            deleteEdge.setTranslateX(t.getX() - (0.5 * deleteEdge.getWidth())); // Show popup in the center
        } else { // Else (if mouse is in the right 1/5th)
            deleteEdge.setTranslateX(t.getX() - deleteEdge.getWidth()); // Show popup to the left
        }
    }

    /**
     * Hide the delete edge popup
     */
    private void hideDeleteEdgePopup() {
        deleteEdge.setVisible(false); // Hide the popup
        deleteEdge.setPickOnBounds(false); // Set clickable to false

        deleteEdgeId.setText(""); // Reset the edge Id field
        selectedEdge = null; // Reset the selected edge
    }

    /**
     * Show the right click menu
     *
     * @param t Mouse Event
     */
    private void showRightClickMenu(MouseEvent t) {
        hidePopups(); // Hide all popups

        rightClick.setVisible(true); // Show the right click menu
        rightClick.setPickOnBounds(true); // Set clickable to true

        // Relative to mouse
        if (t.getY() < onTopOfTopElements.getHeight() / 2) { // If mouse is in bottom half of screen
            rightClick.setTranslateY(t.getY()); // Show above
        } else { // Else (if mouse is in top half of screen)
            rightClick.setTranslateY(t.getY() - rightClick.getHeight()); // Show below
        }

        // Relative to mouse
        if (onTopOfTopElements.getWidth() * 0.2 > t.getX()) { // If mouse is in the left 1/5th of screen
            rightClick.setTranslateX(t.getX()); // Show popup to the right
        } else if (onTopOfTopElements.getWidth() * 0.8 > t.getX()) { // Else if it's in the middle
            rightClick.setTranslateX(t.getX() - (0.5 * rightClick.getWidth())); // Show popup in the center
        } else { // Else (if mouse is in the right 1/5th)
            rightClick.setTranslateX(t.getX() - rightClick.getWidth()); // Show popup to the left
        }
    }

    /**
     * Hide the right click menu
     */
    private void hideRightClickMenu() {
        rightClick.setVisible(false); // Hide the right click menu
        rightClick.setPickOnBounds(false); // Set clickable to false
    }

    /**
     * Save the edited node
     *
     * @param e Action Event
     */
    @FXML
    private void editNodeSave(ActionEvent e) {
        Node newNode = new Node( // Create new node object
                selectedNode.getNodeID(), // Same NodeID
                selectedNode.getX(),
                selectedNode.getY(),
                LevelManager.getInstance().getFloor(),
                editNodeBuilding.getText(),
                editNodeType.getText(),
                editNodeLongName.getText(),
                editNodeShortName.getText()
        );
        Submit.getInstance().editNode(newNode); // Update LocalStorage/the database
        hidePopups(); // Hide all popups
        refreshData(); // Refresh the data from LocalStorage
        renderMap(); // Render/refresh the map (with the updated data)
    }

    /**
     * Delete the currently selected edge
     *
     * @param e Action Event
     */
    @FXML
    private void confirmDeleteEdge(ActionEvent e) {
        Submit.getInstance().removeEdge(selectedEdge); // Remove the selected edge
        hidePopups(); // Hide all popups
        refreshData(); // Refresh the node and edge data from LocalStorage
        renderMap(); // Render/display the map (with the updated information)
    }

    /**
     * Delete the currently selected node
     *
     * @param e Action Event
     */
    @FXML
    private void deleteNode(ActionEvent e) {
        Submit.getInstance().removeNode(selectedNode); // Remove the selected node
        hidePopups(); // Hide all popups
        refreshData(); // Refresh the node and edge data from LocalStorage
        renderMap(); // Render/display the map (with the updated information)
    }

    /**
     * Show the edge confirmation popup.
     *
     * @param startNode Edge start node
     * @param endNode   Edge end node
     * @param _dragEnd  Edge end circle
     */
    private void showEdgePopup(Node startNode, Node endNode, Circle _dragEnd) {
        int x = (int) _dragEnd.getCenterX(); // Get end node X position (for UI use)
        int y = (int) _dragEnd.getCenterY(); // Get end node Y position (for UI use)
        addEdgeField.setPickOnBounds(true); // Set popup as clickable
        addEdgeField.setVisible(true); // Show popup
        edgeIdPreview.setText(startNode.getNodeID() + "_" + endNode.getNodeID()); // Prefill edgeId (not editable)

        // Relative to mouse
        if (y < onTopOfTopElements.getHeight() / 2) { // If mouse is in the bottom half of screen
            addEdgeField.setTranslateY(y + 20); // Show popup above
        } else { // Else (if mouse is in top half of screen)
            addEdgeField.setTranslateY(y - addEdgeField.getHeight() - 20); // Show popup below
        }

        if (onTopOfTopElements.getWidth() * 0.2 > x) { // If mouse is in the left 1/5th of screen
            addEdgeField.setTranslateX(x); // Show popup to the right
        } else if (onTopOfTopElements.getWidth() * 0.8 > x) { // Else if it's in the middle
            addEdgeField.setTranslateX(x - (0.5 * addEdgeField.getWidth())); // Show popup in the center
        } else { // Else if it's in the right 1/5th of screen
            addEdgeField.setTranslateX(x - addEdgeField.getWidth()); // Show popup to the left
        }
    }

    /**
     * Hide add edge confirmation popup
     */
    private void hideAddEdgePopup() {
        addEdgeField.setPickOnBounds(false); // Set clickable to false
        addEdgeField.setVisible(false); // Hide popup
        dragStart = null; // Reset dragStart
        dragEnd = null; // Reset dragEnd
        addEdgeWarning.setVisible(false); // Hide warning message
        edgeIdPreview.setText(""); // Clear edgeId
        if (renderedEdgePreview != null) { // If edge preview rendered
            topElements.getChildren().remove(renderedEdgePreview); // Hide edge preview
        }
    }

    /**
     * Hide add node popup
     */
    public void hideAddNodePopup() {
        addNodeField.setPickOnBounds(false); // Set clickable to false
        addNodeField.setVisible(false); // Hide popup
        nodeId.setText(""); // Reset nodeId field
        nodeBuilding.setText(""); // Reset node building field
        nodeType.setText(""); // Reset node type field
        nodeShortName.setText(""); // Reset node short name field
        nodeLongName.setText(""); // Reset node long name field
        if (renderedAddNode != null) { // If node preview rendered
            onTopOfTopElements.getChildren().remove(renderedAddNode); // Hide node preview
        }
    }

    /**
     * Display list of nodes and associated edges
     *
     * @param _listOfNodes Arraylist of nodes to render
     */
    public void drawPath(ArrayList<ArrayList<Node>> _listOfNodes) {
        if (_listOfNodes.size() < 1) {
            return;
        }
        currentPath = _listOfNodes;
        tonysPath.getElements().clear();
        for (ArrayList<Node> listOfNode : _listOfNodes) {
            Node firstNode = listOfNode.get(0);
            MoveTo start = new MoveTo(xCoordOnTopElement(firstNode.getX()), yCoordOnTopElement(firstNode.getY()));
            tonysPath.getElements().add(start);
            listOfNode.forEach(n -> {
                tonysPath.getElements().add(new LineTo(xCoordOnTopElement(n.getX()), yCoordOnTopElement(n.getY())));
            });
        }
    }

    /**
     * toggles the navigation window
     */
    public void toggleNav() {
        LevelManager.getInstance().addListener(this);
        clearMap(); // clear the map
        popPop.setPrefWidth(350.0); // Set preferable width to 350
        navigation = new Navigation(this); // Load controller
        navigation.loadNav(); // Load nav controller
        listOfNodes = navigation.getListOfNodes(); // Get list of nodes from navigation
        if (!LoadFXML.getCurrentWindow().equals("navBar")) { // If navbar selected
            onTopOfTopElements.getChildren().clear(); // Clear children
            tonysPath.getElements().clear();
            return;
        }
        displayHotspots(0.8); // Display nodes at 0.8 (80%) opacity
    }

    /**
     * Clear the map
     */
    public void clearMap() {
        onTopOfTopElements.getChildren().clear();
        topElements.getChildren().clear(); // Clear top elements
        currentPath.clear();
        tonysPath.getElements().clear(); // Clear Tony's path
        hidePopups(); // Hide all popups
    }

    /**
     * toggle the requests window
     */
    public void openRequests() {
        popPop.setPrefWidth(657);

        clearMap(); // Clear map
        //  currentPath= new ArrayList();
        popPop.setPrefWidth(350.0); // Set preferable width to 350
        LoadFXML.getInstance().loadWindow("Requests", "reqBar", popPop); // Load requests window
    }


    /**
     * toggle the login window
     */
    public void openLogin() {
        popPop.setPrefWidth(340);
        clearMap(); // Clear map
        // currentPath= new ArrayList();
        popPop.setPrefWidth(350.0); // Set preferable width to 350
        if (!AuthenticationManager.getInstance().isAuthenticated()) { // If user isn't authenticated
            LoadFXML.getInstance().loadWindow("Login", "loginBar", popPop); // Display login button
        } else { // Else (if user is authenticated)
            AuthenticationManager.getInstance().signOut(); // Display sign out button
        }
    }

    /**
     * toggle the check in window
     */
    public void openCheckIn() {
        popPop.setPrefWidth(657);
        clearMap(); // Clear map
        popPop.setPrefWidth(657.0); // Set preferable width to 657
        LoadFXML.getInstance().loadWindow("COVIDSurvey", "surveyBar", popPop); // Load registration window
    }

    /**
     * Triggered by Add Node button
     *
     * @param event Action Event
     */
    public void addNode(ActionEvent event) {
        addNodeInternal( // Call add node internal with relevant information
                addNodeX,
                addNodeY,
                LevelManager.getInstance().getFloor(),
                nodeId.getText(),
                nodeBuilding.getText(),
                nodeType.getText(),
                nodeShortName.getText(),
                nodeLongName.getText()
        );
        hidePopups(); // Hide all popups
    }

    /**
     * Triggered by Add Edge button
     */
    public void addEdge() {
        String edgeId = addEdgeStart.getNodeID() + "_" + addEdgeEnd.getNodeID(); // Create edgeId
        // If the edge (or the reverse edge) already exists
        if (edgesMap.containsKey(edgeId) || edgesMap.containsKey(addEdgeEnd.getNodeID() + "_" + addEdgeStart.getNodeID()) || addEdgeStart.getNodeID().equals(addEdgeEnd.getNodeID())) {
            addEdgeWarning.setVisible(true); // Display the "Edge already exists" warning
            return; // Don't add edge
        }
        Edge edge = new Edge(edgeId, addEdgeStart.getNodeID(), addEdgeEnd.getNodeID()); // Create new edge
        Submit.getInstance().addEdge(edge); // Add the edge
        hidePopups(); // Hide all popups
        refreshData(); // Refresh the node and edge data from LocalStorage
        renderMap(); // Render/display the map (with the updated information)
    }

    /**
     * exit the application
     */
    public void exitApplication() {
        Shutdown.getInstance().exit(); // Graceful shutdown
    }


    /**
     * Checks if the specified node is within the current specifications (IE: the correct building/s)
     *
     * @param n Node
     * @return true if the node is within current specifications
     */
    public boolean nodeWithinSpec(Node n) {
        return n.getFloor().equals(LevelManager.getInstance().getFloor());
    }


    /**
     * Add a node
     *
     * @param x             Node x coordinate
     * @param y             Node y coordinate
     * @param nodeFloor     Node floor/level
     * @param nodeId        Node ID
     * @param nodeBuilding  Node building
     * @param nodeType      Node type
     * @param nodeShortName Node short name
     * @param nodeLongName  Node long name
     */
    private void addNodeInternal(int x, int y, String nodeFloor, String nodeId, String nodeBuilding, String nodeType, String nodeShortName, String nodeLongName) {
        Node node = new Node(nodeId, x, y, nodeFloor, nodeBuilding, nodeType, nodeLongName, nodeShortName); // Create a node
        Submit.getInstance().addNode(node); // Add the node
        refreshData(); // Refresh the node and edge data from LocalStorage
        renderMap(); // Render/display the map (with the updated information)
        dragStart = null; // Reset dragStart
        dragEnd = null; // Reset dragEnd
    }

    /**
     * Load the specified nodes into the database
     *
     * @param e Action Event
     */
    @FXML
    private void loadNodesCsv(ActionEvent e) {
        FileChooser fileChooser = new FileChooser(); // New file chooser
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv") // Only allow csv files
        );
        File selectedFile = fileChooser.showOpenDialog(anchor.getScene().getWindow()); // Open file chooser
        if (selectedFile == null) {
            return;
        }
        // Load the csv into the database
        PathFindingDatabaseManager.getInstance().insertNodeCsvIntoDatabase(selectedFile.getAbsolutePath());
        hidePopups(); // Hide all popups
        refreshData(); // Pull/update data from LocalStorage
        renderMap(); // Render/refresh the map (with updated data)
    }

    /**
     * Load the specified edges into the database
     *
     * @param e Action Event
     */
    @FXML
    private void loadEdgesCsv(ActionEvent e) {
        FileChooser fileChooser = new FileChooser(); // New file chooser
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv") // Only allow csv files
        );
        File selectedFile = fileChooser.showOpenDialog(anchor.getScene().getWindow()); // Open file chooser
        if (selectedFile == null) {
            return;
        }
        // Load the csv into the database
        PathFindingDatabaseManager.getInstance().insertEdgeCsvIntoDatabase(selectedFile.getAbsolutePath());
        hidePopups(); // Hide all popups
        refreshData(); // Pull/update data from LocalStorage
        renderMap(); // Render/refresh the map (with updated data)
    }

    /**
     * Save nodes to the specified CSV
     *
     * @param e Action Event
     */
    @FXML
    private void saveNodesCsv(ActionEvent e) {
        FileChooser fileChooser = new FileChooser(); // New file chooser
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv") // Only allow csv files
        );
        File saveLocation = fileChooser.showSaveDialog(anchor.getScene().getWindow()); // Open file chooser
        if (saveLocation == null) {
            return;
        }
        // Save the CSV file from LocalStorage
        CSVOperator.writeNodeCSV(LocalStorage.getInstance().getNodes(), saveLocation.getAbsolutePath());
        hidePopups(); // Hide all popups
    }

    /**
     * Save edges to the specified CSV
     *
     * @param e Action Event
     */
    @FXML
    private void saveEdgesCsv(ActionEvent e) {
        FileChooser fileChooser = new FileChooser(); // New file chooser
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv") // Only allow csv files
        );
        File saveLocation = fileChooser.showSaveDialog(anchor.getScene().getWindow()); // Open file chooser
        if (saveLocation == null) {
            return;
        }
        // Save the CSV file from LocalStorage
        CSVOperator.writeEdgeCSV(LocalStorage.getInstance().getEdges(), saveLocation.getAbsolutePath());
        hidePopups(); // Hide all popups
    }

    @FXML
    private void setFloor0(ActionEvent e) {
        LevelManager.getInstance().setFloor(0);
    }

    @FXML
    private void setFloor1(ActionEvent e) {
        LevelManager.getInstance().setFloor(1);
    }

    @FXML
    private void setFloor2(ActionEvent e) {
        LevelManager.getInstance().setFloor(2);
    }

    @FXML
    private void setFloor3(ActionEvent e) {
        LevelManager.getInstance().setFloor(3);
    }

    @FXML
    private void setFloor4(ActionEvent e) {
        LevelManager.getInstance().setFloor(4);
    }

    @FXML
    private void setFloor5(ActionEvent e) {
        LevelManager.getInstance().setFloor(5);
    }

    @Override
    public void levelChanged(int _level) {
        // TODO Selected floor should be highlighted
        refreshData(); // Update localNodes with new floor
        switch (LoadFXML.getCurrentWindow()) {
            case "mapEditorBar":
                renderMap(); // Render/refresh map (with updated data)
                break;
            case "navBar":
                clearMap();
                refreshData();
                displayHotspots(.8);
                break;
        }
    }
//    public void edgeBetweenFloors() {
//        if (start) {
//
//            switch (sNode.getFloor()) {
//                case "G":
//                    groundBttn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5.0), new Insets(-5.0))));
//                    break;
//                case "1":
//                    floor1Bttn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5.0), new Insets(-5.0))));
//                    break;
//                case "2":
//                    floor2Bttn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5.0), new Insets(-5.0))));
//                    break;
//                case "3":
//                    floor3Bttn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5.0), new Insets(-5.0))));
//                    break;
//                case "L2":
//                    L2Bttn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5.0), new Insets(-5.0))));
//                    break;
//                case "L1":
//                    L1Bttn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5.0), new Insets(-5.0))));
//                    break;
//            }
//
//        }
//
//    }
}