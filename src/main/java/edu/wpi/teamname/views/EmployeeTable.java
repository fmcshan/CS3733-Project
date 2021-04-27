package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Database.socketListeners.Initiator;
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
                        switch (label.getId()) {
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
                        switch (vbox.getId()) {
                            case "roleBox":
                                vbox.getChildren().forEach(v -> {
                                    Label label = (Label) v;
                                    label.setText(r.isAdmin() ? "True" : "False");
                                    if (!r.isAdmin()) {
                                        label.setStyle("-fx-background-color: #f13426; -fx-background-radius: 4px");
                                    }
                                    System.out.println(r.isAdmin());
                                });
                                break;
                            case "actionsBox":
                                System.out.println("made it here");
                                vbox.getChildren().forEach(v -> {
                                    JFXButton button = (JFXButton) v;
                                    ContextMenu contextMenu = new ContextMenu();
                                    MenuItem makeAdmin = new MenuItem("make admin");
                                    MenuItem revokeAdmin = new MenuItem("revoke admin");
                                    MenuItem delete = new MenuItem("delete");
                                    contextMenu.getItems().add(makeAdmin);
                                    contextMenu.getItems().add(revokeAdmin);
                                    contextMenu.getItems().add(delete);
                                    button.setOnAction(b -> {
                                        contextMenu.show(button, Side.BOTTOM, 0, 0);
                                    });
                                    contextMenu.setOnAction(e -> {
                                        switch (((MenuItem) e.getTarget()).getText()) {
                                            case "make admin":
//                                                 _emp.isAdmin() = true;
//                                                Submit.getInstance().updateGiftDelivery(_emp);
                                                break;
                                            case "revoke admin":
//                                                _emp.isAdmin() = false;
//                                                Submit.getInstance().updateGiftDelivery(_emp);
                                                break;
                                            case "delete":
//                                                Submit.getInstance().deleteGiftDelivery(_req);
//                                                Submit.getInstance().updateGiftDelivery(_emp);
                                                break;
                                        }
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
