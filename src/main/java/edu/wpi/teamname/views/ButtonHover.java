package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.paint.Color;

import javax.swing.*;

public class ButtonHover {

    private static String idle = "-fx-background-color: white; -fx-text-fill: #9e9e9e; -fx-font-weight: 400; -fx-font-size: 24; -fx-background-radius: 8px";
    private static String hover = "-fx-background-color: #0067B1; -fx-text-fill: white; -fx-font-weight: 800; -fx-font-size: 24; -fx-background-radius: 8px; -fx-opacity: .8";

    public static void buttonHover(JFXButton button, MaterialDesignIconView icon) {
        if (LoadFXML.getCurrentWindow().equals("")) {
            button.setStyle(hover);
            button.setOnMouseEntered(e -> {
                button.setStyle(hover);
                icon.setFill(Color.WHITE);
            });
            button.setOnMouseExited(e -> {
                button.setStyle(hover);
                icon.setFill(Color.WHITE);
            });
        } else {
            button.setStyle(idle);
            button.setOnMouseEntered(e -> {
                button.setStyle(hover);
                icon.setFill(Color.WHITE);
            });
            button.setOnMouseExited(e -> {
                button.setStyle(idle);
                icon.setFill(Color.valueOf("c3c3c3"));
            });
            return;
        }
        button.setStyle(idle);
    }

    public static void setIdle(JFXButton button) {
        button.setStyle(idle);
    }

}
