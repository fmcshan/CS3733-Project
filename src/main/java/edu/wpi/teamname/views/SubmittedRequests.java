package edu.wpi.teamname.views;

import edu.wpi.teamname.Database.GiftDeliveryStorage;
import edu.wpi.teamname.Database.LocalStorage;
//import edu.wpi.teamname.Database.socketListeners.Initiator;
//import edu.wpi.teamname.Database.socketListeners.RegistrationListener;
import edu.wpi.teamname.Database.socketListeners.GiftDeliveryListener;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.util.ArrayList;
public class SubmittedRequests implements GiftDeliveryListener {
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

    private GiftDeliveryStorage currentlySelected = null;

    public SubmittedRequests() {

    }

    public void initialize() {

        //Initiator.getInstance().addRegistrationListener(this);

//        acknowledgeColumn.setCellValueFactory(cellData -> cellData);
//// or cellData -> new SimpleBooleanProperty(cellData.getValue().getGender())
//// if your model class doesn't use JavaFX properties
//
//        acknowledgeColumn.setCellFactory(col -> new TableCell<edu.wpi.teamname.Database.UserRegistration, Boolean>() {
//            @Override
//            protected void updateItem(Boolean item, boolean empty) {
//                super.updateItem(item, empty) ;
//                setText(empty ? null : item ? "Male" : "Female" );
//            }
//        });
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
        requestedByColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        contactColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        assignToColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        requestTypeColumn.setCellValueFactory(new PropertyValueFactory<>("requestType"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        requestedItemsColumn.setCellValueFactory(new PropertyValueFactory<>("requestedItems"));
        requestedByColumn.setCellValueFactory(new PropertyValueFactory<>("requestedBy"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        assignToColumn.setCellValueFactory(new PropertyValueFactory<>("assignTo"));
//        assignToColumn.setCellFactory(col -> {
//            ArrayList<String> options = new ArrayList<String>();
//            options.add("Sally");;
//            options.add("Joe");
//            TableCell<String, StringProperty> c = new TableCell<>();
//            final ComboBox<String> comboBox = new ComboBox<String>((ObservableList<String>) options);
//            c.itemProperty().addListener((observable, oldValue, newValue) -> {
//                if (oldValue != null) {
//                    comboBox.valueProperty().unbindBidirectional(oldValue);
//                }
//                if (newValue != null) {
//                    comboBox.valueProperty().bindBidirectional(newValue);
//                }
//            });
//            //c.graphicProperty().bind(Bindings.when(c.emptyProperty()).then((String) null).otherwise((ObservableStringValue) comboBox));
//            return c;
//        });

        //loadCSVFileName.setText("L1Edges.csv"); // Set input text to default file
        loadData(); // Load file to table

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            currentlySelected = (GiftDeliveryStorage) newSelection; // Listen for row selection events
        });
    }

    public void loadData() {
        ArrayList<GiftDeliveryStorage> requests = LocalStorage.getInstance().getGiftDeliveryStorages();

        if (requests == null) {
            return;
        }

        requests.forEach(e -> {
            table.getItems().add(0, e);
        }); // Populate table
    }

    @Override
    public void giftDeliveryAdded(GiftDeliveryStorage _obj) {
        table.getItems().add(0, _obj);
    }

//    public void closeForm(ActionEvent actionEvent) {
//        Success success = new Success(new UserRegistration());
//        success.loadSuccess();
//    }

}
