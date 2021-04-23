package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.Node;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class MapEditorView extends MapDisplay {

    @Override
    public void initialize() {
        popPop.setPickOnBounds(false);
        displayNodes(.8);
        displayEdges(.6);

        topElements.onMouseClickedProperty().set((EventHandler<MouseEvent>) this::openAddNodePopup);

        addNodeField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    hideAddNodePopup();
                }
            }
        });

        anchor.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    hideAddNodePopup();
                }
            }
        });
    }

    private void openAddNodePopup(MouseEvent t) {
        addNodeField.setPickOnBounds(true);
        addNodeField.setVisible(true);

        addNodeField.setTranslateX(t.getX());
        addNodeField.setTranslateY(t.getY());

        if (t.getY() < topElements.getHeight()/2) {
            addNodeField.setTranslateY(t.getY() + 20);
        } else {
            addNodeField.setTranslateY(t.getY() - addNodeField.getHeight() - 20);
        }

        if (topElements.getWidth() * 0.2 > t.getX()) {
            addNodeField.setTranslateX(t.getX()); // left
        } else if (topElements.getWidth() * 0.8 > t.getX()) {
            addNodeField.setTranslateX(t.getX() - (0.5 * addNodeField.getWidth())); // middle
        } else {
            addNodeField.setTranslateX(t.getX() - addNodeField.getWidth()); // right
        }

    }

    public void hideAddNodePopup() {
        addNodeField.setPickOnBounds(false);
        addNodeField.setVisible(false);
    }

    @Override
    public void drawPath(ArrayList<Node> _listOfNodes) {

    }
}
