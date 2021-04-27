package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.Database.LocalStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EmployeeTable {

    @FXML
    private VBox cellHolder;

    public void initialize() {
        LocalStorage.getInstance().getUsers().forEach(r -> {
            try {
                Node node = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/EmployeeTableCells.fxml"));
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
                            case "phoneCell":
                                label.setText(r.getPhone());
                                break;
                            default:
                                label.setText("PANIK");
                        }
                    } else if (h instanceof VBox) {
                        VBox vbox = (VBox) h;
                        switch(vbox.getId()) {
                            case "roleBox":
                                vbox.getChildren().forEach(v -> {
                                    Label label = (Label) v;
                                    label.setText(r.isAdmin() ? "True" : "False");
                                    if (!r.isAdmin()) { label.setStyle("-fx-background-color: #f13426; -fx-background-radius: 4px"); }
                                    System.out.println(r.isAdmin());
                                });
                            break;
                            case "actionsBox":
                                System.out.println("made it here");
                                vbox.getChildren().forEach(v -> {
                                    JFXButton button = (JFXButton) v;
                                        ContextMenu contextMenu = new ContextMenu();
                                        MenuItem inProgress = new MenuItem("In Progress");
                                        MenuItem completed = new MenuItem("Completed");
                                        MenuItem unassigned = new MenuItem("Unassigned");
                                        contextMenu.getItems().add(inProgress);
                                        contextMenu.getItems().add(completed);
                                        contextMenu.getItems().add(unassigned);
                                        button.setOnAction(b -> {
                                            contextMenu.show(button, Side.BOTTOM, 0, 0);
                                    });
                                });
                            break;
                        }
                    }
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
