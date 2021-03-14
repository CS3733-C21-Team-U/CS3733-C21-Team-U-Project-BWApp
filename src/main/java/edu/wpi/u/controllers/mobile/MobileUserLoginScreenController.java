package edu.wpi.u.controllers.mobile;


import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.exceptions.AccountNameNotFoundException;
import edu.wpi.u.exceptions.PasswordNotFoundException;
import edu.wpi.u.exceptions.PhoneNumberNotFoundException;
import edu.wpi.u.requests.SpecificRequest;
import io.netty.handler.codec.http.HttpHeaders;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.asynchttpclient.*;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileUserLoginScreenController {


    // TODO: Properly rename JFX artifacts
    @FXML
    public JFXTextField userNameTextField;
    @FXML
    public JFXPasswordField passWordField;
    @FXML
    public JFXButton loginButton;
    @FXML
    public JFXButton forgotPasswordButton;
    @FXML
    public JFXProgressBar progressBar;
    @FXML public JFXButton submitButton;
    //public JFXButton loginButton2;
    @FXML public Label errorLabel;
    @FXML public JFXButton debugLoginAdminButton;
    public JFXButton debugLoginGuestButton;

    //TODO
    //THIS PAGE WILL READ A USER DB VALUE BOOLEAN THAT SAYS WHETHER THEY HAVE COMPLETED A COVID SURVEY REQUEST
    // IF YES, THEY WILL BE TAKEN TO THE PATHFINDING PAGE
    //IF NO, THEY WILL BE TAKEN TO THE COVIDSURVEY PAGE

    public void initialize() throws IOException {

//        App.getPrimaryStage().setFullScreen(false);
//        App.getPrimaryStage().setWidth(412);
//        App.getPrimaryStage().setHeight(732);

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        userNameTextField.getValidators().add(validator);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                userNameTextField.validate();
            }
        });
        RequiredFieldValidator validator4 = new RequiredFieldValidator();
        validator4.setMessage("Username Invalid");
        userNameTextField.getValidators().add(validator4);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal){
                if (App.userService.checkUsername(passWordField.getText()).equals("")) {
                    userNameTextField.validate();
                }
            }
        });


        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("Password Required");
        passWordField.getValidators().add(validator2);
        passWordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passWordField.validate();
            }
        });

        RequiredFieldValidator validator5 = new RequiredFieldValidator();
        validator5.setMessage("Username Invalid");
        userNameTextField.getValidators().add(validator5);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal){
                if (App.userService.checkUsername(passWordField.getText()).equals("")) {
                    userNameTextField.validate();
                }
            }

        });


        RequiredFieldValidator validator3 = new RequiredFieldValidator();
        validator3.setMessage("Token Required");
        passWordField.getValidators().add(validator3);
        passWordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passWordField.validate();
            }
        });
    }



//    //Throws exceptions if username or password not found
//    public void handleForgotPassword() throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/ForgotPasswordScreen.fxml"));
//        App.getPrimaryStage().getScene().setRoot(root);
//    }
//
//    public void handleSubmit() throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/Enter2FATokenScreen.fxml"));
//        App.getPrimaryStage().getScene().setRoot(root);
//    }



    public void handleDebugLogin(ActionEvent actionEvent) throws IOException {
//        if (//user Covid Status approved = true){
//        App.userService.setUser("admin", "admin", "Employees");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/Mobile/MobileEnter2FATokenScreen.fxml"));
//        fxmlLoader.load();
//        fxmlLoader.getController();
//        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
       // }
        App.userService.setUser("admin", "admin", "Employees");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/controllers/mobile/MobileEmbenedGoogleMapsController.java"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }





//
//    public void handleDebugLoginGuest(ActionEvent actionEvent) throws IOException {
//        App.userService.setUser("patient", "patient", "Guests");
//        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        App.getPrimaryStage().getScene().setRoot(root);
//    }

//    public void handleBackButton(ActionEvent actionEvent) throws IOException {
//
//    }

    public void handleReturn(ActionEvent actionEvent) throws IOException {
        App.getPrimaryStage().setFullScreen(true);
        App.getPrimaryStage().setWidth(1920);
        App.getPrimaryStage().setHeight(1080);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleLonginWithNo2FA(ActionEvent actionEvent) {
        if (!App.userService.checkUsername(userNameTextField.getText()).equals("")) {
            if (!App.userService.checkPassword(passWordField.getText(),userNameTextField.getText()).equals("")) {
                App.userService.setUser(userNameTextField.getText(), passWordField.getText(), App.userService.checkUsername(userNameTextField.getText()));
                Parent root = null;
                if(searchRequests()){
                    try {
                        root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/mobile/MobilePathfindingBase.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/mobile/MobileEmbenedGoogleMaps.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                MobileContainerController.getInstance().getMobileRoot().getChildren().clear();
                MobileContainerController.getInstance().getMobileRoot().getChildren().add(root);
            }
        }
    }


    public void handleForgotPassword(ActionEvent actionEvent) {
    }

    public boolean searchRequests(){
        for(SpecificRequest r : App.requestService.getRequests()){
            if(r.getGenericRequest().getCreator() != null){
                if(r.getGenericRequest().getCreator().equals(App.userService.getActiveUser().getUserName()) &&
                        r.getType().equals("CovidSurvey") && r.getGenericRequest().isResolved()){
                    if(r.getSpecificData().get(0).equals("High")){
                        App.mapInteractionModel.highRisk = true;
                    }
                    return true;
                }
            }
        }
        return false;
    }
}




