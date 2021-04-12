package edu.wpi.teamname.views;//package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.CSVReader;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//
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


    public void initialize() {
        //TableView NodesTable = new TableView();
        nodeTable.setEditable(true);

        //TableColumn<Node, String> column1 = new TableColumn<>("node ID");
        nodeIDCol.setCellValueFactory(new PropertyValueFactory<>("nodeID"));


       // TableColumn<Node, Integer> column2 = new TableColumn<>("xCoord");
        xCoordCol.setCellValueFactory(new PropertyValueFactory<>("x"));
        //column2.setCellFactory(ComboBoxTableCell.<Node, Integer>forTableColumn(column2)));

       // TableColumn<Node, Integer> column3 = new TableColumn<>("yCoord");
        yCoordCol.setCellValueFactory(new PropertyValueFactory<>("y"));

        //TableColumn<Node, String> column4 = new TableColumn<>("floor");
        floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));

        //TableColumn<Node, String> column5 = new TableColumn<>("building");
        buildingCol.setCellValueFactory(new PropertyValueFactory<>("building"));
       // TableColumn<Node, String> column6 = new TableColumn<>("nodeType");
        nodeTypeCol.setCellValueFactory(new PropertyValueFactory<>("nodeType"));
       // TableColumn<Node, String> column7 = new TableColumn<>("longName");
        longNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));

       // TableColumn<Node, String> column8 = new TableColumn<>("shortName");
        shortNameCol.setCellValueFactory(new PropertyValueFactory<>("shortName"));


//        NodesTable.getColumns().add(column1);
//        NodesTable.getColumns().add(column2);
//        NodesTable.getColumns().add(column3);
//        NodesTable.getColumns().add(column4);
//        NodesTable.getColumns().add(column5);
//        NodesTable.getColumns().add(column6);
//        NodesTable.getColumns().add(column7);
//        NodesTable.getColumns().add(column8);


        List<List<String>> allNodesData = CSVReader.readFile(System.getProperty("user.dir") + "/L1Nodes.csv");
        Set<List<String>> nodesDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(nodesDataAsSet);

        //nodes = FXCollections.observableArrayList();
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
}


