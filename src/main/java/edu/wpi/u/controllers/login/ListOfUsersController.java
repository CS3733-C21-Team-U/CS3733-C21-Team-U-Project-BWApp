package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.u.App;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
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

        //PropertyValueFactory factoryUserID = new PropertyValueFactory<>("Name");
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Username"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Usertype"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Phone Number"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("AppDate"));

        //PropertyValueFactory factory = new PropertyValueFactory<>("Name");
        //nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Name"))

        guestList.addAll(App.userService.getGuests());
        guestTableView.setItems(guestList);
     //   guestTableView.getItems().add(App.userService.getGuests());


    }

    public void handleEditUser(ActionEvent actionEvent) {
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
