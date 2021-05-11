package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.MasterServiceRequestStorage;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.Database.UserRegistration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class TableCellFactory {

    private static VBox generate_status_cell(MasterServiceRequestStorage _req) {
        VBox statusWrapper = new VBox();
        statusWrapper.setPrefWidth(181.14);
        statusWrapper.setAlignment(Pos.CENTER);
        Label status = new Label("Reason");
        status.setStyle("-fx-font-size: 14px; -fx-padding: 0 15px 0 15px;");
        VBox.setMargin(status, new Insets(0,0,0,15));
        statusWrapper.getChildren().add(status);

        String base_style = "-fx-font-size: 14px; -fx-padding: 0 15px 0 15px;-fx-background-radius: 4px;";
        if (_req.isCompleted()) {
            status.setText("Completed");
            status.setTextFill(Color.WHITE);
            status.setStyle(base_style + "-fx-background-color:#00c455;");
        } else if (!_req.getAssignTo().isEmpty()) {
            status.setText("In Progress");
            status.setTextFill(Color.valueOf("#626d7c"));
            status.setStyle(base_style + "-fx-background-color:#ebf0f5;");
        } else {
            status.setText("Unassigned");
            status.setTextFill(Color.WHITE);
            status.setStyle(base_style + "-fx-background-color:#f13426;");
        }

        return statusWrapper;
    }

    private static HBox generate_clear_cell(UserRegistration _reg) {
        HBox clearWrapper = new HBox();
        clearWrapper.setPrefWidth(70);
        clearWrapper.setAlignment(Pos.TOP_CENTER);
        HBox.setMargin(clearWrapper, new Insets(0,0,0,5));

        MaterialDesignIconView clearedIcon;
        if (_reg.getCleared()) {
            clearedIcon = new MaterialDesignIconView(MaterialDesignIcon.CHECKBOX_MARKED_CIRCLE);
            clearedIcon.setGlyphSize(25);
            clearedIcon.setFill(Color.valueOf("317fb8"));
            clearWrapper.getChildren().add(clearedIcon);
        }

        return clearWrapper;
    }

    private static MaterialDesignIconView generate_star(boolean empty) {
        MaterialDesignIconView star;
        if (empty) {
            star = new MaterialDesignIconView(MaterialDesignIcon.STAR_OUTLINE);
        } else {
            star = new MaterialDesignIconView(MaterialDesignIcon.STAR);
        }
        star.setFill(Color.valueOf("317fb8"));
        star.setGlyphSize(20);
        return star;
    }

    private static HBox generate_rating_cell(UserRegistration _reg) {
        HBox ratingWrapper = new HBox();
        ratingWrapper.setPrefWidth(130);
        ratingWrapper.setAlignment(Pos.TOP_CENTER);
        HBox.setMargin(ratingWrapper, new Insets(0,0,10,0));

        HBox starWrapper = new HBox();
        starWrapper.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 0 0 0 4;");
        starWrapper.setPrefWidth(160);
        starWrapper.setAlignment(Pos.TOP_CENTER);

        if (_reg.getRating() == -1) {
            for (int i = 0; i < 5; i++) {
                starWrapper.getChildren().add(generate_star(true));
            }
            return starWrapper;
        }

        for (int i = 0; i < _reg.getRating(); i++) {
            starWrapper.getChildren().add(generate_star(false));
        }

        for (int i = 0; i < 5 -_reg.getRating(); i++) {
            starWrapper.getChildren().add(generate_star(true));
        }

        ratingWrapper.getChildren().add(starWrapper);

        return ratingWrapper;
    }

    private static void addHoverListener(HBox _toAdd) {
        _toAdd.setOnMouseEntered(e -> {
            _toAdd.setStyle("-fx-background-color: #F7F7F8; -fx-background-radius: 8px;");
        });

        _toAdd.setOnMouseExited(e -> {
            _toAdd.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8px;");
        });
    }

    private static HBox generate_row() {
        HBox newRow = new HBox();
        addHoverListener(newRow);
        newRow.setStyle("-fx-background-color: #fff; -fx-background-radius: 8px;");
        return newRow;
    }

    private static Label generate_label(String _value, double _width) {
        Label genLabel = new Label(_value.replace("\"", ""));
        genLabel.setPrefWidth(_width);
        genLabel.setStyle("-fx-font-size: 14px; -fx-padding: 0 0 0 20px;");
        return genLabel;
    }

    private static JFXComboBox<String> generate_employee_dropdown(MasterServiceRequestStorage _req) {
        JFXComboBox<String> newEmployeeSelection = new JFXComboBox<>();
        newEmployeeSelection.setStyle(" -fx-padding: 0 0 0 20;");
        newEmployeeSelection.setPrefSize(163, 30);
        newEmployeeSelection.setFocusColor(Color.valueOf("#ababab"));
        newEmployeeSelection.setUnFocusColor(Color.valueOf("#c3c3c3"));
        newEmployeeSelection.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: #9e9e9e;");
        HBox.setMargin(newEmployeeSelection, new Insets(0,0,0,10));
        newEmployeeSelection.setPromptText("Employee");
        LocalStorage.getInstance().getUsers().forEach(u -> {
            newEmployeeSelection.getItems().add(u.getName());
        });

        newEmployeeSelection.setValue(_req.getAssignTo());
        newEmployeeSelection.setOnAction(c -> {
            _req.setAssignTo(newEmployeeSelection.getValue().toString());
            Submit.getInstance().updateGiftDelivery(_req);
        });
        return newEmployeeSelection;
    }

    private static VBox generate_context_menu(MasterServiceRequestStorage _req) {
        VBox menuWrapper = new VBox();
        menuWrapper.setPrefWidth(100);
        menuWrapper.setAlignment(Pos.CENTER);
        JFXButton btn = new JFXButton();
        btn.setText("...");
        btn.setStyle("-fx-font-size: 28px; -fx-font-weight: 800; -fx-padding: -15px 0 0 0px;");
        menuWrapper.getChildren().add(btn);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem completed = new MenuItem("Mark as Completed");
        MenuItem reset = new MenuItem("Reset");
        MenuItem delete = new MenuItem("Delete");
        completed.setStyle("-fx-font-weight: 400; -fx-font-size: 14");
        reset.setStyle("-fx-font-weight: 400; -fx-font-size: 14");
        delete.setStyle("-fx-font-weight: 400; -fx-font-size: 14");
        if (!_req.isCompleted()) {
            contextMenu.getItems().add(completed);
        } else {
            contextMenu.getItems().add(reset);
        }
        contextMenu.getItems().add(delete);
        btn.setOnAction(b -> contextMenu.show(btn, Side.BOTTOM, 0, 0));
        contextMenu.setOnAction(e -> {
            switch (((MenuItem) e.getTarget()).getText()) {
                case "Mark as Completed":
                    _req.setCompleted(true);
                    Submit.getInstance().updateGiftDelivery(_req);
                    break;
                case "Delete":
                    Submit.getInstance().deleteGiftDelivery(_req);
                    break;
                case "Reset":
                    _req.setAssignTo("");
                    _req.setCompleted(false);
                    Submit.getInstance().updateGiftDelivery(_req);
                    break;
            }
        });

        return menuWrapper;
    }

    private static VBox generate_context_menu_check_in(UserRegistration _reg) {
        VBox menuWrapper = new VBox();
        menuWrapper.setPrefWidth(60);
        menuWrapper.setAlignment(Pos.CENTER);
        JFXButton btn = new JFXButton();
        btn.setText("...");
        btn.setStyle("-fx-font-size: 28px; -fx-font-weight: 800; -fx-padding: -15px 0 0 0px;");
        menuWrapper.getChildren().add(btn);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem cleared = new MenuItem("Mark as Cleared");
        MenuItem reset = new MenuItem("Reset");
        cleared.setStyle("-fx-font-weight: 400; -fx-font-size: 14");
        reset.setStyle("-fx-font-weight: 400; -fx-font-size: 14");
        if (!_reg.getCleared()) {
            contextMenu.getItems().add(cleared);
        } else {
            contextMenu.getItems().add(reset);
        }
        btn.setOnAction(b -> contextMenu.show(btn, Side.BOTTOM, 0, 0));
        contextMenu.setOnAction(e -> {
            switch (((MenuItem) e.getTarget()).getText()) {
                case "Mark as Cleared":
                    _reg.setCleared(true);
                    Submit.getInstance().editUserRegistration(_reg);
                    break;
                case "Reset":
                    _reg.setCleared(false);
                    Submit.getInstance().editUserRegistration(_reg);
                    break;
            }
        });
        return menuWrapper;
    }

    private static void wrap_elements(HBox _parent, Node... _toWrap) {
        for (Node toWrap : _toWrap) {
            _parent.getChildren().add(toWrap);
        }
    }

    public static HBox generate_gift_delivery_request(MasterServiceRequestStorage _req) {
        HBox cellWrapper = generate_row();
        Label name = generate_label(_req.getRequestedBy(), 181.14);
        String giftList = String.join(", ", _req.getRequestedItems());
        Label gift = generate_label(giftList, 262.28);
        Label phone = generate_label(_req.getContact(), 181.14);
        Label location = generate_label(_req.getLocation(), 181.14);
        JFXComboBox<String> employeeSelection = generate_employee_dropdown(_req);
        VBox statusCell = generate_status_cell(_req);
        VBox contextMenu = generate_context_menu(_req);

        wrap_elements(cellWrapper, name, gift, phone, location, employeeSelection, statusCell, contextMenu);
        return cellWrapper;
    }

    public static HBox generate_food_delivery_request(MasterServiceRequestStorage _req) {
        HBox cellWrapper = generate_row();
        Label name = generate_label(_req.getRequestedBy(), 181.14);
        String foodList = String.join(", ", _req.getRequestedItems());
        Label food = generate_label(foodList, 262.28);
        Label phone = generate_label(_req.getContact(), 181.14);
        Label location = generate_label(_req.getLocation(), 181.14);
        JFXComboBox<String> employeeSelection = generate_employee_dropdown(_req);
        VBox statusCell = generate_status_cell(_req);
        VBox contextMenu = generate_context_menu(_req);

        wrap_elements(cellWrapper, name, food, phone, location, employeeSelection, statusCell, contextMenu);
        return cellWrapper;
    }

    public static HBox generate_computer_service_request(MasterServiceRequestStorage _req) {
        HBox cellWrapper = generate_row();
        Label name = generate_label(_req.getRequestedBy(), 131.14);
        Label description = generate_label(_req.getDescription(), 131.14);
        Label urgency = generate_label(_req.getRequestedItems().get(0), 181.14);
        Label phone = generate_label(_req.getContact(), 181.14);
        Label location = generate_label(_req.getLocation(), 181.14);
        JFXComboBox<String> employeeSelection = generate_employee_dropdown(_req);
        VBox statusCell = generate_status_cell(_req);
        VBox contextMenu = generate_context_menu(_req);

        wrap_elements(cellWrapper, name, description, urgency, phone, location, employeeSelection, statusCell, contextMenu);
        return cellWrapper;
    }

    public static HBox generate_facilities_maintenance_request(MasterServiceRequestStorage _req) {
        HBox cellWrapper = generate_row();
        Label name = generate_label(_req.getRequestedBy(), 131.14);
        Label description = generate_label(_req.getDescription(), 181.14);
        Label urgency = generate_label(_req.getRequestedItems().get(0), 131.14);
        Label phone = generate_label(_req.getContact(), 181.14);
        Label location = generate_label(_req.getLocation(), 181.14);
        JFXComboBox<String> employeeSelection = generate_employee_dropdown(_req);
        VBox statusCell = generate_status_cell(_req);
        VBox contextMenu = generate_context_menu(_req);

        wrap_elements(cellWrapper, name, description, urgency, phone, location, employeeSelection, statusCell, contextMenu);
        return cellWrapper;
    }

    public static HBox generate_laundry_service_request(MasterServiceRequestStorage _req) {
        HBox cellWrapper = generate_row();
        Label name = generate_label(_req.getRequestedBy(), 131.14);
        Label load = generate_label(_req.getRequestedItems().get(0), 181.14);
        Label wash = generate_label(_req.getDescription(), 131.14);
        Label phone = generate_label(_req.getContact(), 181.14);
        Label location = generate_label(_req.getLocation(), 181.14);
        JFXComboBox<String> employeeSelection = generate_employee_dropdown(_req);
        VBox statusCell = generate_status_cell(_req);
        VBox contextMenu = generate_context_menu(_req);

        wrap_elements(cellWrapper, name, load, wash, phone, location, employeeSelection, statusCell, contextMenu);
        return cellWrapper;
    }

    public static HBox generate_medicine_delivery_cell(MasterServiceRequestStorage _req) {
        HBox cellWrapper = generate_row();
        Label name = generate_label(_req.getRequestedBy(), 181.14);
        Label medicationName = generate_label(_req.getRequestedItems().get(0), 262.28);
        Label dosage = generate_label(_req.getDescription(), 181.14);
        Label location = generate_label(_req.getLocation(), 181.14);
        JFXComboBox<String> employeeSelection = generate_employee_dropdown(_req);
        VBox statusCell = generate_status_cell(_req);
        VBox contextMenu = generate_context_menu(_req);

        wrap_elements(cellWrapper, name, medicationName, dosage, location, employeeSelection, statusCell, contextMenu);
        return cellWrapper;
    }

    public static HBox generate_patient_transportation_cell(MasterServiceRequestStorage _req) {
        HBox cellWrapper = generate_row();
        Label name = generate_label(_req.getRequestedBy(), 181.14);
        Label location = generate_label(_req.getLocation(), 262.28);
        Label destination = generate_label(_req.getDescription(), 181.14);
        Label reason = generate_label(_req.getRequestedItems().get(0), 181.14);
        JFXComboBox<String> employeeSelection = generate_employee_dropdown(_req);
        VBox statusCell = generate_status_cell(_req);
        VBox contextMenu = generate_context_menu(_req);

        wrap_elements(cellWrapper, name, location, destination, reason, employeeSelection, statusCell, contextMenu);
        return cellWrapper;
    }

    public static HBox generate_sanitation_cell(MasterServiceRequestStorage _req) {
        HBox cellWrapper = generate_row();
        Label name = generate_label(_req.getRequestedBy(), 181.14);
        Label urgency = generate_label(_req.getRequestedItems().get(0), 262.28);
        Label reason = generate_label(_req.getContact(), 181.14);
        Label location = generate_label(_req.getLocation(), 181.14);
        JFXComboBox<String> employeeSelection = generate_employee_dropdown(_req);
        VBox statusCell = generate_status_cell(_req);
        VBox contextMenu = generate_context_menu(_req);

        wrap_elements(cellWrapper, name, urgency, reason, location, employeeSelection, statusCell, contextMenu);
        return cellWrapper;
    }

    public static HBox generate_check_in(UserRegistration _reg) {
        HBox cellWrapper = generate_row();
        Label name = generate_label(_reg.getName(), 200);
        Label date = generate_label(_reg.getDate(), 100);
        String reasonText = String.join(", ", _reg.getReasonsForVisit());
        Label reason = generate_label(reasonText, 200);
        Label phone = generate_label(_reg.getPhoneNumber(), 115);
        HBox clearCell = generate_clear_cell(_reg);
        HBox ratingCell = generate_rating_cell(_reg);
        Label comments = generate_label(_reg.getDetails(), 385);
        VBox contextMenu = generate_context_menu_check_in(_reg);

        wrap_elements(cellWrapper, name, date, reason, phone, clearCell, ratingCell, comments, contextMenu);
        return cellWrapper;
    }
}
