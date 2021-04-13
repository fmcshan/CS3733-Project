package edu.wpi.teamname.views;//package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.CSVOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NodesEditor {

    @FXML
    private TableView nodeTable;
    @FXML
    private TableColumn nodeIDCol;
    @FXML
    private TableColumn xCoordCol;
    @FXML
    private TableColumn yCoordCol;
    @FXML
    private TableColumn floorCol;
    @FXML
    private TableColumn buildingCol;
    @FXML
    private TableColumn nodeTypeCol;
    @FXML
    private TableColumn longNameCol;
    @FXML
    private TableColumn shortNameCol;
    @FXML
    private JFXTextField loadCSVFileName;
    private Node currentlySelected = null; // Track currently selected row

    /**
     * Clears the current table, then loads the specified csv (loadCSVFileName text input) into nodeTable
     */
    public void loadCSVToTable() {
        List<List<String>> allNodesData = CSVOperator.readFile(System.getProperty("user.dir") + "/" + loadCSVFileName.getText()); // Load new CSV
        Set<List<String>> nodesDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(nodesDataAsSet);

        nodeTable.getItems().clear(); // Clear table

        allNodesData.forEach(n -> {
            nodeTable.getItems().add(new Node(n.get(0), Integer.parseInt(n.get(1)), Integer.parseInt(n.get(2)), n.get(3), n.get(4), n.get(5), n.get(6), n.get(7)));
        }); // Populate table
    }

    /**
     * Populates nodeTable with the Node data from L1Nodes.csv
     */
    public void initialize() {
        nodeIDCol.setCellFactory(TextFieldTableCell.forTableColumn());
        xCoordCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        yCoordCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        floorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        buildingCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nodeTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        longNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        shortNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nodeIDCol.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
        xCoordCol.setCellValueFactory(new PropertyValueFactory<>("x"));
        yCoordCol.setCellValueFactory(new PropertyValueFactory<>("y"));
        floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));
        buildingCol.setCellValueFactory(new PropertyValueFactory<>("building"));
        nodeTypeCol.setCellValueFactory(new PropertyValueFactory<>("nodeType"));
        longNameCol.setCellValueFactory(new PropertyValueFactory<>("longName"));
        shortNameCol.setCellValueFactory(new PropertyValueFactory<>("shortName"));

        loadCSVFileName.setText("L1Nodes.csv"); // Set input text to default file
        loadCSVToTable(); // Load file to table

        nodeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            currentlySelected = (Node) newSelection; // Listen for row selection events
        });
    }

    /**
     * Called on cell edit; rewrites row with change
     *
     * @param editCell Cell edit event
     */
    public void changeCellEvent(TableColumn.CellEditEvent editCell) {
        int c = editCell.getTablePosition().getColumn(); // Edited col index
        int r = editCell.getTablePosition().getRow(); // Edited row index
        Node node = (Node) editCell.getRowValue(); // Current row
        String newValue = String.valueOf(editCell.getNewValue()); // Get new edit cell value
        Node newNode = new Node(
                c == 0 ? newValue : node.getNodeID(),
                c == 1 ? Integer.parseInt(newValue) : node.getX(),
                c == 2 ? Integer.parseInt(newValue) : node.getY(),
                c == 3 ? newValue : node.getFloor(),
                c == 4 ? newValue : node.getBuilding(),
                c == 5 ? newValue : node.getNodeType(),
                c == 6 ? newValue : node.getLongName(),
                c == 7 ? newValue : node.getShortName()
        ); // Create a new node with new cell value

        nodeTable.getItems().remove(r);
        nodeTable.getItems().add(r, newNode);
    }

    /**
     * Called on loadCSV button click, loads the specified csv (loadCSVFileName text input) into nodeTable
     *
     * @param actionEvent Button click event
     */
    public void loadCSV(ActionEvent actionEvent) {
        loadCSVToTable();
    }

    /**
     * Saves the table to the specified csv file (loadCSVFileName text input)
     *
     * @param actionEvent Button click event
     */
    public void saveCSV(ActionEvent actionEvent) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodeTable.getItems().forEach(n -> {
            nodes.add((Node) n);
        });
        CSVOperator.writeNodeCSV(nodes, loadCSVFileName.getText()); // Write nodes to csv
    }

    /**
     * Deletes the currently selected row
     *
     * @param actionEvent Button click event
     */
    public void deleteNode(ActionEvent actionEvent) {
        if (currentlySelected != null) { // If a row is selected
            nodeTable.getItems().remove(currentlySelected); // Delete row
        }
    }

    /**
     * Adds a node/row to nodeTable
     *
     * @param actionEvent Button click event
     */
    public void addNode(ActionEvent actionEvent) {
        nodeTable.getItems().add(0, new Node(
                "Node ID",
                0,
                0,
                "Floor",
                "Building",
                "Node Type",
                "Long Name",
                "Short Name"
        )); // Add node to nodeTable
    }
}
