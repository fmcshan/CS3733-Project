package edu.wpi.teamname.views;//package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.CSVOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.util.*;

public class NodesEditor {

 @FXML private TableView nodeTable;
 @FXML private TableColumn nodeIDCol;
 @FXML private TableColumn xCoordCol;
 @FXML private TableColumn yCoordCol;
 @FXML private TableColumn floorCol;
 @FXML private TableColumn buildingCol;
 @FXML private TableColumn nodeTypeCol;
 @FXML private TableColumn longNameCol;
 @FXML private TableColumn shortNameCol;

 private HashMap<Integer, String[]> changes = new HashMap<Integer, String[]>();

    /**
     * Populates the table with the Node Data from L1Nodes.csv
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

        List<List<String>> allNodesData = CSVOperator.readFile(System.getProperty("user.dir") + "/L1Nodes.csv");
        Set<List<String>> nodesDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(nodesDataAsSet);

        /**
         * For each node in the list of node data, get each parameter to populate the table
         */
        for (List<String> singleNodeData : allNodesData) {
            nodeTable.getItems().add(new Node(singleNodeData.get(0),
                    Integer.parseInt(singleNodeData.get(1)),
                    Integer.parseInt(singleNodeData.get(2)),
                    singleNodeData.get(3),
                    singleNodeData.get(4),
                    singleNodeData.get(5),
                    singleNodeData.get(6),
                    singleNodeData.get(7)));
        }
    }

    public void trackChange(TableColumn.CellEditEvent editCell) {
        Node node = (Node) editCell.getRowValue();

        String[] currentRow = new String[] {
                node.getNodeID(),
                String.valueOf(node.getX()),
                String.valueOf(node.getY()),
                node.getFloor(),
                node.getBuilding(),
                node.getNodeType(),
                node.getLongName(),
                node.getShortName()
        };
        changes.put(editCell.getTablePosition().getRow(), currentRow);
    }

    public void changeCellEvent(TableColumn.CellEditEvent editCell) {
            trackChange(editCell);
 }

    public void loadCSV(ActionEvent actionEvent) {
    }

    public void saveCSV(ActionEvent actionEvent) {
    }

    public void deleteNode(ActionEvent actionEvent) {
    }

    public void addNode(ActionEvent actionEvent) {
    }
}


