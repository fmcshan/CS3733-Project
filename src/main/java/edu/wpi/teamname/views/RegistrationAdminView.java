package edu.wpi.teamname.views;

import edu.wpi.teamname.Database.LocalStorage;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class RegistrationAdminView {

    @FXML
    private VBox cellHolder;

    public void initialize() {
        LocalStorage.getInstance().getRegistrations().forEach(r -> {
            //String reasonsForVisit = String.join(", ", r.getReasonsForVisit()).replace("\"", "");
            cellHolder.getChildren().add(TableCellFactory.generate_check_in(r));
            //generateRow(r.getName(), r.getDate(), reasonsForVisit, r.getPhoneNumber());
        });
    }

//    private void generateRow(String patientName, String patientDOB, String patientReason, String patientPhone) {
//
//        Label nameCell = new Label(patientName);
//        nameCell.setAlignment(Pos.CENTER_LEFT);
//        nameCell.setStyle("-fx-font-size: 14");
//        nameCell.setPadding(new Insets(0, 0, 0, 20));
//        nameCell.setPrefWidth(254);
//
//        Label dateCell = new Label(patientDOB);
//        dateCell.setAlignment(Pos.CENTER_LEFT);
//        dateCell.setStyle("-fx-font-size: 14");
//        dateCell.setPadding(new Insets(0, 0, 0, 20));
//        dateCell.setPrefWidth(204);
//
//        Label reasonCell = new Label(patientReason);
//        reasonCell.setAlignment(Pos.CENTER_LEFT);
//        reasonCell.setStyle("-fx-font-size: 14");
//        reasonCell.setPadding(new Insets(0, 0, 0, 20));
//        reasonCell.setPrefWidth(584);
//
//        Label phoneCell = new Label(patientPhone);
//        phoneCell.setAlignment(Pos.CENTER_LEFT);
//        phoneCell.setStyle("-fx-font-size: 14");
//        phoneCell.setPadding(new Insets(0, 0, 0, 20));
//        phoneCell.setPrefWidth(232);
//
//        HBox row = new HBox(nameCell, dateCell, reasonCell, phoneCell);
//        row.setAlignment(Pos.CENTER_LEFT);
//        row.setStyle("-fx-background-color: white");
//        row.setMaxWidth(1270);
//        row.setMinHeight(38);
//        cellHolder.getChildren().add(row);
//
//        row.setOnMouseEntered(e -> {
//            row.setStyle("-fx-background-color: #F7F7F8; -fx-background-radius: 8px;");
//        });
//
//        row.setOnMouseExited(e -> {
//            row.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8px;");
//        });
//    }
}
