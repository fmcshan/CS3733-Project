package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.database.PathFindingDatabaseManager;
import javafx.fxml.FXML;

import java.util.ArrayList;

public class PatientTransportation extends MasterRequest{

    ArrayList<Node> listOfNodes = new ArrayList<>();
    @FXML
    private JFXComboBox<String> comboBox;
    @FXML
    private JFXComboBox<String> comboBox1;
    public void initialize(){
        listOfNodes = PathFindingDatabaseManager.getInstance().getNodes();

        listOfNodes.forEach(n -> {
            comboBox.getItems().add(n.getLongName());
            comboBox1.getItems().add(n.getLongName());
        });
    }
}
