package edu.wpi.teamname.views;

import edu.wpi.teamname.Database.LocalStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EmployeeTable {

    @FXML
    private VBox cellHolder;

    public void initialize() {
        LocalStorage.getInstance().getUsers().forEach(r -> {
            try {
                Node node = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/CheckInTableCells.fxml"));
                cellHolder.getChildren().add(node);
                HBox hbox = (HBox) node;
                hbox.getChildren().forEach(h -> {
                    if (h instanceof Label) {
                        Label label = (Label) h;
                        switch(label.getId()) {
                            case "nameCell":
                                label.setText(r.getName());
                                break;
                            case "emailCell":
                                label.setText(r.getEmail());
                                break;
                            case "roleCell":

                                break;
                            case "phoneCell":
                                label.setText(r.getPhone());
                                break;
                            default:
                                label.setText("PANIK");
                        }
                    }
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
