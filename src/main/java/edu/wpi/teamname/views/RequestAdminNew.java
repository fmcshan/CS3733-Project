package edu.wpi.teamname.views;

import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.MasterServiceRequestStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RequestAdminNew {

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
        LocalStorage.getInstance().getMasterStorages().forEach(g -> {
            try {
                String requestType = g.getRequestType().replace(" ", "");
                System.out.println(requestType);
                Node node = loadWindow(requestType);
                cellHolder.getChildren().add(node); //TODO Clements Haaaalp
                HBox hbox = (HBox) node;
                switch (g.getRequestType()) {
                    case "Gift Delivery":
                        giftCellHolder.getChildren().add(node);
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch(label.getId()) {
                                    case "nameCell":
                                        label.setText(g.getRequestedBy());
                                        break;
                                    case "giftCell":
                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "phoneCell":
                                        label.setText(g.getContact());
                                        break;
                                    case "locationCell":
                                        label.setText(g.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            }
                        });
                        break;
                    case "Food Delivery":
                        foodCellHolder.getChildren().add(node);
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch(label.getId()) {
                                    case "nameCell":
                                        label.setText(g.getRequestedBy());
                                        break;
                                    case "foodCell":
                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "phoneCell":
                                        label.setText(g.getContact());
                                        break;
                                    case "locationCell":
                                        label.setText(g.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            }
                        });
                        break;
                    case "Computer Services": //TODO revisit priority
                        computerCellHolder.getChildren().add(node);
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch(label.getId()) {
                                    case "nameCell":
                                        label.setText(g.getRequestedBy());
                                        break;
                                    case "descriptionCell":
                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "priorityCell":
//                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
//                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "phoneCell":
                                        label.setText(g.getContact());
                                        break;
                                    case "locationCell":
                                        label.setText(g.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            }
                        });
                        break;
                    case "Facilities Request": //TODO revisit Urgency
                        facilitiesCellHolder.getChildren().add(node);
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch(label.getId()) {
                                    case "nameCell":
                                        label.setText(g.getRequestedBy());
                                        break;
                                    case "descriptionCell":
                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "urgencyCell":
//                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
//                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "phoneCell":
                                        label.setText(g.getContact());
                                        break;
                                    case "locationCell":
                                        label.setText(g.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            }
                        });
                        break;
                    case "Laundry Service": //TODO revisit Wash
                        laundryCellHolder.getChildren().add(node);
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch(label.getId()) {
                                    case "nameCell":
                                        label.setText(g.getRequestedBy());
                                        break;
                                    case "loadCell":
                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "washCell":
//                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
//                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "phoneCell":
                                        label.setText(g.getContact());
                                        break;
                                    case "locationCell":
                                        label.setText(g.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            }
                        });
                        break;
                    case "Medicine Delivery": //TODO revisit Dosage
                        medicineCellHolder.getChildren().add(node);
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch(label.getId()) {
                                    case "nameCell":
                                        label.setText(g.getRequestedBy());
                                        break;
                                    case "medicationCell":
                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "dosageCell":
//                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
//                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "locationCell":
                                        label.setText(g.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            }
                        });
                        break;
                    case "Patient Transportation": //TODO revisit Dest and Assistance
                        transportCellHolder.getChildren().add(node);
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch(label.getId()) {
                                    case "nameCell":
                                        label.setText(g.getRequestedBy());
                                        break;
                                    case "currentCell":
                                        label.setText(g.getLocation());
                                        break;
                                    case "destCell":
//                                        label.setText(g.getLocation());
                                        break;
                                    case "assistanceCell":
//                                        label.setText(g.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            }
                        });
                        break;
                    case "Sanitation Service": // TODO revisit Reason
                        sanitationCellHolder.getChildren().add(node);
                        hbox.getChildren().forEach(h -> {
                            if (h instanceof Label) {
                                Label label = (Label) h;
                                switch(label.getId()) {
                                    case "nameCell":
                                        label.setText(g.getRequestedBy());
                                        break;
                                    case "urgencyCell":
                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "reasonCell":
//                                        String youAreStringNow = String.join(", ", g.getRequestedItems());
//                                        label.setText(youAreStringNow.replace("\"", ""));
                                        break;
                                    case "locationCell":
                                        label.setText(g.getLocation());
                                        break;
                                    default:
                                        label.setText("PANIK");
                                }
                            }
                        });
                        break;
                    default:
                        System.out.println("PANIK");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public Node loadWindow(String fileName) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/ServiceRequestCells/" + fileName + "Cells.fxml"));
            return node;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
