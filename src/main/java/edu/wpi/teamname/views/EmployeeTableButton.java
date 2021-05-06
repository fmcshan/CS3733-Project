package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamname.views.manager.ButtonManager;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.fxml.FXML;

public class EmployeeTableButton {

    @FXML
    private JFXButton employeeButton;

    public JFXButton getEmployeeButton() {
        return employeeButton;
    }

    public void openEmployeeTable() {
        SceneManager.getInstance().getDefaultPage().toggleEmployee();
        ButtonManager.selectButton(employeeButton);
    }
}
