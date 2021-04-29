package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import edu.wpi.teamname.Database.socketListeners.UserListener;
import edu.wpi.teamname.views.manager.EmployeeManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.HashMap;

public class EmployeeTable implements UserListener {

    @FXML
    private VBox cellHolder;
    @FXML
    private VBox userPop;

    HashMap<String, JFXTextField> userNameMap = new HashMap<>();
    HashMap<String, JFXTextField> userEmailMap = new HashMap<>();
    HashMap<String, JFXTextField> userPhoneMap = new HashMap<>();
    boolean userAdded = false;
    boolean invalidUser = false;

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

            Label roleCell = new Label();
            roleCell.setAlignment(Pos.CENTER_LEFT);
            roleCell.setStyle("-fx-font-size: 14; -fx-font: Segoe UI Semibold; -fx-text-fill: white; -fx-background-color: #00c455; -fx-background-radius: 4px");
            roleCell.setPrefWidth(62);
            roleCell.setPadding(new Insets(0, 17, 0, 17));

            roleCell.setText(r.isAdmin() ? "True" : "False");
            if (!r.isAdmin()) {
                roleCell.setStyle("-fx-font-size: 14; -fx-font: Segoe UI Semibold; -fx-text-fill: white; -fx-background-color: #f13426; -fx-background-radius: 4px");
                roleCell.setPadding(new Insets(0, 15, 0, 15));
            }

            JFXButton actionsButton = new JFXButton("...");
            actionsButton.setStyle("-fx-font-weight: Bold; -fx-font-size: 28");
            actionsButton.setTextOverrun(OverrunStyle.ELLIPSIS);
            actionsButton.setAlignment(Pos.CENTER_LEFT);
            actionsButton.setPadding(new Insets(-15, 5, 0, 5));

            ContextMenu contextMenu = new ContextMenu();
            MenuItem makeAdmin = new MenuItem("Make Admin");
            MenuItem revokeAdmin = new MenuItem("Revoke Admin");
            MenuItem delete = new MenuItem("Delete");
            MenuItem saveEdit = new MenuItem("Save Edit");
            makeAdmin.setStyle("-fx-font-weight: 400; -fx-font-size: 14");
            revokeAdmin.setStyle("-fx-font-weight: 400; -fx-font-size: 14");
            delete.setStyle("-fx-font-weight: 400; -fx-font-size: 14");
            saveEdit.setStyle("-fx-font-weight: 400; -fx-font-size: 14");
            if (!r.isAdmin()) {
                contextMenu.getItems().add(makeAdmin);
            } else {
                contextMenu.getItems().add(revokeAdmin);
            }
            contextMenu.getItems().add(delete);
            contextMenu.getItems().add(saveEdit);
            actionsButton.setOnAction(b -> {
                contextMenu.show(actionsButton, Side.BOTTOM, 0, 0);
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
                        r.setName(name);
                        r.setEmail(email);
                        r.setPhone(phone);
                        Submit.getInstance().editUser(r);
                }
            });

            generateRow(r.getLocalId(), r.getName(), r.getEmail(), roleCell, r.getPhone(), actionsButton);
