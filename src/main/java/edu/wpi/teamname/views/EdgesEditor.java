package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.App;
import edu.wpi.teamname.Database.CSVOperator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
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
    private Edge currentlySelected = null; // Track currently selected row

    /**
     * Clears the current table, then loads the specified csv (loadCSVFileName text input) into edgeTable
     */
    public void loadCSVToTable() {
        List<List<String>> allEdgesData = CSVOperator.readFile(System.getProperty("user.dir") + "/" + loadCSVFileName.getText()); // Load new CSV
        Set<List<String>> edgesDataAsSet = new HashSet<>(allEdgesData); // to avoid duplicate elements
        allEdgesData.clear();
        allEdgesData.addAll(edgesDataAsSet);

        edgeTable.getItems().clear(); // Clear table

        allEdgesData.forEach(e -> {
            edgeTable.getItems().add(new Edge(e.get(0), e.get(1), e.get(2)));
        }); // Populate table

    }

    /**
     * Populates edgeTable with the Edge data from L1Edges.csv
     */
    public void initialize() {
        edgeIDCol.setCellFactory(TextFieldTableCell.forTableColumn());
        startNodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        endNodeCol.setCellFactory(TextFieldTableCell.forTableColumn());

        edgeIDCol.setCellValueFactory(new PropertyValueFactory<>("edgeID"));
        startNodeCol.setCellValueFactory(new PropertyValueFactory<>("startNode"));
        endNodeCol.setCellValueFactory(new PropertyValueFactory<>("endNode"));

        loadCSVFileName.setText("L1Edges.csv"); // Set input text to default file
        loadCSVToTable(); // Load file to table

        edgeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            currentlySelected = (Edge) newSelection; // Listen for row selection events
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
        Edge edge = (Edge) editCell.getRowValue(); // Current row
        String newValue = String.valueOf(editCell.getNewValue()); // Get new edit cell value
        Edge newEdge = new Edge(
                c == 0 ? newValue : edge.getEdgeID(),
                c == 1 ? newValue : edge.getStartNode(),
                c == 2 ? newValue : edge.getEndNode()
        ); // Create a new node with new cell value

        edgeTable.getItems().remove(r);
        edgeTable.getItems().add(r, newEdge);
    }

    /**
     * Called on loadCSV button click, loads the specified csv (loadCSVFileName text input) into edgeTable
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
        ArrayList<Edge> edges = new ArrayList<Edge>();
        edgeTable.getItems().forEach(e -> {
            edges.add((Edge) e);
        });
        CSVOperator.writeEdgeCSV(edges, loadCSVFileName.getText());
    }

    /**
     * Deletes the currently selected row
     *
     * @param actionEvent Button click event
     */
    public void deleteEdge(ActionEvent actionEvent) {
        if (currentlySelected != null) { // If a row is selected
            edgeTable.getItems().remove(currentlySelected); // Delete row
        }
    }

    /**
     * Adds an edge/row to edgeTable
     *
     * @param actionEvent Button click event
     */
    public void addEdge(ActionEvent actionEvent) {
        edgeTable.getItems().add(0, new Edge(
                "Edge ID",
                "Start Node",
                "End Node"
        )); // Add edge to edgeTable
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
}
