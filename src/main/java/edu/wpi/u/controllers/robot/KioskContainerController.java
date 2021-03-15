package edu.wpi.u.controllers.robot;


import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class KioskContainerController {

    public static KioskContainerController mobile_instance = null;

    @FXML
    public AnchorPane kioskRoot;

    public KioskContainerController(){
        System.out.println("Mobile constructor");
        mobile_instance = this;
    }

    public static KioskContainerController getInstance(){
        if(mobile_instance == null){
            mobile_instance = new KioskContainerController();
        }
        return mobile_instance;
    }

    public AnchorPane getMobileRoot() {
        return kioskRoot;
    }

    public void initialize() throws IOException {

    }

    public void handleDebugButton(ActionEvent actionEvent) {
        App.getPrimaryStage().setFullScreen(false);
        App.getPrimaryStage().setWidth(1900);
        App.getPrimaryStage().setHeight(1000);
    }

    public void handleSkipToGuestButton(ActionEvent actionEvent) throws IOException {
        App.userService.setGuest("debug");
        App.userService.getActiveUser().setType(Role.GUEST);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleSkipToPatientButton(ActionEvent actionEvent) throws IOException {
        App.userService.setPatient("debug");
        App.userService.getActiveUser().setType(Role.PATIENT);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleSkipToAdminButton(ActionEvent actionEvent) throws IOException {
        App.userService.setEmployee("debug");
        App.userService.getActiveUser().setType(Role.ADMIN);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

}




