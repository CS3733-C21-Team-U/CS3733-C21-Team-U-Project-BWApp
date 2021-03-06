package edu.wpi.u.controllers.user;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.users.Employee;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import static edu.wpi.u.users.Role.*;

public class ModifyUserController {


    @FXML public JFXTextField usernameTextField;
    @FXML public JFXTextField nameTextField;
    @FXML public JFXTextField emailTextField;
    @FXML public JFXTextField phoneNumTextField;
    @FXML public JFXTextField passwordTextField;
    @FXML public JFXComboBox userTypeComboBox;
    @FXML public JFXDatePicker appointmentDatePicker;
    @FXML public JFXButton editUserButton;
    public JFXButton cancelButton;


    public void initialize() throws IOException {
        userTypeComboBox.getItems().addAll(DOCTOR.toString(), PATIENT.toString(), ADMIN.toString(), MAINTENANCE.toString(), NURSE.toString(), SECURITY_GUARD.toString(), TECHNICAL_SUPPORT.toString(), TRANSLATORS.toString(), DEFAULT.toString() );

        if(App.isEdtingGuest){
            nameTextField.setText(App.selectedGuest.getName());
            usernameTextField.setText(App.selectedGuest.getUserName());
            passwordTextField.setText(App.selectedGuest.getPassword());
            emailTextField.setText(App.selectedGuest.getEmail());
            phoneNumTextField.setText(App.selectedGuest.getPhoneNumber());
            //appointmentDatePicker.setValue(App.selectedGuest.getAppointmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            //TODO : Fix
        }else{
            nameTextField.setText(App.selectedEmployee.getName());
            usernameTextField.setText(App.selectedEmployee.getUserName());
            passwordTextField.setText(App.selectedEmployee.getPassword());
            emailTextField.setText(App.selectedEmployee.getEmail());
            phoneNumTextField.setText(App.selectedEmployee.getPhoneNumber());
            //Hite date picker
        }


        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        usernameTextField.getValidators().add(validator);
        usernameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                usernameTextField.validate();
            }
            for(Guest user : App.userService.getGuests()){
                if(user.getUserName().equals(newVal)){

                }
            }
        });


        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("Password Required");
        nameTextField.getValidators().add(validator2);
        nameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                nameTextField.validate();
            }
        });
        // TODO: ADD REGEX FUNCTIONALITY TO THIS
        RequiredFieldValidator validator3 = new RequiredFieldValidator();
        validator3.setMessage("Phone Number Required");
        phoneNumTextField.getValidators().add(validator3);
        phoneNumTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                phoneNumTextField.validate();
            }
        });

        RequiredFieldValidator validator4 = new RequiredFieldValidator();
        validator4.setMessage("Password Required");
        passwordTextField.getValidators().add(validator4);
        passwordTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passwordTextField.validate();
            }
        });
        // TODO: ADD REGEX FUNCTIONALITY TO THIS
        RequiredFieldValidator validator5 = new RequiredFieldValidator();
        validator5.setMessage("Email Required");
        emailTextField.getValidators().add(validator5);
        emailTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                emailTextField.validate();
            }
        });

        RequiredFieldValidator validator6 = new RequiredFieldValidator();
        validator6.setMessage("Email not in correct format");
        emailTextField.getValidators().add(validator6);
        emailTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            String regex = "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            Pattern pat = Pattern.compile(regex);
            if (newVal){
                if(!pat.matcher(emailTextField.getText()).matches()) {
                    emailTextField.validate();
                }
            }
        });
    }

    public void handleEditUser() throws IOException, ParseException {


        LocalDate localDate = appointmentDatePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        if(!App.isEdtingGuest){
            /*
            String userID,
            String name,
            String accountName,
            String password,
            String email,
            Role type,
            String phoneNumber,
            String locationNodeID,
            boolean deleted
             */
            // todo : fix this to add a locationNodeID field in fxml
            App.userService.updateEmployee(new Employee(App.selectedEmployee.getUserID(), nameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), Role.valueOf(userTypeComboBox.getValue().toString()), phoneNumTextField.getText(), false));

            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/ViewUserList.fxml"));
            anchor.getChildren().clear();
            anchor.getChildren().add(root);

        }
        else{
            // TODO : Fix fields
            //App.userService.updateGuest(App.selectedGuest.getUserID(), nameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(),  Role.valueOf(userTypeComboBox.getValue().toString()),  phoneNumTextField.getText() , false);

            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/ViewUserList.fxml"));
            anchor.getChildren().clear();
            anchor.getChildren().add(root);

        }


    }

    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/ViewUserList.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }
//
//    /**This function takes a user from the combobox and fills the fields and artifacts with the selected user's info
//     *
//     * @param userSelectComboBox
//     */
//    public void handleSelectUser(JFXComboBox userSelectComboBox){
//
//    }
}
