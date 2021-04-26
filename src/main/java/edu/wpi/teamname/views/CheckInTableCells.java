package edu.wpi.teamname.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CheckInTableCells {

    @FXML
    private Label nameCell;

    @FXML
    private Label dateCell;

    @FXML
    private Label reasonCell;

    @FXML
    private Label phoneCell;

    public Label getNameCell() {
        return nameCell;
    }

    public Label getDateCell() {
        return dateCell;
    }

    public Label getReasonCell() {
        return reasonCell;
    }

    public Label getPhoneCell() {
        return phoneCell;
    }
}
