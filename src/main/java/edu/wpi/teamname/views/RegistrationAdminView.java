package edu.wpi.teamname.views;

import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import edu.wpi.teamname.Database.socketListeners.RegistrationListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class RegistrationAdminView implements RegistrationListener {
    @FXML
    public TableView table;
    @FXML
    public TableColumn nameColumn;
    @FXML
    public TableColumn dateOfBirthColumn;
    //    @FXML
//    public TableColumn submittedAtColumn;
    @FXML
    public TableColumn reasonsForVisitColumn;
    @FXML
    public TableColumn phoneNumberColumn;
    //    @FXML
//    public TableColumn acknowledgeColumn;
    private edu.wpi.teamname.Database.UserRegistration currentlySelected = null;

    public RegistrationAdminView() {
        //initialize();
        //ArrayList<String> test = new ArrayList<>();
        //test.add("hi");
        //table.setItems((ObservableList) new edu.wpi.teamname.Database.UserRegistration("hi", "hi", test, "hi"));
        //nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //nameColumn.getColumns().add()
        //nameColumn.getColumns().add("Test");
        //nameColumn.setCellValueFactory(new PropertyValueFactory<String>("Jane Doe"));
        //nameColumn.getColumns().add()
    }

    public void initialize() {

        Initiator.getInstance().addRegistrationListener(this);

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

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateOfBirthColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        //submittedAtColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        reasonsForVisitColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<ArrayList<String>>() {
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
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        //acknowledgeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));


        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        //submittedAtColumn.setCellValueFactory(new PropertyValueFactory<>("submittedAt"));
        reasonsForVisitColumn.setCellValueFactory(new PropertyValueFactory<>("reasonsForVisit"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        //acknowledgeColumn.setCellValueFactory(new PropertyValueFactory<>("acknowledged"));

        //loadCSVFileName.setText("L1Edges.csv"); // Set input text to default file
        loadData(); // Load file to table

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            currentlySelected = (edu.wpi.teamname.Database.UserRegistration) newSelection; // Listen for row selection events
        });
    }

    public void loadData() {
        ArrayList<edu.wpi.teamname.Database.UserRegistration> registrations = LocalStorage.getInstance().getRegistrations();

        if (registrations == null) {
            return;
        }

        registrations.forEach(e -> {
            table.getItems().add(0, e);
        }); // Populate table
    }

    public void closeForm(ActionEvent actionEvent) {
        Success success = new Success(new UserRegistration());
        success.loadSuccess();
    }

    @Override
    public void registrationAdded(edu.wpi.teamname.Database.UserRegistration _obj) {
        table.getItems().add(0, _obj);
    }
}
