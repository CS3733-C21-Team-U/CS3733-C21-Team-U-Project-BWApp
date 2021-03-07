package edu.wpi.u.controllers.user;

import com.jfoenix.controls.JFXButton;
import edu.wpi.u.App;
import edu.wpi.u.users.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ViewUserListController {


    public TableView<Guest> guestTableView;
    public TableView<Employee> employeeTableView;
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
//    public TableColumn guestIDColumn;
//    public TableColumn employeeIDColumn;
    public JFXButton addUserButton;
    Guest myGuest;
    Employee myEmployee;
    public TreeTableView<Patient> patientTreeTableView = new TreeTableView<>();

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

        TreeTableColumn<Patient, String> treeTableColumnName = new TreeTableColumn<>("Name");
        treeTableColumnName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        patientTreeTableView.getColumns().add(treeTableColumnName);

        TreeItem<Patient> patient1 = new TreeItem<>(new Patient("testree", "Test name", "t", "t", "t", Role.PATIENT, "9998887777", null,false,null,"tesprov",null,null));

        TreeItem<Patient> patients = new TreeItem<>(new Patient("Column id", "name col"));
        patients.getChildren().add(patient1);

        patientTreeTableView.setRoot(patients);
        /*
    String userID,
    String name,
    String accountName,
    String password,
    String email,
    Role type,
    String phoneNumber,
    String locationNodeID,
    boolean deleted,
    ArrayList<Appointment> appointments,
    String providerName,
    String parkingLocation,
    String recommendedParkingLocation) {
 */

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
//        guestIDColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userID"));
//        nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
//        userNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
//        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
//        userTypeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Type"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
//        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("PhoneNumber"));
//        appDateColumn.setCellValueFactory(new PropertyValueFactory<User, String>("AppointmentDate"));
//
//        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userID"));
//        employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
//        employeeUserNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
//        employeePasswordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
//        employeeUserTypeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Type"));
//        employeeEmailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
//        employeePhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("PhoneNumber"));
//
//        //PropertyValueFactory factory = new PropertyValueFactory<>("Name");
//        //nameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Name"))
//
//        guestList.addAll(App.userService.getGuests());
//        guestTableView.setItems(guestList);
//     //   guestTableView.getItems().add(App.userService.getGuests());
//
//        employeeList.addAll(App.userService.getEmployees());
//        employeeTableView.setItems(employeeList);
    }

    public void handleEditUserList(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/ModifyUser.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }

    public void handleEditGuestList(ActionEvent actionEvent) throws IOException {
        App.isEdtingGuest = true;
        myGuest = guestTableView.getSelectionModel().getSelectedItem();
        App.selectedGuest = myGuest;
        handleEditUserList(actionEvent);
    }

    public void handleEditEmpList(ActionEvent actionEvent) throws IOException {
        App.isEdtingGuest = false;
        myEmployee = employeeTableView.getSelectionModel().getSelectedItem();
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
