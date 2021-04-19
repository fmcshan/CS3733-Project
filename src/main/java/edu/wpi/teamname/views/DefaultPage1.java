package edu.wpi.teamname.views;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DefaultPage1 {

    @FXML
    private ImageView hospitalMap;
    @FXML
    private AnchorPane anchor;

     public void initialize() {

         anchor.widthProperty().addListener((obs, oldVal, newVal) -> {

         });

         anchor.heightProperty().addListener((obs, oldVal, newVal) -> {

         });

         hospitalMap.fitWidthProperty().bind(anchor.widthProperty());
         hospitalMap.fitHeightProperty().bind(anchor.heightProperty());
     }
}
