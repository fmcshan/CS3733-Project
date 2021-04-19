package edu.wpi.teamname.views;

import edu.wpi.teamname.Algo.AStar;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.DatabaseThread;
import edu.wpi.teamname.Database.LocalStorage;
import edu.wpi.teamname.Database.PathFindingDatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Controller for Navigation.fxml
 *
 * @author Anthony LoPresti, Lauren Sowerbutts, Justin Luce
 */
public class Navigation {

    @FXML
    private ComboBox<String> toCombo; // destination drop down
    @FXML
    private ComboBox<String> fromCombo; // start location drop down
    @FXML
    private DefaultPage defaultPage; // DefaultPage.fxml controller

    ArrayList<Node> listOfNodes = new ArrayList<>(); // create a list of nodes
    HashMap<String, Node> nodesMap = new HashMap<>(); //

    /**
     * constructor for Navigation
     *
     * @param defaultPage controller of DefaultPage.fxml
     */
    public Navigation(DefaultPage defaultPage) {
        this.defaultPage = defaultPage;
    }

    /**
     * run on startup
     */
    public void initialize() {

        //        Callback<ListView<Node>, ListCell<Node>> cellFactory = new Callback<ListView<Node>, ListCell<Node>>() {
//
//            @Override
//            public ListCell<Node> call(ListView<Node> l) {
//                return new ListCell<Node>() {
//
//                    @Override
//                    protected void updateItem(Node item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (item == null || empty) {
//                            setGraphic(null);
//                        } else {
//                            setText(item.getLongName());
//                        }
//                    }
//                };
//            }
//        };
//
//        fromCombo.setConverter(new StringConverter<String>() {
//            @Override
//            public String toString(Node node) {
//                if (node == null){
//                    return null;
//                } else {
//                    return node.getLongName();
//                }
//            }
//
//            @Override
//            public Node fromString(String string) {
//                return null;
//            }
//        });
//
//        fromCombo.setButtonCell(cellFactory.call(null));
//        fromCombo.setCellFactory(cellFactory);

        listOfNodes = PathFindingDatabaseManager.getInstance().getNodes();

        listOfNodes.forEach(n -> {
            nodesMap.put(n.getNodeID(), n);
            toCombo.getItems().add(n.getNodeID());
            fromCombo.getItems().add(n.getNodeID());
        });
    }

    /**
     * load Navigation bar in the Default Page when button is pressed/ make it disappear
     */
    public void loadNav() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/teamname/views/Navigation.fxml")); // used to load fxml in it's own controller
        try {
            loader.setControllerFactory(type -> {
                if (type == Navigation.class) {
                    return this;
                } else {
                    try {
                        return type.newInstance();
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            Parent root = loader.load();
            defaultPage.openWindowPopPop("navBar", root); // open/close navigation bar
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getFromCombo() {
        return fromCombo.getValue();
    }

    public String getToCombo() {
        return toCombo.getValue();
    }
}
