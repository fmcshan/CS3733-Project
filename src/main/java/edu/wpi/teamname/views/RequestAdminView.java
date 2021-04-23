package edu.wpi.teamname.views;

import edu.wpi.teamname.Database.GiftDeliveryStorage;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.Parser;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Database.socketListeners.GiftDeliveryListener;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.BooleanStringConverter;

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

    private GiftDeliveryStorage updatedForm = null;

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

        assignToColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // set cell to text field
        requestedByColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // set cell to text field
        contactColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // set cell to text field
        requestTypeColumn.setCellValueFactory(new PropertyValueFactory<>("requestType"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        requestedItemsColumn.setCellValueFactory(new PropertyValueFactory<>("requestedItems"));
        requestedByColumn.setCellValueFactory(new PropertyValueFactory<>("requestedBy"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        assignToColumn.setCellValueFactory(new PropertyValueFactory<>("assignTo"));

        //completeCheckBox.setCellFactory(TextFieldTableCell.forTableColumn()); // set cell to text field
        completeCheckBox.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        completeCheckBox.setCellValueFactory(new PropertyValueFactory<>("completed"));

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
        table.getItems().clear();
        loadData();

    }

    /**
     * Close form once exit button is pressed
     * @param actionEvent
     */
    public void exitView(ActionEvent actionEvent) {
        LoadFXML.setCurrentWindow("");
        SceneManager.getInstance().getDefaultPage().closeWindows();
    }

    public void assignToChange(TableColumn.CellEditEvent cellEditEvent) {
        GiftDeliveryStorage request = (GiftDeliveryStorage) cellEditEvent.getRowValue(); // Current row
        String newAssignedTo = cellEditEvent.getNewValue().toString();
        GiftDeliveryStorage newRequest = new GiftDeliveryStorage(request.getId(), request.getRequestType(), request.getLocation(), request.getRequestedItems(), request.getRequestedBy(), request.getContact(), newAssignedTo, false);
        Submit.getInstance().updateGiftDelivery(newRequest);
    }

    public void doneChange(TableColumn.CellEditEvent cellEditEvent) {
        GiftDeliveryStorage request = (GiftDeliveryStorage) cellEditEvent.getRowValue(); // Current row
        Boolean isCompleted = Boolean.parseBoolean(String.valueOf(cellEditEvent.getNewValue()));
        //System.out.println(isCompleted);
        GiftDeliveryStorage newRequest = new GiftDeliveryStorage(request.getId(), request.getRequestType(), request.getLocation(), request.getRequestedItems(), request.getRequestedBy(), request.getContact(), request.getAssignTo(), isCompleted);
        Submit.getInstance().updateGiftDelivery(newRequest);
    }
}
