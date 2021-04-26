package edu.wpi.teamname.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RegistrationAdminViewNew {

    @FXML
    private VBox cellHolder;

    public void initialize() {
        Node[] nodes = new Node[10]; // javaFX node not Algo team node
        for (int i = 0; i < nodes.length; i++) {
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/CheckInTableCells.fxml"));
                cellHolder.getChildren().add(nodes[i]);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }



}
