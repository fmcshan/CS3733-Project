package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.CSVOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MapEditorGraph {
    @FXML
    private Label validID;
    @FXML
    private JFXComboBox<String> selectNode;
    @FXML
    private JFXComboBox<String> selectEdge;
    @FXML
    private JFXTextField edgeFile;
    @FXML
    private JFXButton submitNode;
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
    @FXML
    private JFXButton newEdge;
    @FXML
    private JFXButton addEdge;
    @FXML
    private Label validID1;
    @FXML
    private JFXButton DeleteEdge;
    @FXML
    private JFXButton DeleteNode;




    ArrayList<Node> listOfNodes = new ArrayList<>();
    List<List<String>> theEdges;
    List<List<String>> theNodes;

    @FXML
    void saveNodes(ActionEvent event) {

    }


    @FXML
    void loadFileNode(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV Files", "csv");
        chooser.setFileFilter(filter);
        int chosenVal = chooser.showOpenDialog(null);
        List<List<String>> allNodesData = CSVOperator.readFile(chooser.getSelectedFile().getAbsolutePath()); // Load new CSV
        Set<List<String>> NodeDataAsSet = new HashSet<>(allNodesData); // to avoid duplicate elements
        allNodesData.clear();
        allNodesData.addAll(NodeDataAsSet);
        theNodes = allNodesData;
        theNodes.forEach(n -> {
            selectNode.getItems().add(n.get(0));
        });
        //C:\Users\ryant\IdeaProjects\CS3733-Project
    }

    @FXML
    void loadFileEdge(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV Files", "csv");
        chooser.setFileFilter(filter);
        int chosenVal = chooser.showOpenDialog(null);
        List<List<String>> allEdgesData = CSVOperator.readFile(chooser.getSelectedFile().getAbsolutePath()); // Load new CSV
        Set<List<String>> edgesDataAsSet = new HashSet<>(allEdgesData); // to avoid duplicate elements
        allEdgesData.clear();
        allEdgesData.addAll(edgesDataAsSet);
        theEdges = allEdgesData;
        allEdgesData.forEach(n -> {
            selectEdge.getItems().add(n.get(0));
        });
        //C:\Users\ryant\IdeaProjects\CS3733-Project
    }

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
        if(!(submitNode.isVisible())) {
            theNodes.forEach(n -> {
                if (n.get(0).equals(selectNode.getValue())) {
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
        if(!(addEdge.isVisible())) {
        theEdges.forEach(e -> {
            if(e.get(0).equals(selectEdge.getValue())){
                e.set(1, String.valueOf(StartNode.getText()));
                e.set(2, String.valueOf(EndNode.getText()));
            }
    });
    }}


    public void initialize(){
        addEdge.setVisible(false);
        validID1.setVisible(false);
        validID.setVisible(false);
        submitNode.setVisible(false);


    }

    public void LongName(ActionEvent actionEvent) {
    }

    public void ShortName(ActionEvent actionEvent) {
    }

    public void addNode(ActionEvent actionEvent) {
        selectNode.setDisable(true);
        NodeID.setText("Enter Node ID");
        X.setText("");
        Y.setText("");
        Floor.setText("");
        Building.setText("");
        NodeType.setText("");
        ShortName.setText("");
        LongName.setText("");
        submitNode.setVisible(true);
    }

    public void submitNode(ActionEvent actionEvent) {
        if (NodeID.getText().equals("Enter Node ID")){
            validID.setText("Please enter a valid ID");
            validID.setVisible(true);
        } else{
            selectNode.getItems().add(NodeID.getText());
            validID.setVisible(false);
            submitNode.setVisible(false);
            List<String> addedInfo = new ArrayList<String>();
            addedInfo.add(NodeID.getText());
            addedInfo.add(X.getText());
            addedInfo.add(Y.getText());
            addedInfo.add(Floor.getText());
            addedInfo.add(Building.getText());
            addedInfo.add(NodeType.getText());
            addedInfo.add(LongName.getText());
            addedInfo.add(ShortName.getText());
            theNodes.add(addedInfo);
            NodeID.setText("");
            X.setText("");
            Y.setText("");
            Floor.setText("");
            Building.setText("");
            NodeType.setText("");
            ShortName.setText("");
            LongName.setText("");
            selectNode.setDisable(false);
    }


}

    public void newEdge(ActionEvent actionEvent) {NodeID.setText("Enter NodeID");
        selectEdge.setDisable(true);
        EdgeID.setText("Enter Edge ID");
        StartNode.setText("");
        EndNode.setText("");
        addEdge.setVisible(true);
    }

    public void addEdge(ActionEvent actionEvent) {
        if (EdgeID.getText().equals("Enter Edge ID")){
            validID1.setVisible(true);
            validID1.setText("Please enter a valid ID");

        } else{
            selectEdge.getItems().add(EdgeID.getText());
            validID1.setVisible(false);
            addEdge.setVisible(false);
            List<String> addedInfo = new ArrayList<String>();
            addedInfo.add(EdgeID.getText());
            addedInfo.add(StartNode.getText());
            addedInfo.add(EndNode.getText());
            theEdges.add(addedInfo);
            EdgeID.setText("");
            StartNode.setText("");
            EndNode.setText("");
            selectEdge.setDisable(false);
        }
    }



    @FXML
    void deleteEdge(ActionEvent event) {
        if(addEdge.isVisible()){
            validID1.setText("Please add edge first");
            validID1.setVisible(true);
        } else {
            selectEdge.getItems().remove(EdgeID.getText());
            EdgeID.setText("");
            StartNode.setText("");
            EndNode.setText("");
        }


    }

    @FXML
    void deleteNode(ActionEvent event) {
        if(submitNode.isVisible()){
            validID.setText("Please add node first");
            validID.setVisible(true);
        } else{
                selectNode.getItems().remove(NodeID.getText());
            NodeID.setText("");
            X.setText("");
            Y.setText("");
            Floor.setText("");
            Building.setText("");
            NodeType.setText("");
            ShortName.setText("");
            LongName.setText("");

        }

    }
}
