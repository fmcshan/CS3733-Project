package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Edge;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.CSVOperator;
import edu.wpi.teamname.Database.DatabaseThread;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapEditorGraph {
    @FXML
    private JFXComboBox<String> selectNode;
    @FXML
    private JFXComboBox<String> selectEdge;
    @FXML
    private JFXTextField edgeFile;
    @FXML
    private JFXTextField nodeFile;
    @FXML
    private JFXTextField NodeID;
    @FXML
    private JFXTextField X;
    @FXML
    private JFXTextField Y;
    @FXML
    private JFXTextField Floor;
    @FXML
    private JFXTextField Building;
    @FXML
    private JFXTextField NodeType;
    @FXML
    private JFXTextField ShortName;
    @FXML
    private JFXTextField LongName;


    ArrayList<Node> listOfNodes = new ArrayList<>();

    @FXML
    public void fillFields(ActionEvent action){
        listOfNodes = DatabaseThread.getInstance().getNodes();
        listOfNodes.forEach(n -> {
            if(n.getLongName().equals(selectNode.getValue())){
                NodeID.setText(n.getNodeID());
                X.setText(String.valueOf(n.getX()));
                Y.setText(String.valueOf(n.getY()));
                Floor.setText(n.getFloor());
                Building.setText(n.getBuilding());
                NodeType.setText(n.getNodeType());
                ShortName.setText(n.getShortName());
                LongName.setText(n.getLongName());
            }
        });
    }


    public void initialize(){
        listOfNodes = DatabaseThread.getInstance().getNodes();
        listOfNodes.forEach(n -> {
            selectNode.getItems().add(n.getLongName());
        });
        List<List<String>> allEdgesData = CSVOperator.readFile(System.getProperty("user.dir") + "/" + edgeFile.getText()); // Load new CSV
        Set<List<String>> edgesDataAsSet = new HashSet<>(allEdgesData); // to avoid duplicate elements
        allEdgesData.clear();
        allEdgesData.addAll(edgesDataAsSet);
        allEdgesData.forEach(n -> {
            selectEdge.getItems().add(n.get(0));
        });
    }

    public void LongName(ActionEvent actionEvent) {
    }

    public void ShortName(ActionEvent actionEvent) {
    }
}
