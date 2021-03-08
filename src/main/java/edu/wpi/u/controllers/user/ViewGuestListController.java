package edu.wpi.u.controllers.user;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.u.App;
import edu.wpi.u.users.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

import java.io.IOException;
import java.sql.Timestamp;

public class ViewGuestListController {
    @FXML public JFXTreeTableView<User> treeTableView = new JFXTreeTableView<>();

    public void initialize() throws IOException {

        ObservableList<User> users = FXCollections.observableArrayList();
        for (Guest u : App.userService.getGuests()){
            users.add(new Guest(new SimpleStringProperty(u.getGuestID()), new SimpleStringProperty(u.getName()), new SimpleStringProperty(String.valueOf(u.getType())), new SimpleLongProperty(u.getVisitDate().getTime()), new SimpleStringProperty(u.getVisitReason())));
        }
        System.out.println("Guest list : " + users);
        JFXTreeTableColumn<User, String> treeTableColumnID = new JFXTreeTableColumn<>("Name");
        treeTableColumnID.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
            if (treeTableColumnID.validateValue(param)){
                return param.getValue().getValue().namefxProperty();
            }
            else {
                return treeTableColumnID.getComputedValue(param);
            }
        });
        treeTableColumnID.setPrefWidth(100);
        treeTableColumnID.setEditable(false);
        final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        treeTableView.getColumns().setAll(treeTableColumnID);
    }
}
