package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Database.UserRegistration;
import edu.wpi.teamname.views.Navigation;

public class NavManager {
    private static final NavManager instance = new NavManager();
    private NavManager() {}

    public static synchronized NavManager getInstance() {return instance;}

    private Navigation navigationPage;

    public Navigation getNavigationPage() {
        return navigationPage;
    }


    private UserRegistration userRegistration;

    public UserRegistration getUserRegistration() {
        return userRegistration;
    }

    public void setUserRegistration(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
    }
}
