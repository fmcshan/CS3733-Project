package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Database.UserRegistration;
import edu.wpi.teamname.views.LoadFXML;
import edu.wpi.teamname.views.Navigation;
import javafx.application.Platform;

import java.util.ArrayList;

public class NavManager {
    private static final NavManager instance = new NavManager();
    private ArrayList<ChatBotCommand> chatBotListeners = new ArrayList<>();
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

    public void addChatBotCommandListener(ChatBotCommand _toAdd) {
        chatBotListeners.add(_toAdd);
    }

    private String startNode;
    private String endNode;

    public String getStartNode() {
        return startNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public void resetNodes() {
        this.startNode = "";
        this.endNode = "";
    }

    public void triggerNavigationCommand(String _start, String _end) {
        this.startNode = _start;
        this.endNode = _end;
        if (!LoadFXML.getCurrentWindow().equals("navBar")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    SceneManager.getInstance().getDefaultPage().toggleNav();
                }
            });
        } else {
            for (ChatBotCommand cl : chatBotListeners) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        cl.navigate(_start, _end);
                    }
                });
            }
        }

    }
}
