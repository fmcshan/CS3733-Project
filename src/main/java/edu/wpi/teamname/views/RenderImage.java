package edu.wpi.teamname.views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.FileInputStream;

public class RenderImage {

    public void renderImage(String fileName, int imageWidth, int imageHeight, AnchorPane topElements) throws Exception {
        try {
            FileInputStream input = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\edu\\wpi\\teamname\\Icons\\" + fileName + ".png");
            Image image = new Image(input);
            ImageView lab = new ImageView(image);
            lab.setFitWidth(imageWidth);
            lab.setFitHeight(imageHeight);
            topElements.getChildren().add(lab);
        } catch (Exception e) {
            System.out.println("Wrong file path you absolute clown");
        }
    }
}
