package edu.wpi.teamname.views;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class DefaultPage1 {

    @FXML
    private ImageView hospitalMap;
    @FXML
    private StackPane stackPane;

     public void initialize() {

         stackPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            hospitalMap.fitWidthProperty().bind(stackPane.widthProperty());
         });

         stackPane.heightProperty().addListener((obs, oldVal, newVal) -> {
             hospitalMap.fitHeightProperty().bind(stackPane.heightProperty());
         });

     }
}
