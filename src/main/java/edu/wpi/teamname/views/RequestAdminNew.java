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

    public void initialize() {
        LocalStorage.getInstance().getMasterStorages().forEach(g -> {
            try {
                String requestType = g.getRequestType().replace(" ", "");
                Node node = loadWindow(requestType);
                cellHolder.getChildren().add(node);
                HBox hbox = (HBox) node;
                switch (g.getRequestType()) {
                    case "Gift Delivery":
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
                    case "Computer Service":
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
                    case "Facilities Maintenance":
                        break;
                    case "Laundry Service":
                        break;
                    case "Medicine Delivery":
                        break;
                    case "Patient Transportation":
                        break;
                    case "Sanitation Service":
                        break;
                    default:
                        System.out.println("PANIK");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public Node loadWindow(String fileName) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/Service Request Cells/" + fileName + "Cells.fxml"));
            return node;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
