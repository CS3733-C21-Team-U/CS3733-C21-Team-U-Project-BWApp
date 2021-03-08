package edu.wpi.u.controllers.user;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.u.App;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.sql.Timestamp;

public class ViewGuestListController {
    @FXML public JFXTreeTableView<User> treeTableView = new JFXTreeTableView<>();

    public void initialize() throws IOException {

        JFXTreeTableColumn<User, String> treeTableColumnName = new JFXTreeTableColumn<>("Name");
        treeTableColumnName.setCellValueFactory(param -> param.getValue().getValue().namefxProperty());
        treeTableColumnName.setPrefWidth(100);
        ObservableList<User> users = FXCollections.observableArrayList();
        for (Guest u : App.userService.getGuests()){
            users.add(new Guest(u.getGuestID(), u.getName(), u.getType(), new Timestamp(System.currentTimeMillis()), " ", u.isDeleted()));
        }
        final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        treeTableView.getColumns().setAll(treeTableColumnName);
    }
}
