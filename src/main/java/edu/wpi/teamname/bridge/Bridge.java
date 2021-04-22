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

    private List<CloseListener> closeListeners = new ArrayList<>();
    private List<MapEditorListener> mapEditorListeners = new ArrayList<>();
    private List<RegListener> regListeners = new ArrayList<>();
    private List<RequestListener> requestListeners = new ArrayList<>();
    private List<CloseMapEditorListener> closeMapListeners = new ArrayList<>();

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
    public void addCloseMapListener(CloseMapEditorListener toAdd) {
        closeMapListeners.add(toAdd);
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
        System.out.println("made it into listener");
        regListeners.forEach(r -> {
            r.toggleRegistration();
        });
    }
    public void loadRequestListener() {
        requestListeners.forEach(r -> {
            r.toggleRequest();
        });
    }

    public void closeMapListener() {
        closeMapListeners.forEach(c -> {
            c.toggleMap();
        });
    }
}
