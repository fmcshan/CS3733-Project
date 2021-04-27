package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import edu.wpi.teamname.Database.socketListeners.UserListener;
import javafx.application.Platform;
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

public class EmployeeTable implements UserListener {

    @FXML
    private VBox cellHolder;

    public void initialize() {
        Initiator.getInstance().addUserListener(this);
        populateTable();
    }

    private void populateTable() {
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
                                });
                                break;
                            case "actionsBox":
                                vbox.getChildren().forEach(v -> {
                                    JFXButton button = (JFXButton) v;
                                    ContextMenu contextMenu = new ContextMenu();
                                    MenuItem makeAdmin = new MenuItem("Make Admin");
                                    MenuItem revokeAdmin = new MenuItem("Revoke Admin");
                                    MenuItem delete = new MenuItem("Delete");
                                    if (!r.isAdmin()) {
                                        contextMenu.getItems().add(makeAdmin);
                                    } else {
                                        contextMenu.getItems().add(revokeAdmin);
                                    }
                                    contextMenu.getItems().add(delete);
                                    button.setOnAction(b -> {
                                        contextMenu.show(button, Side.BOTTOM, 0, 0);
                                    });
                                    contextMenu.setOnAction(e -> {
                                        switch (((MenuItem) e.getTarget()).getText()) {
                                            case "Make Admin":
                                                Submit.getInstance().grantAdmin(r);
                                                break;
                                            case "Revoke Admin":
                                                Submit.getInstance().revokeAdmin(r);
                                                break;
                                            case "Delete":
                                                Submit.getInstance().deleteUser(r);
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

    @Override
    public void refreshUsers() {
        cellHolder.getChildren().clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                populateTable();
            }
        });
    }

    @Override
    public void updateUser(User _user) {
        cellHolder.getChildren().clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                populateTable();
            }
        });
    }
}
