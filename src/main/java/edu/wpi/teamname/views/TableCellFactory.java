package edu.wpi.teamname.views;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class TableCellFactory {

    // TDODO: Implement this, killed due to lack of time.
    public static void generate_sanitation_cell() {
        HBox cellWrapper = new HBox();
        cellWrapper.setStyle("-fx-background-color: #fff; -fx-background-radius: 8px;");

        Label nameLabel = new Label("Name");
        nameLabel.setPrefWidth(181.14);
        nameLabel.setStyle("-fx-font-size: 14px;");

        Label urgencyLabel = new Label("Level of Urgency");
        urgencyLabel.setPrefWidth(262.28);
        urgencyLabel.setStyle("-fx-font-size: 14px;");

        Label reason = new Label("Reason");
        reason.setPrefWidth(181.14);
        reason.setStyle("-fx-font-size: 14px;");

        Label location = new Label("Location");
        location.setPrefWidth(181.14);
        location.setStyle("-fx-font-size: 14px;");

//        Label location = new Label("Location");
//        location.setPrefWidth(181.14);
//        location.setStyle("-fx-font-size: 14px;");
    }
}
