package edu.wpi.teamname.views.manager;

import com.jfoenix.controls.JFXButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ButtonManager {

    public static ArrayList<JFXButton> buttons = new ArrayList<>();

    private static void remove_class(String _class, ArrayList<JFXButton> _buttons) {
        for (JFXButton button : _buttons) {
            try {
                button.getStyleClass().remove(_class);
            } catch (Exception ignored) {

            }
        }
    }

    public static void remove_class() {
        remove_class("nav-btn-selected", buttons);
    }

    public static void selectButton(JFXButton button) {
        if (!buttons.contains(button)) {
            buttons.add(button);
        }
        remove_class("nav-btn-selected", buttons);
        button.getStyleClass().add("nav-btn-selected");
    }


}