//            try {
//                Node node = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/EmployeeTableCells.fxml"));
//                cellHolder.getChildren().add(node);
//                HBox hbox = (HBox) node;
//                hbox.setOnMouseEntered(e -> {
//                    hbox.setStyle("-fx-background-color: #F7F7F8; -fx-background-radius: 8px;");
//                });
//
//                hbox.setOnMouseExited(e -> {
//                    hbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8px;");
//                });
//                hbox.getChildren().forEach(h -> {
//                    if (h instanceof JFXTextField) {
//                        JFXTextField textField = (JFXTextField) h;
//                        switch (textField.getId()) {
//                            case "nameCell":
//                                userNameMap.put(r.getLocalId(), textField);
//                                textField.setText(r.getName());
//
//                                hbox.setOnMouseEntered(e -> {
//                                    hbox.setStyle("-fx-background-color: #F7F7F8; -fx-background-radius: 8px;");
//                                });
//
//                                hbox.setOnMouseExited(e -> {
//                                    hbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8px;");
//                                });
//                                break;
//                            case "emailCell":
//                                userEmailMap.put(r.getLocalId(), textField);
//                                textField.setText(r.getEmail());
//                                break;
//                            case "phoneCell":
//                                userPhoneMap.put(r.getLocalId(), textField);
//                                textField.setText(r.getPhone());
//                                break;
//                            default:
//                                textField.setText("PANIK");
//                        }
//                    } else if (h instanceof VBox) {
//                        VBox vbox = (VBox) h;
//                        switch (vbox.getId()) {
//                            case "roleBox":
//                                vbox.getChildren().forEach(v -> {
//                                    Label label = (Label) v;
//                                    label.setText(r.isAdmin() ? "True" : "False");
//                                    if (!r.isAdmin()) {
//                                        label.setStyle("-fx-background-color: #f13426; -fx-background-radius: 4px");
//                                    }
//                                });
//                                break;
//                            case "actionsBox":
//                                vbox.getChildren().forEach(v -> {
//                                    JFXButton button = (JFXButton) v;
//                                    ContextMenu contextMenu = new ContextMenu();
//                                    MenuItem makeAdmin = new MenuItem("Make Admin");
//                                    MenuItem revokeAdmin = new MenuItem("Revoke Admin");
//                                    MenuItem delete = new MenuItem("Delete");
//                                    MenuItem saveEdit = new MenuItem("Save Edit");
//                                    if (!r.isAdmin()) {
//                                        contextMenu.getItems().add(makeAdmin);
//                                    } else {
//                                        contextMenu.getItems().add(revokeAdmin);
//                                    }
//                                    contextMenu.getItems().add(delete);
//                                    contextMenu.getItems().add(saveEdit);
//                                    button.setOnAction(b -> {
//                                        contextMenu.show(button, Side.BOTTOM, 0, 0);
//                                    });
//                                    contextMenu.setOnAction(e -> {
//                                        switch (((MenuItem) e.getTarget()).getText()) {
//                                            case "Make Admin":
//                                                Submit.getInstance().grantAdmin(r);
//                                                break;
//                                            case "Revoke Admin":
//                                                Submit.getInstance().revokeAdmin(r);
//                                                break;
//                                            case "Delete":
//                                                Submit.getInstance().deleteUser(r);
//                                                break;
//                                            case "Save Edit":
//                                                String name = userNameMap.get(r.getLocalId()).getText();
//                                                String email = userEmailMap.get(r.getLocalId()).getText();
//                                                String phone = userPhoneMap.get(r.getLocalId()).getText();
//                                                r.setName(name);
//                                                r.setEmail(email);
//                                                r.setPhone(phone);
//                                                Submit.getInstance().editUser(r);
//                                        }
//                                    });
//                                });
//                                break;
//                        }
//                    }
//                });
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
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

        if (userAdded) {
            cellHolder.getChildren().remove(0);
            userAdded = false;
            if (invalidUser) {
                cellHolder.getChildren().remove(0);
                invalidUser = false;
            }
            return;
        }
        JFXTextField nameCell = new JFXTextField();
        nameCell.setAlignment(Pos.CENTER_LEFT);
        nameCell.setStyle("-fx-font-size: 14");
        nameCell.setPadding(new Insets(0, 0, 0, 20));
        nameCell.setPrefWidth(300);
        nameCell.setFocusColor(Color.WHITE);
        nameCell.setUnFocusColor(Color.WHITE);
        nameCell.setPromptText("Employee Name");

        JFXTextField emailCell = new JFXTextField();
        emailCell.setAlignment(Pos.CENTER_LEFT);
        emailCell.setStyle("-fx-font-size: 14");
        emailCell.setPadding(new Insets(0, 0, 0, 20));
        emailCell.setPrefWidth(300);
        emailCell.setFocusColor(Color.WHITE);
        emailCell.setUnFocusColor(Color.WHITE);
        emailCell.setPromptText("Employee Email");

        JFXTextField passwordCell = new JFXTextField();
        passwordCell.setAlignment(Pos.CENTER_LEFT);
        passwordCell.setStyle("-fx-font-size: 14");
        passwordCell.setPadding(new Insets(0, 0, 0, 20));
        passwordCell.setPrefWidth(300);
        passwordCell.setFocusColor(Color.WHITE);
        passwordCell.setUnFocusColor(Color.WHITE);
        passwordCell.setPromptText("Employee Password");

        JFXTextField phoneCell = new JFXTextField();
        phoneCell.setAlignment(Pos.CENTER_LEFT);
        phoneCell.setStyle("-fx-font-size: 14");
        phoneCell.setPadding(new Insets(0, 0, 0, 20));
        phoneCell.setPrefWidth(300);
        phoneCell.setFocusColor(Color.WHITE);
        phoneCell.setUnFocusColor(Color.WHITE);
        phoneCell.setPromptText("Employee Phone Number");

        MaterialDesignIconView addUserIcon = new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT_PLUS);
        addUserIcon.setFill(Paint.valueOf("#c3c3c3"));
        addUserIcon.setGlyphSize(30);

        JFXButton createUserButton = new JFXButton();
        createUserButton.setAlignment(Pos.CENTER_LEFT);
        createUserButton.setPadding(new Insets(-3, 11, 2, 3));
        createUserButton.setGraphic(addUserIcon);

        VBox userBox = new VBox(createUserButton);
        userBox.setAlignment(Pos.CENTER);
        userBox.setPrefWidth(73);

        HBox row = new HBox(nameCell, emailCell, passwordCell, phoneCell, userBox);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: white");
        row.setMaxWidth(1270);
        row.setMinHeight(38);
        cellHolder.getChildren().add(0, row);

        createUserButton.setOnAction(b -> {

            if (invalidUser) {
                cellHolder.getChildren().remove(0);
                invalidUser = false;
            }

            Label invalidName = new Label("");
            invalidName.setStyle("-fx-text-fill: #ff6347; -fx-font-size: 12");
            invalidName.setAlignment(Pos.CENTER_LEFT);
            invalidName.setPadding(new Insets(0, 0, 0, 20));
            invalidName.setMinWidth(300);

            Label invalidEmail = new Label("");
            invalidEmail.setStyle("-fx-text-fill: #ff6347; -fx-font-size: 12");
            invalidEmail.setAlignment(Pos.CENTER_LEFT);
            invalidEmail.setPadding(new Insets(0, 0, 0, 20));
            invalidName.setMinWidth(300);

            Label invalidPassword = new Label("");
            invalidPassword.setStyle("-fx-text-fill: #ff6347; -fx-font-size: 12");
            invalidPassword.setAlignment(Pos.CENTER_LEFT);
            invalidPassword.setPadding(new Insets(0, 0, 0, 20));
            invalidPassword.setMinWidth(300);

            Label invalidPhone = new Label("");
            invalidPhone.setStyle("-fx-text-fill: #ff6347; -fx-font-size: 12");
            invalidPhone.setAlignment(Pos.CENTER_LEFT);
            invalidPhone.setPadding(new Insets(0, 0, 0, 20));
            invalidPhone.setMinWidth(300);

            if (!nameCell.getText().matches("^[aA-zZ]{0,12} [aA-zZ]{0,12}$")) {
                invalidName.setText("Invalid Name");
                HBox invalidRow = new HBox(invalidName, invalidEmail, invalidPassword, invalidPhone);
                cellHolder.getChildren().add(0, invalidRow);
                invalidUser = true;
                return;
            }

            if (!emailCell.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
                invalidName.setText("Invalid Email Address");
                HBox invalidRow = new HBox(invalidName, invalidEmail, invalidPassword, invalidPhone);
                cellHolder.getChildren().add(0, invalidRow);
                invalidUser = true;
                return;
            } else {
                LocalStorage.getInstance().getUsers().forEach(r -> {
                    if (r.getEmail().equals(emailCell.getText())) {
                        invalidName.setText("Email Address already in use");
                        HBox invalidRow = new HBox(invalidName, invalidEmail, invalidPassword, invalidPhone);
                        cellHolder.getChildren().add(0, invalidRow);
                        invalidUser = true;
                    }
                });
            }

            if (invalidUser) { // if the user has already marked as invalid don't check again
                return;
            }

            if (!passwordCell.getText().matches(".{6,32}")) {
                invalidName.setText("Invalid Password");
                HBox invalidRow = new HBox(invalidName, invalidEmail, invalidPassword, invalidPhone);
                cellHolder.getChildren().add(0, invalidRow);
                invalidUser = true;
                return;
            }

            if (!phoneCell.getText().matches("^\\(?([0-9]{3})\\)?-?([0-9]{3})-?([0-9]{4})$")) {
                invalidName.setText("Invalid Phone Number");
                HBox invalidRow = new HBox(invalidName, invalidEmail, invalidPassword, invalidPhone);
                cellHolder.getChildren().add(0, invalidRow);
                invalidUser = true;
                return;
            } else {
                LocalStorage.getInstance().getUsers().forEach(r -> {
                    if(r.getPhone().equals("+1" + phoneCell.getText().replace("-", ""))) {
                        invalidName.setText("Phone Number already in use");
                        HBox invalidRow = new HBox(invalidName, invalidEmail, invalidPassword, invalidPhone);
                        cellHolder.getChildren().add(0, invalidRow);
                        invalidUser = true;
                        return;
                    }
                });
            }

            if (invalidUser) { // if the user has already marked as invalid don't check again
                return;
            }

            User user = new User(emailCell.getText(), nameCell.getText(), phoneCell.getText(), passwordCell.getText());
            Submit.getInstance().newUser(user);
            userAdded = false;
            invalidUser = false;
        });

        userAdded = true;

