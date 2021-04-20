package edu.wpi.teamname.views;

import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.socketListeners.Initiator;
import edu.wpi.teamname.Database.socketListeners.RegistrationListener;
import edu.wpi.teamname.bridge.Bridge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.util.ArrayList;

/**
 * Controller for RegistrationAdminView.fxml
 * @author Lauren Sowerbutts, Frank McShan
 */
public class RegistrationAdminView implements RegistrationListener {
    @FXML
    public TableView table;
    @FXML
    public TableColumn nameColumn;
    @FXML
    public TableColumn dateOfBirthColumn;
    @FXML
    public TableColumn reasonsForVisitColumn;
    @FXML
    public TableColumn phoneNumberColumn;

    private edu.wpi.teamname.Database.UserRegistration currentlySelected = null;

    /**
     * Run on startup
     */
    public void initialize() {

        Initiator.getInstance().addRegistrationListener(this);

        // set cell to text field
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
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // set cell to text field
        dateOfBirthColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // set cell to text field
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // set cell to text field
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        reasonsForVisitColumn.setCellValueFactory(new PropertyValueFactory<>("reasonsForVisit"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        loadData(); // Load file to table

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            currentlySelected = (edu.wpi.teamname.Database.UserRegistration) newSelection; // Listen for row selection events
        });
    }

    /**
     * Load data into table
     */
    public void loadData() {
        ArrayList<edu.wpi.teamname.Database.UserRegistration> registrations = LocalStorage.getInstance().getRegistrations();

        if (registrations == null) {
            return;
        }

        registrations.forEach(e -> {
            table.getItems().add(0, e);
        }); // Populate table
    }

    /**
     * When a form is submitted, add to table
     * @param _obj
     */
    @Override
    public void registrationAdded(edu.wpi.teamname.Database.UserRegistration _obj) {
        table.getItems().add(0, _obj);
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
