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
 @FXML private JFXTextField loadCSVFileName;

 private HashMap<Integer, Node> changes = new HashMap<Integer, Node>();
 private String defaultCSV = "L1Nodes.csv";

    public void loadCSVToTable() {
        List<List<String>> allNodesData = CSVOperator.readFile(System.getProperty("user.dir") + "/" + loadCSVFileName.getText());
        defaultCSV = loadCSVFileName.getText();
        Set<List<String>> nodesDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(nodesDataAsSet);

        nodeTable.getItems().clear();

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

    public void addNodeToTable() {
        ArrayList<Node> allNodesData = new ArrayList<Node>();
        nodeTable.getItems().forEach(n -> {
            allNodesData.add((Node) n);
        });
        allNodesData.add(new Node("Node ID", 0, 0, "Floor", "Building", "Node Type", "Long Name", "Short Name"));
        nodeTable.getItems().clear();

        allNodesData.forEach(n -> {
            nodeTable.getItems().add(n);
        });
        nodeTable.getSortOrder().addAll(xCoordCol, yCoordCol);
    }

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

        loadCSVFileName.setText(defaultCSV);
        loadCSVToTable();
    }

    public void trackChange(TableColumn.CellEditEvent editCell) {
        Node node = (Node) editCell.getRowValue();
        String newValue = (String) editCell.getNewValue();
        String nodeID = node.getNodeID();
        String nodeX = String.valueOf(node.getX());
        String nodeY = String.valueOf(node.getY());
        String nodeFloor = node.getFloor();
        String nodeBuilding = node.getBuilding();
        String nodeType = node.getNodeType();
        String nodeLongName = node.getLongName();
        String nodeShortName = node.getShortName();
        switch (editCell.getTablePosition().getColumn()) {
            case 0:
                nodeID = newValue;
                break;
            case 1:
                nodeX = newValue;
                break;
            case 2:
                nodeY = newValue;
                break;
            case 3:
                nodeFloor = newValue;
                break;
            case 4:
                nodeBuilding = newValue;
                break;
            case 5:
                nodeType = newValue;
                break;
            case 6:
                nodeLongName = newValue;
                break;
            case 7:
                nodeShortName = newValue;
                break;
        }

        Node newNode = new Node(nodeID, Integer.parseInt(nodeX), Integer.parseInt(nodeY), nodeFloor, nodeBuilding, nodeType, nodeLongName, nodeShortName);
        changes.put(editCell.getTablePosition().getRow(), newNode);
    }

    public void changeCellEvent(TableColumn.CellEditEvent editCell) {
            trackChange(editCell);
 }

    public void loadCSV(ActionEvent actionEvent) {
        loadCSVToTable();
    }

    public void saveCSV(ActionEvent actionEvent) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        final int[] i = {0};
        nodeTable.getItems().forEach(n -> {
            if (changes.containsKey(i[0])) {
                nodes.add((changes.get(i[0])));
            } else {
                nodes.add((Node) n);
            }
            i[0]++;
        });
        CSVOperator.writeCSV(nodes, loadCSVFileName.getText(), "beans");
    }

    public void deleteNode(ActionEvent actionEvent) {

    }

    public void addNode(ActionEvent actionEvent) {
        addNodeToTable();
    }
}


