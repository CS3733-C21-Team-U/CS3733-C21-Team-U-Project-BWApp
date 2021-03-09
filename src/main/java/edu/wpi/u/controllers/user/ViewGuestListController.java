package edu.wpi.u.controllers.user;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.u.App;
import edu.wpi.u.users.*;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

import java.io.IOException;
import java.util.Date;

public class ViewGuestListController {
    @FXML public JFXTreeTableView<User> treeTableView = new JFXTreeTableView<>();

    public void initialize() throws IOException {

        // todo : make page only visible to admins and Role.SECURITY

        ObservableList<User> users = FXCollections.observableArrayList();
        for (User u : App.userService.getGuests()){
            users.add(new Guest(new SimpleStringProperty(u.getGuestID()), new SimpleStringProperty(u.getName()),new SimpleStringProperty(String.valueOf(Role.GUEST)), new SimpleLongProperty(u.getVisitDate().getTime()), new SimpleStringProperty(u.getVisitReason())));
        }

        JFXTreeTableColumn<User, String> treeTableColumnID = new JFXTreeTableColumn<>("ID");
        treeTableColumnID.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
            if (treeTableColumnID.validateValue(param)){
                return param.getValue().getValue().guestIDfxProperty();
            }
            else {
                return treeTableColumnID.getComputedValue(param);
            }
        });
        treeTableColumnID.setPrefWidth(100);
        treeTableColumnID.setEditable(false);

        JFXTreeTableColumn<User, String> treeTableColumnName = new JFXTreeTableColumn<>("Name");
        treeTableColumnName.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
            if (treeTableColumnName.validateValue(param)){
                return param.getValue().getValue().namefxProperty();
            }
            else {
                return treeTableColumnName.getComputedValue(param);
            }
        });
        treeTableColumnName.setPrefWidth(100);
        treeTableColumnName.setEditable(false);

        JFXTreeTableColumn<User, Long> treeTableColumnVisitDate = new JFXTreeTableColumn<>("Visit date");
        treeTableColumnVisitDate.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,Long> param) ->{
            if (treeTableColumnVisitDate.validateValue(param)){
                SimpleObjectProperty<Date> d = new SimpleObjectProperty<>();
                return param.getValue().getValue().visitDatefxProperty().asObject(); // todo : date format
            }
            else {
                return treeTableColumnVisitDate.getComputedValue(param);
            }
        });
        treeTableColumnVisitDate.setPrefWidth(150);
        treeTableColumnVisitDate.setEditable(false);

        JFXTreeTableColumn<User, String> treeTableColumnVisitReason = new JFXTreeTableColumn<>("Visit reason");
        treeTableColumnVisitReason.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
            if (treeTableColumnVisitReason.validateValue(param)){
                return param.getValue().getValue().visitReasonfxProperty();
            }
            else {
                return treeTableColumnVisitReason.getComputedValue(param);
            }
        });
        treeTableColumnVisitReason.setPrefWidth(150);
        treeTableColumnVisitReason.setEditable(false);


        final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        treeTableView.getColumns().setAll(treeTableColumnID, treeTableColumnName ,treeTableColumnVisitDate, treeTableColumnVisitReason);
    }
}
