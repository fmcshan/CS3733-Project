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
    private List<MapEditorListener> mapEditorListeners = new ArrayList<MapEditorListener>();
    private List<RegListener> regListeners = new ArrayList<RegListener>();
    private List<RequestListener> requestListeners = new ArrayList<RequestListener>();

    public void addCloseListener(CloseListener toAdd) {
        closeListeners.add(toAdd);
    }
    public void addMapEditorListener(MapEditorListener toAdd) {
        mapEditorListeners.add(toAdd);
    }
    public void addRegListener(RegListener toAdd) {
        regListeners.add(toAdd);
    }
    public void addRequestListener(RequestListener toAdd) {
        requestListeners.add(toAdd);
    }

    public void close() {
        closeListeners.forEach(cl -> {
            cl.closeButtonPressed();
        });
    }
    public void loadMapEditor() {
        mapEditorListeners.forEach(m -> {
            m.toggleMapEditor();
        });
    }
    public void loadRegistration() {
        regListeners.forEach(r -> {
            r.toggleRegistration();
        });
    }
    public void loadRequestListener() {
        requestListeners.forEach(r -> {
            r.toggleRequest();
        });
    }
}
