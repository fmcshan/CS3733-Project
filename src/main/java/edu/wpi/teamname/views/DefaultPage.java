package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Authentication.AuthListener;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DefaultPage implements AuthListener {

    @FXML
    private VBox popPop;
    @FXML
    private VBox adminPop;
    @FXML
    private VBox requestPop;
    @FXML
    private Path tonysPath;
    @FXML
    private AnchorPane anchor;
    @FXML
    private ImageView hospitalMap;

    String openWindow = "";
    ArrayList<Node> currentPath = new ArrayList<>();

    public VBox getPopPop() {
        return popPop;
    }

    public void initialize() {
        AuthenticationManager.getInstance().addListener(this);

        tonysPath.getElements().clear();

        anchor.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
        });

        anchor.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (currentPath.size() > 0) {
                drawPath(currentPath);
            }
        });
    }

    public void loadWindowPopPop(String fileName, String windowName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindowPopPop(windowName, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadWindowAdminPop(String fileName, String windowName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindowAdminPop(windowName, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadWindowRequestPop(String fileName, String windowName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/" + fileName + ".fxml"));
            openWindowRequestPop(windowName, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openWindowPopPop(String windowName, Parent root) {
        popPop.getChildren().clear();
        if (!windowName.equals(openWindow)) {
            popPop.getChildren().add(root);
            openWindow = windowName;
            return;
        }
        openWindow = "";
    }

    public void openWindowAdminPop(String windowName, Parent root) {
        adminPop.getChildren().clear();
        if (!windowName.equals(openWindow)) {
            adminPop.getChildren().add(root);
            openWindow = windowName;
            return;
        }
        openWindow = "";
    }

    public void openWindowRequestPop(String windowName, Parent root) {
        requestPop.getChildren().clear();
        if (!windowName.equals(openWindow)) {
            requestPop.getChildren().add(root);
            openWindow = windowName;
            return;
        }
        openWindow = "";
    }

    public void toggleNav(ActionEvent actionEvent) {

        // load controller here
        Navigation navigation = new Navigation(this);

        navigation.loadNav();
    }

    public void openRequests(ActionEvent actionEvent) {
        loadWindowPopPop("Requests", "reqBar");
    }

    public void openLogin(ActionEvent actionEvent) {
        loadWindowPopPop("Login", "loginBar");
    }

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void drawPath(ArrayList<Node> _listOfNodes) {
        if (_listOfNodes.size() < 1) {
            return;
        }
        currentPath = _listOfNodes;
        tonysPath.getElements().clear();
        double mapWidth = hospitalMap.boundsInParentProperty().get().getWidth();
        double mapHeight = hospitalMap.boundsInParentProperty().get().getHeight();
        double fileWidth = hospitalMap.getImage().getWidth();
        double fileHeight = hospitalMap.getImage().getHeight();
        double fileFxWidthRatio = mapWidth / fileWidth;
        double fileFxHeightRatio = mapHeight / fileHeight;
        Node firstNode = _listOfNodes.get(0);
        MoveTo start = new MoveTo(firstNode.getX() * fileFxWidthRatio, firstNode.getY() * fileFxHeightRatio);
        tonysPath.getElements().add(start);
        System.out.println(fileFxWidthRatio);
        _listOfNodes.forEach(n -> {
            tonysPath.getElements().add(new LineTo(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio));
        });
    }

            @Override
    public void userLogin() {
        loadWindowAdminPop("MapEditorButton", "mapButton");
        loadWindowRequestPop("SubmittedRequests", "reqButton");
    }
}
