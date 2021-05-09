package edu.wpi.teamname.views;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import edu.wpi.teamname.views.manager.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Controller for Navigation.fxml
 *
 * @author Justin Luce
 */
public class RevisionHistory {
    static DefaultPage defaultPage = SceneManager.getInstance().getDefaultPage();
    @FXML
    private ScrollPane scrollBar;

    @FXML
    private VBox navBox;

    @FXML
    private MaterialDesignIconView exitOut;

    public RevisionHistory() {

    }

    public void initialize() {
        System.out.println("Hello");
    }


    public void closeWindows() {
        defaultPage.getPopPop().getChildren().clear();

    }

}