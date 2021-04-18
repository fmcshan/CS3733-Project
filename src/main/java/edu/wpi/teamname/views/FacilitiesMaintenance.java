package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.database.PathFindingDatabaseManager;
import javafx.fxml.FXML;

import java.util.ArrayList;

public class FacilitiesMaintenance extends MasterRequest {

    ArrayList<Node> listOfNodes = new ArrayList<>();
    @FXML
    private JFXComboBox<String> comboBox;
    public void initialize(){
        listOfNodes = PathFindingDatabaseManager.getInstance().getNodes();

        listOfNodes.forEach(n -> {
            comboBox.getItems().add(n.getLongName());
        });
    }


}
