package edu.wpi.teamname.views.manager;
import edu.wpi.teamname.views.DefaultPage;

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
