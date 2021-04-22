package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.DatabaseThread;
import edu.wpi.teamname.Database.UserRegistration;
import edu.wpi.teamname.Database.socketListeners.RegistrationListener;
import edu.wpi.teamname.views.DefaultPage;

import java.util.ArrayList;

public class SceneManager {
    private static final SceneManager instance = new SceneManager();
    private SceneManager() {}

    public static synchronized SceneManager getInstance() {return instance;}


    private DefaultPage currentDefaultPage;

    public DefaultPage getDefaultPage() {
        return currentDefaultPage;
    }

    public void setDefaultPage(DefaultPage _def) {
        this.currentDefaultPage = _def;
    }
}
