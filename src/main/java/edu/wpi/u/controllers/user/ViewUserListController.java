package edu.wpi.u.controllers.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TreeTableColumn.CellEditEvent;

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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ViewUserListController {

    public JFXButton addUserButton;
    public AnchorPane usersRoot;

    Patient myPatient;
    Guest myGuest;
    Employee myEmployee;
    @FXML private JFXTreeTableView<User> treeTableView = new JFXTreeTableView<>();

    public void initialize() throws IOException {

        App.isLoggedIn.addListener((observable, oldValue, newValue) -> {
            if(!App.userService.getActiveUser().getType().equals(Role.ADMIN)) {
                addUserButton.setStyle("-fx-opacity: 0");
                addUserButton.setDisable(true);
            }
        });



        /*
        Patient - > show your doctor & relevant people to service requests you have submitted
        Admin - > show everyone
        Employee - > People relevant to service requests they're filling out
         */

        ObservableList<User> users2 = FXCollections.observableArrayList();
        for (User u : App.userService.getUsers()){ // excludes Guests
            if (u.getType() == Role.PATIENT){
                // todo : check
                users2.add(new Patient(new SimpleStringProperty(u.getUserID())
                        , new SimpleStringProperty(u.getName()),new SimpleStringProperty(u.getUserName()), new SimpleStringProperty(u.getPassword())
                        , new SimpleStringProperty(String.valueOf(u.getType())), new SimpleStringProperty(u.getPhoneNumber())
                        , new SimpleStringProperty(u.getEmail()), new SimpleBooleanProperty(u.isDeleted())));
            }
            else if (u.getType() != Role.GUEST){
                users2.add(new Employee(new SimpleStringProperty(u.getUserID())
                        , new SimpleStringProperty(u.getName()),new SimpleStringProperty(u.getUserName()), new SimpleStringProperty(u.getPassword())
                        , new SimpleStringProperty(String.valueOf(u.getType())), new SimpleStringProperty(u.getPhoneNumber())
                        , new SimpleStringProperty(u.getEmail()), new SimpleBooleanProperty(u.isDeleted())));
            }
        }

        JFXTreeTableColumn<User, String> treeTableColumnID = new JFXTreeTableColumn<>("User ID");
        treeTableColumnID.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
            if (treeTableColumnID.validateValue(param)){
                return param.getValue().getValue().userIDfxProperty();
            }
            else {
                return treeTableColumnID.getComputedValue(param);
            }
        });
        treeTableColumnID.setPrefWidth(100);
        treeTableColumnID.setEditable(false);
        treeTableColumnID.setCellFactory((TreeTableColumn<User,String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));


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

        treeTableColumnName.setCellFactory((TreeTableColumn<User,String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        treeTableColumnName.setOnEditCommit((CellEditEvent<User,String> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition().getRow())
                    .getValue()
                    .namefxProperty()
                    .set(t.getNewValue());
            if (t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.PATIENT))){
                for (Patient p : App.userService.getPatients()){
                    if (p.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        p.setName(t.getNewValue());
                        App.userService.updatePatient(p);
                    }
                }
            }
            else if(!t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.GUEST))){
                for (Employee e: App.userService.getEmployees()){
                    if (e.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        e.setName(t.getNewValue());
                        App.userService.updateEmployee(e);
                    }
                }
            }
        });

        JFXTreeTableColumn<User, String> treeTableColumnUserName = new JFXTreeTableColumn<>("Username");
        treeTableColumnUserName.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
            if (treeTableColumnUserName.validateValue(param)){
                return param.getValue().getValue().userNamefxProperty();
            }
            else {
                return treeTableColumnUserName.getComputedValue(param);
            }
        });
        treeTableColumnUserName.setPrefWidth(100);

        treeTableColumnUserName.setCellFactory((TreeTableColumn<User,String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        treeTableColumnUserName.setOnEditCommit((CellEditEvent<User,String> t) -> {
            String username = t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserNamefx();
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition().getRow())
                    .getValue()
                    .userNamefxProperty()
                    .set(t.getNewValue());
            if (t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.PATIENT))){
                for (Patient p : App.userService.getPatients()){
                    if (p.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        p.setUserName(t.getNewValue());
                        App.userService.updatePatient(p);
                    }
                }
            }
            else if (!t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.GUEST))){
                for (Employee e: App.userService.getEmployees()){
                    if (e.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        e.setUserName(t.getNewValue());
                        App.userService.updateEmployee(e);
                    }
                }
            }
        });



        JFXTreeTableColumn<User, String> treeTableColumnPassword = new JFXTreeTableColumn<>("Password");
        treeTableColumnPassword.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
            if (treeTableColumnPassword.validateValue(param)){
                return param.getValue().getValue().passwordfxProperty();
            }
            else {
                return treeTableColumnPassword.getComputedValue(param);
            }
        });
        treeTableColumnPassword.setPrefWidth(100);

        treeTableColumnPassword.setCellFactory((TreeTableColumn<User,String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        treeTableColumnPassword.setOnEditCommit((CellEditEvent<User,String> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition().getRow())
                    .getValue()
                    .passwordfxProperty()
                    .set(t.getNewValue());
            if (t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.PATIENT))){
                for (Patient p : App.userService.getPatients()){
                    if (p.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        p.setPassword(t.getNewValue());
                        App.userService.updatePatient(p);
                    }
                }
            }
            else if (!t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.GUEST))){
                for (Employee e: App.userService.getEmployees()){
                    if (e.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        e.setPassword(t.getNewValue());
                        App.userService.updateEmployee(e);
                    }
                }
            }
        });

        JFXTreeTableColumn<User, String> treeTableColumnEmail = new JFXTreeTableColumn<>("Email");
        treeTableColumnEmail.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
            if (treeTableColumnEmail.validateValue(param)){
                return param.getValue().getValue().emailfxProperty();
            }
            else {
                return treeTableColumnEmail.getComputedValue(param);
            }
        });
        treeTableColumnEmail.setPrefWidth(100);

        treeTableColumnEmail.setCellFactory((TreeTableColumn<User,String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        treeTableColumnEmail.setOnEditCommit((CellEditEvent<User,String> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition().getRow())
                    .getValue()
                    .emailfxProperty()
                    .set(t.getNewValue());
            if (t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.PATIENT))){
                for (Patient p : App.userService.getPatients()){
                    if (p.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        p.setEmail(t.getNewValue());
                        App.userService.updatePatient(p);
                    }
                }
            }
            else if (!t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.GUEST))){
                for (Employee e: App.userService.getEmployees()){
                    if (e.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        e.setEmail(t.getNewValue());
                        App.userService.updateEmployee(e);
                    }
                }
            }
        });

        JFXTreeTableColumn<User, String> treeTableColumnType = new JFXTreeTableColumn<>("Role / Type");
        treeTableColumnType.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
            if (treeTableColumnType.validateValue(param)){
                return param.getValue().getValue().typefxProperty();
            }
            else {
                return treeTableColumnType.getComputedValue(param);
            }
        });
        treeTableColumnType.setPrefWidth(100);

        treeTableColumnType.setCellFactory((TreeTableColumn<User,String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        treeTableColumnType.setOnEditCommit((CellEditEvent<User,String> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition().getRow())
                    .getValue()
                    .typefxProperty()
                    .set(t.getNewValue());
            if (t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.PATIENT))){
                for (Patient p : App.userService.getPatients()){
                    if (p.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        p.setType(Role.valueOf(t.getNewValue())); // todo : somehow add a dropdown
                        App.userService.updatePatient(p);
                    }
                }
            }
            else if (!t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.GUEST))){
                for (Employee e: App.userService.getEmployees()){
                    if (e.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        e.setType(Role.valueOf(t.getNewValue()));
                        App.userService.updateEmployee(e);
                    }
                }
            }
        });

        JFXTreeTableColumn<User, String> treeTableColumnPhonenumber = new JFXTreeTableColumn<>("Phone number");
        treeTableColumnPhonenumber.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
            if (treeTableColumnPhonenumber.validateValue(param)){
                return param.getValue().getValue().phoneNumberfxProperty();
            }
            else {
                return treeTableColumnPhonenumber.getComputedValue(param);
            }
        });
        treeTableColumnPhonenumber.setPrefWidth(100);

        treeTableColumnPhonenumber.setCellFactory((TreeTableColumn<User,String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        treeTableColumnPhonenumber.setOnEditCommit((CellEditEvent<User,String> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition().getRow())
                    .getValue()
                    .phoneNumberfxProperty()
                    .set(t.getNewValue());
            if (t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.PATIENT))){
                for (Patient p : App.userService.getPatients()){
                    if (p.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        p.setPhoneNumber(t.getNewValue());
                        App.userService.updatePatient(p);
                    }
                }
            }
            else if (!t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.GUEST))){
                for (Employee e: App.userService.getEmployees()){
                    if (e.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
                        e.setPhoneNumber(t.getNewValue());
                        App.userService.updateEmployee(e);
                    }
                }
            }
        });


