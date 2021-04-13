package edu.wpi.teamname.views;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.teamname.Database.CSVOperator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EdgesEditor {

    @FXML
    private JFXTreeTableView myTable;
    @FXML
    private TreeTableColumn edgeId;
    @FXML
    private TreeTableColumn startNode;
    @FXML
    private TreeTableColumn endNode;
    }
