package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.MasterServiceRequestStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Database.socketListeners.GiftDeliveryListener;
import edu.wpi.teamname.Database.socketListeners.Initiator;
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
import javafx.scene.paint.Color;

import java.io.IOException;

public class RequestAdminNew implements GiftDeliveryListener {

    @FXML
    private VBox cellHolder;
    @FXML
    private VBox giftCellHolder;
    @FXML
    private VBox foodCellHolder;
    @FXML
    private VBox computerCellHolder;
    @FXML
    private VBox facilitiesCellHolder;
    @FXML
    private VBox laundryCellHolder;
    @FXML
    private VBox medicineCellHolder;
    @FXML
    private VBox transportCellHolder;
    @FXML
    private VBox sanitationCellHolder;

    public void initialize() {
        Initiator.getInstance().addGiftDeliveryListener(this);
        loadTables();
    }

    public Node loadWindow(String fileName) {
        try {
            return FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/ServiceRequestCells/" + fileName + "Cells.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void addGiftDelivery(MasterServiceRequestStorage _req) {
        for (int i = 0; i < 2; i++) {
            try {
                String requestType = _req.getRequestType().replace(" ", "");
                Node node = loadWindow(requestType);
                if (i == 0) {
                    cellHolder.getChildren().add(node);
                }
                HBox hbox = (HBox) node;
                switch (_req.getRequestType()) {
                    case "Gift Delivery":
                        System.out.println("In gift delivery");
                        if (i == 1) {
                            giftCellHolder.getChildren().add(node);
                        }
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch (label.getId()) {
                                    case "nameCell":
                                        label.setText(_req.getRequestedBy());
                                        break;
                                    case "giftCell":
                                        String youAreStringNow = String.join(", ", _req.getRequestedItems());
                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "phoneCell":
                                        label.setText(_req.getContact());
                                        break;
                                    case "locationCell":
                                        label.setText(_req.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            } else loadData(_req, h);
                        });
                        break;
                    case "Food Delivery":
                        System.out.println("in food delivery");
                        if (i == 1) {
                            foodCellHolder.getChildren().add(node);
                        }
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch (label.getId()) {
                                    case "nameCell":
                                        label.setText(_req.getRequestedBy());
                                        break;
                                    case "foodCell":
                                        String youAreStringNow = String.join(", ", _req.getRequestedItems());
                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "phoneCell":
                                        label.setText(_req.getContact());
                                        break;
                                    case "locationCell":
                                        label.setText(_req.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            } else {
                                loadData(_req, h);
                            }
                        });
                        break;
                    case "Computer Services":
                        if (i == 1) {
                            computerCellHolder.getChildren().add(node);
                        }
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch (label.getId()) {
                                    case "nameCell":
                                        label.setText(_req.getRequestedBy());
                                        break;
                                    case "descriptionCell":
                                        label.setText(_req.getDescription());
                                        break;
                                    case "priorityCell":
                                        label.setText(_req.getRequestedItems().get(0).replace("\"", ""));
                                        break;
                                    case "phoneCell":
                                        label.setText(_req.getContact());
                                        break;
                                    case "locationCell":
                                        label.setText(_req.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            } else loadData(_req, h);
                        });
                        break;
                    case "Facilities Request":
                        if (i == 1) {
                            facilitiesCellHolder.getChildren().add(node);
                        }
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch (label.getId()) {
                                    case "nameCell":
                                        label.setText(_req.getRequestedBy());
                                        break;
                                    case "descriptionCell":
                                        label.setText(_req.getDescription());
                                        break;
                                    case "urgencyCell":
                                        label.setText(_req.getRequestedItems().get(0).replace("\"", ""));
                                        break;
                                    case "phoneCell":
                                        label.setText(_req.getContact());
                                        break;
                                    case "locationCell":
                                        label.setText(_req.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            } else loadData(_req, h);
                        });
                        break;
                    case "Laundry Service":
                        if (i == 1) {
                            laundryCellHolder.getChildren().add(node);
                        }
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch (label.getId()) {
                                    case "nameCell":
                                        label.setText(_req.getRequestedBy());
                                        break;
                                    case "loadCell":
                                        label.setText(_req.getRequestedItems().get(0).replace("\"", ""));
                                        break;
                                    case "washCell":
                                        label.setText(_req.getDescription());
                                        break;
                                    case "phoneCell":
                                        label.setText(_req.getContact());
                                        break;
                                    case "locationCell":
                                        label.setText(_req.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            } else loadData(_req, h);
                        });
                        break;
                    case "Medicine Delivery":
                        if (i == 1) {
                            medicineCellHolder.getChildren().add(node);
                        }
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch (label.getId()) {
                                    case "nameCell":
                                        label.setText(_req.getRequestedBy());
                                        break;
                                    case "medicationNameCell":
                                        label.setText(_req.getRequestedItems().get(0).replace("\"", ""));
                                        break;
                                    case "dosageCell":
                                        label.setText(_req.getDescription());
                                        break;
                                    case "locationCell":
                                        label.setText(_req.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            } else loadData(_req, h);
                        });
                        break;
                    case "Patient Transportation":
                        if (i == 1) {
                            transportCellHolder.getChildren().add(node);
                        }
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch (label.getId()) {
                                    case "nameCell":
                                        label.setText(_req.getRequestedBy());
                                        break;
                                    case "currentLocationCell":
                                        label.setText(_req.getLocation());
                                        break;
                                    case "destinationCell":
                                        label.setText(_req.getDescription());
                                        break;
                                    case "reasonCell":
                                        label.setText(_req.getRequestedItems().get(0).replace("\"", ""));
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            } else loadData(_req, h);
                        });
                        break;
                    case "Sanitation Service":
                        if (i == 1) {
                            sanitationCellHolder.getChildren().add(node);
                        }
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch (label.getId()) {
                                    case "nameCell":
                                        label.setText(_req.getRequestedBy());
                                        break;
                                    case "urgencyCell":
                                        label.setText(_req.getRequestedItems().get(0).replace("\"", ""));
                                        break;
                                    case "reasonCell":
                                        label.setText(_req.getContact());
                                        break;
                                    case "locationCell":
                                        label.setText(_req.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            } else loadData(_req, h);
                        });
                        break;
                    default:
                        System.out.println("PANIK");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void loadData(MasterServiceRequestStorage _req, Node h) {
        if (h instanceof VBox) {
            VBox vBox = (VBox) h;
            vBox.getChildren().forEach(v -> {
                if (v instanceof JFXComboBox) {
                    JFXComboBox combo = (JFXComboBox) v;
                    combo.setValue(_req.getAssignTo());
                    LocalStorage.getInstance().getUsers().forEach(u -> combo.getItems().add(u.getName()));
                    combo.setOnAction(c -> {
                        _req.setAssignTo(combo.getValue().toString());
                        Submit.getInstance().updateGiftDelivery(_req);
                    });
                } else if (v instanceof Label) {
                    Label label = (Label) v;
                    if (_req.isCompleted()) {
                        label.setText("Completed");
                        label.setTextFill(Color.WHITE);
                        label.setStyle("-fx-background-color:#00c455;-fx-background-radius: 4px;");
                    } else if (!_req.getAssignTo().isEmpty()) {
                        label.setText("In Progress");
                        label.setTextFill(Color.valueOf("#626d7c"));
                        label.setStyle("-fx-background-color:#ebf0f5;-fx-background-radius: 4px;");
                    } else {
                        label.setText("Unassigned");
                        label.setTextFill(Color.WHITE);
                        label.setStyle("-fx-background-color:#f13426;-fx-background-radius: 4px;");
                    }

                } else if (v instanceof JFXButton) {
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem completed = new MenuItem("Mark as Completed");
                    MenuItem reset = new MenuItem("Reset");
                    MenuItem delete = new MenuItem("Delete");
                    if (!_req.isCompleted()) {
                        contextMenu.getItems().add(completed);
                    } else {
                        contextMenu.getItems().add(reset);
                    }
                    contextMenu.getItems().add(delete);
                    JFXButton button = (JFXButton) v;
                    button.setOnAction(b -> contextMenu.show(button, Side.BOTTOM, 0, 0));
                    contextMenu.setOnAction(e -> {
                        switch (((MenuItem) e.getTarget()).getText()) {
                            case "Mark as Completed":
                                _req.setCompleted(true);
                                Submit.getInstance().updateGiftDelivery(_req);
                                break;
                            case "Delete":
                                Submit.getInstance().deleteGiftDelivery(_req);
                                break;
                            case "Reset":
                                _req.setAssignTo("");
                                _req.setCompleted(false);
                                Submit.getInstance().updateGiftDelivery(_req);
                                break;
                        }
                    });
                }
            });
        }
    }

    private void loadTables() {
        cellHolder.getChildren().clear();
        giftCellHolder.getChildren().clear();
        foodCellHolder.getChildren().clear();
        computerCellHolder.getChildren().clear();
        facilitiesCellHolder.getChildren().clear();
        laundryCellHolder.getChildren().clear();
        medicineCellHolder.getChildren().clear();
        transportCellHolder.getChildren().clear();
        sanitationCellHolder.getChildren().clear();
        LocalStorage.getInstance().getMasterStorages().forEach(this::addGiftDelivery);
    }

    @Override
    public void giftDeliveryAdded(MasterServiceRequestStorage _obj) {
        loadTables();
    }

    @Override
    public void giftDeliveryUpdated() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loadTables();
            }
        });
    }
}
