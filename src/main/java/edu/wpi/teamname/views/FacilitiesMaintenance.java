package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.fxml.FXML;

import java.util.ArrayList;

public class FacilitiesMaintenance extends MasterRequest {

    ArrayList<Node> listOfNodes = new ArrayList<>();
    @FXML
    private JFXComboBox<String> comboBox;
    public void initialize(){
        listOfNodes = PathFindingDatabaseManager.getInstance().getNodes();

        listOfNodes.forEach(n -> {
            //nodesMap.put(n.getNodeID(), n);
            comboBox.getItems().add(n.getLongName());
            //fromCombo.getItems().add(n.getNodeID());
        });
    }


}