//        JFXTreeTableColumn<User, String> treeTableColumnLocationNodeID = new JFXTreeTableColumn<>("Location (Node ID)");
//        treeTableColumnLocationNodeID.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
//            if (treeTableColumnLocationNodeID.validateValue(param)){
//                return param.getValue().getValue().locationNodeIDfxProperty();
//            }
//            else {
//                return treeTableColumnLocationNodeID.getComputedValue(param);
//            }
//        });
//        treeTableColumnLocationNodeID.setPrefWidth(100);
//
//        treeTableColumnLocationNodeID.setCellFactory((TreeTableColumn<User,String> param) -> new GenericEditableTreeTableCell<>(
//                new TextFieldEditorBuilder()));
//        treeTableColumnLocationNodeID.setOnEditCommit((CellEditEvent<User,String> t) -> {
//            t.getTreeTableView()
//                    .getTreeItem(t.getTreeTablePosition().getRow())
//                    .getValue()
//                    .locationNodeIDfxProperty()
//                    .set(t.getNewValue());
//            if (t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.PATIENT))){
//                for (Patient p : App.userService.getPatients()){
//                    // todo : add checking the password too for more accuracy on the user
//                    if (p.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
//                        p.setLocationNodeID(t.getNewValue());
//                        App.userService.updatePatient(p);
//                    }
//                }
//            }
//            else if (!t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getTypefx().equals(String.valueOf(Role.GUEST))){
//                for (Employee e: App.userService.getEmployees()){
//                    if (e.getUserID().equals(t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getUserIDfx())){
//                        e.setLocationNodeID(t.getNewValue());
//                        App.userService.updateEmployee(e);
//                    }
//                }
//            }
//        });


        final TreeItem<User> root = new RecursiveTreeItem<>(users2, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        treeTableView.setEditable(true);
        treeTableView.getColumns().setAll(treeTableColumnID,treeTableColumnName, treeTableColumnUserName, treeTableColumnPassword, treeTableColumnEmail, treeTableColumnType, treeTableColumnPhonenumber);

        myGuest = App.selectedGuest;
        myEmployee = App.selectedEmployee;

    }

    public void handleEditUserList(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/ModifyUser.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }

    public void handleEditEmpList(ActionEvent actionEvent) throws IOException {
        App.isEdtingGuest = false;
        //myEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        App.selectedEmployee = myEmployee;
        handleEditUserList(actionEvent);
    }

    public void handleAddUserButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/AddUser.fxml"));
        usersRoot.getChildren().clear();
        usersRoot.getChildren().add(root);
    }
}
