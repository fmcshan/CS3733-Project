package edu.wpi.teamname.views;

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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NodesEditor {

    @FXML
    public JFXTreeTableView myTable;
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

    class DisplayNode extends RecursiveTreeObject<DisplayNode>{
        SimpleStringProperty nodeId;
        SimpleIntegerProperty xCoord;
        SimpleIntegerProperty yCoord;
        SimpleStringProperty floor;
        SimpleStringProperty building;
        SimpleStringProperty nodeType;
        SimpleStringProperty shortName;
        SimpleStringProperty longName;

        public DisplayNode(String nodeId, Integer xCoord, Integer yCoord, String floor, String building, String nodeType, String shortName, String longName) {
            this.nodeId = new SimpleStringProperty(nodeId);
            this.xCoord = new SimpleIntegerProperty(xCoord);
            this.yCoord = new SimpleIntegerProperty(yCoord);
            this.floor = new SimpleStringProperty(floor);
            this.building = new SimpleStringProperty(building);
            this.nodeType = new SimpleStringProperty(nodeType);
            this.shortName = new SimpleStringProperty(shortName);
            this.longName = new SimpleStringProperty(longName);
        }
    }

    ObservableList<Node> nodes;

    public void addToTable() {
        List<List<String>> allNodesData = CSVReader.readFile(System.getProperty("user.dir") + "/L1Nodes.csv");
        Set<List<String>> nodesDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(nodesDataAsSet);

        nodes = FXCollections.observableArrayList();
        for (List<String> singleNodeData : allNodesData) {
            nodes.add(new Node(singleNodeData.get(0),
                    Integer.parseInt(singleNodeData.get(1)), Integer.parseInt(singleNodeData.get(2)),
                    singleNodeData.get(3), singleNodeData.get(4), singleNodeData.get(5), singleNodeData.get(6),
                    singleNodeData.get(7)));
           }

        }

        final TreeItem<DisplayNode> root = new RecursiveTreeItem<DisplayNode>((DisplayNode) nodes, RecursiveTreeObject::getChildren);
        NodesEditor.myTable.setShowRoot(false);
        NodesEditor.myTable.setEditable(true);
    }


