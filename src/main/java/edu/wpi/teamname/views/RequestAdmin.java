package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.MasterServiceRequestStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Database.socketListeners.GiftDeliveryListener;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import edu.wpi.teamname.ServiceRequests.FacilitiesRequest;
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

public class RequestAdmin implements GiftDeliveryListener {

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
//            ex.printStackTrace();
        }
        return null;
    }

    private void addGiftDelivery(MasterServiceRequestStorage _req) {
        try {
            switch (_req.getRequestType()) {
                case "Gift Delivery":
                    giftCellHolder.getChildren().add(TableCellFactory.generate_gift_delivery_request(_req));
                    cellHolder.getChildren().add(TableCellFactory.generate_gift_delivery_request(_req));
                    break;

                case "Food Delivery":
                    foodCellHolder.getChildren().add(TableCellFactory.generate_food_delivery_request(_req));
                    cellHolder.getChildren().add(TableCellFactory.generate_food_delivery_request(_req));
                    break;

                case "Computer Services":
                    computerCellHolder.getChildren().add(TableCellFactory.generate_computer_service_request(_req));
                    cellHolder.getChildren().add(TableCellFactory.generate_computer_service_request(_req));
                    break;

                case "Facilities Maintenance":
                    facilitiesCellHolder.getChildren().add(TableCellFactory.generate_facilities_maintenance_request(_req));
                    cellHolder.getChildren().add(TableCellFactory.generate_facilities_maintenance_request(_req));
                    break;

                case "Laundry Service":
                    laundryCellHolder.getChildren().add(TableCellFactory.generate_laundry_service_request(_req));
                    cellHolder.getChildren().add(TableCellFactory.generate_laundry_service_request(_req));
                    break;

                case "Medicine Delivery":
                    medicineCellHolder.getChildren().add(TableCellFactory.generate_medicine_delivery_cell(_req));
                    cellHolder.getChildren().add(TableCellFactory.generate_medicine_delivery_cell(_req));
                    break;

                case "Patient Transportation":
                    transportCellHolder.getChildren().add(TableCellFactory.generate_patient_transportation_cell(_req));
                    cellHolder.getChildren().add(TableCellFactory.generate_patient_transportation_cell(_req));
                    break;

                case "Sanitation Service":
                    sanitationCellHolder.getChildren().add(TableCellFactory.generate_sanitation_cell(_req));
                    cellHolder.getChildren().add(TableCellFactory.generate_sanitation_cell(_req));
                    break;
                default:
                    System.out.println("PANIK");
            }
        } catch (Exception ex) {
//                ex.printStackTrace();
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loadTables();
            }
        });
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
