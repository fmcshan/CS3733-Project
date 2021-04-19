package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.CSVOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

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
    @FXML
    private JFXTextField EdgeID;
    @FXML
    private JFXTextField StartNode;
    @FXML
    private JFXTextField EndNode;


    ArrayList<Node> listOfNodes = new ArrayList<>();
    List<List<String>> theEdges;
    List<List<String>> theNodes;



    @FXML
    public void fillFieldsNode(ActionEvent action){
        theNodes.forEach(n -> {
            if(n.get(0).equals(selectNode.getValue())){
                NodeID.setText(n.get(0));
                X.setText(String.valueOf(n.get(1)));
                Y.setText(String.valueOf(n.get(2)));
                Floor.setText(n.get(3));
                Building.setText(n.get(4));
                NodeType.setText(n.get(5));
                ShortName.setText(n.get(7));
                LongName.setText(n.get(6));
            }
        });
    }

    @FXML
    void changeNodeEvent(ActionEvent event) {
        final String[] oldID = new String[1];
        theNodes.forEach(n -> {
            if(n.get(0).equals(selectNode.getValue())){
                n.set(1, String.valueOf(X.getText()));
                n.set(2, String.valueOf(Y.getText()));
                n.set(3, String.valueOf(Floor.getText()));
                n.set(4, String.valueOf(Building.getText()));
                n.set(5, String.valueOf(NodeType.getText()));
                n.set(7, String.valueOf(ShortName.getText()));
                n.set(6, String.valueOf(LongName.getText()));

            }

        });


    }



    @FXML
    public void fillFieldsEdge(ActionEvent action){
        theEdges.forEach(n -> {
            if(n.get(0).equals(selectEdge.getValue())){
                EdgeID.setText(n.get(0));
                StartNode.setText(n.get(1));
                EndNode.setText(n.get(2));

            }
        });
    }

    @FXML
    void changeEdgeEvent(ActionEvent event) {

        theEdges.forEach(e -> {
            if(e.get(0).equals(selectEdge.getValue())){
                e.set(1, String.valueOf(StartNode.getText()));
                e.set(2, String.valueOf(EndNode.getText()));
            }
    });
    }


    public void initialize(){
        List<List<String>> allNodesData = CSVOperator.readFile(System.getProperty("user.dir") + "/" + nodeFile.getText()); // Load new CSV
        Set<List<String>> NodeDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(NodeDataAsSet);
        theNodes = allNodesData;
        theNodes.forEach(n -> {
            selectNode.getItems().add(n.get(0));
        });
        List<List<String>> allEdgesData = CSVOperator.readFile(System.getProperty("user.dir") + "/" + edgeFile.getText()); // Load new CSV
        Set<List<String>> edgesDataAsSet = new HashSet<>(allEdgesData); // to avoid duplicate elements
        allEdgesData.clear();
        allEdgesData.addAll(edgesDataAsSet);
        theEdges = allEdgesData;
        allEdgesData.forEach(n -> {
            selectEdge.getItems().add(n.get(0));
        });
    }

    public void LongName(ActionEvent actionEvent) {
    }

    public void ShortName(ActionEvent actionEvent) {
    }

    public void addNode(ActionEvent actionEvent) {
        NodeID.setText("Enter NodeID");
        X.setText("");
        Y.setText("");
        Floor.setText("");
        Building.setText("");
        NodeType.setText("");
        ShortName.setText("");
        LongName.setText("");
    }

    public void checkEdited(){
        if (NodeID.getText().equals("Enter NodeID"));
    }
}
