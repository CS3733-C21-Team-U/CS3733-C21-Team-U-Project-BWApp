package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.u.App;
import edu.wpi.u.users.Employee;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeTableColumn;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableColumn;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class ListOfUsersController {


    public TableView guestTableView;
    public TableView employeeTableView;
    public JFXButton editSelectedButton;
    public TableColumn nameTableColumn;
    public ObservableList<User> guestList = FXCollections.observableArrayList();
    public ObservableList<User> employeeList = FXCollections.observableArrayList();
    public TableColumn userNameColumn;
    public TableColumn passwordColumn;
    public TableColumn userTypeColumn;
    public TableColumn emailColumn;
    public TableColumn phoneNumberColumn;
    public TableColumn appDateColumn;
    public TableColumn employeeNameTableColumn;
    public TableColumn employeeUserNameColumn;
    public TableColumn employeePasswordColumn;
    public TableColumn employeeUserTypeColumn;
    public TableColumn employeeEmailColumn;
    public TableColumn employeePhoneNumberColumn;
//    public TableView.TableViewSelectionModel guestSelectionModel = guestTableView.getSelectionModel();
//    public TableView.TableViewSelectionModel employeeSelectionModel = employeeTableView.getSelectionModel();
    public TableColumn guestIDColumn;
    public TableColumn employeeIDColumn;
    Guest myGuest;
    Employee myEmployee;

//    TableColumn<User, String> guestTableColumnUserID = new TableColumn<User, String>("userID");
//    TableColumn<User, String> guestTableColumnName = new TableColumn<>("name");
//    TableColumn<User, String> guestTableColumnUserName = new TableColumn<>("Username");
//    TableColumn<User, String> guestTableColumnPassword = new TableColumn<>("Password");
//    TableColumn<User, String> guestTableColumnUserType = new TableColumn<>("User Type");
//    TableColumn<User, String> guestTableColumnEmail = new TableColumn<>("Email");
//    TableColumn<User, String> guestTableColumnPhoneNum = new TableColumn<>("Phone #");
//    TableColumn<User, String> guestTableColumnAppDate = new TableColumn<>("Appt. Date");
//
//    TableColumn<User, String> employeeTableColumnUserID = new TableColumn<>("userID");
//    TableColumn<User, String> employeeTableColumnName = new TableColumn<>("name");
//    TableColumn<User, String> employeeTableColumnUserName = new TableColumn<>("Username");
//    TableColumn<User, String> employeeTableColumnPassword = new TableColumn<>("Password");
//    TableColumn<User, String> employeeTableColumnUserType = new TableColumn<>("User Type");
//    TableColumn<User, String> employeeTableColumnEmail = new TableColumn<>("Email");
//    TableColumn<User, String> employeeTableColumnPhoneNum = new TableColumn<>("Phone #");


    public void initialize() throws IOException {
//        guestTableView.getColumns().add(guestTableColumnUserID);
//        guestTableView.getColumns().add(guestTableColumnName);
//        guestTableView.getColumns().add(guestTableColumnUserName);
//        guestTableView.getColumns().add(guestTableColumnPassword);
//        guestTableView.getColumns().add(guestTableColumnUserType);
//        guestTableView.getColumns().add(guestTableColumnEmail);
//        guestTableView.getColumns().add(guestTableColumnPhoneNum);
//        guestTableView.getColumns().add(guestTableColumnAppDate);
//
//        employeeTableView.getColumns().add(employeeTableColumnUserID);
//        employeeTableView.getColumns().add(employeeTableColumnName);
//        employeeTableView.getColumns().add(employeeTableColumnUserName);
//        employeeTableView.getColumns().add(employeeTableColumnPassword);
//        employeeTableView.getColumns().add(employeeTableColumnUserType);
//        employeeTableView.getColumns().add(employeeTableColumnEmail);
//        employeeTableView.getColumns().add(employeeTableColumnPhoneNum);
        myGuest = App.selectedGuest;
        myEmployee = App.selectedEmployee;

        //PropertyValueFactory factoryUserID = new PropertyValueFactory<>("Name");
        guestIDColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userID"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Type"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("PhoneNumber"));
        appDateColumn.setCellValueFactory(new PropertyValueFactory<User, String>("AppointmentDate"));

        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userID"));
        employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
        employeeUserNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
        employeePasswordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
        employeeUserTypeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Type"));
        employeeEmailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
        employeePhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("PhoneNumber"));

        //PropertyValueFactory factory = new PropertyValueFactory<>("Name");
        //nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Name"))

        guestList.addAll(App.userService.getGuests());
        guestTableView.setItems(guestList);
     //   guestTableView.getItems().add(App.userService.getGuests());

        employeeList.addAll(App.userService.getEmployees());
        employeeTableView.setItems(employeeList);
    }

    public void handleEditUser(ActionEvent actionEvent) throws IOException {
        if(guestTableView.getSelectionModel().getSelectedItem() != null){
          myGuest = (Guest) guestTableView.getSelectionModel().getSelectedItem();
          App.selectedGuest = myGuest;
        }
        if(employeeTableView.getSelectionModel().getSelectedItem() != null){
            myEmployee = (Employee) employeeTableView.getSelectionModel().getSelectedItem();
            App.selectedEmployee = myEmployee;
        }
        //switch scene
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/EditUser.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }

    //getColumns(.add(treeTableColumnUserID));

//   @Override
//   public void handle(MouseEvent t)
//   {
//       TableCell c;
//       c = (TableCell) t.getSource();
//       c.getIndex();
//       PanelController.selectedItem = c.getIndex();}
//
//
//    public void handleEditUser() {
//    }
}