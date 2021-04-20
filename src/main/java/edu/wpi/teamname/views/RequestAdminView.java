package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teamname.Database.GiftDeliveryStorage;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.socketListeners.GiftDeliveryListener;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import edu.wpi.teamname.bridge.Bridge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import java.util.ArrayList;

/**
 * Controller for RequestAdminView.fxml
 * @author Lauren Sowerbutts, Frank McShan
 */
public class RequestAdminView implements GiftDeliveryListener {
    @FXML
    public TableView table;
    @FXML
    public TableColumn requestTypeColumn;
    @FXML
    public TableColumn locationColumn;
    @FXML
    public TableColumn requestedItemsColumn;
    @FXML
    public TableColumn requestedByColumn;
    @FXML
    public TableColumn contactColumn;
    @FXML
    public TableColumn assignToColumn;
    @FXML
    public TableColumn completeCheckBox;

    private GiftDeliveryStorage currentlySelected = null;

    /**
     * Run on startup
     */
    public void initialize() {
        Initiator.getInstance().addGiftDeliveryListener(this);

        requestTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        locationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        requestedItemsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<ArrayList<String>>() {
            @Override
            public String toString(ArrayList<String> object) {
                String ans = "";
                for (String s : object) {
                    ans += s + " ";
                }
                return ans.replace("\"", "");
            }

            @Override
            public ArrayList<String> fromString(String string) {
                return null;
            }
        }));

        ObservableList<String> people = FXCollections.observableArrayList("Lauren", "Frank", "Justin"); // create list of employees
        assignToColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), people)); // set cell to combobox
        completeCheckBox.setCellFactory(CheckBoxTableCell.forTableColumn(completeCheckBox)); // set cell to checkbox
        requestedByColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // set cell to text field
        contactColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // set cell to text field
        requestTypeColumn.setCellValueFactory(new PropertyValueFactory<>("requestType"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        requestedItemsColumn.setCellValueFactory(new PropertyValueFactory<>("requestedItems"));
        requestedByColumn.setCellValueFactory(new PropertyValueFactory<>("requestedBy"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        loadData(); // Load file to table

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            currentlySelected = (GiftDeliveryStorage) newSelection; // Listen for row selection events
        });
    }

    /**
     * Load data into table
     */
    public void loadData() {
        ArrayList<GiftDeliveryStorage> requests = LocalStorage.getInstance().getGiftDeliveryStorages();

        if (requests == null) {
            return;
        }

        requests.forEach(e -> {
            table.getItems().add(0, e);
        }); // Populate table
    }

    /**
     * When a form is submitted, add to table
     * @param _obj
     */
    @Override
    public void giftDeliveryAdded(GiftDeliveryStorage _obj) {
        table.getItems().add(0, _obj);
    }

    @Override
    public void giftDeliveryUpdated() {

    }

    /**
     * Close form once exit button is pressed
     * @param actionEvent
     */
    public void exitView(ActionEvent actionEvent) {
        LoadFXML.setCurrentWindow("");
        Bridge.getInstance().close();
    }

}
