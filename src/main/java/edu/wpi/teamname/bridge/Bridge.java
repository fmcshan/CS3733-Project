package edu.wpi.teamname.bridge;

import edu.wpi.teamname.Database.SocketManager;

import java.util.ArrayList;
import java.util.List;

public class Bridge {
    private static final Bridge instance = new Bridge();

    public static synchronized Bridge getInstance() {
        return instance;
    }

    private Bridge() {

    }

    private List<CloseListener> closeListeners = new ArrayList<CloseListener>();

    public void addCloseListener(CloseListener toAdd) {
        closeListeners.add(toAdd);
    }

    public void close() {
        closeListeners.forEach(cl -> {
            cl.closeButtonPressed();
        });
    }
}
