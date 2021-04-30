package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.SceneManager;

public class EmployeeTableButton {
    public void openEmployeeTable() {
        SceneManager.getInstance().getDefaultPage().toggleEmployee();
    }
}
