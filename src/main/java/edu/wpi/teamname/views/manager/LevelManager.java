package edu.wpi.teamname.views.manager;

import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.DatabaseThread;
import edu.wpi.teamname.Database.UserRegistration;
import edu.wpi.teamname.Database.socketListeners.RegistrationListener;
import edu.wpi.teamname.views.DefaultPage;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelManager {
    private static final LevelManager instance = new LevelManager();
    public static synchronized LevelManager getInstance() {return instance;}

    private final List<String> floorImageNames = Arrays.asList("00_thelowerlevel2.png", "00_thelowerlevel1.png", "00_thegroundfloor.png", "01_thefirstfloor.png", "02_thesecondfloor.png", "03_thethirdfloor.png");
    private List<Image> floorImages = new ArrayList<Image>();
    private int level;

    private ArrayList<LevelChangeListener> listeners = new ArrayList<>();

    private LevelManager() {
        floorImageNames.forEach(fp -> {
            String absoluteFilePath = System.getProperty("user.dir") + "/src/main/resources/edu/wpi/teamname/images/" + fp;
            File file = new File(absoluteFilePath);
            Image image = new Image(file.toURI().toString());
            floorImages.add(image);
        });
    }

    public void setFloor(int _level) {
        level = _level;
        Image image = floorImages.get(_level);
        SceneManager.getInstance().getDefaultPage().setHospitalMap(image);

        for (LevelChangeListener lcl: listeners) {
            try {
                lcl.levelChanged(_level);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addListener(LevelChangeListener _toAdd) {
        if (!listeners.contains(_toAdd)) {
            listeners.add(_toAdd);
        }
    }

    public int getLevel() {
        return level;
    }
}
