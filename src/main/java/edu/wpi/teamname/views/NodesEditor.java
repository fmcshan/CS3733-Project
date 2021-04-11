package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.teamname.Database.CSVReader;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NodesEditor {

    @FXML
    private JFXTreeTableView myTable;
    @FXML
    private TreeTableColumn nodeId;
    @FXML
    private TreeTableColumn xCoord;
    @FXML
    private TreeTableColumn yCoord;
    @FXML
    private TreeTableColumn floor;
    @FXML
    private TreeTableColumn building;
    @FXML
    private TreeTableColumn nodeType;
    @FXML
    private TreeTableColumn shortName;
    @FXML
    private TreeTableColumn longName;


    public void addToTable() {
        List<List<String>> allNodesData = CSVReader.readFile(System.getProperty("user.dir") + "/L1Nodes.csv");
        Set<List<String>> nodesDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(nodesDataAsSet);


        for (List<String> singleNodeData : allNodesData) {
           nodeId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures, ObservableValue>() {
               @Override
               public ObservableValue call(TreeTableColumn.CellDataFeatures param) {
                   return new ReadOnlyObjectWrapper(singleNodeData.get(0));
               }
           });

        }
    }
}