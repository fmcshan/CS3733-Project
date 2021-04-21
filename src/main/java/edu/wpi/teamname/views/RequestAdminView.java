package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.teamname.Algo.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.GiftDeliveryStorage;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Database.socketListeners.GiftDeliveryListener;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import edu.wpi.teamname.bridge.Bridge;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import java.util.Observable;

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
        //assignToColumn.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));

//        final ObservableList<String> observableList = FXCollections.observableList(
//                new ArrayList<>(),
//                (String tp) -> {
//                    return new Observable[]{tp};
//                });
//
//        final ObservableList<TestProperty> observableList = FXCollections.observableList(
//                new ArrayList<>(),
//                (TestProperty tp) -> new Observable[]{tp.selectedProperty(), tp.titleProperty()});

        ArrayList<String> intList = new ArrayList();
        intList.add("Frank");
        intList.add("Lauren");

        //ObservableList<String> ob = FXCollections.observableArrayList(intList);
        people.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
                System.out.println("Changed on " + c);
                if(c.next()){
                    System.out.println(c.getFrom());
                }

            }

        });

        //ob.set(0, 1);

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
        Bridge.getInstance().close();
    }

    public void assignToChange(TableColumn.CellEditEvent cellEditEvent) {
        GiftDeliveryStorage request = (GiftDeliveryStorage) cellEditEvent.getRowValue(); // Current row
        String newAssignedTo = cellEditEvent.getNewValue().toString();
        GiftDeliveryStorage newRequest = new GiftDeliveryStorage(request.getId(), request.getRequestType(), request.getLocation(), request.getRequestedItems(), request.getRequestedBy(), request.getContact(), newAssignedTo, request.isCompleted());
        Submit.getInstance().updateGiftDelivery(newRequest);
    }

    public void doneChange(TableColumn.CellEditEvent cellEditEvent) {
        GiftDeliveryStorage request = (GiftDeliveryStorage) cellEditEvent.getRowValue(); // Current row
        boolean newAssignedTo = (boolean) cellEditEvent.getNewValue();
        GiftDeliveryStorage newRequest = new GiftDeliveryStorage(request.getId(), request.getRequestType(), request.getLocation(), request.getRequestedItems(), request.getRequestedBy(), request.getContact(), request.getAssignTo(), newAssignedTo);
        Submit.getInstance().updateGiftDelivery(newRequest);
    }

//    public void calcPath() {
//        if (table.get() == null || !nodesMap.containsKey(fromCombo.getValue())) { // if combobox is null or the key does not exist
//            return;
//        }
//        if (toCombo.getValue() == null || !nodesMap.containsKey(toCombo.getValue())) { // if combobox is null or the key does not exist
//            return;
//        }
//        Node startNode = nodesMap.get(fromCombo.getValue()); // get starting location
//        Node endNode = nodesMap.get(toCombo.getValue()); // get ending location
//        AStar AStar = new AStar(listOfNodes, startNode, endNode); // perform AStar
//        ArrayList<Node> path = AStar.returnPath(); // list the nodes found using AStar to create a path
//        defaultPage.drawPath(path); // draw the path on the map
//    }
}
