package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import edu.wpi.teamname.Database.socketListeners.UserListener;
import edu.wpi.teamname.views.manager.EmployeeManager;
import edu.wpi.teamname.views.manager.SceneManager;
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
import java.util.HashMap;

public class EmployeeTable implements UserListener {

    @FXML
    private VBox cellHolder;
    @FXML
    private VBox userPop;

    HashMap<String, JFXTextField> userNameMap  = new HashMap<>();
    HashMap<String, JFXTextField> userEmailMap = new HashMap<>();
    HashMap<String, JFXTextField> userPhoneMap = new HashMap<>();

    public EmployeeTable() {
    }

    public VBox getUserPop() {
        return userPop;
    }

    public void initialize() {
        EmployeeManager.getInstance().setEmployeeTable(this);
        Initiator.getInstance().addUserListener(this);
        populateTable();
    }

    public void populateTable() {
        LocalStorage.getInstance().getUsers().forEach(r -> {
            try {
                Node node = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/EmployeeTableCells.fxml"));
                cellHolder.getChildren().add(node);
                HBox hbox = (HBox) node;
                hbox.getChildren().forEach(h -> {
                    if (h instanceof JFXTextField) {
                        JFXTextField textField = (JFXTextField) h;
                        switch (textField.getId()) {
                            case "nameCell":
                                userNameMap.put(r.getLocalId(), textField);
                                textField.setText(r.getName());
                                break;
                            case "emailCell":
                                userEmailMap.put(r.getLocalId(), textField);
                                textField.setText(r.getEmail());
                                break;
                            case "phoneCell":
                                userPhoneMap.put(r.getLocalId(), textField);
                                textField.setText(r.getPhone());
                                break;
                            default:
                                textField.setText("PANIK");
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
                                    MenuItem saveEdit = new MenuItem("Save Edit");
                                    if (!r.isAdmin()) {
                                        contextMenu.getItems().add(makeAdmin);
                                    } else {
                                        contextMenu.getItems().add(revokeAdmin);
                                    }
                                    contextMenu.getItems().add(delete);
                                    contextMenu.getItems().add(saveEdit);
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
                                            case "Save Edit":
                                                String name = userNameMap.get(r.getLocalId()).getText();
                                                String email = userEmailMap.get(r.getLocalId()).getText();
                                                String phone = userPhoneMap.get(r.getLocalId()).getText();
                                                System.out.println(name);
                                                System.out.println(email);
                                                System.out.println(phone);
                                                r.setName(name);
                                                r.setEmail(email);
                                                r.setPhone(phone);
                                                Submit.getInstance().editUser(r);
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cellHolder.getChildren().clear();
                populateTable();
            }
        });
    }

    @Override
    public void updateUser(User _user) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cellHolder.getChildren().clear();
                populateTable();
            }
        });
    }

    public void addUser() {
        LoadFXML.getInstance().loadWindow("AddEmployee", "userBar", userPop);
    }
}
