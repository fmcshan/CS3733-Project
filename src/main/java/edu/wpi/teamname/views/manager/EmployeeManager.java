package edu.wpi.teamname.views.manager;
import edu.wpi.teamname.views.EmployeeTable;

public class EmployeeManager {

    private static final EmployeeManager instance = new EmployeeManager();
    private EmployeeManager() {}

    public static synchronized EmployeeManager getInstance() {return instance;}

    private EmployeeTable currentEmployeeTable;

    public EmployeeTable getEmployeeTable() {
        return currentEmployeeTable;
    }

    public void setEmployeeTable(EmployeeTable _def) {
        this.currentEmployeeTable = _def;
    }

}
