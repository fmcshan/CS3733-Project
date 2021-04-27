package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamname.Authentication.User;
import edu.wpi.teamname.Database.Submit;
import edu.wpi.teamname.views.manager.EmployeeManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AddEmployee {

    @FXML
    private JFXTextField nameCell;
    @FXML
    private JFXTextField emailCell;
    @FXML
    private JFXTextField passwordCell;
    @FXML
    private JFXTextField phoneCell;

    @FXML
    public User createUser() {
        User user = new User(emailCell.getText(), nameCell.getText(), phoneCell.getText(), passwordCell.getText());
        Submit.getInstance().newUser(user);
        EmployeeManager.getInstance().getEmployeeTable().getUserPop().getChildren().clear();
        return user;
    }
}
