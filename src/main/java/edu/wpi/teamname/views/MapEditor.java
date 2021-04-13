package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.App;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class MapEditor {
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXComboBox<String> toCombo;
    @FXML
    private JFXComboBox<String> fromCombo;
    @FXML
    private ImageView hospitalMap;

    public void initialize() {
        PathFindingDatabaseManager.getInstance().getNodes().forEach(n -> {
            toCombo.getItems().add(n.getLongName());
            fromCombo.getItems().add(n.getLongName());
        });

        hospitalMap.fitWidthProperty().bind(anchor.widthProperty());
        hospitalMap.fitHeightProperty().bind(anchor.heightProperty());
    }

    public void drawPath(ArrayList<Node> listOfNodes) {
        double mapWidth = hospitalMap.getFitWidth();
        double mapHeight = hospitalMap.getFitHeight();
        int fileWidth = 996;
        int fileHeight = 676;
        double fileFxWidthRatio = fileWidth / mapWidth;
        double fileFxHeightRatio = fileHeight / mapHeight;
        Node firstNode = listOfNodes.remove(0);
        MoveTo start = new MoveTo(firstNode.getX() * fileFxWidthRatio, firstNode.getY() * fileFxHeightRatio);
        Collection<LineTo> collection = new ArrayList<>();

        listOfNodes.forEach(n -> {
            collection.add(new LineTo(n.getX() * fileFxWidthRatio, n.getY() * fileFxHeightRatio));
        });
        Path path = new Path(start, (PathElement) collection);
        path.setFill(Color.TOMATO);
        path.setStrokeWidth(4);
    }

    public void returnHome(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/DefaultPage.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }
}