//        LoadFXML.getInstance().loadWindow("AddEmployee", "userBar", userPop);
    }

    private void generateRow(String employeeID, String employeeName, String employeeEmail, Label roleCell, String employeePhone, JFXButton actionsButton) {

        JFXTextField nameCell = new JFXTextField(employeeName);
        nameCell.setAlignment(Pos.CENTER_LEFT);
        nameCell.setStyle("-fx-font-size: 14");
        nameCell.setPadding(new Insets(0, 0, 0, 20));
        nameCell.setPrefWidth(300);
        nameCell.setFocusColor(Color.WHITE);
        nameCell.setUnFocusColor(Color.WHITE);
        userNameMap.put(employeeID, nameCell);

        JFXTextField emailCell = new JFXTextField(employeeEmail);
        emailCell.setAlignment(Pos.CENTER_LEFT);
        emailCell.setStyle("-fx-font-size: 14");
        emailCell.setPadding(new Insets(0, 0, 0, 20));
        emailCell.setPrefWidth(300);
        emailCell.setFocusColor(Color.WHITE);
        emailCell.setUnFocusColor(Color.WHITE);
        userEmailMap.put(employeeID, emailCell);

        VBox roleBox = new VBox(roleCell);
        roleBox.setAlignment(Pos.CENTER_LEFT);
        roleBox.setPrefWidth(300);
        VBox.setMargin(roleCell, new Insets(0, 0, 0, 10));

        JFXTextField phoneCell = new JFXTextField(employeePhone);
        phoneCell.setAlignment(Pos.CENTER_LEFT);
        phoneCell.setStyle("-fx-font-size: 14");
        phoneCell.setPadding(new Insets(0, 0, 0, 20));
        phoneCell.setPrefWidth(300);
        phoneCell.setFocusColor(Color.WHITE);
        phoneCell.setUnFocusColor(Color.WHITE);
        userPhoneMap.put(employeeID, emailCell);

        VBox actionsBox = new VBox(actionsButton);
        actionsBox.setAlignment(Pos.CENTER);
        actionsBox.setPrefWidth(73);

        HBox row = new HBox(nameCell, emailCell, roleBox, phoneCell, actionsBox);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: white");
        row.setMaxWidth(1270);
        row.setMinHeight(38);
        cellHolder.getChildren().add(row);

        row.setOnMouseEntered(e -> {
            row.setStyle("-fx-background-color: #F7F7F8; -fx-background-radius: 8px;");
        });

        row.setOnMouseExited(e -> {
            row.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8px;");
        });
    }
}
