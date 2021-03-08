package edu.wpi.u.controllers.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.wpi.u.App;
import edu.wpi.u.users.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;

public class ViewUserListController {

    public JFXButton editSelectedButton;
    public JFXButton addUserButton;
    public JFXButton editSelectedButton1;
    Guest myGuest;
    Employee myEmployee;
    @FXML private JFXTreeTableView<User> treeTableView = new JFXTreeTableView<>();


    public void initialize() throws IOException {

        JFXTreeTableColumn<User, String> treeTableColumnName = new JFXTreeTableColumn<>("Name");
        treeTableColumnName.setCellValueFactory(param -> param.getValue().getValue().namefxProperty());
        treeTableColumnName.setPrefWidth(100);

        JFXTreeTableColumn<User, String> treeTableColumnUserName = new JFXTreeTableColumn<>("Username");
        treeTableColumnUserName.setCellValueFactory(param -> param.getValue().getValue().userNamefxProperty());
        treeTableColumnUserName.setPrefWidth(125);

        JFXTreeTableColumn<User, String> treeTableColumnPassword = new JFXTreeTableColumn<>("Password");
        treeTableColumnPassword.setCellValueFactory(param -> param.getValue().getValue().passwordfxProperty());
        treeTableColumnPassword.setPrefWidth(125);

        JFXTreeTableColumn<User, String> treeTableColumnEmail = new JFXTreeTableColumn<>("Email");
        treeTableColumnEmail.setCellValueFactory(param -> param.getValue().getValue().emailfxProperty());
        treeTableColumnEmail.setPrefWidth(125);

        JFXTreeTableColumn<User, String> treeTableColumnType = new JFXTreeTableColumn<>("Role");
        treeTableColumnType.setCellValueFactory(param -> param.getValue().getValue().typefxProperty());
        treeTableColumnType.setPrefWidth(100);

        JFXTreeTableColumn<User, String> treeTableColumnPhonenumber = new JFXTreeTableColumn<>("Phone Number");
        treeTableColumnPhonenumber.setCellValueFactory(param -> param.getValue().getValue().phoneNumberfxProperty());
        treeTableColumnPhonenumber.setPrefWidth(135);

        JFXTreeTableColumn<User, String> treeTableColumnLocationNodeID = new JFXTreeTableColumn<>("Location (NodeID)");
        treeTableColumnLocationNodeID.setCellValueFactory(param -> param.getValue().getValue().locationNodeIDfxProperty());
        treeTableColumnLocationNodeID.setPrefWidth(100);

        ObservableList<User> users2 = FXCollections.observableArrayList();
        for (User u : App.userService.getUsers()){
            if (u.getType() == Role.PATIENT){
                users2.add(new Patient(new SimpleStringProperty(u.getUserID())
                        , new SimpleStringProperty(u.getName()),new SimpleStringProperty(u.getUserName()), new SimpleStringProperty(u.getPassword())
                        , new SimpleStringProperty(String.valueOf(u.getType())), new SimpleStringProperty(u.getPhoneNumber())
                        , new SimpleStringProperty(u.getEmail()), new SimpleBooleanProperty(u.isDeleted()), new SimpleStringProperty(u.getLocationNodeID())));
            }
            else if (u.getType() == Role.GUEST){
                //String guestID, String name, Role type, Timestamp visitDate, String visitReason, boolean deleted
                // todo : find a way to get visitDate
            }
            else {
                users2.add(new Employee(new SimpleStringProperty(u.getUserID())
                        , new SimpleStringProperty(u.getName()),new SimpleStringProperty(u.getUserName()), new SimpleStringProperty(u.getPassword())
                        , new SimpleStringProperty(String.valueOf(u.getType())), new SimpleStringProperty(u.getPhoneNumber())
                        , new SimpleStringProperty(u.getEmail()), new SimpleBooleanProperty(u.isDeleted()), new SimpleStringProperty(u.getLocationNodeID())));
            }
        }
        final TreeItem<User> root = new RecursiveTreeItem<User>(users2, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        treeTableView.getColumns().setAll(treeTableColumnName, treeTableColumnUserName, treeTableColumnPassword, treeTableColumnEmail, treeTableColumnType, treeTableColumnPhonenumber, treeTableColumnLocationNodeID);

        myGuest = App.selectedGuest;
        myEmployee = App.selectedEmployee;

    }

    public void handleEditUserList(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/ModifyUser.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }

    public void handleEditGuestList(ActionEvent actionEvent) throws IOException {
        App.isEdtingGuest = true;
        //myGuest = guestTableView.getSelectionModel().getSelectedItem();
        App.selectedGuest = myGuest;
        handleEditUserList(actionEvent);
    }

    public void handleEditEmpList(ActionEvent actionEvent) throws IOException {
        App.isEdtingGuest = false;
        //myEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        App.selectedEmployee = myEmployee;
        handleEditUserList(actionEvent);
    }

    public void handleAddUserButton(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/AddUser.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

}
