package edu.wpi.teamname.views;

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

public class EdgesEditor {

    @FXML
    private TableView edgeTable;
    @FXML
    private TableColumn edgeIDCol;
    @FXML
    private TableColumn startNodeCol;
    @FXML
    private TableColumn endNodeCol;
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

        edgeTable.getItems().clear(); // Clear table

        allNodesData.forEach(n -> {
            edgeTable.getItems().add(new Node(n.get(0), Integer.parseInt(n.get(1)), Integer.parseInt(n.get(2)), n.get(3), n.get(4), n.get(5), n.get(6), n.get(7)));
        }); // Populate table
    }

    /**
     * Populates nodeTable with the Node data from L1Nodes.csv
     */
    public void initialize() {
        edgeIDCol.setCellFactory(TextFieldTableCell.forTableColumn());
        startNodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        endNodeCol.setCellFactory(TextFieldTableCell.forTableColumn());

        edgeIDCol.setCellValueFactory(new PropertyValueFactory<>("nodeType"));
        startNodeCol.setCellValueFactory(new PropertyValueFactory<>("longName"));
        endNodeCol.setCellValueFactory(new PropertyValueFactory<>("shortName"));

        loadCSVFileName.setText("L1Edges.csv"); // Set input text to default file
        //loadCSVToTable(); // Load file to table

        edgeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            currentlySelected = (Node) newSelection; // Listen for row selection events
        });
    }

    public void addNode(ActionEvent actionEvent) {
    }

    public void deleteNode(ActionEvent actionEvent) {
    }

    public void saveCSV(ActionEvent actionEvent) {
    }

    public void loadCSV(ActionEvent actionEvent) {
    }

    public void changeCellEvent(TableColumn.CellEditEvent cellEditEvent) {
    }

//    /**
//     * Called on cell edit; rewrites row with change
//     *
//     * @param editCell Cell edit event
//     */
//    public void changeCellEvent(TableColumn.CellEditEvent editCell) {
//        int c = editCell.getTablePosition().getColumn(); // Edited col index
//        int r = editCell.getTablePosition().getRow(); // Edited row index
//        Node node = (Node) editCell.getRowValue(); // Current row
//        String newValue = String.valueOf(editCell.getNewValue()); // Get new edit cell value
//        Node newNode = new Node(
//                c == 0 ? newValue : node.getNodeID(),
//                c == 1 ? Integer.parseInt(newValue) : node.getX(),
//                c == 2 ? Integer.parseInt(newValue) : node.getY(),
//                c == 3 ? newValue : node.getFloor(),
//                c == 4 ? newValue : node.getBuilding(),
//                c == 5 ? newValue : node.getNodeType(),
//                c == 6 ? newValue : node.getLongName(),
//                c == 7 ? newValue : node.getShortName()
//        ); // Create a new node with new cell value
//
//        nodeTable.getItems().remove(r);
//        nodeTable.getItems().add(r, newNode);
//    }
//
//    /**
//     * Called on loadCSV button click, loads the specified csv (loadCSVFileName text input) into nodeTable
//     *
//     * @param actionEvent Button click event
//     */
//    public void loadCSV(ActionEvent actionEvent) {
//        loadCSVToTable();
//    }
//
//    /**
//     * Saves the table to the specified csv file (loadCSVFileName text input)
//     *
//     * @param actionEvent Button click event
//     */
//    public void saveCSV(ActionEvent actionEvent) {
//        ArrayList<Node> nodes = new ArrayList<Node>();
//        nodeTable.getItems().forEach(n -> {
//            nodes.add((Node) n);
//        });
//        CSVOperator.writeCSV(nodes, loadCSVFileName.getText(), "beans"); // Write nodes to csv
//    }
//
//    /**
//     * Deletes the currently selected row
//     *
//     * @param actionEvent Button click event
//     */
//    public void deleteNode(ActionEvent actionEvent) {
//        if (currentlySelected != null) { // If a row is selected
//            nodeTable.getItems().remove(currentlySelected); // Delete row
//        }
//    }
//
//    /**
//     * Adds a node/row to nodeTable
//     *
//     * @param actionEvent Button click event
//     */
//    public void addNode(ActionEvent actionEvent) {
//        nodeTable.getItems().add(0, new Node(
//                "Node ID",
//                0,
//                0,
//                "Floor",
//                "Building",
//                "Node Type",
//                "Long Name",
//                "Short Name"
//        )); // Add node to nodeTable
//    }
}
