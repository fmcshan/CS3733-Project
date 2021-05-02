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
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(imageWidth);
            imageView.setFitHeight(imageHeight);
            topElements.getChildren().add(imageView);
        } catch (Exception e) {
            System.out.println("Wrong file path you absolute clown");
        }
    }
}
